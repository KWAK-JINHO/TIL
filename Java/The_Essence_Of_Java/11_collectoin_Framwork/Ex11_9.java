// 뒤로가기 앞으로가기 구현

import java.util.Stack;

public class Ex11_9 {
    public static Stack back = new Stack();
    public static Stack forward = new Stack();

    public static void main(String... args) {
        goURL("네이버");
        goURL("야후");
        goURL("다음");
        goURL("네이트");

        printStatus();
    }

    public static void printStatus() {
        System.out.println(back);
        System.out.println(forward);
        System.out.println(back.peek());
        System.out.println();
    }

    public static void goURL(String url) {
        back.push(url);
        if (!forward.empty())
            forward.clear();
    }

    public static void goForward() {
        if (!forward.empty())
            back.push(forward.pop());
    }

    public static void goBack() {
        if (!back.empty())
            forward.push(back.pop());
    }
}
