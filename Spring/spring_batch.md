## Batch 사용 기본설정

1. Batch 의존관계 설정
2. @SpringBootApplicatoin클래스에 @EnableBatchProcessing 적용 시

   아래의 4개 설정 클래스 자동 실행으로 배치의 모든 초기화 및 실행 구성 이루어짐
   → `DefaultBatchConfiguration` 상속시 @EnableBatchProcessing 자동 포함

```
atchAutoConfiguration: Job을 실행하는 JobLauncherApplicationRunner 빈을 생성

SimpleBatchConfiguration: JobBuilderFactory, StepBuilderFactory를 생성하고 
배치의 주요 객체들을 프록시 객체로 생성한다.
		
BatchConfigurerConfiguration(BasicBatchConfigurer, JpaBatchConfigurer):
	- BasicBatchConfigurer: 위에서 생성한 프록시 객체의 실제 객체를 생성
	- JpaBatchConfigurer: JPA와 관련한 객체를 생성
```

StepBuilderFactory와 JobBuilderFactory가 deprecated 처리되고 Spring Batch 5.0 부터 직접JobRepositor를 주입하여 직접 구현하게 바뀌었다. (
`DefaultBatchConfiguration`에 JobRepsitory와 PlatfromTransacctionManager가 포함되어 있다.)

JobRepository

- 배치 작업의 메타데이터 저장 및 관리, DB 연동

TransactionManager

- 배치작업의 트랜잭션관리, 데이터 일관성과 무결성 보장
- 청크 기반처리, 경계 설정과 관리

## ##/ 아키텍쳐

![image.png](attachment:01da921e-6720-4986-ace6-ccd0f304398b:image.png)

Job Object는 간단해 보일 수 있지만 많은 configuration option들을 알고 있어야하고, 작업을 실행할 수 있는 방법과 해당 실행중에 메타 데이터를 저장할 수 있는 방법에 대한 많은 옵션을
고려해야한다.

사용예시)

```jsx
@Bean
public
Job
footballJob(JobRepository
jobRepository
)
{
    return new JobBuilder("footballJob", jobRepository)
        .start(playerLoad())
        .next(gameLoad())
        .next(playerSummarization())
        .build();
}
```

- 배치작업을 실행하면 JobInstance가 생성
- 만약 이미 존재하는 JobInstance를 실행하려고 한다면, 재시작으로 간주
- 기존의 JobInstance를 재시작할 수 없는 상태라면 새로운 JobInstance를 만들어야 한다.

  → restartable = false로 설정하면 **JobInstance가 존재하더라도 재시작되지 않고, 항상 새로운 JobInstance로 실행 = 재시작을 support하지 않는다.**

## Intercepting Job Execution

```jsx
public
interface
JobExecutionListener
{

    void beforeJob(JobExecution
    jobExecution
)
    ;

    void afterJob(JobExecution
    jobExecution
)
    ;
}
```

- JobListeners 를 추가 가능
- AfterJob 메소드는 작업의 성공 또는 실패에 관계없이 호출된다. JobExcution에서 성공, 실패 여부 얻을 수 있다.

## Validatoin

JobBuilder를 통해 validatoin configurtion을 지원한다.

## ##/ 간단한 Job - Step - Tasklet 구조 만들기

Job: 배치 작업의 최상위 단위, 여러개의 Step으로 구성됨

Step: Job안에서 실행되는 하나의 작업 단위, Chunk기반 또는 Tasklet기반으로 구현됨

Tasklet: 단일작업을 수행하는 코드 블록. 데이터 읽기, 처리, 저장을 한번에 수행

1. batch 실행계획이 담긴 JobConfiguration 클래스를 만든다.
    - **@Configuration**어노테이션
    - 이후에 나올 Job과 Step들을 @Bean 어노테이션으로 등록할 수 있으니까
2. Job과 Step을 구성
3. Job을 정의하고 실행할 Step을 연결한다.
    - JobRepository를 사용하여 JobBuilder를 생성, start(step)을 통해 Step 등록
4. 실행하기

## ##/ 실행하기

Sprgin boot 3.x 부터는 EnableBatchProcessing또는 DefaultBatchConfiguration를 사용한 배치 작업 자실행이 되지 않는다. 때문에 아래의 두가지 방법으로 실행 가능

1. JobRepository 직접 주입

- 필요한 경우 직접 Job을 실행하는 방식으로 변경할 수 있다.

1. Runner를 구현(**CommandLineRunner 또는 ApplicationRunne**)하여 Job을 직접 실행

- **배치 작업 명시적으로 실행**
- 필요할 때만 실행되도록 제어 가능
- 실행 시마다 JobParamerters를 다르게 설정할 수 있다.
    - **CommandLineRunner**
        - application.properties에서 --key=value 형태로 전달된 인자를 그대로 사용할 수 있음
    - **ApplicationRunne**
        - 메서드의 파라미터로 받는 ApplicationArguments는 옵션값을 쉽게 추출할 수 있게 한다.