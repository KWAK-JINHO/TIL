# Spring Summary

# Spring 애플리케이션의 기본 구조

## 원격 프로그램 등록 & 연결

• @Controller → Spring에게 **이 클래스가 웹 요청을 처리하는 컨트롤러임을 알림**
• @RequestMapping(URL, method = GET/POST) → 특정 URL과 메서드를 매핑

## Spring 컨트롤러의 메서드 실행 과정

- 컨트롤러 인스턴스 메서드는 톰켓이 대신 객체를 생성하고 메서드를 호출하기 때문에 객체 생성 없이 사용 가능
- **📌 왜 인스턴스 메서드로 만들까?**
- **인스턴스 메서드**는 **클래스 변수(CV), 인스턴스 변수(IV) 모두 사용 가능**하기 때문.
- static 메서드는 **클래스 변수(CV)만 사용 가능**하므로 제한적임.
- Spring MVC에서는 **컨트롤러를 싱글턴 빈으로 등록**하고, **톰캣이 객체를 생성하여 사용**하기 때문에 **static이 필요 없음**.

## Spring 컨트롤러의 메서드는 private로 변경해도 호출 가능한가?

- 가능하지만 public이 권장된다.

- **Spring이 Reflection API를 사용하여 private 메서드라도 호출 가능하게 함.**
- Reflection API는 **클래스의 정보를 런타임에 가져와서 조작할 수 있는 기능**을 제공함.

## **Spring MVC의 요청 처리 과정**

- 1️⃣ **클라이언트가 URL 요청 (**/hello**)**
- 2️⃣ **Tomcat이 요청을 받고** HttpServletRequest **객체 생성**
- 3️⃣ **요청 정보를** request **객체에 담음**
- 4️⃣ **Spring DispatcherServlet이 컨트롤러 메서드를 찾아 매핑**
- 5️⃣ **컨트롤러 메서드의 매개변수(**HttpServletRequest request**)에** request **객체를 전달**
- 6️⃣ **컨트롤러가 데이터를 처리한 후 결과를 반환**
- 7️⃣ **Spring의 ViewResolver가 해당 뷰를 찾아 클라이언트에게 응답**

## HttpServletResponse를 이용한 응답 처리

**📌 Response 객체의 역할**

- 클라이언트에게 **응답을 보내는 역할**을 함.
- setContentType("text/html")을 호출해야 브라우저가 응답을 올바르게 해석 가능.

### 바이너리 파일 vs 텍스트 파일

- 바이너리 파일: 문자와 숫자 저장
- 텍스트 파일: 문자만 저장, 숫자는 문자로 변환

# Spring MVC의 기본 구성

- Spring MVC는 **입력(DispatcherServlet) → 처리(Service) → 출력(View)** 흐름으로 동작함.

## 입력 (DispatcherServlet)

- **DispatcherServlet**은 사용자의 HTTP 요청을 받아 컨트롤러로 전달하는 역할

```
// 매개변수 처리의 진화 과정
public void main(HttpServletRequest request) { }  // 원시적인 방식

public void main(@RequestParam String year) { }  // 요청 데이터를 직접 받음

public void main(@RequestParam int year, @RequestParam int month, @RequestParam int day) { }  // 여러 개의 데이터 처리

public void main(@ModelAttribute MyData myData) { }  // 객체 바인딩
```

- 요청 데이터를 객체로 받을 수 있도록 발전하여, @ModelAttribute**를 이용해 편리하게 데이터 바인딩 가능**

## 처리 (Service & Model)

- Model을 이용한 데이터 처리

```
@Controller
public class MyController {

    @RequestMapping("/process")
    public String process(@RequestParam String name, Model model) {
        String message = "Hello, " + name + "!";
        model.addAttribute("msg", message);  // Model에 데이터 저장
        return "result";  // View 이름 반환
    }
}
```

## 출력(View)

- **DispatcherServlet이 View로 데이터를 전달하여 최종 출력**

## ModelAndView

