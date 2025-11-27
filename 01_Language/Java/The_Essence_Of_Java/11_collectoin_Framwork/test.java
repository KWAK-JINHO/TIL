import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[] arr = new int[25];
        int[][] arr2 = new int[5][5];

        for (int i = 1; i < 26; i++) {
            arr[i - 1] = i;
        }
        System.out.println(Arrays.toString(arr));

        for (int i = 0; i < 5; i++) {
            System.arraycopy(arr, 5 * i, arr2[i], 0, 5);
        }
        System.out.println(Arrays.deepToString(arr2));

        // 2차원 배열 안에서 섞기 -> 5행 5열 2중 포문 돌려서 섞어보쟈~~

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int n = (int) (Math.random() * 5);
                int k = (int) (Math.random() * 5);
                int tmp = arr2[i][j];
                arr2[i][j] = arr2[n][k];
                arr2[n][k] = tmp;
            }
        }

        for (int j = 0; j < 25; j++) {
            int tmp = j / 5;
            arr[j] = arr2[tmp][j % 5];
        }

        System.out.println(Arrays.deepToString(arr2));
        System.out.println(Arrays.toString(arr));
    }
}
