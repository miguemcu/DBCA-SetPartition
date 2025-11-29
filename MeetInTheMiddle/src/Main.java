import java.util.*;

public class Main {

    public static boolean canPartition(int[] nums) {
        int total = 0;
        for (int n : nums) total += n;
        if (total % 2 != 0) return false;

        int target = total / 2;

        int mid = nums.length / 2;
        int[] left = Arrays.copyOfRange(nums, 0, mid);
        int[] right = Arrays.copyOfRange(nums, mid, nums.length);

        List<Integer> sumsLeft = allSums(left);
        List<Integer> sumsRight = allSums(right);

        Collections.sort(sumsRight);

        for (int s : sumsLeft) {
            int needed = target - s;
            if (Collections.binarySearch(sumsRight, needed) >= 0) {
                return true;
            }
        }
        return false;
    }

    // genera todas las sumas posibles de un subconjunto
    private static List<Integer> allSums(int[] nums) {
        List<Integer> sums = new ArrayList<>();
        int n = nums.length;
        int limit = 1 << n;
        for (int mask = 0; mask < limit; mask++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) sum += nums[i];
            }
            sums.add(sum);
        }
        return sums;
    }

    public static void main(String[] args) {
        System.out.println("------------------------------------------------");
        System.out.println(" EXPERIMENTO SET PARTITION");
        System.out.println(" Enfoque: Meet in the Middle");
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
