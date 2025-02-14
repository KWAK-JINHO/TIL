# JPA란?

자바 ORM(Object RelationShip Mappping: 객체와 관계형 DB 매핑)기술에 대한 API 표준 명세.

개발자가 CRUD문을 작성할 때 자바 객체를 애플리케이션과 DB 사이에서 넣고 뺄 때 매핑을 반복해야 하는 문제가 발생 -> 객체는 OOP적으로, DB는 DB대로 설계 후 ORM이 중간에서 2개를 매핑하는 역할.

JPA는 ORM기술의 표준 명세로 인터페이스이고, 이를 구현한 대표적인 구현체인 Hibernate를 사용

# JPA 작동방식

- 애플리케이션 실행
- EntityManagerFactory 생성  (애플리케이션 시작 시 한번만 생성된다.)
- EntityManager생성 (트랜잭션 단위로 생성 후 닫는다.)
- persist(): 1차 캐시에 저장. ( 아직 데이터베이스에는 저장되지 않은 상태, 쓰기 지연 적용)
    - 쓰기 지연: JPA는 쓰기 지연 SQL 저장소에 커밋전까지 SQL를 저장했다가 Tx 커밋 시점에 한번에 실행
- 트랜잭션을 `commit`하면 영속성 컨텍스트에 있는 엔티티가 데이터베이스에 반영됨.
- find(): 1차 캐시에서 바로 조회하고, 없으면 DB에서 가져온다.
- DB에서 가져온 엔티티는 PC에서 관리대상이 된다.(영속 상태)
- 변경 감지(더티 체킹): commit() 시 변경된 엔티티는 자동으로 UPDATE 실행
- 엔티티들은 변경감지 되는 상태이므로 set 으로 변경하면 JPA가 자동 감지함
- remove(): 삭제 후 commit() 시 DELETE 반영
- commit(): 쓰기 지연 SQL실행, DB반영 후 트랜잭션 종료
- EntityManager.close()

## 영속성 컨택스트

-Entity Manager에 의해 관리되며 JPA의 모든 동작이 이 안에서 이루어진다.
-Map으로 되어 있다.
-Key는 엔티티의 PK, Value에 엔티티 객체 자체가 저장된다.

## 2차 캐시

영속성 컨텍스트의 1차 캐시를 보완하기 위해 제공되는 전역적인 캐시
1차 캐시는 EntityManager 단위에서만 작동하지만 2차 캐시는 애플리케이션 범위에서 공유, 동일한 엔티티를 여러 EntityManager에서 재사용할 수 있도록 한다.

Hibernate에서는 @cache 어노테이션으로 지원한다.

읽기전용, 읽기-쓰기(데이터 변경시 캐시 갱신), 비관적 잠금(데이터 변경시 캐시 무효화, 갱신은 안한다.)

장점: 성능 향상, 트래픽 감소
단점: 데이터가 자주 변하면 일관성 유지 어려움, 캐시가 커지면 메모리 사용량 증가

# DAO(data accsess object) vs Repository

## DAO

SQL 최적화가 중요한 경우에 사용
ORM 사용하지 않는 경우 사용
레거시 시스템 -> 기존의 코드가 이미 사용중일 때

## Repository

도메인 중심의 설계(DDD)
ORM 기술(Hibernate)에 의존적이다.
SQL추상화, 객체(Entity) 기반 작업 -> 쿼리메서드, JPQL, Querydsl
빠른 개발, 유지보수에 용이

#### 두 개념은 통합 사용이 가능하다. Repository를 통해 객체 중심의 데이터 접근을 처리, DAO를 통한 복잡한 SQL쿼리 작성

# JPA가 지원하는 쿼리

![[스크린샷 2024-12-05 오후 12.42.26.png]]

## 쿼리 메서드

메서드 이름을 통해 간단하게 쿼리를 작성할 수 있다.

### 사용법

JpaRepository<Post, Long>를 상속하여 사용 가능

### 특징

- 간단한 조회 기능에 적합
- 메서드 이름이 길어질 수 있다.
- 복잡한 쿼리 작성은 어려움

## JPQL

JPA에서 제공하는 객체지향 쿼리 언어
엔티티 객체와 매핑된 속성을 기준으로 SQL과 유사한 쿼리를 작성
SQL과 유사하지만 DB테이블이 아니라 엔티티를 대상으로 작업한다.

