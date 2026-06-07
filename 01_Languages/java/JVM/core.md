


## 공유 영역

- Method area: 클래스, 필드, 메서드, 인터페이스, 클래스변수 등을 보관
- Heap:  new키워드로 생성된 객체의 배열, 인스턴스 변수를 보관. GC가 Heap에서 참조되지 않는 객체를 확인하고 제거한다.

## 스레드 개별 영역(Thread-Safe)

- Stack: 메서드의 호출정보(매개변수, 지역변수, return 값, 연산 중 생기는 값등)저장. 메서드 수행이 끝나면 삭제
- PC Register: 스레드별 현재 실행 중인 명령어 주소 기록
- Native method stack: C/C++ 등 네이티브 코드 실행용 스택

# Execution Engine

- Interpreter: 바이트코드를 한 줄씩 해석
- JIT(Just-In-Time) 컴파일러: 반복되는 코드(Hot Spot)을 기계어로 컴파일하여 캐싱
- GC(Garbage Collector): Heap영역의 미사용 객체를 자동 정리 [[ GC 추가 내용은 여기를 참고 ]](05_gc/gc.md)
- JNI(Java Native method Interface): OS 고유 기능 및 외부 언어 라이브러리와의 통로


