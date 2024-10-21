import java.util.ArrayList;

class Tv {
}

class Audio {
}

public class genericTest {
    public static void main(String[] args) {
//        ArrayList list = new ArrayList();
        ArrayList<Tv> list = new ArrayList<Tv>();
        list.add(new Tv());

        Tv t = list.get(0);
    }
}
