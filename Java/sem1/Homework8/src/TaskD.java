import java.util.Scanner;

public class TaskD {
    static final int MOD = 998_244_353;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n, k;
        n = sc.nextInt();
        k = sc.nextInt();

        long[] degreesOfK = new long[n + 1];
        degreesOfK[0] = 1;

        long[] d = new long[n + 1];
        d[0] = 0;

        long res = 0;

        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                degreesOfK[i] = MathUtils.mult(MathUtils.modPow(k, (i + 1) / 2), i);
            } else {
                degreesOfK[i] = MathUtils.mult(MathUtils.modPow(k, i / 2), i / 2);
                degreesOfK[i] += MathUtils.mult(MathUtils.modPow(k, i / 2 + 1), i / 2);
                if(degreesOfK[i] >= MOD)
                    degreesOfK[i] -= MOD;
            }

            d[i] = degreesOfK[i];

            for (int j = 1; j*j <= i; j++) {
                if (i % j == 0 && j < i) {
                    degreesOfK[i] = MathUtils.sub(degreesOfK[i], MathUtils.mult(d[j], i / j - 1));
                    d[i] = MathUtils.sub(d[i], MathUtils.mult(d[j], i / j));
                    if (j * j < i && j != 1) {
                        j = i / j;
                        degreesOfK[i] = MathUtils.sub(degreesOfK[i], MathUtils.mult(d[j], i / j - 1));
                        d[i] = MathUtils.sub(d[i], MathUtils.mult(d[j], i / j));
                        j = i / j;
                    }
                }

            }

            res = (res+degreesOfK[i]) % MOD;
        }

        System.out.println(res);
    }

    static class MathUtils {
        public static long mult(final long a, final long b) {
            return (a * b) % MOD;
        }

        public static long modPow(long a, long b) {
            long res = 1;
            while (b > 0) {
                if ((b & 1) != 0) {
                    res = mult(res, a);
                }
                a = mult(a, a);
                b >>>= 1;
            }
            return res;
        }

        public static long add(long a, long b) {
            a += b;
            if (a >= MOD) a -= MOD;

            return a;
        }

        public static long sub(long a, long b) {
            a -= b;
            if (a < 0) a += MOD;

            return a;
        }

    }
}