**모델과 뷰를 하나로 합친 개념**

- **DispatcherServlet이 Model을 View로 전달하는 구조**
- 최근에는 거의 사용되지 않는다

💡 **오해하면 안 되는 점!**

👉 사용자의 입력값을 Model이 직접 받는 것이 아님!

👉 사용자의 입력값을 @RequestParam, @ModelAttribute 등이 받고, **Model은 단순히 데이터를 View로 전달하는 역할**

## **Reflection API**

**런타임에 동적으로 클래스, 메서드, 필드에 접근하고 수정할 수 있음**

- Spring은 **Reflection API를 활용해 빈을 생성하고, private 메서드도 호출 가능**
- **객체지향 원칙을 훼손할 수 있으므로 꼭 필요할 때만 사용해야 함**

## ViewResolver

**컨트롤러에서 반환한 논리적 뷰 이름을 실제 물리적 뷰 경로로 변환**

예시)

```
@Controller
public class MyController {
    @RequestMapping("/home")
    public String home() {
        return "home";  // 실제 파일: "/WEB-INF/views/home.jsp"
    }
}
```

# 데이터 저장소

각 저장소는 Map 구조를 사용하며, 범위와 생명주기에 따라 다르다.

| 저장소          | 접근 범위       | 생존 기간     | 설명                        |
|--------------|-------------|:----------|---------------------------|
| pageContetxt | 현재 페이지      | 요청 완료 시   | 페이지 내에서만 사용 가능 (초기화됨)     |
| request      | 요청 간        | 요청 완료 시   | 요청이 끝나면 삭제된다.(주로 사용)      |
| session      | 사용자별 저장     | 브라우저 종료 시 | 쿠키 기반 사용자 정보 저장(서버 부담 크다) |
| application  | 웹 애플리케이션 전체 | 서버 종료 시   | 전역적으로 공유(보안문제 주의)         |

# 서블릿

## Servlet

- 서블릿은 Java 기반으로 웹 요청(HTTP)을 처리하기 위해 만들어진 자바 클래스
- Servlet API를 통해 웹 컨테이너(톰켓)상에서 동작하며, HTTP요청을받아 필요한 비즈니스 로직을 수행하고 결과를 HTTP 응답의 형태로 되돌려주는 역할을 한다.
- 서블릿은 싱글톤으로 관리되며, 최초 요청 시 생성된다.(싱글톤 구조이기 때문에 요청이 많아도 새로운 인스턴스 만들지 않는다. )

### Servlet의 life Cycle

1. 클라이언트의 요청
2. 톰켓이 Servlet Context에서 서블릿 객체가 존재하는지 확인
3. 없다면 Servlet 클래스를 로딩 -> init() -> service() / 있다면 바로 service()
4. 클라이언트에게 응답 반환

### 서블릿은 싱글톤이지만 멀티스레드를 지원한다!

- 서블릿내에 인스턴스 변수가 없기 때문에 싱글톤이여도 괜찮음. 전부 로컬변수 이기 때문에.

참고내용)

👉DispatcherServlet: 내부적으로 HttpServlet을 상속받은 서블릿

👉Servlet-Context: 서블릿 컨테이너의 전역 설정과 데이터 저장을 담당하는 객체

## 서블릿 URL 매핑 및 등록 방식 정리

### **URL 매핑 방식 (@WebServlet)**

1. exact mapping : 특정 URL과 정확히 일치해야 서블릿이 실행된다.
2. path mapping :  특정 패턴을 포함하는 URL과 매핑 가능 ( / * 사용 )
3. extension mapping : 특정 확장자를 가진 URL과 매핑 ( * .do -> test.do 등의 URL 처리)
4. 디폴트: 모든 요청을 특정 서블릿으로 전달하는 매핑 ( / * : 모든 요청을 이 서블릿에서 처리)

## JSP

자바 서버 페이지(HTML안에 자바 코드가 있다) = 서블릿(자바 코드 안에 HTML)으로 자동으로 변환된다

