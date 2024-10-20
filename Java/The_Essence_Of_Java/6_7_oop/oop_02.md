# 객체지향 프로그래밍2

- 그룹화: 그룹과 그룹의 관계
- 집합의 그룹핑
    1. 클래스그룹
    2. 멤버그룹

### 상속

- 자식이 변경되는건 부모에게 영향이 없다.
- 자식과 부모간 (부모만큼의) 교집합이 있다. -> 교집합만으로 다형성을 다룰 수 있다.

#### 관계

1. 상속: 제약이 많아서 잘 안씀
2. 포함: 잘 모르겠으면 대부분 포함이다.
    - 집합: 다 때려박는거
    - 구성: 개별개별을 모아서 만드는거

### 참조변수 super(≈this)

- this는 lv와 iv 를 구별하는데 사용
- super는 부모멤버와 자식멤버을 구별하는데 사용한다. (부모와 자식이 같은이름의 변수를 사용할때 부모는 super. 자식은 this. 으로 구별)
- 같은변수가 없을 때도 super this둘다 사용가능. 둘다 같은 변수를 가르킨다.
- 객체 자신을 가리키는 참조변수. 인스턴스 메서드(생성자)내에만 존재

### super()

- 부모의 생성자호출할 때 사용
- 부모의 멤보는 부모의 생성자를 호출해서 초기화.
- 자식은 자식이 선언한 것만 초기화 하는게 좋다.
  예시)

```commandline
class Point {
  int x, y;
  
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

class Point3D extends Point {
  super(x, y); // 부모의 생성자 Point(int x, int y)를 호출
  this.z = z;
}
```

#### super() 조건

- 생성자의 첫 줄에 반드시 다른 생성자를 호출(생성자 체이닝). 그렇지 않으면 컴파일러가 생성자의 첫 줄에 super();를 삽입

### 제어자(modifier)

- 하나의 대상에 여러 제어자를 사용가능(접근 제어자는 하나만)

#### 제어자의 종류

- 접근 제어자
    - public: 접근 제한 없다. 하나의 파일안에 한개만 사용 가능. 파일명과 이름 같아야한다.
    - protected: 같은 패키지, 다른 패키지의 자식클래스에서 접근 가능
    - default: 같은 패키지 내에서만 접근 가능
    - private: 같은 클래스 내에서만 접근 가능
- 그외
    - static
        - 멤버변수: 모든 인스턴스에 공통적으로 사용되는 클래스 변수가 된다. 클래스 변수는 인스턴스를 생성하지 않고도 사용 가능하며 클래스가 메모리에 로드될 때 생성됨.
        - 메서드: static 메서드 에서는 iv, instatnce 메서드 사용불가
    - fianl
        - 클래스: 변경될 수 없는 클래스, 확장될 수 없는 클래스가 된다.
        - 메서드: 변경될 수 없는 메서드. 오버라이딩 사용 불가
        - 멤버변수, 지역변수: 값을 변경할 수 없는 상수
    - abstract
        - 클래스: 구현부가 없는 메서드(선언부만 존재), 인스턴스 생성 불가. 추상클래스를 상속받아서 완전한 클래스를 만든후에 객체생성 가능.
        - 메서드: 선언부만 작성

### 캡슐화

- 접근 제어자를 사용하는 이유
    - 외부로부터 데이터를 보호하기 위해서
    - 외부에는 불필요한 내용을 감추기 위해서
- 외부로부터의 직접접근을 막고 메서드를 통한 간접접근을 허용시킨다.

### 싱글톤 패턴

1. 객체를 미리생성
2. 생성된 객체가 캐시역할: 재사용 -> 효율적이다
    - 웹쪽에서는 캐시가 성능에 영향이 크다
3. 배열
    - equals로 비교

### 다형성

- 부모 타입 참조 변수로 자식 타입의 객체를 다루는 (타입이 불일치해도 괜찮다!)
  예) ```Tv t = new smartTv();```
- Tv: 참조변수 타입
- t: 생성된 객체를 참조하는 변수명
- new: 새로운 객체를 힙메모리에 생성한다는 키워드
- SmartTv(): 실제로 생성되는 객체의 타입

- 자식 타입의 참조변수로 부모 타입의 객체를 가리킬 수 없다.

### 참조변수의 형변환

- 타입을 일치시켜 주는 것만 한다.
- 사용할 수 있는 멤버의 갯수를 조절하기 위해 한다.(부모자식 관계일 경우에만 가능)
- 리모콘을 변경함으로써 사용할 수 있는 멤버의 개수를 조절하는게 참조변수의 형변환
- 참조변수가 가리키는 실제 객체가 중요하다.(실제 객체의 멤버 개수가 n개 이면 그 n개 안에서 놀아야함 자손에서 조상으로 바꾸는건 멤버 개수가 줄어드니까 상관없는데
  조상에서 자손으로 바꾸려면 에러가 뜰 수도 있음)
- 컴파일러는 형변환만 맞으면 에러를 안띄운다. 하지만 실제 객체가 해당 멤버를 가지고 있지 않으면 에러가 뜬다.

#### instanceof

- 형변환하기전에 instanceof 연산자로 반드시 확인하고 해야함
  예)

