# Basic structure

> 출처 : 점프 투 자바, 나도코딩  자바 무료 강의 2시간 완성
  
### 문자열 비교
```
String s1 = "Java"; // 이렇게 선언하면 메모리 한공간에 Java가 올라간다
String s2 = "Java"; // 하나 더 쓰면 위에 쓴것과 같은 메모리 공간을 공유한다.
하지만
String s1 = new String("Java");
String s2 = new String("Java");
```
- 이렇게 new키워드를 사용해 문자열을 생성한다면, 새로운 객체가 힙 메모리에 생성된다. 때문에 동일한 문자열 리터럴이라도 서로 다른 메모리 공간을 차지하게 된다.
- 비교할때는 == 이 아닌 equals로 비교

### 예시
```
public class Sample {
    public static void main(String[] args) {
        System.out.println("Hello java");
    }
}
```

### 클래스 블록
```
public class 클래스명 {
}
```
- 자바 코드의 가장 바깥쪽 영역. 여러 메서드 블록을 포함
- 클래스명 앞에 public 키워드를 추가하면 그 클래스의 이름은 파일명과 동일해야 한다.
---

### 메서드 블록
```
[public|private|protected] [static] (리턴자료형|void) 메서드명1(입력자료형 매개변수, ...) {
        명령문(statement);
        ...
}
```

#### public, private, protected 는 메서드의 **접근 제어자**이다.
- private : 해당 클래스 내에서만
- public : 모든 클래스에서
- default : 같은 패키지 내에서만(아무것도 적지 않았을때 적용)
- protected : 같은 패키지 내에서, 다른 패키지인 경우 자식 클래스에서만 접근 가능

#### static 이 붙으면 static 메서드가 된다.
```
class Counter  {
    static int count = 0;
        ...
    }

    public static int getCount() {
        return count;
    }
}
```
- Counter 클래스에 getCount() 라는 스태틱 메서드를 추가했다. 메서드 앞에 static 키워드를 붙이면 Counter.getCount()처럼 클래스를 통해 메서드를 직접 호출할 수 있다.
- void는 메서드가 실행된 후 리턴되는 값의 자료형이다. 리턴값이 있으면 리턴 자료형을 적고 리턴값이 없다면 void
- 입력 자료형 매개변수 와 인수. 위 예시를 보면 args 변수는 String[] 배열 자료형임을 의미
---

### Java VS JavaScript
// 계속 헷갈리는 개념 정리..

#### 배열
1. 자바
   - 정적 배열: 배열의 크기가 고정되어 있으며, 배열을 생성할 때 그 크기를 지정해야 한다.
   배열의 크기는 한 번 설정하면 변경할 수 없다.
   - 형식 : 특정 타입의 요소만 가질 수 있다. (ex: int[], string[])
- 선언 및 초기화
```
// 정수 배열 선언 및 초기화
int[] numbers = {1, 2, 3, 4, 5};

// 배열 생성 후 초기화
int[] moreNumbers = new int[5]; // 크기가 5인 배열 생성
moreNumbers[0] = 1;
moreNumbers[1] = 2;
```

2. 자바스크립트
   - 동적 배열: 배열의 크기가 동적으로 조절 가능하다. 배열에 요소를 추가, 제거할 수 있다.
   - 형식 : 다양한 타입의 요소를 가질 수 있다.
- 선언 및 초기화
```
// 배열 선언 및 초기화
let numbers = [1, 2, 3, 4, 5];

// 배열에 요소 추가
numbers.push(6);

// 배열의 크기 동적 조절
console.log(numbers.length); // 출력: 6
```
   
#### 객체
1. 자바
    - 클래스 기반: 객체는 클래스를 통해 정의된다. 클래스는 객체의 상태(속성)와 동작(메소드)을 정의한다.
    - 인스턴스 생성: 'new' 키워드를 사용하여 클래스의 인스턴스를 생성한다.
 
2. 자바스크립트
    - 키-값 쌍: 키-값으로 데이터를 저장한다. 객체의 속성은 문자열이나 심볼로 정의된 키를 가진다.
    - 동적속성: 동적으로 속성을 추가하거나 제거할 수 있다.
