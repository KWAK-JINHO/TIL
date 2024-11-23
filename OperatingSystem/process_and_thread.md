# process_vs_thread

## 프로세스와 스레드의 기본 개념

### 프로세스

- 운영체제로부터 자원을 할당받은 "작업의 단위"
- 프로그램이 메모리에 적재되어 실행되고 있는 상태

### 스레드

- 프로세스가 할당받은 자원을 이용하는 "실행 흐름의 단위"
- 하나의 프로세스는 최소 하나의 메인 스레드를 가짐

| 프로그램               | 프로세스                      |
|--------------------|---------------------------|
| 실행 가능한 파일 형태의 코드   | 실행 중인 프로그램                |
| 저장 장치에 존재하는 정적인 상태 | 메모리에 적재되어 CPU 자원을 할당받은 상태 |

## 프로세스와 스레드의 메모리 구조

### 프로세스의 메모리 영역

1. 코드 영역(Code/Text): 실행 코드가 기계어 형태로 저장
2. 데이터 영역: 전역 변수, static 변수 등 저장
3. 스택 영역: 함수 호출 관련 정보 저장
4. 힙 영역: 동적 할당되는 메모리 영역

## 동시성과 병렬성

### 동시성이 필요한 이유

1. 하드웨어적 한계: CPU 발열 문제로 인한 클럭 속도 제한
2. 논리적 효율: 작업을 잘게 나눠 번갈아 처리하는 방식 채택

### Context Switching

- 실행 중인 프로세스/스레드를 전환하는 작업
- PCB/TCB를 통해 상태 정보 저장 및 복원
- **프로세스 생성 시 PCB도 커널 영역의 데이터 영역에 함께 생성**
- 부모 프로세스가 wait() 호출하거나 kill 당하면 삭제

### 문맥교환 시 비용

- 커널 모드 전환 시 시스템 콜 오버헤드
- CPU 레지스터 상태 저장/복원
- 메모리 맵 전환 (캐시 무효화 등)

## 프로세스 스케줄링

### FCFS (First Come First Served)

장점:

- 구현 단순
- 기아상태 없음
  단점:
- 평균 대기시간 김
- Convoy Effect 발생

### SJF (Shortest Job First)

장점:

- 평균 대기시간 최소
- 처리량 증가
  단점:
- 긴 프로세스의 기아상태 발생
- CPU 버스트 시간 예측 어려움

### Priority Scheduling

장점:

- 시스템 요구사항 반영 용이
  단점:
- 우선순위 낮은 프로세스 기아상태
- 우선순위 설정 오버헤드

### RR (Round Robin)

장점:

- 동일한 CPU 시간 분배
- 응답시간 향상
- 기아상태 없음
  단점:
- 문맥교환 오버헤드 큼
- 우선순위 높은 프로세스의 처리 지연

### Multilevel Queue

장점:

- 다양한 프로세스 특성 반영 가능
- 우선순위와 라운드로빈의 장점 결합
  단점:
- 큐 간 스케줄링 오버헤드
- 낮은 우선순위 큐의 기아현상

## 프로세스와 스레드의 상태

### 프로세스 상태

- 생성 → 준비 → 실행 → 대기 → 종료

실행 → 준비 전환 (인터럽트):

- 할당시간 종료
- 우선순위 기반 선점
- 시스템 인터럽트

실행 → 대기 전환 (I/O):

- 읽기/쓰기 발생
- DB 쿼리 요청 등

### 트랩 vs 인터럽트

- 트랩: 의도적, 시스템 콜
- 인터럽트: 비동기적, 외부 이벤트, 하드웨어, 타이머 등

## 스레드 동기화와 TCB

### TCB (Thread Control Block)

- PCB 내부에 존재
- 각 스레드의 정보 관리
- 스레드 간 동기화 정보 관리 (Mutual Exclusion, Semaphore 등)

## 프로세스 vs 스레드 문맥교환 비교

1. 가벼움: 스레드 승 (TCB가 더 가벼움)
2. 캐시 메모리: 스레드 승 (프로세스는 캐시 초기화 필요)
3. 자원 동기화: 무승부
    - 스레드: Race Condition 발생 가능
    - 프로세스: IPC 사용 시 Race Condition 발생 가능