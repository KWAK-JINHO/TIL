import java.util.HashSet;
import java.util.Objects;

public class Ex11_24 {
    public static void main(String[] args) {
        HashSet<Object> set = new HashSet<>();

        set.add("abc");
        set.add("abc");
        set.add(new Person2("David", 10));
        set.add(new Person2("David", 10));

        System.out.println(set);
    }
}

class Person2 {
    String name;
    int age;

    Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Person2 tmp) {
            return name.equals(tmp.name) && age == tmp.age;
        }

        return false;
    }

    public int hashCode() {
        return Objects.hash(name + age);
    }

    public String toString() {
        return name + ":" + age;
    }
}