---

### 인스턴스 생성
- 객체(인스턴스)를 생성하는 과정은 클래스의 설계와 객체 지향 프로그래밍의 핵심 개념을 이해하는데 중요하다.
- 가장 일반적인 방법은 `new` 키워드를 사용하는 것
```
public class Person {
  ...
  // 메서드
  public Person(String name, int age) {
  ...
  }
}
 
public class Main {
 public static void main(String[] args) {
  // Person 클래스의 인스턴스 생성
  Person person = new Person("홍길동", 30);
  // Classname objectname = new ClassName();
     
  // 인스턴스 메서드 호출
  person.introduce();
 }
}
   ```
- Classname : 생성하려는 객체의 클래스 이름
- objectname : 생성된 객체의 참조를 담을 변수 이름
- new ClassName() : 클래스의 생성자를 호출하여 객체를 생성한다.
- 이외에도 '싱글턴 패턴', '팩토리 메서드 패턴' 등 다른 방법도 존재
---

### 인스턴스 변수
- 클래스내에 선언된 변수
```
class 클래스명 {
인스턴스 변수1
인스턴스 변수2
}
```
- 객체마다 다른 값을 가질 수 있다.
- 예시
```
class Person{
   String name;
   int age;
}

public static void main(String[] args){
   Person person = new Person();
   Person.name = "철수";
   Person.age = 20;
}
```

### 클래스 변수
- 클래스 내에 static으로 선언된 변수(모든 객체가 공유하는 변수)
- 인스턴스 변수와 달리 객체 만들필요 없이 클래스 접근 가능
```
class person{
   String name;
   int age;
   static int personCount = 0; // 클래스 변수
}

public static void main(String[] args){
   person.personCount = 10;
   System.out.println(Person.personCount); //10
}
```
---

### 인스턴스 메소드
- 클래스 내에 정의된 메소드
```
class 클래스명{
   인스턴스 메소드1
   인스턴스 메소드2
...
}

class Person{
   String name;
   int age;
   static int personCount = 0;
   public void introduce(){
   System.out.println("이름: + name");
   Systom.out.println("나이: + age");
}

//인스턴스 메소드 사용법
public static void main(String[] args){
   Person person = new Person(); //Person 클래스로 부터 person 객체를 만들고
   person.name = "철수"; // 값 할당
   person.age = 20;       // 값 할당
   person.introduce();    // 점(.) 을 통해서 메소드에 접근
}
```
---

### 클래스 메소드
- 클래스 내에  static 으로 정의된 메소드
```
class 클래스명 {
   String name;
   int age;
   static int personCount = 0;
   public static void printPersonCount(){
   System.out.println(personCount);
}

public static void main(String[] args){
   person.personCount = 10;
   Person.printPersonCount(); //
}
```
---
  
### This 
- 자기자신(보통 클래스 내에서 인스턴스 변수 / 지역변수 구분하기 위해서 사용)
- 사용법 : This.인스턴스변수;
```
class Person{
   String name;
   piblic void setName(String name){ // 인스턴스 변수 명과 전달 값의 이름이 똑같기 때문에 메소드 내에서 구분하기 위해서 this.을 붙인다
   this.name = name;
}

public static void main(Stinrg[] args){
   Person person = new person();
   person.setName("철수");
   Systeom.out.println(person.name); // 철수
}
```
---
  
### 생성자
- 객체가 생성될때 호출되는 메소드
- 클래스 내에서 생성자는 클래스명 괄호안에 전달값을 받아서 객체를 생성할때 쓸수 있다.
```
클래스명(전달값){
   초기화 명령문
}

class Person {
   String name;
   int age;
   Person(String name, int age){ // 메소드 정의
   this.name = name;
   this.age = age;        // 전달받은 값 초기화
}

public static void main(Stinrg[] args){
   Person person = new person("철수", 20); //위의 클래스에 전달값이 대입이 된다.
}
```
---
  
