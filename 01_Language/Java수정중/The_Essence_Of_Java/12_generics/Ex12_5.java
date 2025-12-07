enum Direction {EAST, SOUTH, WEST, NORTH}

public class Ex12_5 {
    public static void main(String[] args) {
        Direction d1 = Direction.EAST;
        Direction d2 = Direction.valueOf("WEST");
        Direction d3 = Direction.valueOf(Direction.class, "EAST");

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        System.out.println(d1 == d2);
        System.out.println(d1 == d3);
        System.out.println(d1.equals(d3));
//        System.out.println(d1 > d3);
        System.out.println(d1.compareTo(d3));
        System.out.println(d1.compareTo(d2));

        switch (d1) {
            case EAST:
                System.out.println("EAST");
                break;
            case WEST:
                System.out.println("WEST");
            case NORTH:
                System.out.println("N");
            case SOUTH:
                System.out.println("S");
        }

        Direction[] dArr = Direction.values();

        for (Direction d : dArr)
            System.out.printf("%s=%d%n", d.name(), d.ordinal());


    }
}