- JSP페이지는 따로 맵핑해줄 필요가 없다. 자동으로 맵핑됨
- JSP페이지 호출만 해주면 된다.

---

---
수정중
---

---

## MIME

- 텍스트 기반 프로토콜에 바이너리 데이터 전송하기 위해서 고안함
- 전송할 데이터의 타입을 적어주면 된다.

| **타입**      | **설명**          | **MIME타입 예시(타입/서브타입)**                                                                                                            |
|-------------|-----------------|-----------------------------------------------------------------------------------------------------------------------------------|
| text        | 텍스트를 포함하는 모든 문서 | text/plain, text/html, text/css, text/javascript                                                                                  |
| image       | 모든 종류의 이미지      | image/bmp, image/webp                                                                                                             |
| audio       | 모든 종류의 오디오 파일   | audio/midi, audio/mpeg,audio/webm, audio/ogg,audio/wav                                                                            |
| video       | 모든 종류의 비디오 파일   | video/webm, video/ogg                                                                                                             |
| application | 모든 종류의 이진 데이터   | application/octetstream,application/pkcs12, application/vnd.mspowerpoint, application/xhtml+xml, application/xml, application/pdf |
|             |                 |                                                                                                                                   |

## Base64

2진법 = base2 = 0, 1
10진법 = base10 = 0~9
16진법 = base16 = 0~9 ABCDEF
64진법 = A~Z, a-z, 0~9, +, / ⇒ 모두 64개(6 bit)의 문자로 구성

HTML 링크가 있어서 편하지만 링크가 깨지기 쉽다. 하지만 HTML에 글자로 다 박으면 안깨짐

### 바이너리 데이터를 텍스트 기반인 HTTP 프로토콜로 보내는 방법

1. MIME으로 바이너리 그대로 보내기
2. Base64로 바이너리를 텍스트로 변환해서 보낸다. 단점: Data의 사이즈가 커진다.

# 관심사의 분리와 MVC 패턴

## 관심사(Concern)란?

- 프로그램이 해결해야 할 작업이나 문제
- 객체지향 설계의 핵심은 관심사를 적절히 분리하는 것

## SOLID 원칙과 SRP(단일 책임 원칙)

- SOLID는 객체지향 5대 원칙
- SRP(Single Responsibility Principle)
- 하나의 메서드는 하나의 책임만 가져야 함
- 하나의 클래스는 하나의 변경 사유만 가져야 함

## 좋은 코드의 분리 원칙

1. 관심사별 분리
2. 변경 가능성에 따른 분리

- 자주 변하는 것과 덜 변하는 것 구분

3. 공통(중복) 코드 분리

- 반복되는 코드는 별도 모듈로 분리

## Controller의 관심사 분리

기존의 문제점: 여러 메서드에서 반복되는 패턴

1. 력(request.getParameter())
2. 처리(비즈니스 로직)
3. 출력(응답 생성)

## MVC 패턴으로의 분리

- Model: 데이터와 비즈니스 로직
- View: 화면 출력 담당
- Controller: 요청 처리와 흐름 제어

### 스프링 MVC의 처리 흐름

1. Controller: 입력값을 매개변수로 받음
2. Model 객체 생성 및 작업 처리
3. Model에 결과 데이터 저장
4. View 이름 반환
5. DispatcherServlet이 Model을 View에 전달
6. View가 Model의 데이터를 사용하여 화면 생성

이런 구조로 인해:

- 관심사가 명확히 분리됨
- 코드 재사용성 증가
- 유지보수가 용이해짐
- 테스트가 쉬워짐

# ModelAndView

- 모델과 뷰를 합친다.
- 모델을 매개변수로 받는게(dispatcherServlet으로 생성하는게 아니고) 아니고 안에서 만들어준다
- 이후에 결과를 저장하고 뷰를 지정한다음
- return mv로 Model과 View를 전달하고 거기서 모델을 뷰에게 전달한다.
- 잘 안씀