### Getter
- 인스턴스 변수의 값 반환
```
반환형 get을 포함한 이름(){
   return 반환값;
}

class Person{
   int age;
   public int getAge(){
      return age; //인스턴스 변수를 그대로 반환
   }
}
```
  
### Setter
- 인스턴스 변수의 값 설정
```commandline
void set이름(전달값){
}

class Person{
   int age;
   public void setAge(int age){
      this.age = age;
   }
}
```

### Getter 와 Setter
```
public static void main(Stinrg[] args){
       Person person = new person();
       person.setAge(20); // 값의 설정(setter)
       System.out.println(person.getAge()); //값 가져오기
}
```
---

### 상속
```commandline
class 자식 클래스명 estends 부모 클래스명{
    // 확장할 내용
}
```
---

### 메소드 오버라이딩
- 부모 클래스의 메소드 재정의
```commandline
// 부모클래스
class Person{
  public void introduce(){
    System.out.println("사람입니다");
  }
}
// 자식클래스
class Student extends Person{
  public void introduce(){
    System.out.println("학생입니다");
  }
}
```
---
  
### 다형성
```commandline
// 부모클래스
class Person{
  public void introduce(){
    System.out.println("사람입니다");
  }
}
// 자식클래스
class Student extends Person{
  public void introduce(){
    System.out.println("학생입니다");
  }
}

public static void main(String[] args){
    Person person = new Person();
    Person student = new Student();
    person.introduce();
    student.introduce();
}
```
- 서로 다른 객체를 만들고 Person 이라는 참조 변수를 통해서 각각의 객체를 참조하게 된다.
---

### Super
- 부모클래스에 접근하기 위해서 사용
- super.부모 클래스 변수;
- supre.부모 클래스 메소드();

### 참조
- 객체의 메모리 주소를 가리킴
- 기본 자료형 : 실제 값 저장 // int, float, double, long, boolean...
- 참조 자료형 : 값이 저장된 메모리 주소를 저장 // String, Person, Student...
```
int a = 10;
int b = 20;
a = b; // b의 값을 a에 복사(a값이 수정됨)

String s1 = "가";
String s2 = "나";
s1 = s2; // s2가 가리키고 있던 값을 s1 도 가리키게 되면서 s1 도 "나"가 된다.
```
---

### Final
- 변경할 수 없게 쓰는 키워드
- 변수에 사용 : final String name = "철수"; // name은 변경할 수 없게 된다. 값을 무조건 할당 해주어야 한다.
- 메소드에 사용 : public final void introduce(){} // introduce 라고 하는 메소드를 새롭게 정의 불가능. 자식 클래스에서 오버라이딩 할 수 없게 된다

### 열거형
```commandline
enum Gender{
   Male,
   FeMale
}

class Person{
   Gender gender; // 변수명
   public void setGender(Gender gender){
      this.gender = gender; // 인스턴스 변수
   }
}

public static void main(String[] args){
   Person person = new Person();
   Person.setGender(Gender.MALE); // MALE, FEMALE / switch-case 에서 유용하게 쓰임
   
   switch(person.gender){
      case MALE: System.out.pritln("남자"); break;
      case FEMALE: System.out.println("여자"); break;
   }
}
```
---

### 추상 클래스
- 아직 완성되지 않은 클래스
- 키워드: abstract
```commandline
abstract class Shape{
  abstract double calculateArea(); // 도형의 면적을 결정하는 메소드 선언, 추상클래스로서 미완성이다
}
class Square extends Shape{ // 정사각형을 만들기 위한 클래스 선언
  private double s;
  public Square(double s);{ // s값을 생성자를 통해 전달 받는다
    this.s = s;
  }
  double calcualtArea(){
    return s * s; // 면적 계산
  }
}
```
---

### 인터페이스
- 클래스를 작성할때 기본이 되는 뼈대
```commandline
//추상클래스
abstract class Shape{
  abstract double calculateArea();
}

//인터페이스
interface Shape{
  double calculateArea(); // interface 라고 하는 인터페이스를 구현하는 클래스에서는 calcualteArea 메소드를 정의해줘야한다.
}

//구현
class Square implements Shape{ //Shape 인터페이스 안에 메소드를 여기서 완성시켜주면된다.
  private double s;
  public Square(double s);{
    this.s = s;
  }
  double calcualtArea(){
    return s * s;
  }
}
```
---

