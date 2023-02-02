import java.io.IOException;
import java.util.*;


import static java.lang.Math.min;

public class ReverseMin2 {
    public static void main(String[] args) throws IOException {
        ScannerAnalog sc = new ScannerAnalog(System.in);

        int[][] answer = new int[1][1];
        answer[0][0] = -1;

        int indexArray = 1;

        int maxCountStolb = 0;

        int cnt = 4;

        /*while ((cnt--) > 0 && sc.hasNext()) {
            String temp = sc.nextLine();
            answer = Arrays.copyOf(answer, answer.length + 1);
            answer[indexArray] = new int[1];

            Scanner_oleg sc2 = new Scanner_oleg(temp);

            int currentCountStolb = 0;
            while (sc2.hasNextInt()) {
                int s = sc2.nextInt();
                currentCountStolb++;

                answer[indexArray] = Arrays.copyOf(answer[indexArray], answer[indexArray].length + 1);
                answer[indexArray][answer[indexArray].length - 1] = s;
            }
            if (currentCountStolb > maxCountStolb)
                maxCountStolb = currentCountStolb;

            ++indexArray;
        }
        System.err.println(Arrays.deepToString(answer));

        int[] minElementStolb = new int[maxCountStolb + 1];
        Arrays.fill(minElementStolb, Integer.MAX_VALUE);

        for (int i = 1; i < answer.length; i++) {
            for (int j = 1; j < answer[i].length; ++j) {
                int currentElement = answer[i][j];

                if (currentElement > minElementStolb[j]) {
                    currentElement = minElementStolb[j];
                } else {
                    minElementStolb[j] = currentElement;
                }
                if (j > 1) {
                    if (currentElement > minElementStolb[j - 1]) {
                        currentElement = minElementStolb[j - 1];
                    }
                    minElementStolb[j] = min(minElementStolb[j], minElementStolb[j - 1]);

                }
                System.out.print(currentElement + " ");
            }
            System.out.println();
        }*/

    }
}
