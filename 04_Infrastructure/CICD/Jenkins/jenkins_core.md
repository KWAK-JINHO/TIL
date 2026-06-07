# Jenkins
Jenkins는 CI/CD 환경을 구축하기 위한 오픈소스 도구이다.

![스크린샷 2026-04-29 오전 1.09.01.png](../../../../../../../../var/folders/0m/x0wj6bbj17d9rm9tcxg5qjhm0000gn/T/TemporaryItems/NSIRD_screencaptureui_RhMnd8/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-04-29%20%EC%98%A4%EC%A0%84%201.09.01.png)

# Jenkins pipeline ("코드로 관리하는 인프라")
Jenkins pipeline은 Jenkins에 continuous delivery(CD) pipeline을 구현하고 통합하는 것을 지원하는 플로그인 모음. 

## Jenkins pipeline 구성요소
- pipeline: 파이프라인의 시작과 끝을 알리는 전체 블록
- agent: 빌드가 실행될 환경을 지정
- stages: 여러 개의 stage를 하나로 묶는 논리적인 단위
- stage: 빌드, 테스트, 배포 등 큰 작업의 단계를 정의
- steps: stage안에서 실제로 실행될 명렁(sh, echo 등)의 집합. 구체적 행동
- post: 모든 과정이 끝난 후 실행할 후속 작업

## 
Jenkins pipeline에는 Declarative와 scripted의 두가지 주요 스타일 존재.
보통 유지보수성과 일관성 측면에서 Jenkinsfile + Declarative 사용을 권장, 필요할 때 script 로 groovy를 섞는다.

## Jenkins pipeline의 흐름

checkout -> build -> test -> artifact -> deploy -> notify

이걸 DSL로 표현하면
```
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }

    post {
        failure {
            echo '빌드 실패'
        }
    }
}
```
- stage로 흐름을 나눈다
- 실행은 steps에 넣는다
- 실패 처리는 post에 둔다
의 구조

# CPS(Continuation Passing Style)
Jenkins Pipeline은 실행 중 중단/재시작이 가능해야 한다.  
그래서 Jenkinsfile 코드를 바로 실행하지 않고, 중간 상태를 저장할 수 있는 형태로 변환해서 실행한다.
덕분에 Jenkins 재시작 후에도 Pipeline이 이어서 실행될 수 있다.

## @NonCPS
특정 메서드를 CPS 변환 대상에서 제외하는 어노테이션.  

사용하는 이유
- 직렬화(serialization)가 불가능한 경우
- 복잡한 알고리즘이나 루프 처리를 더 빠르게 수행하고 싶을 때

주의할 점
- @NonCPS 메서드 안에서는 Jenkins step 호출불가. Step(sh, git, timeout 등)은 호출되는 순간 상태를 저장하려고 하기 때문에 충돌이 발생한다.

# serialization
Jenkins Pipeline은 중간 상태를 저장해야 하므로 실행 중인 객체를 직렬화할 수 있어야 한다.  
Pipeline 실행 중 Jenkins가 재시작되거나 stage 사이에서 대기할 때 현재 상태를 저장한다.

# Shared Library
Jenkinsfile의 반복되는 build, test, deploy, notify 로직(pipeline)을 라이브러리로 빼서 관리할 수 있다.

# Sandbox
Groovy 코드가 실행되기 전, Jenkins가 허용한 명령어인지 검사하는 보안필터

---
참고 링크