### 제네릭스
- 다양한 형태의 데이터를 다룰 수 있게 해주는 것. 코드의 재사용성을 높이고 타입 안정성 확보 가능
```commandline
T 변수명
public static void main(String[] args){
  int intValue = 3;
  double doubleValue = 3.14;
  String stringValue = "안녕";
  // 각각의 메소드 내에서 출력하고 싶을때
  // 서로 다른 전달값을 받아서 메소드 오버로딩해서 만든다.
  prtinValue(intValue);
  printValue(doubleValue);
  printValue(stringValue);
  
  public static void printValue(int value){
    System.out.println(value);
  }
  public static void printValue(double value){
    System.out.println(value);
  }
  public static void printValue(string value){
    System.out.println(value);
  }
  //위의 코드에서 아래처럼 쓸 수 있음
  public static <T> void printValue(T value){
    System.out.println(value);
  }
}
```
---

### 제네릭 클래스
- 제네릭 기반 클래스 다양한 데이터 유형을 처리할 수 있게 설계된 클래스
```commandline
// 사용법
class 클래스명<T>{
}

class BoxInteger{
  int data;
  public void setData(int data){ //setter를 통해서 전달 받은값을 인스턴스 변수에 설정
    this.data = data;
  }
}
class boxString{
  int data;
  public void setData(String data){ //위와 다르게 String 값
    this.data = data;
  }
}

public static void main(Stinrg[] args){
  BoxInteger iBox = new BoxInteger();
  iBox.setData(3); //정수 담기
  
  BoxString sBox = new BoxString();
  sBox.setData("안녕"); //문자열 담기
}
// 위에 두 클래스가 하는 동작이 똑같을때 제네릭 클래스로 만들 수 있다.
class Box<T>{
  T data;
  public void setData(T data){ //위와 다르게 String 값
    this.data = data;
  }
} // 이 박스는 모든 데이터를 담을 수 있는 Box 클래스
// 사용법
public static void main(Stinrg[] args){
  Box<Integer> iBox = new Box<>(); //클래스 뒤에 어떤 데이터 담을지 정해준다.
  iBox.setData(3); //정수 담기
  
  Box<String> sBox = new Box<>();
  sBox.setData("안녕"); //문자열 담기
}
```
---

### Wrapper 클래스
- 기본 자료형 + 추가 기능 제공 클래스
- Integer -> int
- Double -> double
- Character -> char
```commandline
public static void main(String[] args){
   int = 1;
   double d = 1.0;
   char c = 'a';
}
// Wrapper 클래스 사용
public static void main(String[] args){
   Integer = 1;
   Double d = 1.0;
   Character c = 'a';
   
   System.out.println(i.intValue());
   System.out.println(d.intValue()); // double 같은 경우는 정수로 변환해서 출력하고 싶은경우 이렇게 쓴다.
   System.out.println(c.charValue());
}
```
---

### 익명 클래스
- 한번만 사용되는 이름 없는 클래스

#### 예시
```commandline
class Person{
 public void introduce(){
   System.out.println("사람입니다");
 }
}
// 메인 메서드에서의 inroduce() 사용
public stoatic void main(String[] args){
  Person person = new Person();
  person.introduce(); // 사람입니다
}
// 아래는 익명 클래스 사용시 (클래스가 선언되어 있어야 확장시켜서 사용 가능 없으면 안됨)
public stoatic void main(String[] args){
  Person person = new Person() {
     @Override // 어노테이션
     public void introduce(){
       System.out.println("익명입니다");
     }
  };
  person.introduce(); // 익명입니다
}
```
---

### 람다식
- 간결한 형태의 코드 묶음. 메소드의 동작을 간단하게 표현하기 위해서 사용

