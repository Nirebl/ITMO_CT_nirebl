package search;

public class BinarySearchMin {
    // defined as standardСondition:
    // 1 <= args.length && args[i] is integer && args[i] != null &&
    // \exists K in [0, args.size()) \forall i > K, args[i - 1] < args[i] &&
    // \forall i < K - 1 args[i] > args[i + 1]

    // Pred:
    // standardCondition(args):

    public static void main(String[] args) {
        int[] numbers = new int[args.length];

        for (int i = 0; i < args.length; i++) {
            numbers[i] = Integer.parseInt(args[i]);
        }
        // Post : numbers.size() = args.size() && \forall k in [0, numbers.size()) numbers[k] = Integer.parseInt(args[k])

        //System.out.println(binaryMinIterative(numbers));

        System.out.println(binaryMinRecursive(numbers, 0, numbers.length));
    }

    // Let min(a) = min(a[0]...a[a.size()-1])
    // Let includesMin(l, r) \exists k in [l, r) : k = min(a)

    // Pred: standardСondition(a)
    // Post : R = min(a)
    public static int binaryMinIterative(int[] a) {
        int l = 0;
        int r = a.length;
        // Post : standardСondition(a) && l = 0 && r = a.length && l <= r - 1 && includesMin(l,r)

        // Inv: standardСondition(a) && includesMin(l,r)
        while (l != r - 1) {
            int m = (l + r) / 2;
            // Post : standardСondition(a) && (l < r - 1 && m = (l + r) / 2) --> l < m < r && includesMin(l,r)

            if (a[m - 1] < a[m]) {
                // Pred : standardСondition(a) && l < m < r && a[m - 1] < a[m] && includesMin(l,r)
                r = m;
                // Post : standardСondition(a) && r' = m && includesMin(l,r')
            } else {
                // Pred : standardСondition(a) && l < m < r && a[m - 1] >= a[m] && includesMin(l,r)
                l = m;
                // Post : standardСondition(a) && l' = m && includesMin(l',r)
            }
            // Post : standardСondition(a) && includesMin(l,r)
        }
        // Post : standardСondition(a) && (includesMin(l,r) && l + 1 = r) --> includesMin(l, l + 1) --> a[l] = min(a)
        return a[l];
    }

    // Pred: standardСondition(a) && includesMin(l,r)
    // Post : R = min(a)
    public static int binaryMinRecursive(int[] a, int l, int r) {
        if (l != r - 1) {
            int m = (l + r) / 2;

            // Post : standardСondition(a) && (l < r - 1 && m = (l + r) / 2) --> l < m < r && includesMin(l,r)
            if (a[m - 1] < a[m]) {
                // Pred : standardСondition(a) && l < m < r && a[m - 1] < a[m] && includesMin(l,r)
                r = m;
                // Post : standardСondition(a) && r' = m && includesMin(l,r')
            } else {
                // Pred : standardСondition(a) && l < m < r && a[m - 1] >= a[m] && includesMin(l,r)
                l = m;
                // Post : standardСondition(a) && l' = m && includesMin(l',r)
            }
            // Post : standardСondition(a) && includesMin(l,r)
            return binaryMinRecursive(a, l, r);
        }
        // Post : standardСondition(a) && (includesMin(l,r) && l + 1 = r) --> includesMin(l, l + 1) --> a[l] = min(a)

        return a[l];
    }
}
