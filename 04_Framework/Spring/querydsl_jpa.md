# Querydsl

SQL 형식의 쿼리를 Type Safe하게 생성할 수 있도록 하는 라이브러리

---

# Qclass

엔티티 클래스 속성과 구조를 설명해주는 메타데이터.
Type Safe하게 쿼리 조건 설정 가능

### QClass 생성 위치 지정

생성위치를 `build/` 디렉토리 하위로 지정함으로써, 별도의 `.gitignore` 설정 없이도 불필요한 QClass가 Git에 포함되는 것을 방지하며 협업 시 위치를
강제하는 효과가 있다.

```build.gradle
def generated = 'build/generated/sources/annotationProcessor/java/main'  
  
tasks.withType(JavaCompile).configureEach {  
    options.getGeneratedSourceOutputDirectory().set(file(generated))  
}  
  
clean {  
    delete file(generated)  
}
```

# JPAQueryFactory Bean 등록

```
@Configuration  
public class QuerydslConfig {  
    @PersistenceContext  
    private EntityManager entityManager;  
  
    @Bean  
    public JPAQueryFactory jpaQueryFactory() {  
        return new JPAQueryFactory(entityManager);  
    }  
}
```

Querydsl이 JPA의 EntityManager를 사용해 쿼리를 실행할 수 있도록 멀티스레드 환경에서도 thread-safe한 JPAQueryFactory를 Bean으로 등록하는 과정

# 구현 방식의 변경

- 기존의 방식
    1. Interface 작성: MemberRepositoryCustom (추상 메서드 정의)
    2. Implementation 작성: MemberRepositoryImpl (실제 Querydsl 로직 구현)
    3. Final Repository: MemberRepository extends JpaRepository, MemberRepositoryCustom

  이러한 기존의 방식은 파일 개수가 늘어나 관리 포인트가 많다. 하지만 JPAQueryFactory를 빈으로 등록해두었다면 굳이 인터페이스 상속구조를 따를 필요가 없다.


- 상속 없는 레포지토리 권장방식 예시

```java

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final JPAQueryFactory queryFactory; // 빈으로 등록된 객체를 직접 주입

    public List<Member> search(MemberSearchCondition condition) {
        return queryFactory
                .selectFrom(member)
                .where(nameEq(condition.getName()))
                .fetch();
    }
}
```

# 서브쿼리

QueryDSL에서 서브쿼리를 작성할 때는 `JPAExpressions`를 사용한다.

### 사용예시

```
JPQLQuery<Long> subQuery = JPAExpressions
        .select(reviewProduct.product.id)
        .from(reviewProduct)
        .where(ratingBuilder);

BooleanBuilder builder = new BooleanBuilder();

builder.and(product.id.in(subQuery));

List<Product> results = queryFactory
        .selectFrom(product)
        .where(builder)
        .fetch();
```

# 동적 정렬

동적 정렬은 정렬 조건이 고정되지 않고 상황에 따라 바뀌는걸 의미한다. Querydsl의 동적 정렬은 OrderSpecifier 객체를 사용.

### 사용예시

```
// 1. 정렬 방향 결정 (Order.ASC 또는 Order.DESC)
Order direction = isAscending ? Order.ASC : Order.DESC;

// 2. 어떤 필드를 기준으로 할지 결정 (예: product.price)
OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(direction, product.price);

// 3. 쿼리에 적용
queryFactory
    .selectFrom(product)
    .orderBy(orderSpecifier)
    .fetch();
```

### 다중 정렬 가능

- `OrderSpecifier[]` 배열을 사용하여 여러 정렬 조건을 순차적으로 적용
- 첫번째 정렬 조건이 같은 레코드에 대해 두 번째 정렬 조건이 적용됨
- 정렬할 필드가 많아질 경우 FileSort(인덱스를 타지 않는 정렬)이 발생해 성능저하를 유발 가능

### Pageable과의 통합

- Spring Data의 `Pageable`에서 제공하는 `Sort` 객체를 사용하여 동적으로 `OrderSpecifier` 생성 가능

### 주의사항

- 정렬 조건을 설정하지 않으면 `null`이 반환되어 오류 발생 가능
- 항상 기본 정렬 조건을 설정하는 것이 안전한 방법

# 동적쿼리

### BooleanBuilder

동적으로 누적시키는 방식. 해당 조건이 존재하는지 확인하는 if문이 필요.

```
BooleanBuilder builder = new BooleanBuilder();

if (name != null && !name.isEmpty()) {
builder.and(member.name.eq(name));
}
if (age != null) {
builder.and(member.age.eq(age));
}
if (status != null) {
builder.and(member.status.eq(status));
} 
...
```

위 코드의 가독성을 optional을 사용해 if문을 제거함으로서 개선 가능

### BooleanExpression

Private 메서드로 만들어 Where()절에 (,)로 나열하면 AND로 동작해 자동으로 null을 무시한다.
모든 조건이 null이 되면 테이블 전체 스캔을할 가능성이 있다. 때문에 해당경우에 대해서 추가 처리가 필요.
(페이징 최대 결과 수 제한, 서비스 레이어에서 검색 조건의 유효성을 검증하는 방식 등)

# Select절의 EXISTS 메서드

EXISTS 메서드는 count(1)>0 보다 훨씬 빠르다. EXISTS는 타겟을 찾으면 종료되지만 COUNT는 타겟을 찾아도 끝까지 스캔하기 때문인데,
querydsl의 경우에는 EXISTS를 사용하지 않고 fetchcount를 사용한다.(JPQL 표준스펙이 Select절에서의 EXISTS를 지원하지 않기 때문)
하지만 버그가 발견되어 5.0부터 fetchcuont가 deprecated가 되어 직접 구현한다.

### 구현 예시

```
boolean exists = queryFactory
                    .selectOne()
                    .from(member)
                    .where(member.id.eq(1L))
                    .fetchFirst() != null;
// 조회 결과가 없으면 null 이 반환되기 때문에 null 체크
```

fetchFirst는 내부적으로 limit(1).fetchOne()과 동일하다.

참고

- 향로님의 테코톡 Querydsl 편
- 테코톡 바론, 블랙캣의 Querydsl with JPA
