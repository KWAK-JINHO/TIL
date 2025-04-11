# Querydsl

SQL 형식의 쿼리를 Type Safe하게 생성할 수 있도록 하는 DSL을 제공하는 라이브러리

# Qclass

엔티티 클래스 속성과 구조를 설명해주는 메타데이터
Type Safe하게 쿼리 조건 설정 가능

## QClass 생성 위치 지정

```
def generated = 'build/generated/sources/annotationProcessor/java/main'  
  
tasks.withType(JavaCompile).configureEach {  
    options.getGeneratedSourceOutputDirectory().set(file(generated))  
}  
  
clean {  
    delete file(generated)  
}
```

본인의 IDE 세팅마다 생성 위치가 달라질수 있어 협업시에 위와같이 build.grage 파일에 위치를 지정해주는게 좋다.
build 디렉토리안에 두면 자동으로 git에 올라가지 않는다는 장점이 있다.

# JPAQueryFactory 빈 등록

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

위와 같이 Jpa를 통해 엔티티를 조회하기 때문에 entity매니저가 필요하고 빈으로 등록한다.

# 서브쿼리 작성

QueryDSL에서 서브쿼리를 작성할 때는 `JPAExpressions`를 사용합니다.

- **JPAQuery**
    - 일반적인 쿼리 생성에 사용
    - 사용 범위가 넓고 다양한 메소드 지원
    - 주로 메인 쿼리 작성에 사용
- **JPAExpressions**
    - 유틸리티성 클래스로 설계됨
    - 다양한 select 메소드 지원
    - 서브쿼리 작성에 특화됨
    - 사용 방법이 더 간결하고 명확함

```java
// 서브쿼리 예시
JPQLQuery<Long> subQuery = JPAExpressions
                .select(reviewProduct.product.id)
                .from(reviewProduct)
                .where(ratingBuilder);

// 메인 쿼리에서 서브쿼리 사용
builder.

and(product.id.in(subQuery));
```

# 동적 정렬 (OrderSpecifier)

OrderSpecifier는 QueryDSL에서 동적인 정렬 조건을 생성할 때 사용합니다.

## 기본 구성

- 매개변수:
    - 첫 번째: `Order.ASC` 또는 `Order.DESC` (정렬 방향)
    - 두 번째: 정렬 기준이 되는 QClass 필드 (target)

## 다중 정렬

- `OrderSpecifier[]` 배열을 사용하여 여러 정렬 조건을 순차적으로 적용
- 첫 번째 정렬 조건이 같은 레코드에 대해 두 번째 정렬 조건이 적용됨

## Pageable과의 통합

- Spring Data의 `Pageable`에서 제공하는 `Sort` 객체를 사용하여 동적으로 `OrderSpecifier` 생성 가능

## 주의사항

- 정렬 조건을 설정하지 않으면 `null`이 반환되어 오류 발생 가능
- 항상 기본 정렬 조건을 설정하는 것이 안전

# 1. extends와 implements 사용하지 않기

보통 queryDsl 사용하면 JpaRepository 와 RespositoryCustom이라는 별도의 인터페이스를 추가 상속받아서 implement로 구현 했었다.

이 때문에 JPAQueryFactory를 사용한다면 위처럼 상속구현 구조가 없어도 된다.

# 2. 동적쿼리

