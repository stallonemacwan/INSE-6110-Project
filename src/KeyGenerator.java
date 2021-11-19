import java.util.Random;

public class KeyGenerator {
    public static int check_prime(int a) {
        boolean flag = false;
        for (int i = 2; i <= a / 2; i++) {
            if (a % i == 0) {
                flag = true;
                break;
            }
        }
        if (!flag)
            return a;
        else
            return 0;
    }

    public static int check_gcd(long a, int b) {
        long tmp;
        long ba = b;
        while (b != 0) {
            if (a < b) {
                tmp = a;
                a = b;
                ba = tmp;
            }
            tmp = b;
            ba = a % b;
            a = tmp;
        }
        int ans = (int) a;
        return ans;
    }

    public static int mod_Inverse(int base, long N) {
        int i = 1;
        while (i < N) {
            if (((long) base * i) % N == 1) {
                break;
            }
            i++;
        }
        return i;
    }

    public static void main(String args[]) {
        Random r = new Random();
        int p, q, d, e;
        long N, Phi;
        do {
            int n = r.nextInt(65535);
            p = check_prime(n);
        } while (p == 0);
        do {
            int n = r.nextInt(65535);
            q = check_prime(n);
        } while (q == 0);
        System.out.println("My parameters generated:");
        System.out.println("Value of p: " + p);
        System.out.println("Value of q: " + q);

        N = (long) p * (long) q;
        int valid = 1;
        Phi = ((long) p - 1) * ((long) q - 1);
        do {
            int n = r.nextInt(65535);
            e = check_prime(n);
            if (e < Phi && e != 0)
                valid = check_gcd(Phi, e);
            else
                do {
                    int ne = r.nextInt(65535);
                    e = check_prime(ne);
                } while (e == 0);
        } while (valid != 1);

        System.out.println("Value of N: " + N);
        System.out.println("Value of Phi: " + Phi);
        System.out.println("Value of e: " + e);

        d = mod_Inverse(e, Phi);
        System.out.println("Value of d: " + d);
    }
}
