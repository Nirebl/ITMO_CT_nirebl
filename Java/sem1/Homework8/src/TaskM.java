import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TaskM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int n;
            n = sc.nextInt();

            int[] difficulties = new int[n];

            for (int i = 0; i < n; ++i) {
                difficulties[i] = sc.nextInt();
            }

            int answer = 0;
            Map<Integer, Integer> c = new HashMap<>();

            for (int j = n - 1; j >= 0; --j) {
                for (int i = 0; i <= j - 1; i++) {
                    int ak = 2 * difficulties[j] - difficulties[i];
                    if (c.containsKey(ak)) {
                        answer += c.get(ak);
                    }
                }
                c.put(difficulties[j], c.getOrDefault(difficulties[j], 0) + 1);
            }
            System.out.println(answer);
        }
    }

}
