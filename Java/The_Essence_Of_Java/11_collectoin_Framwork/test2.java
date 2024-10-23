import java.util.Arrays;

public class test2 {
    public static void main(String[] args) {
        int[] arr = {1, 3, 3};
        int[] arr2 = {1, 3, 3};

        // arr 배열을 다꺼내서 문자 합치고 arr2배열 다꺼내서 문자 합치고 비교할게~
        Arrays.sort(arr);
        Arrays.sort(arr2);
        boolean flag = false;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != arr2[i]) {
                flag = true;
                break;
            }
        }
        if (flag) System.out.println("다릅니다.");
        else System.out.println("같습니다.");
    }
}
