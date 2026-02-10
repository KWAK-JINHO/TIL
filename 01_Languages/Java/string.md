### 문자열 결합 도구

- Java 8에 도입
- String.join(): 단순 결합용. 가독성이 좋음.
- StringJoiner: 접두사/접미사가 필요하거나 동적 결합이 필요할 때 사용.

### 주의사항

- 성능상 `+` 연산보다 우수 (내부적으로 StringBuilder 활용)
- 대량의 데이터 처리 시에는 Stream API의 joining()이 더 유연함.
- Java 21 환경에서는 코드의 간결성을 위해 가급적 Stream 지향.