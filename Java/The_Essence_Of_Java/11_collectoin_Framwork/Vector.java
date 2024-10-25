// Vector와 최대한 비슷하게 만들자
// TDD -> 확신을 가지기 위해, 리펙토링하기 위해서 하는 것
// 매개변수에 Null, 음수, 등등 넣어보기, 데이터를 매우 많이 넣어보기 등등 최대한 많은 에러 테스트
// v.size == 0
// v.add("1")
// v.size() == 1 이렇게 있을 때 위아래에서 확인하기
// TDD -> 1. given 조건 2. when 테스트 3. assert 결과 확인 => 이 순서로 테스트 작성하자.

public class Vector {
    public static void main(String[] args) {
        MyVector myVector = new MyVector();
        myVector.objArr[0] = new Object();
        myVector.objArr[1] = new Object();
        myVector.objArr[2] = "ㄴㄹㅇㅎ";
        System.out.println(myVector.size());

    }
}

class MyVector {
    Object[] objArr = {};
    int capacity;
    int size;

    MyVector() {
        this(16);
    }

    MyVector(int capacity) {
        this.capacity = capacity;
        objArr = new Object[capacity];
    }

    int size() {
        size = 0;
        for (int i = 0; i < this.objArr.length; i++) {
            if (this.objArr[i] != null) size++;
        }
        return size;
    }

//    int capacity() {
//
//    }

//    boolean isEmpty() {
//
//    }
}
