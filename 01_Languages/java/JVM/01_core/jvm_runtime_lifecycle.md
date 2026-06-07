# JVM Runtime Lifecycle

## 1. 프로세스 시작
   - 사용자가 `java` 명령을 실행하면, OS가 JVM 프로세스를 생성한다.
   - 가상머신 구동을 위한 초기 인프라 엔진이 활성화된다.

## 2. Runtime Data Area 초기화
   - JVM 엔진이 OS로부터 물리적 메모리를 할당받아 가상 주소 공간을 구획한다.
   - 공유영역인 Method Area와 Heap 공간의 물리적 크기를 정한다.
   - 가상머신의 메인 동작을 담당하는 Main Thread를 생성

## 3. 클래스 로딩 진입
   - Class Loader가 어플리케이션의 메인 클래스(`.class`) 를 찾아낸다.
   - 클래스의 Linking 및 Initialization

## 4. 프로그램 실행
   - Execution Engine이 바이트코드를 Interpreter 방식으로 한 줄씩 해석하거나, JIT Compiler를 통해 기계어로 변환하여 실행

## 5. 런타임 확장
   - 비즈니스 로직 중 사용자 요청이나 멀티스레드 코드가 실행되면 새로운 자바 스레드가 생성됩니다.
   - 이때 JVM은 OS에 System Call을 보내 커널 스레드와 1:1 매핑을 수행하며, 새로 생성된 스레드만을 위한 독립 영역(Stack, PC Register, Native Method Stack)을 즉시 동적으로 할당
   - Heap과 Metaspace 영역도 데이터 증가에 따라 설정된 최대치(`-Xmx` 등)까지 유연하게 확장된다.

## 6. 자동 메모리 관리
   - Execution Engine 내부의 GC 스레드가 주기적으로 Heap 공간을 스캔하여 참조를 잃은(Unreachable) 객체들을 자동 정리합니다.

## 7. 프로세스 종료
   - main 스레드 및 모든 non-daemon 스레드가 종료되면 JVM이 종료된다.
   - JVM이 사용하던 Runtime Data Area가 OS에 반환된다.