import java.util.ArrayList;
import java.util.Iterator;

public class prac11_1 {
    public static void main(String[] args) {
        ArrayList list1 = new ArrayList<>();
        ArrayList list2 = new ArrayList<>();
        ArrayList kyo = new ArrayList<>();
        ArrayList cha = new ArrayList<>();
        ArrayList hap = new ArrayList<>();

        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);

        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);

        Iterator it = list1.iterator();
        while (it.hasNext()) {
            Object tmp = it.next();
            if (list2.contains(tmp))
                kyo.add(tmp);
        }

        it = list1.iterator();
        while (it.hasNext()) {
            Object tmp = it.next();
            if (!list2.contains(tmp))
                cha.add(tmp);
        }

        it = list1.iterator();
        while (it.hasNext()) {
            hap.add(it.next());
        }

        it = list2.iterator();
        while (it.hasNext()) {
            hap.add(it.next());
        }


        System.out.println(list1);
        System.out.println(list2);
        System.out.println(kyo);
        System.out.println(cha);
        System.out.println(hap);
    }
}
