# JVM 메모리 구조

- jvm은 Java Virtual Machine 의 약자로 자바 가상 머신이라고 불린다.
- 자바와 운영체제 사이에서 중개자 역할을 수행.
- 가비지 컬렉터를 사용한 메모리 관리를 자동으로 수행.

### 자바 프로그램의 실행 단계

1. Java Code(.java)
2. Javac Compiler
3. Byte Code(.class)
4. JVM
5. 운영체제

- Java 소스 코드(.java)가 Java 컴파일러(javac)에 의해 바이트코드(.class)로 컴파일
- 이 바이트코드는 JVM에 의해 읽히고 해석.
- JVM은 바이트코드를 실행하며, 이 과정에서 필요한 시스템 리소스에 접근하기 위해 운영체제와 상호작용한다.

### JVM 메모리 구조

- 구조는 크게 보면, Garbage Collector, Execution Engine, Class Loader, Runtime Data Area로, 4가지가 있다.

1. Class Loader
    - 자바 클래스 파일(.class)을 메모리에 로드하고 링크하는 역할

2. 실행 엔진 (Execution Engine)
    - 인터프리터 (Interpreter): 바이트코드를 한 줄씩 해석하고 실행

3. Garbage Collector
    - 힙 메모리 영역에 생성된 객체들 중에서 참조되지 않은 객체들을 탐색 후 제거하는 역할

4. Runtime Data Area
    - JVM의 메모리 영역으로 자바 애플리케이션을 실행할 때 사용되는 데이터들을 적재하는 영역
    - 이 영역은 크게 Method Area, Heap Area, Stack Area, PC Register, Native Method Stack로 나눌 수 있다.

    1. Method area: 모든 쓰레드가 공유하는 메모리 영역. 메소드 영역은 클래스, 인터페이스, 메소드, 필드, Static 변수 등의 바이트 코드를 보관
    2. Heap area: 모든 쓰레드가 공유, new 키워드로 생성된 객체와 배열이 생성되는 영역. 또한, 메소드 영역에 로드된 클래스만
       생성이 가능하고 Garbage Collector가 참조되지 않는 메모리를 확인하고 제거하는 영역
    3. Stack area: 메서드 호출 시마다 각각의 스택 프레임(그 메서드만을 위한 공간)이 생성. 그리고 메서드 안에서 사용되는 값들을 저장하고,
       호출된 메서드의 매개변수, 지역변수, 리턴 값 및 연산 시 일어나는 값들을 임시로 저장. 메서드 수행이 끝나면 프레임별로 삭제
    4. PC Register: 쓰레드가 시작될 때 생성되며, 생성될 때마다 생성되는 공간으로 쓰레드마다 하나씩 존재한다..
       쓰레드가 어떤 부분을 무슨 명령으로 실행해야할 지에 대한 기록을 하는 부분, 현재 수행중인 JVM 명령의 주소를 갖는다.
    5. Native method stack: 자바 외 언어로 작성된 네이티브 코드를 위한 메모리 영역