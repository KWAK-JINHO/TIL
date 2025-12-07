# Spring Boot Summary

# Spring과 달라진 점

## 1. 설정 파일의 차이

### Spring MVC

- web.xml을 통해 DispatcherServlet(이하 D.S.)을 설정하고 ApplicationContext(이하 A.C.)를 초기화.
- root-context.xml, servlet-context.xml을 직접 작성

### Spring Boot

@SpringBootApplicatoin을 사용하면 자동으로 A.C.를 생성하며, 내장 톰켓을 포함하므로 별도 설정이 필요 없다. (SpringApplication.run 실행시 내장톰켓 실행 및 A.C. 생성)

#### @SpringBootApplicatoin

`@Configuration`: Spring의 XML 설정을 Java로 대체, 직접 Bean을 등록할 수 있음
`@EnableAutoConfiguraion`: Spring Boot가 필요할만한 Bean을 자동등록
`@ComponentScan`: 패키지 내의 @Component, @Service, @Controller, @Repository 자동 검색 및 등록

## 2. 의존성 관리 방식

### Spring MVC

- 의존성을 직접 설정해야 하며, 여러 개의 라이브러리를 개별적으로 추가해야 한다.

```
// xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.10</version>
</dependency>
```

### Spring Boot

- spring-boot-starter-* 라이브러리를 통해 필요한 기능을 자동으로 포함

```
// xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## 3. 내장 서버 제공

### Spring MVC

- 일반적으로 Tomcat, Jetty등의 WAS(Web Application Server에 배포해야 한다.)

### Spring Boot

- spring-boot-starter-web을 사용하면 내장 톰캣을 자동 실행

## 기타등등

### Configuration

- SpringMVC는 XML기반 설정이 일반적이며 @Configuration을 사용해 Java기반 설정도 가능
- Spring boot는 기본적으로 자동 설정을 사용하며 필요한 설정은 properties, yml을 이용해 설정 변경 가능.

# 요약

- Spring Boot는 설정을 Map 처럼 관리하고 자동으로 Bean을 등록한다.
- 개발자는 최소한의 설정만 하면 되고, 나머지는 AutoConfiguration이 해결한다.