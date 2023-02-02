import java.util.Scanner;

public class TaskB {
    public static void main(String[] args) {
        int n;
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();

        int current_x = -710 * 25_000;

        while (n-- > 0) {
            System.out.println(current_x);
            current_x += 710;
        }
    }
}
