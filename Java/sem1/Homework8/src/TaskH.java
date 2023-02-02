import java.util.*;

public class TaskH {
    static final int MOD = 998_244_353;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;
        n = sc.nextInt();

        long mx = 0;

        long[] a = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            a[i] = sc.nextInt();
            mx = Math.max(mx, a[i]);
        }

        long[] pref = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            pref[i] = a[i];
            if (i > 0)
                pref[i] += pref[i - 1];
        }

        HashMap<Long, Long> mp = new HashMap<Long, Long>();
        int q;
        q = sc.nextInt();
        while (q-- > 0) {
            long t;
            t = sc.nextInt();
            if (mp.containsKey(t)) {
                System.out.println(mp.get(t));
                continue;
            }
            if (t < mx) {
                System.out.println("Impossible");
                continue;
            }

            int i = 0;
            long years = 0;
            while (i < n) {
                int l;
                int r;
                l = i;
                r = n;
                while (l < r - 1) {
                    int m = (l + r) / 2;
                    if (f(i, m, pref) <= t) {
                        l = m;
                    } else {
                        r = m;
                    }
                }
                i = r;
                years++;
            }
            mp.put(t, years);
            System.out.println(years);
        }
    }

    public static long f(int l, int r, long[] pref) {
        return pref[r] - (l > 0 ? pref[l - 1] : 0);
    }
}
