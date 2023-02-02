package search;

public class BinarySearch {
    // Pred: 2 <= args.length && args[i] is integer
    // Post: R >= 0 && R  <= args.length - 1 && args[R] <= args[0]
    public static void main(String[] args) {

        //args[0] is integer
        int x = Integer.parseInt(args[0]);

        // args.length >= 2
        int[] numbers = new int[args.length - 1];

        // args[i] is integer
        for (int i = 1; i < args.length; i++) {
            numbers[i - 1] = Integer.parseInt(args[i]);
        }
        //System.out.println(binaryIterative(numbers, x));
        System.out.println(binaryRecursive(numbers, -1, numbers.length, x));
        // Post: R >= 0 && R  <= args.length - 1 && args[R] <= args[0]
    }


    // Pred: a.length >= 1:
    // Post: R >= 0 && R  <= args.length - 1 && args[R] <= args[0]
    public static int binaryIterative(int[] a, int value) {
        int l = -1;
        int r = a.length;
        // l = -1 && r = a.length && l <= r - 1
        // I: -1 <= l < k <= r <= a.length
        while (l < r - 1) {
            // l < r - 1
            int m = (l + r) / 2;
            // (l < r - 1 && m = (l + r)/2) -> l < m < r
            if (a[m] <= value) {
                // a[m] <= value -> k <= m
                r = m;
                // r' = (l + r)/2 && k <= r'
            } else {
                // a[m] > value -> m < k
                l = m;
                // l' = (l + r)/2 && l' < k
            }
            // (l' > l || r' < r) && (l < m < r)
        }
        // I: -1 <= l < k <= r <= a.length && !(l < r - 1)

        // Post: R >= 0 && R  <= args.length - 1 && args[R] <= args[0]
        return r;
    }

    // Pred: -1 <= l < r <= a.length &&
    // a.length > 1
    // Post: R >= 0 && R  <= args.length - 1 && args[R] <= args[0]
    public static int binaryRecursive(int[] a, int l, int r, int value) {
        if (l < r - 1) {
            // l < r - 1
            int m = (l + r) / 2;
            // (l < r - 1 && m = (l + r)/2) -> l < m < r
            if (a[m] <= value) {
                // a[m] <= value
                return binaryRecursive(a, l, m, value);
                // R = binaryRecursive(a, m, r, value)
            } else {
                // a[m] > value
                return binaryRecursive(a, m, r, value);
                // R = binaryRecursive(a, l, m, value)
            }
        }

        // Post: R >= 0 && R  <= args.length - 1 && args[R] <= args[0]
        return r;
    }

}
