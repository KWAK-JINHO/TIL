// Card객체 5개를 받아서 등급을 출력하는 메서드 rankCheck

import java.util.Arrays;

public class test6 {
    public static void main(String[] args) {

        Card[] arr = {new Card(3, "H"), new Card(5, "H"), new Card(2, "H"), new Card(4, "H"), new Card(1, "H")};

        System.out.println(rankCheck(arr));
    }

    static String rankCheck(Card[] arr) {
        // 각 숫자가 몇개인지 카운팅하는 배열 하나 만들기 -> 숫자 체크
        // 제일 카운팅 많이 된놈이 가장 많은 카드
        int[] numCounting = new int[12];
        // 문자가 모두 같으면 플러쉬 -> 문자 체크
        String strCounting = "";

        for (int i = 0; i < arr.length; i++) {
            numCounting[arr[i].rank]++;
            strCounting = strCounting + arr[i].shape;
        }
        System.out.println(Arrays.toString(numCounting));
        System.out.println(strCounting);

        // 예외처리
        // 1. 숫자도 모두 같고 문자도 모두 같은 경우
        // 2. 등급이 아무 것도 없을 경우


        boolean fourCheck = false;
        boolean threeCheck = false;
        int pairCheck = 0;
        int straightCheck = 0;
        String rank = "NO RANK";

        for (int i = 0; i < numCounting.length; i++) {
            if (numCounting[i] == 4) fourCheck = true;
            if (numCounting[i] == 3) threeCheck = true;
            if (numCounting[i] == 2) pairCheck++;
            if (numCounting[i] == 1 && numCounting[i + 1] == 1) {
                System.out.println("hi");
                straightCheck++;
            }
        }

        if (pairCheck == 1) rank = "1 PAIR";
        if (pairCheck == 2) rank = "2 PAIR";
        if (threeCheck) rank = "THREE CARD";
        if (fourCheck) rank = "FOUR CARD";
        System.out.println(pairCheck);
        System.out.println(threeCheck);
        if (threeCheck && pairCheck == 1) rank = "FULL HOUSE";
        if (straightCheck == 4) rank = "STRAIGHT";
        if (strCounting.equals("HHHHH") || strCounting.equals("DDDDD")) {
            if (rank.equals("STRAIGHT")) {
                rank = "STRAIGHT FLUSH";
            } else rank = "FLUSH";
        }

        return rank;
    }
}

class Card {
    int rank;
    String shape;

    Card(int rank, String shape) {
        this.rank = rank;
        this.shape = shape;
    }
}
