# Collection - 데이터 구조와 그에 관련된 작업을 처리하기 위한 구조적 개념

## ArrayList

- 내부적으로 배열을 사용하여 데이터를 저장. 배열의 크기가 가득 차면, 더 큰 배열을 생성하고 기존 배열의 요소들을 새로운 배열로 복사

#### 장단점

- 장점
  - 빠른 조회: 인덱스 기반 접근이 빠른다. 배열처럼 요소에 접근하므로 메서드를 사용한 요소 접근이 O(1) 시간 복잡도를 가진다.
  - 메모리 효율: 요소들이 연속된 메모리 공간에 저장되므로, 메모리 사용 측면에서 효율적이다.
- 단점
  - 느림 삽입/삭제: 중간에 요소를 삽입하거나 삭제하려면, 해당 인덱스 이후의 모든 요소를 이동시켜야 하기 때문에 O(n) 시간 복잡도를 가진다.
  - 배열 크기 재조정: 배열이 가득 차면, 크기를 늘리기 위해 배열 전체를 복사하는 작업이 필요하므로 성능에 영향

#### 사용예시

```commandline
public static void main(String[] args){
  ArrayList<String>list = new ArrayList<>();
  list.add("철수");
  list.add("영희");
  // 배열순회
  for (String s:list){
    System.out.println(s);
  }
}
```

#### 메서드

- add: 추가
- get: 가져오기
- size: 크기
- set: 수정
- contains: 포함 여부
- remove: 삭제
- clear: 전체삭제

## LinkedList

- 각각의 요소가 노드로 구성되어있으며, 각 노드는 다음 요소(노드)와의 참조(링크)를 가지고 있다. 이중 연결리스트로 구현되어 있어, 각 노드는 다음 노드와 이전 노드에 대한 참조를 가진다.

#### 장단점

- 장점
  - 빠른 삽입/삭제: 리스트의 중간에서 요소를 삽입하거나 삭제할때 참조링크만 변경하면 되므로 O(1) (노드의 위치를 찾는 작업이 O(n)이므로, 전체적으로 O(n)일 수 있다.)
  - 동적 크기 관리: ArrayList 처럼 배열 크기를 재조정할 필요가 없으며 필요에 따라 메모리가 할당된다.
- 단점
  - 느린조회 O(n)
  - 추가 메모리 사용: 각 용소가 다음과 이전 노드에 대한 참조를 가지고 있기 때문에, 추가적인 메모리 공간이 필요

#### 사용예시

```commandline
public static void main(String[] args){
  LinkedList<String>list = new LinkedList<>();
  list.add("철수");
  list.add("영희");
  // 배열순회
  for (String s:list){
    System.out.println(s);
  }
}
```

#### 메서드
- add: 추가
- get: 가져오기
- getFirst: 처음 요소 가져오기
- getLast: 마지막 요소 가져오기
- addFirst: 맨 앞에 추가
- addLast: 맨 뒤에 추가
- clear: 전체 삭제

## HashSet

- 'HashSet'은 자바의 'Set' 인터페이스를 구현한 클래스이며, 해시 기반의 자료구조를 사용. 
'Set' 인터페이스는 중복된 요소를 허용하지 않는 컬렉션을 정의.
- HashSet<T>

#### 특징

- 중복된 요소를 허용하지 않는다. 동일한 요소를 추가하려고 하면, 기존의 요소가 유지되고 새로운 요소는 추가되지 않는다.
- 요소를 저장할때 순서를 보장하지 않는다.
- 해시 테이블을 사용하여 요소를 저장, 때문에 추가,삭제,검색 작업이 평균적으로 o(1)의 시간복잡도를 가진다.
최악의 경우에는 동일한 해시 코드를 가진 요소가 여러개 있을때 해시 충돌이 발생.

#### 사용예시

```commandline
// HashSet<T>
public static void main(String[] args){
  HashSet<String> set = new HashSet<>();
  set.add("철수"); // {"철수"}
  set.add("영희"); // {"철수", "영희"}
  set.add("철수"); // {"철수", "영희"}
}
```

#### 메서드

- add: 'HashSet'에 요소를 추가
- remove: 지정한 요소를 'HashSet'에서 제거
- contains: 지정한 요소가 'HashSet'에 포함 되어 있는지 확인
- size: 'HashSet'의 요소 개수를 반환
- clear: 'HashSet'의 모든 요소를 제거

## HashMap

- (key, value)쌍의 자료구조, 중복x, 순서x

#### 사용예시

```commandline
// HashMap<k, v>
public static void main(String[] args){
// 이름, 점수
  HashMap<String, Integer> map = new HashMap<>();
  map.put("철수", 100); 
  map.put("영희", 90); // key에는 철수, 영희가 value 에는 각각 100, 90이 맵핑이 된다.
}
```

#### 메서드

- put: 요소를 추가
- size: 크기
- get: 가져오기
- containsKey: Key 포함 여부
- remove: 삭제
- clear: 전체 삭제

## Iterator

- 컬렉션의 모든 데이터를 차례대로 접근하기 위해서 사용

#### 사용예시

```commandline
public static void main(String[] args){
  ArrayList<String>list = new ArrayList<>();
  list.add("철수");
  list.add("영희");
  // Iterator 변경 전
  for (String s:list){
    System.out.println(s);
  }
  // Iterator로 변경 후
  Iterator<String> it = list.iterator();
  while (it.hasNext()){  // hasNext는 다음 요소가 있다면 true
    System.out.println(it.next());
  }
}
```

#### 메서드

- hasNext: 다음 요소 확인
- next: 다음 요소 가져오기
- remove: 삭제