# Basic structure

> 출처 : 점프 투 자바

## 예시

```
public class Sample {
    public static void main(String[] args) {
        System.out.println("Hello java");
    }
}
```

## 클래스 블록

```
public class 클래스명 {
}
```

- 자바 코드의 가장 바깥쪽 영역. 여러 메서드 블록을 포함
- 클래스명 앞에 public 키워드를 추가하면 그 클래스의 이름은 파일명과 동일해야 한다.

## 메서드 블록

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

#### void는 메서드가 실행된 후 리턴되는 값의 자료형이다. 리턴값이 있으면 리턴 자료형을 적고 리턴값이 없다면 void

#### 입력 자료형 매개변수 와 인수. 위 예시를 보면 args 변수는 String[] 배열 자료형임을 의미