** 지금 내가 model로 사용자가 보낸값을 받는다고 오해했는데 그거는 int year, int month 이렇게 받는거고 model은 view에 그냥 데이터를 나르는 역할 택배상자와 마찬가지이다. model이
참조하는 객체에 사용자에게서 받은 값을 받아서 담아서 View에 보내주는거임
model.addAttribute("year", year); // 이렇게 꺼내서 쓸 수 있게**

# Reflection API

- 런타임에 동적으로 객체를 생성하는 것

# ViewResolver

- 인터페이스 역할, 이를 구현한 애들이 Bean에 등록되어 동작함
- 뷰와 논리적인 뷰 이름간의 연결을 도와줌

# 서블릿과 JSP

서블릿 -> 스프링

1. 상속 제거됨
2. 매개변수 필요한 것만 적으면 된다.
3. 애너테이션 나눠서 처리

기존에는 url 매핑을 클래스 단위로 했었다.:
@controller + RequestMapping

## 서블릿의 생명주기

1. 요청
2. 서블릿 인스턴스 존재 여부 판단(Servlet Context에서 확인)
3. 없다? 서블릿 클래스 로딩 & 인스턴스 생성 -> init() -> 서비스() -> 응답
4. 있다? 서비스() -> 응답

** 첫번째에 만들었으니까 두번째부터는 있다로 바로 간다. **
자식 iv가 있으면 객체가 있다는 소리 <- 이걸로 판단한다.
** 사용자마다 여러 프로그램이 있을 필요가 없고(요청하는 사람마다 다른작업을 하는게 아니기 때문에) 재사용 가능하다. 따라서 싱글톤(하나의 객체를 가지고)으로 되어 있다. **

### 서블릿은 싱글톤인데 여러 사용자가 동시에 같은 요청을 보내도 괜찮은 이유?

- 서블릿이 멀티스레드 환경에서 동작하도록 설계되어 있기 때문이다.
- 서블릿내에 iv가 없기 때문에 싱글톤이여도 괜찮음 전부 lv이기 때문에.

#### iv여도 괜찮은 경우

- 상수
- 다른 객체를 멤버로 가지고 있는데 이 객체에 iv가 존재하지 않는 경우

## JSP와 서블릿의 비교

JSP: 자바 서버 페이지(HTML안에 자바 코드가 있다) = 서블릿(자바 코드 안에 HTML)으로 자동으로 변환된다

- JSP 페이지가 있을때
  JSP페이지의
  <%! ~ %>는 전부 class 안으로 이동(iv, cv)
  <% ~ %>는 전부 service 안으로 이동(lv) : 서비스 메서드의 내부로 들어간다.
  JSP페이지는 따로 맵핑해줄 필요가 없다. 자동으로 맵핑됨
  JSP페이지 호출만 해주면 된다.

## JSP의 호출 과정

JSP파일이 언제 서블릿으로 변환되는지 알아보자
모든 .jsp 확장자인 요청이 들어오면 JSP서블릿이 무조건 다 받아서 서블릿 인스턴스가 존재하는지 확인
없다. ? -> JSP 파일을 서블릿 소스 파일로 변환 -> 컴파일하면 클래스 파일이 만들어지고 -> 다음 객체를 생성하고 -> 인스턴스를 생성(초기화 메서드 실행) -> 서블릿 객체가 만들어지면
존재한다. ? -> 서블릿 객체가 만들어짐
첫 호출때는 객체 생성을 다 해놓는다. -> 처음에는 응답시간 오래걸림 --> 늦은 초기화 lazy - init
두번째부터는 서비스가 응답해주니까 훨씬 빠름
JSP파일이 변경되면 다시 과정을 거쳐야 한다.
때문에 스프링은 이걸 개선하고자 early-init으로 바꿈
둘다 싱글톤이지만 초기화에 있어서 차이가 있다.

## JSP의 기본객체

기본객체: 생성하지 않고 사용할 수 있는 객체 -> 서비스 메서드에 지역변수로 이미 다 선언이 되어 있음
request는 기본객체이기 때문에 생성없이 사용가능하다.

