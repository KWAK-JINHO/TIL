# Spring Batch 기본 설정

Spring Batch V 5.2.1 기준

## 1. 의존성 추가(gradle)

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-batch'
    // JPA 사용 시
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

## 2. 배치 설정 구성

설정 방식 변화
Spring Batch 5.0부터 설정 방식이 변경되었다.

- StepBuilderFactory와 JobBuilderFactory가 Deprecated 처리
- 직접 JobRepository와 PlatformTransactionManager를 주입하는 방식으로 변경

배치 활성화 및 자동 설정
@SpringBootApplication 클래스에 @EnableBatchProcessing 어노테이션을 적용하면 다음 4개의 설정 클래스가 자동으로 실행된다.

1) BatchAutoConfiguration

- JobLauncherApplicationRunner 빈 생성
- 배치 작업(Job) 실행을 담당

2) SimpleBatchConfiguration

- 배치의 주요 객체들을 프록시 객체로 생성
- 이전 버전의 JobBuilderFactory, StepBuilderFactory 생성 (현재는 Deprecated)

3) BatchConfigurerConfiguration

- BasicBatchConfigurer: 프록시 객체의 실제 객체 생성
- JpaBatchConfigurer: JPA 관련 객체 생성

4) DefaultBatchConfiguration

- @EnableBatchProcessing 자동 포함
- JobRepository와 PlatformTransactionManager 포함
- 이 클래스를 상속하면 별도로 @EnableBatchProcessing 추가 불필요

## 3. 핵심 컴포넌트

### JobRepository

- 배치 작업의 메타데이터 저장 및 관리, DB 연동

### TransactionManager

- 배치작업의 트랜잭션관리, 데이터 일관성과 무결성 보장
- 청크 기반처리, 경계 설정과 관리

# Spring batch 아키텍쳐

![](https://velog.velcdn.com/images/letsmake/post/7592bdd6-aa83-43e9-a664-2e2e9b67fc54/image.png)

Job Object는 간단해 보일 수 있지만 많은 configuration option들을 알고 있어야하고, 작업을 실행할 수 있는 방법과 해당 실행중에 메타 데이터를 저장할 수 있는 방법에 대한 많은 옵션을
고려해야한다.

사용예시)

```java

@Bean
public Job footballJob(JobRepository jobRepository) {
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

📌 restartable = false로 설정하면 **JobInstance가 존재하더라도 재시작되지 않고 항상 새로운 JobInstance로 실행된다. 즉, 재시작을 support하지 않는다.**

## Intercepting Job Execution

```java
public interface JobExecutionListener {

    void beforeJob(JobExecution jobExecution);

    void afterJob(JobExecution jobExecution);
}
```

- JobListeners 를 추가 가능
- AfterJob 메소드는 작업의 성공 또는 실패에 관계없이 호출된다. JobExcution에서 성공, 실패 여부 얻을 수 있다.

## Validatoin

JobBuilder를 통해 validatoin configurtion을 지원한다.

## 간단한 Job - Step - Tasklet 구조 만들기 (스프링배치 간단 튜토리얼)

### 용어를 배워보자

- Job: 배치 작업의 최상위 단위, 여러개의 Step으로 구성됨, 배치 작업의 실행 상태를 관리하기 위해 **`JobRepository`**에 메타데이터를 저장하며, 재시작 가능성(restartability)을
  지원
- Step: Job안에서 실행되는 하나의 작업 단위, Chunk기반 또는 Tasklet기반으로 구현됨, 각 Step은 고유한 트랜잭션 경계를 가질 수 있다.
  Step 간에 데이터를 공유하려면 **`ExecutionContext`**를 사용할 수 있다.
- Tasklet: 단일작업을 수행하는 코드 블록. 데이터 읽기, 처리, 저장을 한번에 수행
- Chunk Processing: 데이터를 일정 개수(청크 단위)로 읽고 처리한 후, 한번의 트랜잭션으로 커밋하는 방식
- ItemReader: 외부 API 파일, DB 등에서 데이터를 읽어오는 역할을 한다. → RestTemplate, WebClient 등을 사용해 데이터를 받아오는 부분 담당
- ItemProcessor: 읽은 데이터를 필요한 형태로 가공하거나 변환하는 역할을 합니다. 예를 들어, DTO를 엔티티로 변환하는 로직을 포함할 수 있다.
- ItemWriter: 처리된 데이터를 DB에 저장하는 역할을 합니다. 여러 데이터를 한 번에 저장하는 `saveAll` 방식 등을 사용할 수 있다.
- **JobRepository, JobLauncher, JobParameters:** 배치 실행 이력을 관리하고, Job 실행 시 필요한 파라미터를 전달하며, Job을 시작하는 역할을 담당

### 순서

1. batch 실행계획이 담긴 JobConfiguration 클래스를 만든다.
    - **@Configuration** 어노테이션
    - 이후에 나올 Job과 Step들을 @Bean 어노테이션으로 등록할 수 있으니까
2. Job과 Step을 구성
3. Job을 정의하고 실행할 Step을 연결한다.
    - JobRepository를 사용하여 JobBuilder를 생성, start(step)을 통해 Step 등록
4. 실행하기

### 기본적인 Spring Batch 실행 구조 만들어보기

```java

@Bean
public Job job(JobRepository jobRepository, Step helloStep1, Step helloStep2) {
    return new JobBuilder("myJob", jobRepository)
            .start(helloStep1)
            .next(helloStep2)
            .build();
}

