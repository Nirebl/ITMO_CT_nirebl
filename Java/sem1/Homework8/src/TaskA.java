import java.util.Scanner;

public class TaskA {

    public static void main(String[] args) {
        int a, b, n;
        Scanner cin = new Scanner(System.in);

        a = cin.nextInt();
        b = cin.nextInt();
        n = cin.nextInt();

        int answer = (int) (1 + 2 * Math.ceil((double) (n - b) / (double) (b - a)));
        System.out.print(answer);
    }
}