## 유효 범위와 속성

### HTTP의 특징

- 상태 정보를 저장하지 않는다.(=stateless)
  stateful

## 4개 저장소

1. 접근범위
2. 생존기간
   이렇게 두가지로 분류할 수 있다.
   저장소는 Map으로 되어 있어서 여기에 data를 저장

1. pageContext (lv저장소 / 기본객체 포함)
   여기에 저장했다가 써야함, 다른페이지에서 접근 안됨, 요청할때마다 초기화 된다. 상태저장 x
2. application(전역저장소)
    - 쓰기 (저장), 읽기
      -> 접근 범위: web app 전체에서 접근 가능한 저장소, 1개만 존재 -> 전역 저장소
      접근이 쉬워서 좋지만 id, pwd 저장하기에는 적합하지 않다.
3. session
    - client마다 1개씩 있는 개별 저장소(client를 위한 저장소)
    - 쿠키를 이용해서 이 세션 객체가 누구건지 연결해주는 역할
    - 사용자수만큼 늘어나기 때문에 부담이 크다. 최소한의 Data만 저장해야함
4. request(하나의 JSP 파일을 보낸다! request안에 Map을 가지고 있다.)
    - servlet-context: 요청시 마다 생기고 서로 독립적이다.
    - 포워드가 가능하다.
    - 다른 page data전달 시 request 객체를 사용하는게 좋다. 세션을 사용하는 방법도 있지만 부담이 크다.

### page, request, session, application 정리

App(공유) > session(개별); 서버부담 높다, 잠깐 썻다가 지우는것도 낫밷 > request: 가능하면 request에 저장하는게 가장 좋다 > page(EL(Expression Language: 값을
간단히 하기 위한 언어)때문에 사용)
웹 프로그래밍: page들간의 이동, 이동할때마다 데이터 전달이 필요한데, request는 요청이 처리되는동안만 존재하기 때문에 최대한 이거먼저 고려해야한다.

# URL패턴

@WebServlet으로 서블릿을 URL에 매핑할 때 사용

1. exact mapping: 정확히 일치
2. path mapping: 경로
3. extension mapping (확장자)
4. 디폴트
   loadOnStartup = 미리 객체 만들어두는 옵션 (DispatcherServlet등등)

## 서블릿의 등록

1. web.xml에 등록 (전통적인 방법)
2. 어노테이션으로 등록 (최근 방법)

- 서블릿의 등록 시 children에 저장됨, 그리고 url 패턴하고 서블릿을 연결해준다.
  --> 스프링에서는 이걸 안쓰고 유사한 DispatcherServlet을 내부에 가지고 있다.(스프링에서는 모든 요청을 이걸로 받음)
- 서블릿은 요청을 받으면 전부 children Map에 저장이 된다. -> 서블릿 등록
- 스프링에서는 DispatcherServlet이 다 받는다. (appServlet이라는 이름으로 등록 web.xml)
  load on startup = 미리 만들어두는 옵션

## EL, JSTL

## Filter - 코드 중복과 관리

요청과 응답을 처리하는데 사용되는 Java 클래스
서블릿, JSP로 가기 전에 요청을 가로채거나 응답이 클라이언트로 전달되기 전에 데이터를 수정, 로깅, 인코딩, 인증, 권한체크 하는데 사용
** 스프링 시큐리티가 이걸 활용한다. **

## @RequestParam @ModelAttribute @RequestMapping

### @RequestParam

- 요청의 파라미터를 연결할 매개변수에 붙이는 애너테이션, 생략가능

# Spring MVC의 데이터 바인딩과 어노테이션

## 입력값 자동 바인딩

입력되는 값이 year, month, day인데 전혀 관계 없는 타입의 MyDate를 선언해도 이걸 알아서 넣어주는 원리:

1. 요청한 값들이 HashMap으로 들어온다.
2. MyDate라는 타입이 있을 때 bind()메서드가 Map하고 연결해준다.
    - 타입으로 넘어온 클래스 정보를 가지고 객체를 만든다.
    - MyDate 인스턴스의 setter를 호출해서, Map의 값으로 MyDate 초기화
        - MyDate 모든 인스턴스 변수 돌면서 Map에 존재하는지 찾는다.
        - 찾으면 찾은 값을 setter로 객체에 저장
        - setter가 없으면 스프링이 자동으로 처리해주지 못한다. setter는 필수!

## ModelAttribute

- 적용대상을 Model의 속성으로 자동 추가해주는 어노테이션
- public String main(@ModelAttribute MyDate date, Model m) 이렇게 쓰면 Model Map에 MyDate를 키로 자동 저장
- 수동으로 안써주면 첫글자 소문자로 바꿔서 키에 넣음
- 반환타입과 controller 메서드의 매개변수에 붙일 수 있음

## 컨트롤러의 매개변수 어노테이션

1. @RequestParam
    - 기본형, String일 때 생략 가능
    - view에서 바로 ${param.파라미터이름} 이렇게 참조 가능해서 model에 저장 불필요
    - 단순한 값, 단일 파라미터를 받을 때 주로 사용

2. @ModelAttribute
    - 참조형일 때 생략 가능
    - 여러 매개변수를 객체로 묶어서 받을 때 사용
    - 폼 데이터가 객체의 프로퍼티와 일치할 때 자동 매핑

## WebDataBinder

- 브라우저 요청값이 실제 객체에 binding될 때 중간 역할
- 쿼리스트링(year=2021&month=10&day=1)이 Map의 value 값으로 들어감
- Value가 String이고 MyDate에 타입이 int일 때:
    - 타입 변환 및 데이터 검증 수행
    - 변환/검증 결과를 BindingResult에 저장
    - BindingResult를 컨트롤러에 전달 가능
    - BindingResult 매개변수는 바인딩할 매개변수 바로 뒤에 위치해야 함

# 스프링 회원가입 화면 구현 TIL

## 스프링 컨텍스트 설정

- **servlet-context**: 웹 관련 설정을 담당하는 설정 파일
  ex) view Resolver, Controller Scan etc.. Dispatcher Servlet 관련 요청/응답 처리

- **root-context**: 애플리케이션 전역에서 사용되는 설정 관리
  ex) DB, 서비스, Repository, 관련설정 etc.. Application 전역에서 공유되는 Bean

### Pom.xml

- Maven의 프로젝트 설정파일.(빌드 및 의존성을 담당하는 파일)
- Spinrg-webmvc 의존성 주입

### web.xml

- DispatcherServlet 설정

### 정적 리소스 설정

resources 경로 설정을 제거하면, 정적 리소스를 불러올 때 resources 경로를 명시하지 않아도 됩니다.

## Form 구현

Form은 사용자로부터 데이터를 입력받기 위한 HTML 요소입니다. submit 이벤트가 발생하면 form의 내용이 지정된 주소로 전달됩니다.

### Form 예시

```html

<Form action="<c:url value="/register/save"/>" method="POST" onsubmit="return formCheck(this)">
```

### Form 속성 설명

- **action**: 폼 데이터를 전송할 URL을 지정
    - 미지정시 현재 URL로 전송됨 (default)
- **method**: HTTP 요청 메서드 지정
    - default: GET
- **autofocus**: 페이지 로드시 해당 요소에 자동으로 포커스
- **c:url 태그**:
    - context root 자동 추가
    - session id 자동 추가 기능

## 컨트롤러 매핑

### RequestMapping

`@RequestMapping`의 value 속성을 통해 URL 매핑을 수행합니다.

## Redirect vs Forward

### Redirect (302)

- HTTP 상태코드 302 응답
- 응답 body가 없음
- Location 헤더의 URL을 브라우저가 자동으로 새로 요청

- 요청흐름