@Bean
public Step helloStep1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("helloStep1", jobRepository).tasklet(new Tasklet() {
        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            System.out.println("======================");
            System.out.println(" >> Hello Spring Batch!!");
            System.out.println("======================");

            return RepeatStatus.FINISHED;
        }
    }, platformTransactionManager).build();
}

@Bean
public Step helloStep2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("helloStep2", jobRepository).tasklet((contribution, chunkContext) -> {
        System.out.println("======================");
        System.out.println(" >>step2 was executed");
        System.out.println("======================");

        return RepeatStatus.FINISHED;
    }, platformTransactionManager).build();
}
```

- Job과 Step을 구현
- @Bean으로 스프링 빈으로 등록
- JobBuilder를 통해 myJob이라는 이름의 배치 작업을 생성

```java

@Component
public class BatchJobLauncher implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(String... args) throws Exception {
        JobExecution execution = jobLauncher.run(job, new JobParameters());
        System.out.println("Exit status :: " + execution.getStatus());
    }
}
```

- CommandLineRunner는 Spring Batch에서 배치작업을 실행하는 역할을 하는 클래스중 하나.
- Spring Boot가 시작될 때 CommandLineRunner를 구현한 클래스가 자동 실행되면서 배치 작업도 자동 실행된다.

## 실행하기

Sprgin boot 3.x 부터는 EnableBatchProcessing또는 DefaultBatchConfiguration를 사용한 배치 작업 자실행이 되지 않는다. 때문에 아래의 두가지 방법으로 실행 가능

1. JobRepository 직접 주입

- 필요한 경우 직접 Job을 실행하는 방식으로 변경할 수 있다.

2. Runner를 구현(**CommandLineRunner 또는 ApplicationRunner**)하여 Job을 직접 실행

- **배치 작업 명시적으로 실행**
- 필요할 때만 실행되도록 제어 가능
- 실행 시마다 JobParamerters를 다르게 설정할 수 있다.
    - **CommandLineRunner**
        - application.properties에서 --key=value 형태로 전달된 인자를 그대로 사용할 수 있음
    - **ApplicationRunner**
        - 메서드의 파라미터로 받는 ApplicationArguments는 옵션값을 쉽게 추출할 수 있게 한다.

## ItemWriter 활용과 최적화

### JpaItemWriter

JpaItemWriter는 변경 감지(Dirty Checking)를 활용하여 변경된 내용만 DB에 업데이트하는 기능을 제공한다.

- 엔티티 상태 변경이 감지되면 자동으로 업데이트
- 변경이 없는 엔티티는 불필요한 업데이트 쿼리를 실행하지 않음
- 사용자가 직접 변경 감지 로직을 구현할 필요가 없음

```java
public class JpaItemWriter<T> implements ItemWriter<T>, InitializingBean {
    // 구현 내용
}
```

이 클래스는 제네릭 타입 T를 기반으로 설계되어 있어 **하나의 엔티티 타입만 처리**하도록 되어 있다.

### CompositeItemWriter

여러 엔티티 타입을 처리해야 할 경우 CompositeItemWriter를 사용할 수 있다.

- 여러 ItemWriter를 하나로 묶어 순차적으로 실행
- 각 ItemWriter가 서로 다른 엔티티 타입을 처리할 수 있음

```java

@Bean
public CompositeItemWriter<ProcessedItem> compositeItemWriter() {
    CompositeItemWriter<ProcessedItem> writer = new CompositeItemWriter<>();
    writer.setDelegates(Arrays.asList(
            userWriter(),
            orderWriter(),
            productWriter()
    ));
    return writer;
}

@Bean
public JpaItemWriter<User> userWriter() {
    JpaItemWriter<User> writer = new JpaItemWriter<>();
    writer.setEntityManagerFactory(entityManagerFactory);
    return writer;
}

// 다른 Writer들도 유사하게 구현
```

### ItemStream 인터페이스

배치 작업 실패 시 중단된 지점부터 재시작하기 위한 상태 관리 메커니즘을 제공한다.

- BATCH_STEP_EXECUTION_CONTEXT 테이블에 실행 상태를 저장
- 실패 시 중단된 위치에서 다시 실행 가능

ItemStream 인터페이스는 다음 세 가지 메서드를 정의한다:

```java
public interface ItemStream {
    void open(ExecutionContext executionContext) throws ItemStreamException;

    void update(ExecutionContext executionContext) throws ItemStreamException;

    void close() throws ItemStreamException;
}
```

- open(): 스트림을 초기화하고 이전에 저장된 상태가 있으면 복원
- update(): 현재 상태를 ExecutionContext에 저장
- close(): 스트림 리소스를 정리

### 대량 Insert 최적화

INSERT 문을 개별적으로 실행하면 네트워크 왕복 시간(round-trip time)이 성능에 영향을 미친다. 이를 개선하기 위한 방법:

1. JDBC 배치 처리 활용

```
PreparedStatement ps = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");

// 여러 항목을 배치로 추가
for (User user : users) {
   ps.setLong(1, user.getId());
   ps.setString(2, user.getName());
   ps.setString(3, user.getEmail());
   ps.addBatch();  // 배치에 추가
}

// 한 번에 실행
ps.executeBatch();
```

2. Hibernate/JPA 배치 설정

```properties
# application.properties
spring.jpa.properties.hibernate.jdbc.batch_size=50
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
```

이렇게 설정하면 Hibernate가 자동으로 지정된 크기만큼 INSERT 문을 그룹화하여 한 번에 실행하므로 네트워크 왕복 횟수를 줄이고 성능을 향상시킬 수 있다.