#### 예시
- (전달값1, 전달값2, ...) -> {코드}
```commandline
// 두개의 수를 받아서 더해주는 add 메서드
public int add(int x, int y) {
  return x + y;
}

// 람다식으로 변경 순서
// 1. 접근제어자, 반환형, 이름 을 제거 한다.
// 2. 전달값이 들어가는 괄호와 중괄호 사이에 -> 기호 삽입
// 3. 자료형 제거
// 4. 괄호, return 제거
(x, y) -> x + y // 간소화된 모습
```
---

### 함수형 인터페이스
   - 람다식을 위한 인터페이스
   - 함수형 인터페이스는 딱 하나의 추상메서드를 가져야 한다는 제약사항이 있음

#### 예시
```commandline
@FunctionalInterface
interface 인터페이스명 {
  // 하나의 추상메서드
}

// 사용예시
@FunctionalInterface
interface Calculator {
  int calculate(int x, int y); // 두개의 수를 전달 받아서 어떤 연산결과를 반환해줄 하나의 추상메서드 존재
}
// 메인 메서드에서의 활용
public static void main(String[] args) {
  Calculator add = (x, y) -> x + y; // 두 수를 더한 결과를 반환하는 람다식, 그러면 람다식이 인터페이스 내의 calcualate라는 메서드와 맵핑이되는것.
  int result = add.calculate(2, 3); // 두 수를 더하는 동작 수행
  System.out.println("2 + 3 = " + result);
}
```
---

### 스트림
- 배열 또는 컬랙션 데이터를 효과적으로 처리

#### 예시
```commandline
public static void main(String[] args){
  List<INteger> numbers = Arrays.asList(1,2,3,4,5);
  // 위의 정수형 배열에서 특정 데이터만 스트림을 이용해서 데이터를 가공해서 추출
  numbers.stream() //numbers 라는 리스트로부터 스트림을 얻어온다.
    .filter(n -> n % 2 == 0) // 짝수만 필터링
    .map(n -> n * 2) // 각 요소 값을 2배로 변환
    .forEach(System.out::println); // 결과 출력
} 
```
---

### 예외처리
- try catch
```commandline
try {
  명령문
} catch(변수) {
  예외 처리
}
// try 에서 명령문을 실행하다가 어떤 문제가 생기면 catch 영역을 통해서 문제 처리를 해줄 수 있다.
```

#### 예시
```commandline
public static void main(String[] args){
  int[] numbers = {1,2,3};
  int index = 5; // 존재하지 않는 인덱스
  try {
    int result = numbers[index]; // 존재하지 않는 인덱스를 출력하려고 하니까 catch가 실행
    System.out.println("결과: " + result);
  } catch(Exception e){
    System.out.println("문제 발생");
  }
}
```
---

### catch
- 예외의 종류에 따른 처리

#### 예시
```commandline
public static void main(String[] args){
  int[] numbers = {1,2,3};
  int index = 5; // 존재하지 않는 인덱스
  try {
    int result = numbers[index];
    System.out.println("결과: " + result);
  } catch(ArrayIndexOutOfBoundsException e){   // 여기 괄호안에 예외의 종류에 따라서 catch문에 문장이 실행, 지금 괄호안에 예외처리는 잘못된 인덱스 일경우에 코드 실행
    System.out.println("문제 발생");
  }
}
```
---

### 예외 발생시키기
- 의도적으로 예외 상황 만들기
- throw new 예외();

#### 예시
```commandline
public static void main(String[] args){
  try {
    int age = -5;
    if (age < 0){ // 나이는 음수가 될 수 없다.
      throw new Exception("나이는 음수일 수 없습니다"); // throw 키워드를 통해 발생
    }
  } catch(Exception e){
    System.out.println(e.getMessage()); // e.getMessage()로 throw 에 적혀있는 문장 출력
  }
}
```
---

### Finally
- 예외가 실행되던 안되던 항상 실행되는 코드. 코드에서 사용되는 리소스를 해제 하거나 정리작업을 하기 위해서 사용
```commandline
try {
  명령문
} catch(변수) {
  예외 처리
} finally {
  명령문
}
```

