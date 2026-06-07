# fork & join

Java7 에서 도입된 병렬 처리 프레임워크.

## 핵심 동작 원리

- Fork(분할): 커다란 작업을 쪼개 작은 단위의 서브 테스크로 재귀적으로 나누는 과정
- Join(합치기): 쪼개진 서브 테스크들이 각자 실행을 마치면, 결과를 부모 테스크로 전달, 결과값을 합치는 과정

## 구성 요소

- RecursiveTask: 결과값이 있는 작업 (예: 합계 계산)
- RecursiveAction: 결과값이 없는 작업 (예: 이미지 필터링)
- Common Pool: ForkJoinPool.commonPool()을 통해 별도 생성 없이 시스템 전체에서 공유하는 쓰레드 풀을 사용할 수 있으며, Java의 Parallel Stream이 내부적으로 이를 사용.

## Work-Stealing

- 개별 큐 보유: 각 worker Thread는 자신만의 작업 큐(Deque)를 가진다.
- 쓰레드가 자신의 큐의 일을 모두 끝냈다면 다른 쓰레드의 큐 뒷부분에서 작업을 훔쳐와서 처리.
