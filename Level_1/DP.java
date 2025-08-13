import java.util.*;

class DP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("== Start ==");

        // int n = scanner.nextInt();
        // int res = Fibonacci(n);
        
        int n = 4;
        int res = CountPathsMem(4, new int[n+1]);
        System.out.println("Res = " + res);
        
        scanner.close();
        System.out.println("== End ==");
    }

    // 2. StairsPath
    public static int CountPathsMem(int n, int [] qb) {
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            return 0;
        }

        if (qb[n] != 0)
            return qb[n];

        qb[n] = CountPathsMem(n-1, qb) + CountPathsMem(n-2, qb) + CountPathsMem(n-3, qb);

        return qb[n];
    }

    // 1. Fibonnaci serires -> return n-th term
    public static int Fibonacci(int n) {
        int [] arr = new int[n];
        arr[0] = 1;
        arr[1] = 1;
        for (int i=2; i<n; i++) {
            arr[i] = arr[i-1] + arr[i-2];
        }
        System.out.println(Arrays.toString(arr));
        return arr[n-1];
    }
}