### 특징

- SQL과 비슷한 구문으로 사용하기 쉽다.
- 복잡한 쿼리 작성 가능
- 엔티티에 의존하므로 변경 사항 반영이 쉬움

### 사용법

@Query 어노테이션을 사용해 JPQL 쿼리를 정의
SQL처럼 보이지만 엔티티와 관련된 객체를 대상으로 실행

```
@Query("SELECT p FROM Post p WHERE p.title = :title")
List<Post> findPostsByTitle(@Param("title") String title);
```

## Querydsl

- querydsl은 타입 세이프한 동적 쿼리를 작성할 수 있는 도구
- 쿼리를 코드로 작성하며, 컴파일시점에 문법 오류 체크가 가능
- 복잡한 동적 쿼리를 작성할 때 적합.

### 사용법

```
public List<Post> findPosts(String author, String category) {
    QPost post = QPost.post;

    BooleanBuilder builder = new BooleanBuilder();
    if (author != null) {
        builder.and(post.author.eq(author));
    }
    if (category != null) {
        builder.and(post.category.eq(category));
    }

    return queryFactory
        .selectFrom(post)
        .where(builder)
        .fetch();
}
```

# 엔티티와 데이터베이스 테이블 매핑

## @Entity

클래스에 선언하여 JPA가 관리하는 객체로 등록한다.

### 사용법

- 기본생성자가 반드시 필요
- final, enum, interface 엔티티로 사용할 수 없다.
- @Id(PK)가 반드시 선언되어야 한다.

## @Id

- 엔티티의 기본 키를 지정할 때 사용
- DB테이블의 PK에 매핑
- 주로 Long, Integer, UUID사용

## @GeneratedValue

기본 키의 값을 자동으로 생성하도록 설정

### 사용법

속성을 통해 전략을 선택 가능

- `IDENTITY`: 데이터베이스에 위임 (MySQL의 AUTO_INCREMENT 등)
- `SEQUENCE`: 데이터베이스 시퀀스 사용 (주로 Oracle, PostgreSQL)
- `TABLE`: 키 생성용 테이블을 사용
- `AUTO`: 데이터베이스에 맞게 자동 선택

## @Column

엔티티의 필드를 테이블의 컬럼에 매핑할 때 사용

### 사용법

- `name`: 테이블에서 사용할 컬럼 이름
- `nullable`: `null` 허용 여부 (기본값은 `true`)
- `length`: 문자 길이 지정 (기본값은 255)
- `unique`: 고유 키 설정

```
예시)
@Column(name = "user_name", nullable = false, length = 50)
private String name;
```

## 기본 생성자와 Getter/Setter

- JPA는 프록시 객체를 생성하거나 DB에서 조회된 데이터를 바탕으로 엔티티를 생성할 때 리플렉션을 사용하고 리플렉션은 기본생성자를 통해 객체를 생성
- 따라서 매개변수가 없는 public 또는 protected 기본 생성자가 필요하다.

#### 프록시 객체는 실제 엔티티를 대신하는 가짜 객체라고 생각하면 된다.

JPA는 엔티티를 조회할 떄 프록시 객체를 생성, 프록시 객체는 실제 엔티티를 대신하여 동작하며, 필요한 시점에 DB에서 데이터를 가져와 채운다.

*** 보통 지연로딩을 위해 프록시 객체를 사용하는 것이 기본설정이며, 즉시 로딩 설정이 되어 있거나 프록시 생성 비활성화하면 프록시 객체를 생성하지 않는다.

*** 프록시 객체가 필요한이유

- 지연로딩(Lazy Loading): 모든 연관된 엔티티를 한꺼번에 조회하는 것이 아니라 필요한 시점에 DB에서 조회하여 성능을 향상시키기 위해 사용
- 프록시 객체는 실제 엔티티를 상속하기 때문에 실제 엔티티처럼 사용할 수 있다.
  -> 성능, 메모리 측면에서 좋으나 초기화 시점을 알기 어렵고 지연 로딩 시 예외가 발생할 수 있어 조심행함

사용예시) @ManyToOne(fetch = FetchType.LAZY)

## @Table(선택사항)

엔티티가 매핑될 테이블 이름이나 세부정보 지정

