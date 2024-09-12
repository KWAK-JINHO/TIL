# Spring 웹 개발 기초

### Spring Boot 스타터 사이트를 이용해 스프링 프로젝트 생성

- [스프링 부트 스타터](https://start.spring.io)

### IntelliJ에서 Gradle 대신 Java로 직접 실행

- Gradle을 통해 실행되도록 설정되어 있다면, Java로 변경하는 것이 좋다. Java로 실행하면 속도가 더 빠르다.

### 정적 컨텐츠

- 파일을 그대로 클라이언트에게 전달.
- /static 폴더에 원하는 파일을 넣으면 정적 파일이 그대로 반환된다. 이 과정에서는 프로그래밍적인 처리가 불가능하다.
- 웹 브라우저 -(파일 요청)-> 내장 톰캣 서버 -> 스프링 컨테이너(먼저 컨트롤러에서 해당 파일을 찾음) -> 파일이 없으면 스프링 부트가 resources에서 해당 파일을 찾음 -> HTML 파일을 브라우저에 반환.

### MVC와 템플릿 엔진

- 템플릿 엔진: HTML 파일을 동적으로 렌더링하여 내용을 변경할 수 있게 해주는 도구
- 작동 원리
  1. 웹 브라우저에서 요청을 보냄
  2. 내장 톰캣 서버가 요청을 받는다
  3. 스프링 컨테이너에서 매핑된 컨트롤러를 찾아 해당 메서드를 호출
  4. 컨트롤러는 필요한 데이터를 Model에 담아 View 이름과 함께 반환
  5. ViewResolver가 해당 View를 찾고 템플릿 엔진에 처리를 요청
  6. 템플릿 엔진이 View를 렌더링하여 HTML로 변환
  7. 변환된 HTML이 웹 브라우저로 전송

- 스프링 부트에서 주로 사용되는 템플릿 엔진으로는 Thymeleaf, Freemarker, Mustache 등이 있다.

### API

- API(Application Programming Interface)는 애플리케이션 간의 통신을 위한 인터페이스. 웹 개발에서 API는 주로 데이터를 주고받는 방식을 의미한다.
- 리액트 뷰 등등 쓸때 API로 내려주면 클라이언트가 알아서 화면을 그리는 방식 서버끼리 통신할때도 보통 API 방식(세밀하게 보면 아닐수도 있긴함)

#### JSON

- Key-Value 쌍으로 구성된 데이터 포맷이다.
- 가독성이 좋고 다양한 프로그래밍 언어에서 쉽게 파싱할 수 있다.
- XML에 비해 경량화되어 있어 데이터 전송에 효율적이다.
- 객체를 return 하고 @ResponseBody를 사용하면 json을 반환하는게 기본이다.(XML로 세팅 가능하긴하다.)

#### @ResponseBody

예시
```commandline
@RestController
public class ApiController {
    @GetMapping("/api/hello")
    public Hello hello(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;
        // getter와 setter 메서드
    }
}
```
- 작동원리
  1. 클라이언트에서 HTTP 요청을 보냅다.
    2. 내장 톰캣 서버가 요청을 받는다.
    3. 스프링 컨테이너에서 해당 컨트롤러와 메서드를 찾아 실행한다.
    4. @ResponseBody가 있으면 ViewResolver 대신 HttpMessageConverter가 동작한다.
    5. 반환 타입에 따라 적절한 Converter 선택
       - 문자열: StringHttpMessageConverter
       - 객체: MappingJackson2HttpMessageConverter (JSON 변환)
    6. 변환된 데이터가 HTTP 응답 본문에 직접 작성되어 클라이언트로 전송됩니다.

#### JavaBean방식 

- 객체 지향 프로그래밍을 쉽게 하기 위한 표준화된 클래스 구조
- 기본 생성자: JavaBean은 파라미터가 없는 기본 생성자를 가져야 합니다. (매개변수 있는 생성자는 있어도 되지만, 기본 생성자가 필수입니다.)
- JavaBean의 메서드 getter, setter
- 프로퍼티 방식
  - 프로퍼티란 객체의 속성 또는 필드를 의미하며, 이 필드에 접근하거나 수정하는 방법을 getter와 setter로 구현. 즉, 프로퍼티 방식은 다음과 같은 형태로 객체의 데이터를 다루는 것을 말한다.

- Getter 메서드: 객체의 속성 값을 반환하는 메서드.
- Setter 메서드: 객체의 속성 값을 설정하는 메서드.
```
public class Person {
    private String name;  // 프로퍼티 (속성)

    // Getter 메서드
    public String getName() {
        return name;
    }

    // Setter 메서드
    public void setName(String name) {
        this.name = name;
    }
}
```