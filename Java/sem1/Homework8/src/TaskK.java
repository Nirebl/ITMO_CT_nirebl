import java.util.Vector;
import java.util.Scanner;

public class TaskK {
    static final int MOD = 998_244_353;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;
        n = sc.nextInt();

        int m;
        m = sc.nextInt();

        char[][] a = new char[n][1];

        for (int i = 0; i < n; i++) {
            char[] line = new char[m];
            String str = sc.nextLine();

            for (int j = 0; j < m; j++) {
                line[j] = str.charAt(j);
            }
            a[i] = line;
        }

        int ar = -1, ac = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 'A') {
                    ar = i;
                    ac = j;
                }
            }
        }
        a[ar][ac] = '.';


        /*1Vector<Vector<Character>> a = new Vector<>();
        for (int i = 0; i < n; ++i) {
            Vector<Character> line = new Vector<>();
            String str = sc.nextLine();
            for (int j = 0; j < m; ++j) {
                line.add(str.charAt(j));
            }
            a.add(line);
        }

        int ar = -1, ac = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a.get(i).get(j) == 'A') {
                    ar = i;
                    ac = j;
                }
            }
        }
        a[ar][ac] = '.';*/


    }
}
