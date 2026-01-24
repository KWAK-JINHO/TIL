// Iterator로 배열 값 읽어오기

import java.util.ArrayList;
import java.util.Iterator;

public class Ex11_13 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        Iterator it = list.iterator();

        while (it.hasNext()) {
            Object obj = it.next();
            System.out.println(obj);
        }

        it = list.iterator(); // 1회용이라 한번 더 선언

        for (int i = 0; i < list.size(); i++) {
            Object obj = it.next();
            System.out.println(obj);
        }
    }
}