1. 사용자가 login으로 POST요청을 보낸다.
2. redirect 응답: 서버가 redirect:/login/login... (해당 return)으로 응답.
3. GET요 요청 Redirect URL로

# 쿠키란?

- 이름과 값의 쌍. 아스키 문자만 가능
  이름은 알파벳 + 숫자, 값은 공백, 세미콜론을 제외, 한글은 URL 인코딩/디코딩이 필요하다.
  클라이언트가 요청하면 서버에서 생성 후 응답헤더에 넣어서 전송, 브라우저에 저장되어 클라이언트가 관리한다.

``` 
Cookie cookie = new Cookie("id","abc");
response.addCookie(cookie);
이렇게 서버에 요청.
```

- 이후부터는 클라이언트가 서버에 요청을 하면 자동으로 그 쿠키가 요청헤더에 담긴다.
  원래 서버는 클라이언트의 정보를 저장하지 않는 stateless 프로토콜이지만
  ** 이때부터 서버는 클라이언트를 식별할 수 있다. (쿠키의 존재 이유)**

# 세션이란?

- 서로 관련된 요청 (HTTP Transactions 요청/응답)을 묶어 놓은 것.
- 브라우저마다 개별 저장소(Session 객체)를 서버에서 제공.
- 세선에는 로그인 정보, 상태, 사용자 정보 등 클라이언트 정보가 담긴다

## 브라우저 와 서버의 요청/응답

- 첫 요청에는 쿠키가 없음
- 첫 응답에 세션 ID를 쿠키에 저장해서 Return -> 첫 요청에 세션 객체가 생성된다
- 다음 요청부터 쿠키 포함 = 다음 요청 부터는 같은 세션으로 그룹화 된다. -> 같은 세션에 있는 동안은 세션 저장소(객체)를 사용한다.
- 원래 각 요청은 독립적이나 쿠키를 보내 세션 ID를 주니까 요청에 세션ID가 따라 붙는다.

- 세선을 끝내는 방법은 1.수동종료 2.timeout 두가지가 있다.
- Login ~ Logout 까지가 같은 세션이라고 생각하면 편함.
- 세션 객체마다 ID를 가지고 있다.(응답에 담아서 보냄)
- 세션은 부담이 커서 꼭 필요할떄만 사용하는것이 좋다.

- 소규모 서비스는 클라이언트와 서버가 1:1 이라 session이 하나니까 데이터를 세션에서 관리해도 괜찮다
- 대규모 서비스는 세션간의 동기화가 필요하여 서버 다중화에 불리하다. 때문에 쿠키를 암호화해서 사용한다.

# DAO란?

**DAO (Data Access Object)**는 데이터에 접근하기 위한 객체로, 실제로 DB에 접근하여 데이터를 처리하는 역할을 담당합니다.

- **역할**: 서비스와 DB를 연결하는 역할
- **기능**: DB에 저장된 데이터를 읽기, 쓰기, 삭제, 변경
- **구성**: 테이블당 하나씩 작성하여 데이터 처리 수행

---

## DTO란?

**DTO (Data Transfer Object)**는 계층 간 데이터를 교환하는 역할을 수행하는 객체입니다.  
DB에서 가져온 데이터를 저장하는 **Entity**를 기반으로 만들어지는 **Wrapper** 객체입니다.

- **특징**:
    - 특별한 로직 없이 순수한 데이터 객체
    - 데이터 변경 방지를 위해 `setter`를 사용하지 않고 생성자에서 값을 할당
    - **도메인 모델을 캡슐화**하여 보안을 강화
- **실무적 권장사항**:
    - Controller와 Service 각각에 DTO를 따로 만들어 사용 (의존성을 약화시키기 위해)

### DTO의 사용 이유

1. **UI 로직과 엔티티 분리**:
    - `getter`만으로 데이터를 표현하는 데 한계가 있을 수 있음.
    - 엔티티와 DTO가 분리되지 않을 경우, UI를 위한 로직이 엔티티에 추가될 수 있음.