동적쿼리를 사용하려면 보통 booleanbuilder를 사용한다.
booleanbuilder는 조건을 동적으로 누적시키는 방식으로 작동하기 때문에 해당 조건이 존재하는지 확인하는 if문이 필요하다.

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
} 등등
```

이렇게 되면 해당 쿼리가 어떤 쿼리인지 예상하기 어려워진다.

->optional 처리로 if문 제거 하면 더 간결

또는 동적쿼리는 booleanExpression을 사용한다. 이는 메서드로 만들어서 해당값이 null을 반환하면 조건절에서 제거된다.
boolean Expression은 모든 조건이 null이면 테이블 전체 스캔을 할 수 있기 때문에 DB에 부담이 커질 수 있음 때문에 전부 null일경우의 처리나, 페이징처리 limit(1000)과 같이 최대 결과
수 제한, 아니면 서비스 레이어에서 검색 조건의 유효성을 검증하는 방식의 처리가 필요할 수 있다.

# 3. exist 메소드 금지

기본적인 SQL문에서 exist 메소드는 count(1)>0 보다 훨씬 빠르다 이유는 exist는 타겟하는걸 찾으면 종료하지만 count는 도중에 찾아도 끝까지 가기 때문이다.

그러나 querydsl의 경우에 exist를 사용하지 않고 fetchcount를 사용한다.
이걸 exist로 바꿀수가 없음 querydsljpa의 근간이 되는 JPQL이 from 없이 쿼리를 생성할 수 없기 때문이다
select exist가 빠른 이유는조건에 해당하는걸 하나만 찾으면 종료하기 때문에 빠른것임

querydsl5.0부터 fetchCont deprecated가 되었고

boolean exists = queryFactory
.selectOne()
.from(member)
.where(member.id.eq(1L))
.fetchFirst() != null; 와 같이 구현 가능(fetchFirst는 내부적으로 limit(1).fetchOne()과 동일하다.

이렇게 구현하면 exists랑 동일한 성능을 낼 수 있다.

주의: 조회 결과가 없으면 null 이 반환되기 때문에 null로 체크해야한다.

# 4. cross join 회피

cross join은 카테시안 곱을 계산한다. 모든 경우의 수를 구한다는 뜻

이는 성능이 좋을 수가 없다.
일부 DB는 최적화가 되기는 하지만 웬만하면 피하는게 좋다.

쿼리 작성 시 JOIN 키워드를 명시적으로 사용하지 않고 WHERE 절에서 조인 조건을 지정하는 방식을 '묵시적 조인'이라고 합니다
예시)

```
SELECT * FROM employee e, department d
WHERE e.dept_id = d.id;
```

- SQL 실행 계획에서 크로스 조인이 먼저 발생한 후 WHERE 조건이 적용됩니다
- 대용량 데이터에서는 중간 결과가 매우 커져 성능이 저하됩니다
  QueryDSL에서도 이러한 묵시적 조인을 사용하면 로그에서 CROSS JOIN이 발생하는 것을 확인할 수 있습니다

성능 최적화를 위해 항상 명시적 조인을 사용해야 합니다:
예시)

```
SELECT * FROM employee e 
INNER JOIN department d ON e.dept_id = d.id;
```

- 별칭(alias)을 지정하여 가독성을 높이고 참조를 명확히 하세요
- 조인 조건은 ON 절에 명확히 작성하세요
- 대용량 데이터 처리 시 특히 크로스 조인을 피해야 합니다

# 5. 엔티티보다 DTO를 우선

## 엔티티 조회 시 성능 이슈

엔티티 직접 조회는 다음과 같은 성능 문제를 야기할 수 있습니다:

1. **Hibernate 캐시 관련 문제**
    - 1차, 2차 캐시 관리 오버헤드
    - 캐시 갱신과 일관성 유지에 따른 성능 비용
2. **불필요한 컬럼 조회**
    - 엔티티의 모든 필드를 조회하므로 실제로 필요하지 않은 데이터까지 로딩됨
    - 데이터 전송량 증가 및 메모리 사용량 증가
3. **N+1 쿼리 문제**
    - 특히 OneToOne 관계에서는 지연 로딩(LAZY)이 제대로 동작하지 않음
    - 엔티티를 로딩할 때마다 연관된 엔티티도 함께 조회되어 쿼리 수가 급증
4. **Distinct 비효율**
    - 엔티티 조회에 distinct를 사용하면 엔티티의 모든 컬럼이 distinct 대상이 됨
    - 임시 테이블 생성에 따른 공간과 시간 소모로 성능 저하 발생

## 해결 방안

### 상황별 최적화 전략

1. **실시간 엔티티 변경이 필요한 경우**
    - 엔티티 조회가 적합(영속성 컨텍스트의 변경 감지 활용)
2. **고성능이 요구되거나 대량 데이터 조회 시**
    - DTO 직접 조회 방식이 성능상 유리
    - 필요한 컬럼만 선택적으로 조회 가능

### 최적화 기법

1. **조회 컬럼 최소화**
    - 실제 필요한 컬럼만 조회하여 데이터 전송량 감소
    - 이미 알고 있는 값은 AS 표현식으로 대체하여 DB 조회 최소화
2. **OneToOne 관계 최적화**
    - OneToOne 관계는 LAZY 로딩이 제대로 동작하지 않으므로 주의
    - 필요한 경우에만 명시적으로 join fetch를 사용하거나 별도 쿼리로 분리
3. **연관 관계 최적화**
    - 연관된 엔티티가 필요한 경우 전체 엔티티가 아닌 ID만 조회
    - `select relatedEntity.id from Entity`와 같은 방식으로 필요한 정보만 가져오기
4. **불필요한 Distinct 피하기**
    - 엔티티에 distinct를 사용하면 모든 컬럼이 대상이 되어 성능 저하
    - 정말 필요한 경우가 아니면 애플리케이션 레벨에서 중복 제거 고려

# group by 최적화

일반적으로 mysql에서는 gourp by는 filesort가 필수로 발생한다. (해당 쿼리가 인덱스를 타지 않았을 경우에)
(filesort가 무엇?)

mysql에서 order by 절을 사용하면 filesort가 제거된다.

하지만 querydsl에서는 order by 절을 지원하지 않는다.
-> 직접 구현을 해야한다.

구현)

```
public class OrderByNull extends OrderSpecifier {
	public static final OrderByNull DEFAULT = new OrderByNull();

