import java.util.Scanner;

public class Main {
    public static void main(String[] arg) {
        //Scanner sc = new Scanner(System.in);

        String st = "";
        //st = sc.nextLine();
        int answer = 0;
        for (String asds : arg) {
            st = asds;


            StringBuilder a = new StringBuilder();

            for (int i = 0; i < st.length(); ++i) {
                if (/*st.charAt(i) == ' ' || st.charAt(i) == '"'*/!checkChar(st.charAt(i))) {
                    answer = getAnswer(a, answer);
                    a = new StringBuilder();
                } else {
                    a.append(st.charAt(i));
                }
            }
            answer = getAnswer(a, answer);
        }

        System.out.println(answer);

    }

    private static boolean checkChar(char a) {
        /*return a == '\t' || a == '\n' || a == '\u000B' || a == '\f' || a == '\r' || a == '\u001C'
                || a == '\u001D' || a == '\u001E' || a == '\u001F' || a == ' ' || a == '"';*/
        return a=='-' || a == '0' || a == '1' || a == '2' || a == '3' || a == '4' || a == '5' || a == '6' || a == '7' || a == '8' || a == '9';
    }

    private static int getAnswer(StringBuilder a, int answer) {
        if (a.length() == 0)
            return answer;

        int number = 0;
        int ten = 1;

        boolean minus = a.charAt(0) == '-';

        for (int j = a.length() - 1; j >= 0; --j) {
            if (a.charAt(j) == '-' || a.charAt(j) == '+')
                continue;
            number += (a.charAt(j) - '0') * ten;
            ten *= 10;
        }
        if (minus)
            answer -= number;
        else
            answer += number;
        return answer;
    }
}