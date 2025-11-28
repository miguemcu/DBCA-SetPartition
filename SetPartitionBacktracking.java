/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author m.mejia1
 */
import java.util.*;

public class SetPartitionBacktracking {

    private long calls = 0; // Para diagnóstico opcional

    public boolean canPartition(int[] nums) {
        int totalSum = 0;
        for (int num : nums) totalSum += num;

        if (totalSum % 2 != 0) return false;

        return backtrack(nums, 0, 0, totalSum / 2);
    }

    private boolean backtrack(int[] nums, int index, int currentSum, int target) {
        calls++;

        if (currentSum == target) return true;
        if (currentSum > target || index >= nums.length) return false;

        // Tomarlo
        if (backtrack(nums, index + 1, currentSum + nums[index], target))
            return true;

        // No tomarlo
        return backtrack(nums, index + 1, currentSum, target);
    }

    public long getCallCount() {
        return calls;
    }
}

