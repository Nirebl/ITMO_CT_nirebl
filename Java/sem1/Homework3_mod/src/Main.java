import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.Math.max;


public class Main {
    public static int getEndOfNumb(int j, String st) {
        while (j > 0 && !Character.isWhitespace(st.charAt(j))) {
            --j;
        }
        return j;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> inputStrings = new ArrayList<>();

        String s;
        int countColumns = 0;
        int cc = 2;
        while ((cc--) > 0 && sc.hasNextLine()) {
            s = sc.nextLine();
            inputStrings.add(s);
            countColumns = max(countColumns, s.length());
        }

        long[][] answer = new long[inputStrings.size()][countColumns];
        for (int i = 0; i < answer.length; ++i) {
            Arrays.fill(answer[i], Long.MAX_VALUE);
        }

        for (int i = inputStrings.size() - 1; i >= 0; --i) {
            int currentColumn = 0;
            for (int j = inputStrings.get(i).length() - 1; j >= 0; --j) {
                if (!Character.isWhitespace(inputStrings.get(i).charAt(j))) {
                    int endNumb = j;
                    j = getEndOfNumb(j, inputStrings.get(i));

                    if (Character.isWhitespace(inputStrings.get(i).charAt(j)))
                        answer[i][currentColumn] = (Long.parseLong(inputStrings.get(i).substring(j + 1, endNumb + 1)));
                    else
                        answer[i][currentColumn] = (Long.parseLong(inputStrings.get(i).substring(j, endNumb + 1)));
                    //answer[i][currentColumn] = (Long.parseLong(inputStrings.get(i).substring(j, endNumb)));
                    ++currentColumn;
                }
            }
        }
        for (long[] i : answer) {
            for (long j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
}
