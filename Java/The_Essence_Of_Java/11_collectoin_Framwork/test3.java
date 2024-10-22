public class test3 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int[] arr2 = {2, 1, 3};

        int strike = 0;
        int ball = 0;
        // 뽀문 돌면서 혹시 같은 놈이면 스투라이꾸 추가 하고 포문 탈출 스트라이크 아닌데 이제 포함이면 이제 볼 추가 하는 거덩요~ 킥이거덩여
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                if (arr[i] == arr2[i]) {
                    strike++;
                    break;
                }
                if (arr[i] == arr2[j]) {
                    ball++;
                }
            }
        }
        System.out.println(strike);
        System.out.println(ball);
    }
}