#### 예시
```commandline
public static void main(String[] args){
  try {
    int reusult = 3 / 0;
  } catch(Exception e){
    System.out.println("문제 발생");
  } finally {
    System.out.println("실행 종료"); // 문제 발생 여부와 상관없이 실행됨
  }
}
```
---

### Try With Resources
- 리소스 관리를 편하게 할 수 있는 방법.
```commandline
try (자원할당) {
  명령문
} catch(변수) {
  예외 처리
}
```

#### 예시
```commandline
public static void main(String[] args){
  try(FileWriter writer = new File Writer("file.txt")) {  // FileWriter 리소스를 할당
    writer.write("hi");  // 파일에 "hi" 문자열을 작성
  } catch(Exception e){
    System.out.println("문제 발생");
  }
}
```
---

### 사용자 정의 예외
- 개발자가 직접 정의한 예외 클래스로 특정 상황에서 발생시키고자 할때 사용
```commandline
class 클래스명 extends Exception {  //exception 클래 상속해서 만들어 줄 수 있음
}
```

#### 예시
```commandline
class MyException extends Exception {
  public MyException(String message) {
    super(message);
  }
}

public static void main(String[] args){
  try {
    int age = -5;
    if (age < 0){ 
      throw new MyException("나이는 음수일 수 없습니다"); // throw 키워드를 통해 발생
    }
  } catch(MyException e){
    System.out.println("문제 발생: " + e.getMessage());
  }
}
```
---

### 예외처리 미루기 (throws)
- 메소드를 수행할 때 메소드를 호출한 곳에서 처리
```commandline
반환형 메소드명() throws 예외 {
  명령문
}
```

#### 예시
```commandline
public static int divide(int a , int b) throws Excepton {
  return a / b;
}
public static void main(String[] args){
  try {
    divide(3, 0);  // 위의 return a / b 에서 문제 발생할 것임. 이 때 trows 를 통해서 발생된 예외를 호출한 메서드 쪽으로 미룬다.
  } catch(Excepton e){
    System.out.println("0으로 나눌 수 없어요");  // 예외처리를 이 곳에서 하게됨
  }
}
```
---

### Thread
- 여러 작업을 동시에 수핼할 때 사용
```commandline
class 클래스명 extends thread {
  public void run() {
  // run() 메서드 정의
  }
}
```

#### 예시
```commandline
class MyThread extends Thread {
  public void run(){
    for (int i = 1; i <= 5; i++) {
      System.out.println("Tread:" + i);
    }
  }
}

public static void main(String[] args) {
  MyThread thread = new MyThread();
  thread.start();  // 새로운 쓰레드에서 run() 동작 수행
}
// 실행 결과
// Tread: 1
// Tread: 2
// ...
// Tread: 5
```
---

### Runnable
- 여러작업을 동시에 사용하기 위해서 사용(쓰레드랑 동일 하지만 runnable은 인터페이스이다)
```commandline
class 클래스명 implements Runnable {  // runnable은 인터페이스이기 때문에 extends 가 아니고 implements로 구현
  public void run() {
    // 여기 적혀있는 코드를 새로운 쓰레드에서 실행하게 된다.
  }
}
```

#### Tread 와 차이점
- 자바에서는 클래스를 다중 상속 할 수 없기 때문에 Thread 클래스를 상속하게되면 다른 클래스는 상속이 불가능. 하지만 Runnable 인터페이스를 사용하면 다른 클래스를 상속할 수 있다.

#### 예시
```commandline
class MyThread extends Thread {
  public void run(){
    for (int i = 1; i <= 5; i++) {
      System.out.println("Runnable:" + i);
    }
  }
}

public static void main(String[] args) {
  MyRunnable runnable = new MyRunnable();
  Thread thread = new Thread(runnable); // 새로운 Thread 객체를 만들어 앞의 runnable 객체를 전달.
  thread.start();
}
```
---

