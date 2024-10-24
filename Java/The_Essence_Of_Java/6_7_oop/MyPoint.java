public class MyPoint {
    public static void main(String[] args) {
//        PointTest pointTest = new PointTest();
//        pointTest.initTest();

        Point3D point3d = new Point3D(2, 5, 6);
        System.out.println(point3d);

//        Point point1 = new Point(3, 3);
//        Point point2 = new Point(3, 3);
//        point1.getDistance(point2);
//        point2.getDistance(point2);
//        System.out.println(point1.equals(point2));

    }
}

class Point {
    int x;
    int y;

    Point() {
        this(1, 1);
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static double getDistance(Point p1, Point p2) {
        double x1 = p1.x;
        double y1 = p1.y;
        double x2 = p2.x;
        double y2 = p2.y;

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    double getDistance(Point p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point p)) return false;

        return (this.x == p.x) && (this.y == p.y);
    }
}

class Point3D extends Point {
    int z;

    Point3D() {
        this(1, 1, 1);
    }

    Point3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    @Override
    public String toString() {
        return "x : " + x + ", y : " + y + ", z: " + z;
    }
}

class PointTest {
    public void initTest() {
        Point point = new Point(3, 5);
//        System.out.println(point);
        try {
            if (point.x != 3 || point.y != 5) {
                throw new IllegalArgumentException("x: 3 y: 5 가 아닙니다");
            }
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}