	private OrderByNull() {
		super(Order.ASC, NullExpression.DEFAULT, Default);
	}
}
```

사용 예시

```
)
.groupBy(txAddition.type , txAddition.code)
.orderBy(OrderNyMull.DERAULT)
.fetch();
```

위처럼 gourp By 를 사용했을 때와 Order By Null 사용했을 때 성능차이는 어마무시 하다.

이후에 조금 더 개선한다면 정렬이 필요하더라도 조회결과가 100건 이하라면, 애플리케이션에서 정렬하는것을 추천한다.
result.sort(comparingLong(PointCalculateAmout::getPorintAmount));

이유는 사실 우리가 일반적으로 DB 보다 WAS의 자원이 저렴하다. 정렬을 WAS가 하는게 낫다.
하지만 페이징의 경우 order by null을 사용하지 못한다. 페이징이 아닌경우에 사용.

# 커버링 인덱스

쿼리를 출족시키는데 필요한 모든 컬럼을 갖고 있는 인덱스

select / where / order by / group by 등에서 사용되는 모든 컬럼이 인덱스에 포함된 상태
nooffset방식과 더불어 페이징 조회 성능을 향상시키는 가장 보편적인 방법

select *
from academy a
join (select id
from academy
order by id
limit 10000, 10) as temp
on temp.id = a.id;
여기서 join절 안에가 커버링 인덱스를 사용한것
페이징 성능 향상하는 가장 보편적인 방법이기 때문에 많이 사용하지만 JPQL에서는 from 절의 서브쿼리를 지원하지 않는다. 때문에 이도 직접 구현해야한다.

## 커버링 인덱스 조회는 나눠서 진행

Cluster Key(PK)를 커버링 인덱스로 빠르게 조회하고, 조회된 Key로 SELECT 컬럼들을 후속 조회한다. (cluster key(PK)를 커버링 인덱스로 빠르게 조회하고 select 절에 다른 컬럼없이
cluseter key로만 조회하는 것)

```
List<Long> ids = queryFactory
    .select(book.id)
    .from(book)
    .where(book.name.like("%" + name + "%"))
    .orderBy(book.id.desc())
    .limit(pageSize)
    .offset(pageNo * pageSize)
    .fetch();

if (CollectionUtils.isEmpty(ids)) {
    return new ArrayList<>();
}

return queryFactory
    .select(Projections.fields(BookPaginationDto.class,
            book.id.as("bookId"),
            book.name,
            book.bookNo,
            book.bookType))
    .from(book)
    .where(book.id.in(ids))
    .orderBy(book.id.desc())
    .fetch();
