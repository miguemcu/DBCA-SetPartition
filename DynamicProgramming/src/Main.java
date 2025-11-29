import java.util.*;

public class Main {

    public static boolean canPartition(int[] nums) {
        int total = 0;
        for (int n : nums) total += n;
        if (total % 2 != 0) return false;

        int target = total / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int s = target; s >= num; s--) {
                if (dp[s - num]) dp[s] = true;
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        System.out.println("------------------------------------------------");
        System.out.println(" EXPERIMENTO SET PARTITION");
        System.out.println(" Enfoque: Programación Dinámica");
        System.out.println("------------------------------------------------");

        int[] arr = {3, 1, 5, 9, 12};

        long t1 = System.nanoTime();
        boolean resp = canPartition(arr);
        long t2 = System.nanoTime();
        long exeTime = t2 - t1;

        System.out.println("Set fijo de tamaño = " + arr.length + "");
        System.out.println("Tiempo (ns): " + exeTime );
        System.out.println("Tiempo (s): " + exeTime / 1_000_000_000.0);
        System.out.println("Tiempo (min): " + exeTime / 60_000_000_000.0);
    }
}
