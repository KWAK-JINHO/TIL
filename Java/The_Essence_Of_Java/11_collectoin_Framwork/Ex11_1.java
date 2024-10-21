import java.util.ArrayList;
import java.util.Collections;

public class Ex11_1 {
    public static void main(String[] args) {
        ArrayList list1 = new ArrayList(10);
        list1.add(5);
        list1.add(4);
        list1.add(2);
        list1.add(0);
        list1.add(1);
        list1.add(3);

        ArrayList list2 = new ArrayList(list1.subList(1, 4));
        print(list1, list2);

        Collections.sort(list1);
        Collections.sort(list2);
    }

    static public void print(ArrayList list1, ArrayList list2) {
        System.out.println("hi");
    }
}

