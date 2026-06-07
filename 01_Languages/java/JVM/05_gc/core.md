# Garbage Collector

## GC란?

JVM은 동적으로 생성된 객체를 Heap에 저장한다.
객체의 생명주기를 개발자가 직접 관리하지 않고 Garbage Collector가 자동으로 관리한다.

## Reachability Analysis

JVM은 객체의 유효성을 판단하기 위해 Root Set부터 참조 그래프를 탐색한다.

### Root Set

- Stack의 지역 변수
- Static 변수
- JNI Reference

### Live Object

Root Set에서 도달 가능한 객체

### Garbage Object

어떤 경로로도 도달할 수 없는 객체

## Stop The World

GC 수행 시 애플리케이션 스레드가 일시 정지될 수 있다. GC 튜닝의 핵심 목표는 이를 시간을 최소화하는 것.

## Heap Structure

대부분의 객체는 빨리 죽는다. GC는 이 가정을 기반으로 Heap을 분리한다.

### Young Generation

새로운 객체가 생성되는 영역
- Eden
- Survivor0
- Survivor1

### Old Generation

오랫동안 살아남은 객체 저장

## GC 종류

### Minor GC

Young Generation 대상

### Major GC

Old Generation 대상

### Full GC

Heap 전체 대상