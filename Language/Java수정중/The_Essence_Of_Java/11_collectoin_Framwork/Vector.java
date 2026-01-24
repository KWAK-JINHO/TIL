// Vector와 최대한 비슷하게 만들자
// TDD -> 확신을 가지기 위해, 리펙토링하기 위해서 하는 것
// 매개변수에 Null, 음수, 등등 넣어보기, 데이터를 매우 많이 넣어보기 등등 최대한 많은 에러 테스트
// v.size == 0
// v.add("1")
// v.size() == 1 이렇게 있을 때 위아래에서 확인하기
// TDD -> 1. given 조건 2. when 테스트 3. assert 결과 확인 => 이 순서로 테스트 작성하자.

//import java.util.Vector;

public class Vector {
    public static void main(String[] args) {
        MyVector myVector = new MyVector();
//        myVector.objArr[0] = new Object(); // 직접 인덱스 접근시 null 값의 관리 어려움
        myVector.add(null);
        myVector.add(67);
        myVector.add("asb");
        myVector.add(67);
//        myVector.get(-1);
//        for (int i = 0; i < 1000000; i++) {
//            myVector.add(4);
//        }
        myVector.remove(67);
        System.out.println(myVector);
    }
}

class MyVector {
    private final int capacity;
    private Object[] objArr = {};
    private int size;

    MyVector() {
        this(16);
    }

    MyVector(int capacity) {
        this.capacity = capacity;
        objArr = new Object[capacity];
    }

    // 배열에 저장된 객체의 개수를 반환 하는 메서드,
    public int size() {
//        size = 0;
//        for (int i = 0; i < this.objArr.length; i++) {
//            if (this.objArr[i] != null) size++;
//        }
        return size;
    }

    // this.capacity 로 얻은 값은 실제 길이가 아니고 할당 받은 것이기 때문에 length 사용
    public int capacity() {
        return objArr.length;
    }

    // 객체배열이 비었는지 확인하는 메서드
    public boolean isEmpty() {
        for (Object obj : objArr) if (obj != null) return false;
        return true;
    }

    // 객체를 추가하는 메서드 void add(Object obj)
    // capacity가 full일때 배열길이 두배로 증가 후 복사
    public void add(Object obj) {
        if (size >= objArr.length) {
            Object[] newArr = new Object[objArr.length * 2];
            System.arraycopy(objArr, 0, newArr, 0, objArr.length);
            objArr = newArr;
        }
        objArr[size++] = obj;
    }

    // 해당 인덱스 객체 반환 메서드 Object get(index)
    // 들어오는 값 체크 숫자인지, 음수인지 체크, 배열 밖의 인덱스 인지 체크   <------------- 확인 필요
    Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("인덱스를 확인해 주세요");
        }
        return objArr[index];
    }

    // 객체배열에 저장된 모든 객체를 문자열로 이어서 반환하는 toStinrg 오버라이딩
    @Override
    public String toString() {
        String mergeStr = "";
        for (int i = 0; i < size; i++) {
            mergeStr += objArr[i];
        }
        return mergeStr;
    }

    // 지정된 객체가 저장되어 있는 위치(index) 반환하는 int indexOf
    public int indexOf(Object obj) {
        if (obj == null) { //null 은 아무것도 참조 하지 않으므로 equals 사용 불가 때문에 따로 체크 해줘야 한다.
            for (int i = 0; i < size; i++) {
                if (objArr[i] == null) {
                    return i;
                }
            }
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (obj.equals(objArr[i])) { // 기본형인 경우 == 로 가능하지만 참조형인 경우 ==로 비교하면 주소를 비교하기 때문에 불가능
                return i;
            }
        }
        return -1;
    }

    // objArr에서 지정된 객체를 삭제하는 boolean remove(Object obj)작성  (* indexOf()이용할 것)
    boolean remove(Object obj) {
        int removeIdx = indexOf(obj);
        if (removeIdx >= 0) {
            System.arraycopy(objArr, removeIdx + 1, objArr, removeIdx, size - removeIdx);
            objArr[size - 1] = null;
            size--;
            return true;
        } else return false;
    }
}
