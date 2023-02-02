import java.util.Arrays;
import java.util.Scanner;

public class TaskE {
    void dfs(int[][] graph, int[] used, int[] teams, int v) {

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n, m;
        n = sc.nextInt();
        m = sc.nextInt();

        int[][] graph = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            int v, u;
            v = sc.nextInt();
            u = sc.nextInt();
            v--;
            u--;

            graph[v][u] = 1;
            graph[u][v] = 1;
        }

        int[] teams = new int[m];
        for (int i = 0; i < m; i++) {
            teams[i] = sc.nextInt();
        }


    }
}
