# Basic structure

> 출처 : 점프 투 자바

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

- Counter 클래스에 getCount() 라는 스태틱 메서드를 추가했다. 메서드 앞에 static 키워드를 붙이면 Counter.getCount()
처럼 클래스를 통해 메서드를 직접 호출할 수 있다.

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