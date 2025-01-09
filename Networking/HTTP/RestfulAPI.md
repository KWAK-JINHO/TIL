REST API의 이론적 제약조건에는

- Self-descriptive messages
- HATEOAS(Hypertext As The Engine Of Application State)
  가 포함된다. HATEOAS는 각 API 응답에 관련 리소스의 하이퍼링크를 포함하도록 요구한다.

하지만 실무에서는 이러한 제약조건을 완벽히 구현하는 것이 비효율적일 수 있기 때문에 다음 세 가지 핵심 원칙만 잘 준수해도 실용적인 REST API를 구현할 수 있다.

1. URI를 통한 리소스 표현
    - 리소스(데이터베이스 엔티티 등)는 URI로 식별
    - 예시: `/users` (사용자 목록), `/users/{id}` (특정 사용자)
2. HTTP 메서드를 통한 행위 표현
    - GET: 리소스 조회
    - POST: 리소스 생성
    - PUT/PATCH: 리소스 수정
    - DELETE: 리소스 삭제
3. HTTP 상태 코드의 적절한 사용
    - 200: 성공
    - 201: 리소스 생성 성공
    - 400: 잘못된 요청
    - 401: 인증 필요
    - 403: 권한 없음
    - 404: 리소스를 찾을 수 없음
    - 500: 서버 내부 오류