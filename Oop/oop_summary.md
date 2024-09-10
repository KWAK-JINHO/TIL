# OOP 주요개념

### 1. 추상화
- 복잡한 시스템에서 핵심적인 부분만을 모델링하여 불필요한 세부사항은 숨기는 기법

### 2. 상속
- 부모클랫의 속성과 메소드를 새로운 클래스가 물려받아 재사용하는 기법

### 3. 다형성
- OOP에서 하나의 인터페이스를 통해 여러 다른 타입의 객체를 다룰 수 있는 능력.
- 보통 두가지 형태로 나타난다.
    1. overloading : 컴파일 시간 다형성을 구현하는 방식으로 같은 이름 메서드가 여러개 정의된다. (매개변수의 타입, 개수, 또는 순서가 달라야 한다.)
    2. overriding : 상속 관계에 있는 부모 클래스의 메소드를 자식 클래스에서 재정의하여 사용하는 방식
       oop는 이 두가지를 모두 사용할 수 있으며 overriding이 다른 프로그래밍 언어와 oop를 구분짓는 핵심 적인 특징중 하나이다.

### 4. 캡슐화
- 객체의 내부 상태(속성)와 행동(메소드)을 하나로 묶고 외부에 대한 직접적 접근을 제한하는 기법.
- 데이터를 보호하고 객체간의 상호작용을 제어할 수 있다.


# SOLID 원칙

### 단일 책임 원칙 SRP; Single Responsibility Principle
- 하나의 클래스는 하나의 책임만 가진다.(하나의 클래스에 여러가지 기능을 구현하면 안된다.)

### 개방 폐쇄 원칙 OCP; Open/Closed Principle
- 확장에는 열려있어야하고, 변경에는 닫혀있어야 한다.
- 클래스를 수정하지말고 확장해서 써라
- 잘못된 예시
```commandline
public void generateReport(String type) {
  if (type.equals("PDF")) {
    Systemo.out.println("Generatring PDF ...")
  } else if (type.equals("HTML")) {
    Systemo.out.println("Generatring HTML ...")
}
```
만약 다른 문서의 형태를 추가해야하면 generateReport 클래스를 수정,추가 해야하므로 다른 코드에 영향이 갈 수 있다.
- OCP 적용 예시
```commandline
public interface ReportGenerator {
    void generate();
}

// PDF 리포트 생성 클래스
public class PdfReportGenerator implements ReportGenerator {
    @Override
    public void generate() {
        System.out.println("Generating PDF...");
    }
}

// HTML 리포트 생성 클래스
public class HtmlReportGenerator implements ReportGenerator {
    @Override
    public void generate() {
        System.out.println("Generating HTML...");
    }
}
```
generate란 메소드를 정의한 인터페이스를 만들어서 메소드의 이름과 형태만 정의해둔다. 이후에 해당 인터페이스를 적용한 클래스들이
이 메서드를 override 해서 각자의 방식으로 문서를 생성하는 책임을 수행. 확장이 자유로워짐
  
### 리스코프 치환 원칙 LSP; Liskov's Subsitution Principle
- 자식클래스는 언제나 부모클래스를 대체 가능해야 한다.

### 인터페이스 분리 원칙 ISP; Interface Segregation Principle
- 클래스는 자신이 사용하지 않을 메소드를 구현하도록 강요받지 말아야 한다.

### 의존성 역전 원칙 DIP; Dependency Inversion Principle
- 고수준 모듈이 저수준 모듈에 의존해서는 안 된다.
- 잘못된 예시
```commandline
// 저수준 모듈: Fan 클래스 (구체적인 구현을 담당)
public class Fan {
    public void spin() {
        System.out.println("Fan is spinning");
    }
}

// 고수준 모듈: Room 클래스 (팬을 사용하는 로직을 담당)
public class Room {
    private Fan fan;

    public Room() {
        this.fan = new Fan();  // Fan 클래스에 직접 의존
    }

    public void coolRoom() {
        fan.spin();  // Fan 클래스의 구체적인 메서드 사용
    }
}
```
- DIP 적용 예시
```commandline
// 추상화된 인터페이스
public interface Device {
    void turnOn();
}

// 저수준
public class Fan implements Device {
    @Override
    public void turnOn() {
        System.out.println("Fan is spinning");
    }
}

public class AirConditioner implements Device {
    @Override
    public void turnOn() {
        System.out.println("AirConditioner is cooling the room");
    }
}

// 고수준: Room 클래스는 Device 인터페이스에 의존
public class Room {
    private Device device;

    // 의존성 주입: 인터페이스를 통해 저수준 모듈을 주입받음
    public Room(Device device) {
        this.device = device;
    }

    public void coolRoom() {
        device.turnOn();  // Device 인터페이스의 메서드를 호출
    }
}
```
저수준 모듈인 Fan과 AirConditioner는 공통 인터페이스인 Device를 구현하고, 각자의 turnOn() 메서드를 정의
고수준 모듈인 Room 클래스는 구체적인 클래스(Fan, AirConditioner)에 의존하지 않고, 추상화된 Device 인터페이스에 의존
의존성 주입(생성자 주입)을 통해, Room 클래스는 어떤 Device 구현체가 전달되든 동적으로 사용할 수 있게 됨.