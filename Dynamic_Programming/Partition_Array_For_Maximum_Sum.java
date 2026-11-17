package Dynamic_Programming;

import java.util.Arrays;

public class Partition_Array_For_Maximum_Sum {

    private static int recursive(int idx, int[] arr, int n, int k) {

        if (idx == n) {
            return 0;
        }

        int len = 0;
        int maxNum = Integer.MIN_VALUE;
        int maxSum = Integer.MIN_VALUE;

        for (int j = idx; j < Math.min(n, idx + k); j++) {
            len++;
            maxNum = Math.max(maxNum, arr[j]);

            int sum = len * maxNum + recursive(j + 1, arr, n, k);
            maxSum = Math.max(maxSum, sum);
        }

        return maxSum;
    }

    private static int memoization(int idx, int[] arr, int n, int k, int[] dp) {

        if (idx == n) {
            return 0;
        }

        if (dp[idx] != - 1) {
            return dp[idx];
        }

        int len = 0;
        int maxNum = Integer.MIN_VALUE;
        int maxSum = Integer.MIN_VALUE;

        for (int j = idx; j < Math.min(n, idx + k); j++) {
            len++;
            maxNum = Math.max(maxNum, arr[j]);

            int sum = len * maxNum + memoization(j + 1, arr, n, k, dp);
            maxSum = Math.max(maxSum, sum);
        }

        return dp[idx] = maxSum;

    }

    private static int tabulation(int[] arr, int n, int k) {

        int[] dp = new int[n + 1];

        for (int idx = n - 1; idx >= 0; idx--) {

            int len = 0;
            int maxNum = Integer.MIN_VALUE;
            int maxSum = Integer.MIN_VALUE;

            for (int j = idx; j < Math.min(n, idx + k); j++) {
                len++;
                maxNum = Math.max(maxNum, arr[j]);

                int sum = len * maxNum + dp[j + 1];
                maxSum = Math.max(maxSum, sum);
            }

            dp[idx] = maxSum;
        }

        return dp[0];
    }

    private static int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;

        int[] dp = new int[n];
        Arrays.fill(dp, - 1);

        int recursiveResult = recursive(0, arr, n, k);
        int memoizationResult = memoization(0, arr, n, k, dp);
        int tabulationResult = tabulation(arr, n, k);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return tabulationResult;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              PARTITION ARRAY FOR MAXIMUM SUM                 ║");
        System.out.println("║  Split the array into subarrays of length at most k, turn    ║");
        System.out.println("║  each into its max value, and maximize the total sum         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] arr1 = {1, 15, 7, 9, 2, 5, 10};
        int k1 = 3;
        System.out.println("Input: " + Arrays.toString(arr1) + ", k=" + k1);
        System.out.println("\nPartition: [1,15,7] [9] [2,5,10] → 15*3 + 9 + 10*3 = 84\n");

        int result1 = maxSumAfterPartitioning(arr1, k1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 84");
        System.out.println("  Status: " + (result1 == 84 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: k = 4 ===");
        int[] arr2 = {1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3};
        int k2 = 4;
        System.out.println("Input: " + Arrays.toString(arr2) + ", k=" + k2);
        System.out.println("\nStandard LeetCode example → 83\n");

        int result2 = maxSumAfterPartitioning(arr2, k2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 83");
        System.out.println("  Status: " + (result2 == 83 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Element ===");
        int[] arr3 = {1};
        int k3 = 1;
        System.out.println("Input: " + Arrays.toString(arr3) + ", k=" + k3);
        System.out.println("\nOnly one element → 1\n");

        int result3 = maxSumAfterPartitioning(arr3, k3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result3 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: k = 1 (No Merging) ===");
        int[] arr4 = {3, 1, 2};
        int k4 = 1;
        System.out.println("Input: " + Arrays.toString(arr4) + ", k=" + k4);
        System.out.println("\nEvery subarray has length 1, so the sum is unchanged: 3+1+2 = 6\n");

        int result4 = maxSumAfterPartitioning(arr4, k4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result4 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: k Equals Array Length ===");
        int[] arr5 = {2, 9, 4};
        int k5 = 3;
        System.out.println("Input: " + Arrays.toString(arr5) + ", k=" + k5);
        System.out.println("\nOne subarray of all elements: 9*3 = 27\n");

        int result5 = maxSumAfterPartitioning(arr5, k5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 27");
        System.out.println("  Status: " + (result5 == 27 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: All Same Values ===");
        int[] arr6 = {5, 5, 5, 5};
        int k6 = 2;
        System.out.println("Input: " + Arrays.toString(arr6) + ", k=" + k6);
        System.out.println("\nAny partition gives 5*4 = 20\n");

        int result6 = maxSumAfterPartitioning(arr6, k6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 20");
        System.out.println("  Status: " + (result6 == 20 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Standard Single-Element Large k ===");
        int[] arr7 = {1};
        int k7 = 1;
        System.out.println("Input: " + Arrays.toString(arr7) + ", k=" + k7);
        System.out.println("\nTrivial case, answer is 1\n");

        int result7 = maxSumAfterPartitioning(arr7, k7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result7 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Partition the array into contiguous subarrays of   ║");
        System.out.println("║           length at most k; each subarray becomes its max    ║");
        System.out.println("║           repeated, maximize the total sum                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Front Partitioning                             ║");
        System.out.println("║    From idx, extend a block up to k elements, tracking the   ║");
        System.out.println("║    running max. Block value = len * maxNum, then solve the   ║");
        System.out.println("║    rest starting at j+1.                                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recurrence:                                                 ║");
        System.out.println("║    f(idx) = max over j in [idx, min(n, idx+k)) of            ║");
        System.out.println("║             len * maxNum + f(j+1)                            ║");
        System.out.println("║    Base: f(n) = 0                                            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Three Approaches, Cross-Verified:                           ║");
        System.out.println("║    1) Recursive — tries every block length up to k           ║");
        System.out.println("║    2) Memoization — top-down, caches dp[idx]                 ║");
        System.out.println("║    3) Tabulation — bottom-up, idx from n-1 down to 0         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  maxSumAfterPartitioning() calls all three, prints each      ║");
        System.out.println("║  result, flags any disagreement, returns tabulation.         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: arr = [1, 15, 7, 9, 2, 5, 10], k = 3               ║");
        System.out.println("║    Best: [1,15,7] [9] [2,5,10] = 45 + 9 + 30 = 84            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Same front-partition pattern as Palindrome Partitioning ║");
        System.out.println("║    • Block max is updated incrementally, no re-scan needed   ║");
        System.out.println("║    • Answer lives at dp[0]                                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n × k) for memoization/tabulation        ║");
        System.out.println("║  Space Complexity: O(n) for dp (recursion adds O(n) stack)   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}