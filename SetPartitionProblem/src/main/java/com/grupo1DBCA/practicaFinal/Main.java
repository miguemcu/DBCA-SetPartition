package com.grupo1DBCA.practicaFinal;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    // Backtracking básico: explora todas las posibilidades
    public static boolean canPartition(int[] nums) {
        int total = Arrays.stream(nums).sum();
        if (total % 2 != 0) {
			return false;
		}
        return backtrack(nums, 0, total / 2);
    }

    private static boolean backtrack(int[] nums, int i, int target) {
        if (target == 0) {
			return true;
		}
        if (i >= nums.length || target < 0) {
			return false;
		}

        // tomar
        if (backtrack(nums, i + 1, target - nums[i])) {
			return true;
		}

        // no tomar
        return backtrack(nums, i + 1, target);
    }

    public static void main(String[] args) {
        System.out.println("------------------------------------------------");
        System.out.println("EXPERIMENTO SET PARTITION");
        System.out.println("------------------------------------------------");

        int[] arr = IntStream.range(1, 7001).toArray();

        int total = Arrays.stream(arr).sum();
        int target = total / 2;
        boolean resp = canPartition(arr);

        System.out.println(" Set fijo de tamaño = " + arr.length);

        // Mostrar primeros 5 y últimos 5 elementos
        int[] primeros5 = Arrays.copyOfRange(arr, 0, 5);
        int[] ultimos5 = Arrays.copyOfRange(arr, arr.length - 5, arr.length);

        System.out.println(" Primeros 5 elementos: " + Arrays.toString(primeros5));
        System.out.println(" Últimos 5 elementos: " + Arrays.toString(ultimos5));

        System.out.println("\n Suma total del set = " + total);
        System.out.println(" Suma objetivo (target) = " + target);
        System.out.println(" ¿Se puede particionar? " + resp);
    }
}