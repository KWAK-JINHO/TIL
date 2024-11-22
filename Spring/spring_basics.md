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