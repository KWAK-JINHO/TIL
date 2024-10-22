public class test2 {
    public static void main(String[] args) {
        int[] arr = {1, 3, 3};
        int[] arr2 = {1, 2, 3};

        // arr 배열을 다꺼내서 문자 합치고 arr2배열 다꺼내서 문자 합치고 비교할게~

        String tmp = "";
        String tmp2 = "";

        for (int i : arr)
            tmp += i;

        for (int i : arr2)
            tmp2 += i;

        System.out.println(tmp + tmp2);

        if (!tmp.equals(tmp2))
            System.out.println("다르제~");
        else
            System.out.println("같아유~");
    }
}
