public class SquareandMultiply {
    public static void main(String args[])
    {
        System.out.println(sq(3,11,3));
    }
    static int sq(int x, int n, int c)
    {
        int z =1;
        for(int i=c-1; i<=0;i--)
        {
            z = (z*z) % n;
            if (i == 1)
            {
                z = (z*x) %n;
            }
        }
        return z;
    }
}
