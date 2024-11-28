# HTTP와 웹 애플리케이션의 동작 원리

## 기본 요청 처리 흐름

1. 클라이언트가 URL 요청
2. Tomcat이 요청을 받고 HttpServletRequest 객체 생성
3. 요청 받은 정보를 객체에 담음
4. main 메서드 매개변수로 전달 (HttpServletRequest request)
5. request 객체를 통해 요청 정보 접근 가능

## Spring MVC 기본 어노테이션

* `@Controller`: 해당 클래스가 웹 요청을 처리하는 컨트롤러임을 명시
* `@RequestMapping`: URL 경로와 처리 메서드를 매핑

## 요청 처리 과정

1. 메인 메서드 호출 및 매개변수 전달
2. 비즈니스 로직 처리
3. 결과 출력 (response 객체의 출력 스트림 사용)

* **스트림**: 데이터 흐름을 처리하는 통로

## Content Type 설정

```java
response.setContentType("text/html")
```

* 브라우저에게 전송하는 데이터 타입을 명시

## 클라이언트-서버 통신 흐름

1. 클라이언트의 HTTP 요청
2. 톰캣 8080 포트로 요청 수신
3. HTTP 요청을 파싱하여 ServletRequest 객체 생성
4. URL 분석 및 담당 서블릿 매핑
5. 매핑된 서블릿 실행
6. GET/POST 메서드 호출
7. 비즈니스 로직 처리

## Tomcat 아키텍처

* **Thread Pool**: 다수의 요청을 동시 처리하기 위한 스레드 집합
* **계층 구조**:
* Service: 전체 서비스 컨테이너
* Engine: 요청 처리 엔진
* Host: 도메인별 구분
* Context: 개별 웹 애플리케이션
* Servlet: 실제 요청 처리 컴포넌트

* **프로토콜 처리**: HTTP/1.1, HTTP/2, AJP 등 지원
* **서블릿(DispatcherServlet)**:

1. 요청 접수
2. @RequestMapping 기반 컨트롤러 매핑
3. 결과 전달

## WAS(Web Application Server)

* 과거: 클라이언트 설치 프로그램 → 업데이트 어려움
* 현재: 중앙 서버 기반 (Front Controller 패턴)

### Tomcat 주요 설정 파일

1. server.xml: 서버 전체 설정
2. 서버의 web.xml: 공통 설정
3. 프로젝트의 web.xml: 개별 설정

### 서블릿 등록 방식 변화

* 과거: web.xml에 직접 등록
* 현재: 어노테이션 기반
* @Controller
* @RequestMapping

## HTTP 프로토콜

### 주요 특징

* 텍스트 기반의 단순한 프로토콜
* Stateless(무상태)
* 보완책: 세션과 쿠키
* 확장 가능한 헤더 구조

### HTTP 메시지 구조

1. Status Line: 프로토콜/상태코드/설명
2. Header: 메타 정보
3. 빈 줄: 헤더와 바디 구분
4. Body: 실제 데이터

### HTTP 메서드

#### GET (읽기)

* 리소스 조회 목적
* Body 없음
* Query String으로 데이터 전달
* 특징: 보안 취약, 공유 용이

#### POST (쓰기)

* 데이터 생성/수정 목적
* Body에 데이터 포함
* 크기 제한 없음
* 특징: 상대적 보안, 공유 어려움

### HTTPS

* HTTP + TLS(암호화) 조합
* 보안 강화된 프로토콜

- form 태그를 써야하는데 Post man이라는 확장프로그램으로 반복적인 요청을 테스트 할때 편함

## 텍스트파일과 바이너리파일

바이너리파일: 문자와 숫자가 저장되어 있는파일
쓰기, 읽기 모두 문자는 문자로 숫자는 숫자로 인식

텍스트 파일: 문자만 있는 저장되어 있는 파일 ->  읽기 쉽다
쓰기: 문자는 문자 숫자는 문자로 쓴다.
읽기: 문자는 문자

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

스프링에서는 두 가지 주요 설정 파일이 있습니다:

- **servlet-context**: 웹 관련 설정을 담당하는 설정 파일
- **root-context**: 웹과 관련되지 않은 설정을 담당하는 설정 파일

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