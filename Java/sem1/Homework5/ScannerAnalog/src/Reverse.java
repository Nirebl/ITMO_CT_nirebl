import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class Reverse {
    public static void main(String[] args) throws IOException {
        ScannerAnalog sc = new ScannerAnalog(System.in);

        int[][] answer = new int[1][1];
        answer[0][0] = -1;

        String s;
        int index = 1;
        while (sc.bufferIsFilled()) {
            s = sc.nextLine();
            //System.err.println(s);
            answer = Arrays.copyOf(answer, answer.length + 1);

            ScannerAnalog sc2 = new ScannerAnalog(s);
            answer[index] = new int[1];
            while (sc2.bufferIsFilled()) {
                String temp = sc2.split();
                if (!temp.isEmpty()) {
                    answer[index] = Arrays.copyOf(answer[index], answer[index].length + 1);
                    answer[index][answer[index].length - 1] = Integer.parseInt(temp);
                }
            }
            ++index;
            sc2.close();
        }
        sc.close();

        for (int i = answer.length - 1; i >= 1; --i) {
            for (int j = answer[i].length - 1; j >= 1; --j) {
                System.out.print(answer[i][j] + " ");
            }
            System.out.println();
        }
    }
}
