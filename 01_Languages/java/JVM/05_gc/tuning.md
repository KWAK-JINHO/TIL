# GC Tuning

GC 튜닝의 핵심은 Stop The World(STW) 시간을 줄여 Latency를 낮추고, GC 수행 비용을 최소화하여 Throughput을 높이는 것이다.

## Heap 크기 최적화

-Xms 와 -Xmx를 동일하게 설정하는 것을 권장. Heap 크기가 동적으로 변한다면 OS 메모리 재할당 오버헤드가 발생하기 때문이다.

- -Xms (Initial Heap Size): JVM이 시작될 때 할당하는 최소 메모리 크기
- -Xmx (Maximum Heap Size): JVM이 사용할 수 있는 최대 메모리 크기

## 객체 생성 최적화

- String 결합에 `+` 연산 지양. StringBuilder를 사용해 불필요한 객체 생성을 막아야 한다.
- 기본형 타입을 사용하면 스택에서 처리되거나 메모리 점유가 적다. Integer, Long 대신 int, long 사용하는것이 대량의 데이터를 처리할 때 유의미 할 수 있다.
- DB에서 대량의 데이터를 한꺼번에 조회하여 리스트에 담으면 이 객체들은 Eden영역을 순식간에 점유한다. Eden 영역이 꽉 차면 Minor GC가 발생하게 되는데
  이 횟수가 빈번해지면 application throughput 저하로 이어진다. 적절한 Pagination과 Stream 처리를 통해 메모리에 올라오는 객체 수를 제한해야 한다.

## JVM 옵션

- `-XX:+UseG1GC`: G1GC사용
- `-XX:MaxGCPauseMillis=n`: 목표 최대 STW 시간 설정. 너무 짧게 설정할 경우 GC가 너무 자주 발생하여 throughput이 저하될 수 있다.
- `-XX:ParallelGCThreads=n`: GC를 병렬로 처리할 스레드 수 지정. CPU 코어 수에 맞춰 설정하는 것이 일반적
- `-XX:+PrintGCDetails`/`-Xlog:gc*`: GC 발생 상세 로그 출력