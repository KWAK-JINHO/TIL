# GC Algorithms

## Mark-Sweep

- Mark: 도달 가능한 객체 표시
- Sweep: 표시되지 않은 객체 제거

구현이 단순하지만 메모리 단편화가 발생한다.

## Mark-Compact

- 생존 객체를 한쪽으로 압축한다.
- 단편화를 제거하지만 객체 이동 비용 발생

## Copying

- Heap을 두 영역으로 분리하여, 생존 객체만 복사
- 장점으로 빠른 할당, 단편화 없음
- 단점으로는 추가 메모리 필요

## Generational GC

- Young Generation → Copying
- Old Generation → Mark-Sweep / Mark-Compact 조합하여 사용