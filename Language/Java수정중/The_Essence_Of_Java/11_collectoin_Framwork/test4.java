import java.util.Arrays;

public class test4 {
    public static void main(String[] args) {
        int[] arr = {5, 6, 8};
        System.out.println(mid(arr));
    }

    // mid값 구하는 메서드
    //
    static int mid(int[] arr) {
        Arrays.sort(arr);

        return arr[1];
    }
}
