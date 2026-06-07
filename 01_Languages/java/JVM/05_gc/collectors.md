# GC Collectors

## Serial GC

단일 스레드 기반 GC이다.

- CPU 사용량 적음
- STW 길어짐

## Parallel GC(Java 8 기본)

- GC를 병렬 수행하며, Throughput 중심
- Batch, Offline 처리에 적합하다.

## G1 GC(Java 9 이후 기본)

- Heap을 Region 단위로 관리한다.
- Garbage가 많은 Region을 우선적으로 수집한다.
- Pause Time을 예측 가능하게 설계되었다.

#### terms

- Region: Heap을 동일한 크기의 Region으로 분할하여 관리한다.
- Young GC: Young Generation 영역을 수집하는 GC
- Mixed GC: Young Generation과 일부 Old Region을 함께 수집하는 GC

## ZGC

- 초저지연 GC
- 대부분 작업을 Concurrent 수행
- 매우 짧은 STW를 가진다.
- 대용량 Heap 또는 실시간 서비스에 사용된다.