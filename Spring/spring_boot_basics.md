# Spring 웹 개발 기초

### Spring Boot 스타터 사이트를 이용해 스프링 프로젝트 생성

- [스프링 부트 스타터](https://start.spring.io)

### IntelliJ에서 Gradle 대신 Java로 직접 실행

- Gradle을 통해 실행되도록 설정되어 있다면, Java로 변경하는 것이 좋다. Java로 실행하면 속도가 더 빠르다.

### 정적 컨텐츠

- 파일을 그대로 클라이언트에게 전달.
- /static 폴더에 원하는 파일을 넣으면 정적 파일이 그대로 반환된다. 이 과정에서는 프로그래밍적인 처리가 불가능하다.
- 웹 브라우저 -(파일 요청)-> 내장 톰캣 서버 -> 스프링 컨테이너(먼저 컨트롤러에서 해당 파일을 찾음) -> 파일이 없으면 스프링 부트가 resources에서 해당 파일을 찾음 -> HTML 파일을 브라우저에
  반환.

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

### **Spring Boot의 핵심 구성 요소**

`@SpringBootApplication`은 Spring Boot 애플리케이션의 시작점으로, **3가지 주요 애노테이션의 조합**으로 구성된다.

1. **`@Configuration`**

- Java 설정 파일로, 기존 XML 설정 파일(root-context, servlet-context)처럼 빈을 수동으로 등록하거나 설정한다.
- 애플리케이션의 환경을 명시적으로 설정하는 역할을 한다.

2. **`@EnableAutoConfiguration`**

- Spring Boot의 핵심 기능 중 하나로, 스프링 컨테이너에 필요한 설정(예: 데이터베이스 연결, 뷰 리졸버, 메시지 컨버터 등)을 자동으로 구성한다.
- 이를 통해 개발자는 반복적인 설정 작업을 줄이고 애플리케이션 로직에 집중할 수 있다.

3. **`@ComponentScan`**

- 프로젝트에서 `@Component`, `@Controller`, `@Service`, `@Repository`와 같은 애노테이션이 붙은 클래스를 자동으로 스캔하여 스프링 컨테이너에 등록한다.
- 빈(bean)을 명시적으로 등록하지 않아도 스프링이 자동으로 인식한다.

### **pom.xml: Spring Boot의 특징**

Spring Boot에서 **`pom.xml`**은 두 가지 중요한 특징을 기반으로 애플리케이션 구성을 돕는다.

1. **라이브러리 관리: Spring Boot Starter**

- Spring Boot는 여러 개의 관련 라이브러리를 하나의 **Starter** 그룹으로 묶어 제공한다.
- 예를 들어:
    - `spring-boot-starter-web`: 웹 애플리케이션 개발을 위한 기본 라이브러리(Spring MVC, Embedded Tomcat 등 포함).
    - `spring-boot-starter-data-jpa`: JPA와 데이터베이스 관련 라이브러리 제공.
- 이를 통해 개발자는 필요한 기능에 따라 적절한 Starter를 추가하기만 하면 된다.

2. **자동 설정(AutoConfiguration)**

- 기존 Spring에서는 개발자가 **Bean 등록**과 **환경 설정**을 수동으로 작성해야 했다.
- Spring Boot에서는 **AutoConfiguration**을 통해 이 작업이 자동으로 이루어진다.
- 예를 들어, `spring-boot-starter-web`을 추가하면:
    - 내장 톰캣 서버가 자동으로 설정된다.
    - `DispatcherServlet`, `ViewResolver` 등 웹 관련 Bean이 자동으로 등록된다.

### **Spring Boot의 장점**

1. **자동 빈 등록**

- `@ComponentScan`과 AutoConfiguration이 협력하여 필요한 Bean을 자동으로 등록한다.
- Bean 등록을 위한 XML이나 Java 설정을 최소화한다.

2. **빠른 시작과 간편한 설정**

- 내장 서버(톰캣)와 라이브러리 그룹(Starter)을 사용해 애플리케이션을 쉽게 시작할 수 있다.

3. **라이브러리 의존성 관리**

- Maven Starter를 사용해 의존성 충돌을 줄이고, Spring Boot 버전에 맞는 안정적인 라이브러리 버전을 자동으로 적용한다.
  ㅎ