2. **유지보수성 향상**:
    - 엔티티는 비즈니스 로직을 표현하며, DB 테이블과 밀접한 관계를 가짐.
    - UI 로직 추가는 엔티티의 역할을 복잡하게 하고, 유지보수를 어렵게 만듦.

---

# 계층 (Layer)의 분리

컨트롤러에서 직접 DB에 접근할 수도 있지만, 계층 분리를 통해 중복 제거 및 코드 유지보수를 용이하게 합니다.

### 예시: 로그인과 회원가입 기능 구현

- 로그인: `id`와 `pwd`를 체크하는 메서드
- 회원가입: `id`, `pwd`를 체크하고, **Insert**, **Delete**, **Update**, **Select** 메서드 필요

#### 문제점

- `id`, `pwd` 체크 메서드가 중복됨.

#### 해결책: `UserDao` 사용

- 공통 부분을 `UserDao`로 분리하여 계층을 나눕니다.

- **Controller**:
    - 데이터를 보여주는 역할 (Presentation Layer)
- **UserDao**:
    - 데이터 접근 계층 (Persistence Layer)

---

### 계층 분리의 핵심 원칙

1. **관심사의 분리**:
    - Presentation Layer와 Persistence Layer의 역할을 명확히 분리.
2. **불변과 가변의 분리**:
    - 데이터 구조와 비즈니스 로직을 분리하여 안정성 확보.
3. **중복 코드의 제거**:
    - 공통 기능을 한 곳에 모아 코드 중복을 최소화.

**결론**: `DAO`는 **중복 제거**와 **변경에 유리한 설계**를 위해 분리합니다.

---

# 추가 키워드

### AOP (Aspect-Oriented Programming)

- 횡단 관심사를 분리하여 코드 중복을 줄이고 모듈성을 높이는 프로그래밍 기법.

### DI (Dependency Injection)

- **Setter 주입**, **생성자 주입** 등을 통해 객체 간 의존성을 관리하고 결합도를 낮춤.

### Reflection

- 런타임에 객체의 속성과 메서드에 접근하거나 수정할 수 있는 기술.

# Transactiio이란?

- 더이상 나눌 수 없는 작업의 단위

하나의 단위로 움직여야 하는 것들
예시)출금과 입금

# Transaction의 속성

1. 원자성: 나눌 수 없는 하나의 작업으로 다뤄져야 한다.
2. 일관성: 수행 전과 후가 일관된 상태를 유지해야 한다.
3. 고립성: 각 Tx는 독립적으로 수행되어야 한다.
   -> Tx마다 Isolration레벨이 다르다.
4. 영속성: 성공한 Tx의 결과는 유지되어야 한다.

## Tx의 isolation level

1. READ UNCOMMITED - 커밋되지 않은 데이터도 읽기 가능 -> dirty read
   ![[스크린샷 2024-12-11 오전 11.23.07.png]]
   Tx2 에서 Insert를 하고 나서 커밋 전에도 Tx1에서 Select 하면 커밋전인 내용을 select 가능

## READ COMMITED

커밋된 데이터만 읽기 가능 - phantom read

![[스크린샷 2024-12-11 오전 11.25.41.png]]

## REPEATABLE READ - 반복해서 읽기 가능

Tx의 시작 후 다른 Tx의 변경은 무시된다.
다른 Tx의 영향을 받지 않는다.

## SERIALIZABLE - 한번의 하나의 Tx만 독립적 수행

아주 중요한 애들은 이걸로한다.
성능 떨어지지만 품질이 올라감
다른 Tx가 수행중이면 대기함

# 커밋

작업 내용을 DB에 영구적으로 저장
커밋이 되고 나서는 롤백이 불가하다.

## 자동 커밋

이게 default 임
명령을 수행 후 자동으로 커밋이 수행되는 것(롤백이 불가하다.)

## 수동 커밋

명령 실행 후 명시적으로 커밋 또는 롤백을 입력

# 롤백

최근 변경사항을 취소(마지막 커밋으로 복귀)



