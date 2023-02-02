import java.util.Scanner;


public class TaskI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;
        n = sc.nextInt();

        int xLeft = Integer.MAX_VALUE;
        int xRight = Integer.MIN_VALUE;
        int yLeft = Integer.MAX_VALUE;
        int yRight = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int x, y, h;
            x = sc.nextInt();
            y = sc.nextInt();
            h = sc.nextInt();

            xLeft = Integer.min(xLeft, x - h);
            xRight = Integer.max(xRight, x + h);

            yLeft = Integer.min(yLeft, y - h);
            yRight = Integer.max(yRight, y + h);
        }
        int x = (xRight + xLeft) / 2;
        int y = (yRight + yLeft) / 2;
        int h = (Integer.max(xRight - xLeft, yRight - yLeft) + 1) / 2;
        System.out.print(x + " " + y + " " + h);
    }


}