### Join
- Thread 실행 마칠 때까지 대기하기 위해서 사용
```commandline
public stattic void main(String[] args) throws InteruptedException {  // join 쓸경우 예외처리 해주어야 한다.
  Thread thread = new Thread(() -> {  //메인 메서드 내에서 수행될 객체들 만들고
    for (int i = 1; i <= 5; i++) {
      System.out.println("Thread: " + i);
    }
  });
  thread.start();
  // thread.join();  // join 을 안쓸 경우에 "Thread: i" 와 "Method: i"가 반복하면서 출력되지만 thread.join을 쓸경우 thread의 실행을 마칠 때까지 대기한다.
  method(); 
}

public static void method() {  // 별도의 메서드 에서도 위와 유사한 작업을 하는 코드
  for (int i = 1; i <= 5; i++) {
    System.out.println("Method: " + i);
  }
}
```
---

### 다중 쓰레드
- 여러 쓰레드를 동시에 수행하는 것.

#### 예시
```commandline
public stattic void main(String[] args) {
  Thread thread1 = new Thread(() -> { 
    for (int i = 1; i <= 5; i++) {
      System.out.println("Thread1: " + i);
    }
  });
  Thread thread2 = new Thread(() -> {
    for (int i = 1; i <= 5; i++) {
      System.out.println("Thread2: " + i);
    }
  });
  
  thread1.start();
  thread2.start(); // thread1 과 thread2 가 번갈아 가면서 실행된다.
}
```
---

### 동기화
- 여러 쓰레드가 공유된 자원에 동시에 접근하지 못하게 막는것
```commandline
synchreonized 메소드명() {  // 특정 쓰레드가 이 메소드를 사용하는 동안 다른 쓰레드는 이 메소드를 사용할 수 없게 된다.

}

synchreonized(변수) {  // 어떤 쓰레드가 이 변수에 대해 작업하는동안 다른 쓰레드는 이 변수의 값을 바꿀 수 없게 된다.
  
}
```

#### 예시
```commandline
class SharedData {
  public int data = 0;  
  synchronized public void increment() {  // synchronized 키워드로 인해서 이 메서드를 사용할때는 한번의 하나의 수행만 접근할 수 있게 된다.
    data++;
  }
}

public stattic void main(String[] args) throws InteruptedException {
  SharedData sharedData = new SharedData();
  Thread thread1 = new Thread(() -> { 
    for (int i = 1; i < 1000; i++) {
      sharedData.increment();
    }
  });
  
  Thread thread2 = new Thread(() -> {
    for (int i = 1; i < 1000; i++) {
      sharedData.increment();
    }
  });
  
  thread1.start();
  thread2.start();
  
  thread1.join();
  thread2.join();
  
  System.out.println("SharedData: " + SharedData.data); // SharedData: 2000
}
// 만약에 맨 윗단의 increment메소드의 synchronized 키워드를 삭제한다면 값이 컴퓨터마다 다르게 나온다.
```
---

### 입력
- 프로그램으로 데이터를 가져오기
- Scanner sc = new Scanner(System.in);  -> 사용자가 키보드로 입력하는 값을 받아 올 수 있다.
  - scanner 기능
    - next : 문자열 입력(단어 단위) // String word = sc.next();
    - nexlnt : 정수 입력
    - nextDouble : 실수 입력
    - nextLin : 문장 입력(줄 단위)
---

### 출력
- 프로그램에서 결과를 표시하거나 저장
- system.out.porint();
- system.out.porintln();
- system.out.porintf();  // System.out.printf("이름: %s, 나이: %d", name, age)
---

### 파일과 폴더
- 샐성, 삭제, 정보 조회 등 여러 작업 수행
```commandline
String fileName = "test.txt";
File file = new File(fileName);  // 파일을 생성하는 과정에서 예외가 발생 할 수 있기 때문에 try catch 써줌
try {
} catch {
}
```
- file 관련 기능
  - createNewFile : 새 파일 생성
  - exists : 파일 또는 폴더 존재 여부
  - getName : 이름 정보
  - getAbsolutePath : 절대 경로 정보
- 폴더 관련 기능
  - mkdir : 폴더 만들기
  - mkdirs : 폴더들 만들기
  - listFiles : 파일 및 폴더 목록 조회
---