```

1. 첫 번째 쿼리에서는 필요한 조건(where, orderBy, limit, offset)에 맞는 PK(book.id)만 빠르게 조회합니다.
    - 이때 book.name에 인덱스가 있다면 인덱스만으로 쿼리가 해결됩니다(커버링 인덱스).
    - select 절에 PK만 포함되어 있어 데이터 전송량이 최소화됩니다.
2. 두 번째 쿼리에서는 조회된 PK 목록(ids)을 사용하여 실제 필요한 데이터를 한 번에 조회합니다.
    - where(book.id.in(ids))로 PK 기반 조회를 하므로 매우 효율적입니다.
    - 필요한 컬럼만 DTO로 매핑하여 불필요한 데이터 전송을 방지합니다.

이 방식은 특히 대용량 데이터에서 페이징 처리할 때 효율적이며, 다음과 같은 장점이 있습니다:

- 대량의 OFFSET 사용 시 발생하는 성능 문제 회피
- 필요한 최소한의 데이터만 DB에서 가져옴
- 인덱스를 최대한 활용한 빠른 조회

JPQL과의 차이
두번의 쿼리 네트워크 차이를 제외하고 비슷한 성능을 낸다.

# 성능개선 - update/Insert

## 일괄 update 하는 방식

- Dirty Checking 방식을 많이 사용
  -> 트랜잭션 내부에 있을 때 엔티티의 변경사항이 있으면 자동으로 DB에 반영이되는 방식
  이 방식은 1000~ 10000건 이 있더라도 전부체크해서 업데이트치는데
  무분별한 더티 체킹을 하는지 무조건 체크 해야한다. 대상 건수가 많으면 많을수록 차이가 커진다.
  그렇다고 일괄업데이트가 무조건 좋은게 아니다
  하이버네이트 1,2차 캐시가 갱신이 안된다.
  이럴 경우에 업데이트 대상들에 대한 cache Eviction 필요

실시간 비지니스 처리, 실시간 단건 처리 -> 더티 체킹
대량의 데이터를 일괄로 Update 처리시만 -> Querydsl.update (하이버네이츠 갱신이 필요 없는경우)

QueryDsl이라고해서 다른게 아니고 쿼리가 어떤 성능을 가지고 있는지 충분히 검토되어야 한다.
<font color="#c0504d">딱 필요한 항목들만 조회하고 업데이트 해야한다.</font>

# JPA로 Bulk Insert는 자제한다

- **JDBC**는 관계형 DB에 접근하기 위한 자바 API로, SQL 쿼리 실행과 결과 처리의 표준화된 방법 제공
- **JPA**는 JDBC 위에 구축된 고수준 ORM 표준이지만, 벌크 연산에서 한계가 있음
- JDBC는 `rewriteBatchedStatements` 옵션으로 Insert 합치기가 가능하지만, JPA는 auto_increment 사용 시 Insert 합치기가 적용되지 않음
- JPA의 `merge`, `persist`는 JDBC Batch 방식보다 성능이 현저히 떨어짐
- JdbcTemplate으로 Bulk Insert는 가능하나 Type Safe한 개발이 어려움

## Type Safe한방식으로 Bulk Insert방식은 없을까?

- Querydsl은 추상화된 최상위 계층으로, 여러 하위 모듈을 포함
- Querydsl-JPA는 우리가 알고있는 가장 널리 사용되는 모듈
- Querydsl-SQL은 Native SQL을 생성하는 Q클래스 프레임워크로 Bulk Insert에 유리
- 기존 Querydsl-SQL은 테이블 스캔 방식으로 Q클래스를 생성하여 번거로움
- 베타 DB와 같은 공유 환경에서는 스키마 변경이 어려워 대처가 어려움

## EntityQL

- JPA Entity 기반으로 Querydsl-SQL Q클래스를 생성해주는 프로젝트
- 테이블 스캔 방식의 번거로움과 JPA의 성능 이슈를 동시에 해결

### EntityQL - 단점

Gradle 5이상 필요

- 플러그인이 Gradle 5이상에서만 작동된다.
  어노테이션에 (name="") 필수
- @Column의 name 값으로 QClass 필드가 선언된다.
- @Table의 name 값으로 테이블을 찾을 수 있다.
  primitive type 사용 X
- int, double, boolean 등을 사용할 수 없다.
- 모든 컬럼은 Wrapper Class로 선언해야 한다.
  복잡한 설정
- querydsl-sql이 개선되지 못해 불편한 설정이 많음.
  @Embedded 미지원
- @Embedded 어노테이션을 통한 컬럼 통합 접근 방식을 못한다.
- @JoinColumn은 지원한다.

# 결론

1.상황에 따라 ORM과 전통적 Query 방식을 골라 사용할 것
2.JPA와 Querydsl로 발생하는 쿼리 한번 더 확인하기
3.쿼리가 복잡해지면서(한방 쿼리) 해당 로직들을 서비스에서 풀어내야 하는게 아닌가 고민이 필요하다

참고
> 향로님의 테코톡 Querydsl 편
> 테코톡 바론, 블랙캣의 Querydsl with JPA
