# Optimization

## Scaling Optimization
사용자 8억명의 초당 수백만건의 읽기요청

### CQRS
- 단일 Primary (write전용)
  - 불필요한 write 기능 제거
  - 지연쓰기
  - backfill 전략
- 50+ Read Replica (읽기 분산)
  - 대부분 읽기요청. 중요 트래픽은 전용 replica.
  - Query제한. statement timeout 설정, 느린 쿼리 강제종료 등

샤딩시
- cross-shard join 불가능
- 트랜잭션 관리
- 데이터 이동/리밸런싱
이에 따른 운영 복잡도 증가

왜 PostgreSQL인가?
- ACID(트랜잭션을 보장하고 일관성을 유지)

### PgBouncer
MySQL은 커넥션풀을 제공하는 반면 PostgreSQL은 커넥션당 Process  
connection이 많아지면 메모리/CPU 폭증

이에 따라 connection pooler를 도입 아래와 같은 효과  
- DB connection 수 제한
- context switching  감소
- 인터페이스를 둠으로 안전성 확보

### 운영
DB의 변경을 엄격히 제한  
- schema 변경에 timeout 설정
- index는 CONCURRENTLY만 허용
- 오래걸리는 변경 원천 차단

### 문제점

#### 1. Replication
아래와 같은 이유로 Primary에서 Replica 반영 지연
- CPU 과부하
- WAL(Write-Ahead Log) 전송문제
- Replica수 증가
읽기 일관성을 깨뜨린다.

#### 2. MVCC의 trade off
높은 동시성을 챙길 수 있으나, 테이블/index bloating, Vacuum 
* 테이블/index bloating: 죽은 데이터가 쌓여서 DB가 비대해지는 현상
* 사용 안하는 row 제거하는 작업. 
* Autovacuum 어려움, 어느정도의 세기로 돌릴것인지 테이블마다의 트래픽이 다르기 때문에 환경별 튜닝 필요

확장할수록 오히려 비용이 증가

#### 3. Single Write의 한계
완전한 샤딩을 하는대신 로그, 이벤트등의 워크로드를 Azure Cosmos DB로 분리

### Kubernetes 기반 운영의 주요 지표
- replication lag
- connection 수
- query latency
- CPU / IO

**운영 안정성을 제일 중요시 생각하는 OpenAI의 철학**  
[ OpenAI - 8억 명의 ChatGPT 사용자를 지원하기 위한 PostgreSQL 확장](https://openai.com/ko-KR/index/scaling-postgresql/)