```commandline
void doWork(car c) {
  if(c instanceof FireEngine) {    // 1.형변환 가능한지 확인 
    FireEngine fe = (FireEngine)c; // 2.형변환
    fe.water();
  } else if(c instanceof Ambul) {
    Ambul a = (Ambul)c;
  }
}
```

### 매개변수의 다형성

- 다형성에 대해서 정리해보자
    - Tv t = new SmartTv();
    - 참조변수의 형변환 -> 리모콘 바꾸기: 사용가능한 멤버갯수 조절
    - instanceof연산자: 형변환 가능여부 확인

#### 다형성의 장점

1. 다형적 매개변수
    - 참조형 매개변수는 메서드 호출시 자신과 같은 타입 또는 자손타입의 인스턴스 넘겨주기 가능
2. 하나의 배열로 여러종류 객체를 저장할 수 있다.
    - 조상타입의 배열에 자손들의 객체를 담을 수 있다.
    - 예)
    ```commandline
    class Buyer {  // 물건사는 사람
      int money = 1000; // 소유금액
      int bonusPoint = 0; // 보너스점수
      
      Product[] cart = new Product[10]; // 구입한 물건을 담을 배열(장바구니; 여러종류의 장바구니를 담을 수 있어졌다)
      // 위 배열 생성을 안했을때는 각각의 종류마다 추가를 해줘야한다. 매우 불필요한 작업
      
      int i = 0;
      
      void buy (Product p) {
        if(money < p.price) {
          system.out.println("잔액부족");
          return;
        }
        
        money -= p.price;
        bonuspoint += p.bonusPoint;
        cart[i++] = p;  // 카트에 저장
      }
    }
    // 하나의 배열에 저장하여 새로운 제품이 추가 되어도 메서드 수정이 필요 없어짐
    ```

### 추상 클래스

- 미완성 클래스(미완성 메서드(=추상 메서드)를 가지고 있는 클래스), 키워드는 abstract
- 상속을 통해 추상 메서드를 완성해야 인스턴스 생성가능
- 기존의 클래스들의 공통 부분을 뽑아서 만들기도 함.

- 추상화: 구체화의 반대말(공통부분을 뽑아낸다) -> 재사용성 향상
- int aa(int a, int b) {return a+b;}
- T add(T a, T b)

#### 추상클래스의 작성

- 추상화된 코드는 유연함
- 참조변수의 타입과 클래스가 일치하면 명확

### 인터페이스

- 추상 메서드(인터페이스의 핵심) 집합이라고 할 수 있다. -> 구현된 것이 전혀 없는 설계도 = 모든 멤버가 public이다.
- 메서드 abstract, public 생략 가능. 인터페이스 메서드는 항상 public abstract 이기 때문.
- 상수도 항상 예외없이 public static final
- 인터페이스의 조상은 인터페이스만 가능하다.(클래스처럼 오브젝트가 최고 조상이 아님)
- 다중 상속이 가능하다. (자바는 단일상속임 충돌문제때문에) 몸통이 없기때문에 조상이 누가와도 충돌문제가 없다.

- 인터페이스를 가능한 많이 늘리자 -> 인터페이스는 변경에 유리(메서드로만 이루어져있기 때문)
- 여러 팀이서 개발할때 표준이 되어줌

#### 추상클래스 vs 인터페이스

- 인터페이스를 구현(몸통 만들기)한다는건 추상클래스를 완성한다는 것과 동일
- 둘다 추상 메서드를 가지고 있다.
- iv를 가질 수 있냐 없냐로 나뉘는게 가장크다. 추상클래스는 일반적인 클래스들이 가질 수 있는 요소들을 다 가질 수 있지만 인터페이스는
  추상 메서드로만 가지고 있다.(실제로는 상수, static메서드, 디폴트 메서드도 가지지만 이게 핵심이 아니다!)

### 인터페이스를 이용한 다형성

- 인터페이스도 구현클래스의 부모이다.
- 인터페이스 타입의 매개변수는 인터페이스를 구현한 클래스의 객체만 가능
- 인터페이스를 메서드의 리턴타입으로 지정할 수 있다. -> 안맞으면 형변환
  (이 인터페이스를 구현한 것을 반환하겠다는 뜻)
- 객체에서 다른 객체를 참조할 때 메서드를 거쳐서 간접적으로 접근(=캡슐화)(제일 바깥 껍데기가 인터페이스이다)

#### 인터페이스의 장점

- 변경에 유리한 설계 가능
- 선언과 구현을 분리할 수 있음
- 표준화 가 가능하다
- 서로 관계없는 클래스들을 맺어줄 수 있음

- 인터페이스 사용 안할 경우
    - A(USER) class -- > B(Provider) class // A가 B를 의존(사용)
    - A가 B의 메서드 선언부만 알면된다.
        - 한쪽이 변경되면 다른 한쪽도 변경되어야한다.
- 인터페이스 사용할 경우
    - B에 변경사항이 생기거나 B와 같은 기능의 다른 클래스로 대체 되어도 A는 영향x
    - 인터페이스를 이용해서 B의 선언과 구현을 분리하면 됨