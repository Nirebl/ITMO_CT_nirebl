import java.util.Scanner;

public class TaskJ {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;
        n = sc.nextInt();

        int[][] hills = new int[n][n];

        for (int i = 0; i < n; i++) {
            String stringToParse;
            stringToParse = sc.next();

            for (int j = 0; j < n; j++) {
                hills[i][j] = (stringToParse.charAt(j) - '0') % 2;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (hills[i][j] > 0) {
                    for (int k = j + 1; k < n; k++) {
                        hills[i][k] ^= hills[j][k];
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(hills[i][j]);
            }
            System.out.println();
        }
    }
}
