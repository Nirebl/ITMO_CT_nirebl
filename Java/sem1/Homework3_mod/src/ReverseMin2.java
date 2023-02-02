import java.beans.ParameterDescriptor;
import java.util.*;

import static java.lang.Math.min;

public class ReverseMin2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][] answer = new int[1][1];
        answer[0][0] = -1;

        int indexArray = 1;

        int maxCountStolb = 0;

        while (sc.hasNextLine()) {
            String temp = sc.nextLine();

            if (indexArray == answer.length)
                answer = Arrays.copyOf(answer, answer.length * 2);
            answer[indexArray] = new int[1];

            Scanner sc2 = new Scanner(temp);

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

        int[] minElementStolb = new int[maxCountStolb + 1];
        Arrays.fill(minElementStolb, Integer.MAX_VALUE);

        for (int i = 1; i < indexArray; i++) {
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
        }
    }
}