### 주요 속성

- `name`: 테이블 이름
- `schema`: 스키마 이름
- `uniqueConstraints`: 유니크 제약 조건
  예시)

```
@Entity 
@Table(name = "users") 
public class User {
	...
}
```

# 연관관계 매핑

## 연관관계 종류

단방향 매핑
양방향 매핑

## 주요관계

### **일대일(@OneToOne)**: 두 엔티티가 1:1로 연결

### **일대다(@OneToMany)**: 하나의 엔티티가 여러 엔티티와 연결

- 예시) 사용자가 여러 개의 주문을 가질 수 있음.

```
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user") // 연관관계의 주인은 Order의 user 필드
    private List<Order> orders = new ArrayList<>();
}

```

mappedBy

- 연관관계의 주인을 지정.
- 주인은 실제 외래 키를 관리하는 엔티티.

### **다대일(@ManyToOne)**: 여러 엔티티가 하나의 엔티티와 연결

- 예시) 주문(`Order`)은 하나의 사용자(`User`)와 연결됨.

```
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // 외래 키 컬럼 지정
    private User user;
}

```

@JoinColumn

- 외래 키를 매핑할 컬럼 이름 지정.
- 기본값은 `{참조 객체의 필드명}_{참조하는 엔티티의 기본 키}`.

# 트랜잭션

- 더이상 나눌 수 없는 작업의 단위
- 하나의 단위로 움직여야 하는 것들 예시) 출금과 입금

## 속성

- 원자성: 나눌 수 없는 하나의 작업으로 다뤄져야 한다.
- 일관성: 수행 전과 후가 일관된 상태를 유지해야 한다.
- 고립성: 각 Tx는 독립적으로 수행되어야 한다.
  -> Tx마다 Isolration레벨이 다르다.
- 영속성: 성공한 Tx의 결과는 유지되어야 한다.

### Tx의 isolation level

1.READ UNCOMMITED
커밋되지 않은 데이터도 읽기 가능 -> dirty read
![[스크린샷 2024-12-11 오전 11.23.07.png]]
Tx2 에서 Insert를 하고 나서 커밋 전에도 Tx1에서 Select 하면 커밋전인 내용을 select 가능

2.READ COMMITED
커밋된 데이터만 읽기 가능 - phantom read
![[스크린샷 2024-12-11 오전 11.25.41.png]]

3.REPEATABLE READ - 반복해서 읽기 가능
Tx의 시작 후 다른 Tx의 변경은 무시된다.
다른 Tx의 영향을 받지 않는다.

4.SERIALIZABLE - 한번의 하나의 Tx만 독립적 수행
아주 중요한 애들은 이걸로한다.
성능 떨어지지만 품질이 올라감
다른 Tx가 수행중이면 대기함

## @Teansactional

- Spring환경에서 트랜잭션을 쉽게 관리 할 수 있게 해준다.
- 메서드 또는 클래스에 선언하면 해당 메서드 실행시 자동으로 트랜잭션이 시작, 종료 시 커밋 또는 롤백이 수행

```
@Service
public class UserService {

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
        // 예외 발생 시 자동 롤백
    }
}
```

- Spring은 AOP를 이용해 트랜잭션을 관리하므로 명시적인 `begin`이나 `commit` 코드가 필요 없다

## @Teansactional 주요 속성

### propagation (전파 레벨)

기존 트랜잭션이 있으면 사용할지, 새로 생성할지 결정.

- 기본값: `REQUIRED`: 기존 트랜잭션을 사용하거나 없으면 새로 시작.
- `REQUIRES_NEW`: 항상 새로운 트랜잭션 생성.
- `MANDATORY`: 기존 트랜잭션이 없으면 예외 발생.
- `NESTED`: 중첩 트랜잭션 생성.

### **isolation (격리 수준)**

여러 트랜잭션이 동시에 실행될 때 데이터의 일관성을 보장하는 수준.

- `READ_UNCOMMITTED`: 커밋되지 않은 데이터도 읽을 수 있음.
- `READ_COMMITTED`: 커밋된 데이터만 읽음.
- `REPEATABLE_READ`: 트랜잭션 동안 동일한 데이터를 읽도록 보장.
- `SERIALIZABLE`: 가장 높은 수준의 격리, 동시성 성능 저하.
