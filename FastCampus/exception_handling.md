# 예외처리

- 컴파일러 역할
    - 파싱
    - 문법체크
    - 의미체크
    - 번역

### 오류

- 컴파일러 에러
- 런타임 에러
    - 예외 : 덜심각
        - 필수: Exception(꼭처리 해야됨) -> 사용자 실수
        - 선택: RuntimeException(선택적 처리) -> 개발자 실수
    - 에러
        - 심각
        - 포기 -> OOME(out of memory error)
- 논리적 에러

### 예외처리: 예외가 발생해도 프로그램이 안죽고 돌아가게 하는게 실력이다.

1. 직접처리
2. 보고
    - 1,2 번은 협력(예외 되던지기)
3. 은폐(무시)