# Basic structure

> 출처 : 점프 투 자바, 나도코딩  자바 무료 강의 2시간 완성
  
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

### 상속
```commandline
class 자식 클래스명 estends 부모 클래스명{
    // 확장할 내용
}
```

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

### Super
- 부모클래스에 접근하기 위해서 사용
- super.부모 클래스 변수;
- supre.부모 클래스 메소드();