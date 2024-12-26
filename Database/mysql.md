# SQL 데이터베이스 기본 명령어

## 데이터베이스 관리

```
-- 데이터베이스 목록 조회
SHOW DATABASES;

-- 데이터베이스 삭제 (존재할 경우)
DROP DATABASE IF EXISTS sqlDB;

-- 데이터베이스 생성
CREATE DATABASE sqlDB;

-- 데이터베이스 선택
USE sqlDB;
```

## 주요 SQL 문법

### WHERE 절

조건을 설정하여 데이터 필터링 (>, <, =, AND, OR, NOT 등 사용)

```sql
SELECT *
FROM users
WHERE age > 25
  AND country = 'Korea';
```

### LIKE 연산자

특정 패턴과 일치하는 데이터 검색

- `%`: 여러 문자 대체
- `_`: 단일 문자 대체

```sql
-- '김'으로 시작하는 이름 검색
SELECT *
FROM customers
WHERE name LIKE '김%';

-- 제품명에 '폰' 포함된 항목 검색
SELECT *
FROM products
WHERE product_name LIKE '%폰%';

-- gmail 계정 중 아이디가 한 글자인 항목 검색
SELECT *
FROM users
WHERE email LIKE '_@gmail.com';
```

### LIMIT과 OFFSET

#### LIMIT

결과 행 수 제한

#### OFFSET

시작 위치 지정

```sql
-- 상위 10개 행만 조회
SELECT *
FROM orders LIMIT 10;

-- 가격 높은 순으로 5개 제품 조회
SELECT *
FROM products
ORDER BY price DESC LIMIT 5;

-- 21번째부터 10개 행 조회
SELECT *
FROM customers LIMIT 10
OFFSET 20;
```

### GROUP BY와 HAVING

#### GROUP BY

특정 열 기준으로 데이터 그룹화

#### HAVING

그룹화된 결과에 대한 조건 설정

```sql
-- 국가별 고객 수 집계
SELECT country, COUNT(*) AS total_customers
FROM customers
GROUP BY country;

-- 평균 나이가 30 초과인 국가 조회
SELECT country, AVG(age) AS average_age
FROM customers
GROUP BY country
HAVING AVG(age) > 30;
```

### JOIN 종류

#### INNER JOIN (JOIN)

두 테이블의 공통 데이터만 연결

#### LEFT JOIN

왼쪽 테이블 전체 + 오른쪽 테이블 일치 데이터
일치하지 않는 경우 NULL

#### RIGHT JOIN

오른쪽 테이블 전체 + 왼쪽 테이블 일치 데이터
일치하지 않는 경우 NULL

#### FULL OUTER JOIN

양쪽 테이블의 모든 데이터 연결
일치하지 않는 경우 NULL

```sql
-- INNER JOIN 예시
SELECT orders.*, customers.name
FROM orders
         INNER JOIN customers ON orders.customer_id = customers.id;
```