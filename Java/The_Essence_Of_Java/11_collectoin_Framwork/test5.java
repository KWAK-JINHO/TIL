public class test5 {
    public static void main(String[] args) {
        int[][] arr = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 0, 1},
                {1, 0, 1, 1, 1},
        };

        System.out.println(bingoCnt(arr)); // 2
    }

    // 빙고하는 메서드를 만들어 보자!
    // 자 일단 내 생각에는 행렬의 각 0 번째 인덱스에서 일렬로 쭉검사하면 되는게 아닌가 싶거든? 1이면 쭉 가서 0안나오면 카운팅하면 되잖아 해보자~
    static int bingoCnt(int[][] arr) {
        final int num = 5; // 매직넘버
        int bingo = 0;

        int cnt3 = 0; // 왼 - 오 대각선 체크
        int cnt4 = 0; // 오-왼 대각선 체크

        // 행체크
        for (int i = 0; i < num; i++) {
            int cnt = 0; // 행의 빙고 체크
            int cnt2 = 0; // 열의 빙고 체크

            if (arr[i][i] == 1) {
                cnt3++;
            }

            if (arr[4 - i][i] == 1) {
                cnt4++;
            }

            for (int j = 0; j < num; j++) {
                if (arr[i][j] == 1) {
                    cnt++;
                }

                if (arr[j][i] == 1) {
                    cnt2++;
                }
            }

            if (cnt == 5) {
                bingo++;
            }

            if (cnt2 == 5) {
                bingo++;
            }
        }
        if (cnt3 == 5) {
            bingo++;
        }
        if (cnt4 == 5) {
            bingo++;
        }

        return bingo;
    }
}
