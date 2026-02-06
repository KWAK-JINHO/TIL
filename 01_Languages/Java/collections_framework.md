# collections framework

데이터 군을 저장하는 클래스들을 표준화한 것

---

# Array vs List

Array는 기본 자료구조로 collection framework 아님

|       |                  Array                   |        List         |
|:-----:|:----------------------------------------:|:-------------------:|
|  공변성  | 공변성(Covariant): Child[]를 Parent[]에 할당 가능 |        불공변성         |
|  크기   |           고정: 재할당 외에는 크기 조절 불가           |  가변: 자동으로 크기가 늘어남   |
| 타입 체크 |     런타임에 확인 (ArrayStoreException 위험)     | 컴파일 타임에 확인 (제네릭 활용) |
|  제네릭  |            제네릭 타입으로 배열 생성 불가             |   제네릭 사용 필수 및 권장    |

- 공변성: 두 타입간의 상속관계(B extends A)가 배열 타입에도 그대로 적용되는 특성
  (자식 배열이 부모 타입을 할당해서 만들면 컴파일타임에는 괜찮지만 런타임에 자식 타입의 변수 넣으려고 하면 ArrayStoreException 발생)

---

# 핵심 인터페이스

|          |                    List                     |        Set         |        Map         |
|:--------:|:-------------------------------------------:|:------------------:|:------------------:|
|    순서    |                      O                      |         X          |         X          |
|    중복    |                      O                      |         X          | Key: X<br>Value: O |
| 구현 Class | ArrayList<br>LinkedList<br>Stack(Vector 상속) | HashSet<br>TreeSet | HashMap<br>TreeMap |

# List 인터페이스

## ArrayList(배열 기반)

- 내부적으로 가변 배열을 사용. 인덱스 기반 조회(O(1))
- 중간 삽입/삭제 시 데이터 이동 비용(O(n)) 발생
- 배열 복사 시 일시적 성능 저하

## LinkedList(노드 기반)

- 각 요소가 이전/다음 노드의 주소를 가짐. 삽입/삭제 시 참조만 변경
- 특정 요소를 찾으려면 처음부터 탐색(O(n)). 주소 값을 저장하는 추가 메모리 소모

# Set & Map

- HashSet / HashMap: 해시 알고리즘 사용(O(1)). hashCode()와 equals()의 재정의 필요
- TreeSet / TreeMap: 이진 탐색 트리 기반 데이터 저장, 넣기만 해도 자동 정렬

# Iterator

컬렉션의 내부 구조를 몰라도 모든 데이터를 차례대로 접근하게 해주는 인터페이스

- hasNext(): 다음 요소 존재 확인
- next(): 다음 데이터 호출