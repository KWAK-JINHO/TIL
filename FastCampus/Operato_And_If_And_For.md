# 연산자

- 계산하는 기호
- 반드시 결과를 반환한다.

### 종류

- 계산: +, -, /, %
- 비교: <, >, <=, >=, ==
- 논리: ||, &&, !
- 대입: =

1. 우선순위
2. 결합법칙
3. 산술변환
   - int보다 작은건 int로 변환된다.
   - 둘중에 큰 타입으로 변환

### short circuit; 효율적인 계산

- true || true -> true
- true || false -> true
- 위 두가지 경우 모두 true를 반환하므로 왼쪽만 계산하면 된다.

# 조건문과 반복문

- if(조건): 0 ~ 1번
  - if - else: 2가지 경우
  - if - else if: n가지 경우
- switch case(조건)
  - switch statement
  - switch Expression
- for(반복): 0 ~ n번
  - for(초기화; 조건; 증감)