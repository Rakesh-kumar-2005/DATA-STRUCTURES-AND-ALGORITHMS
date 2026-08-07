package Dynamic_Programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Longest_Increasing_Subsequence {

    private static int recursive(int curr, int prev, int[] arr) {
        if (curr >= arr.length) {
            return 0;
        }

        int notPick = recursive(curr + 1, prev, arr);
        int pick = 0;
        if (prev == - 1 || arr[curr] > arr[prev]) {
            pick = 1 + recursive(curr + 1, curr, arr);
        }

        return Math.max(pick, notPick);
    }

    private static int memoization(int curr, int prev, int[] arr, int[][] dp) {
        if (curr >= arr.length) {
            return 0;
        }

        if (dp[curr][prev + 1] != - 1) {
            return dp[curr][prev + 1];
        }

        int notPick = memoization(curr + 1, prev, arr, dp);

        int pick = 0;
        if (prev == - 1 || arr[curr] > arr[prev]) {
            pick = 1 + memoization(curr + 1, curr, arr, dp);
        }

        return dp[curr][prev + 1] = Math.max(pick, notPick);
    }

    private static int tabulation(int[] arr) {

        int n = arr.length;
        int[][] dp = new int[n + 1][n + 1];

        for (int curr = n - 1; curr >= 0; curr--) {
            for (int prev = curr - 1; prev >= - 1; prev--) {
                int notPick = dp[curr + 1][prev + 1];
                int pick = 0;
                if (prev == - 1 || arr[curr] > arr[prev]) {
                    pick = 1 + dp[curr + 1][curr + 1];
                }
                dp[curr][prev + 1] = Math.max(pick, notPick);
            }
        }

        return dp[0][0];

    }

    private static int twoArraySpaceOptimization(int[] arr) {

        int n = arr.length;
        int[] next = new int[n + 1];

        for (int curr_ind = n - 1; curr_ind >= 0; curr_ind--) {
            int[] curr = new int[n + 1];
            for (int prev_ind = curr_ind - 1; prev_ind >= - 1; prev_ind--) {
                int notPick = next[prev_ind + 1];
                int pick = 0;
                if (prev_ind == - 1 || arr[curr_ind] > arr[prev_ind]) {
                    pick = 1 + next[curr_ind + 1];
                }
                curr[prev_ind + 1] = Math.max(pick, notPick);
            }
            next = curr;
        }

        return next[0];

    }

    private static int optimized(int[] arr) {

        int n = arr.length;
        int max = 1;

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int curr = 1; curr < n; curr++) {
            for (int prev = 0; prev < curr; prev++) {
                if (arr[curr] > arr[prev]) {
                    dp[curr] = Math.max(dp[curr], dp[prev] + 1);
                }
            }
            max = Math.max(max, dp[curr]);
        }

        return max;
    }

    private static int optimizedWithNoTLE(int[] arr) {
        int n = arr.length;
        ArrayList<Integer> lis = new ArrayList<>();

        for (int num : arr) {
            // Find position to replace using binary search
            int pos = Collections.binarySearch(lis, num);

            if (pos < 0) {
                pos = - (pos + 1); // insertion point
            }

            if (pos == lis.size()) {
                lis.add(num); // extend LIS
            } else {
                lis.set(pos, num); // replace to keep tail minimal
            }
        }

        return lis.size();
    }

    public static int longestIncreasingSubsequence(int arr[]) {

        int n = arr.length;
        int[][] dp = new int[n][n + 1];
        for (int[] t : dp) {
            Arrays.fill(t, - 1);
        }

        int recursiveResult = recursive(0, - 1, arr);
        int memoizationResult = memoization(0, - 1, arr, dp);
        int tabulationResult = tabulation(arr);
        int twoArraySpaceOptimizationResult = twoArraySpaceOptimization(arr);
        int optimizedResult = optimized(arr);
        int optimizedWithNoTLEResult = optimizedWithNoTLE(arr);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult
            + " | twoArraySpaceOptimization: " + twoArraySpaceOptimizationResult
            + " | optimized: " + optimizedResult
            + " | optimizedWithNoTLE: " + optimizedWithNoTLEResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult
            || tabulationResult != twoArraySpaceOptimizationResult
            || twoArraySpaceOptimizationResult != optimizedResult
            || optimizedResult != optimizedWithNoTLEResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return twoArraySpaceOptimizationResult;

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           LONGEST INCREASING SUBSEQUENCE                     ║");
        System.out.println("║  Find the length of the longest strictly increasing          ║");
        System.out.println("║  subsequence in the array                                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] arr1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Input: " + Arrays.toString(arr1));
        System.out.println("\nLongest increasing subsequence: [2, 3, 7, 101] (length 4)\n");

        int result1 = longestIncreasingSubsequence(arr1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result1 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Strictly Increasing ===");
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(arr2));
        System.out.println("\nEntire array is already increasing → length 5\n");

        int result2 = longestIncreasingSubsequence(arr2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result2 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Decreasing ===");
        int[] arr3 = {5, 4, 3, 2, 1};
        System.out.println("Input: " + Arrays.toString(arr3));
        System.out.println("\nNo increasing pair exists, best is any single element → length 1\n");

        int result3 = longestIncreasingSubsequence(arr3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result3 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: All Same Elements ===");
        int[] arr4 = {7, 7, 7, 7};
        System.out.println("Input: " + Arrays.toString(arr4));
        System.out.println("\nStrictly increasing means duplicates can't extend the subsequence");
        System.out.println("Best is any single element → length 1\n");

        int result4 = longestIncreasingSubsequence(arr4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result4 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Element ===");
        int[] arr5 = {42};
        System.out.println("Input: " + Arrays.toString(arr5));
        System.out.println("\nOnly one element, length 1\n");

        int result5 = longestIncreasingSubsequence(arr5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result5 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Zigzag Pattern ===");
        int[] arr6 = {0, 1, 0, 3, 2, 3};
        System.out.println("Input: " + Arrays.toString(arr6));
        System.out.println("\nLongest increasing subsequence: [0, 1, 2, 3] (length 4)\n");

        int result6 = longestIncreasingSubsequence(arr6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result6 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Larger Mixed Case ===");
        int[] arr7 = {7, 7, 7, 7, 7, 7, 7};
        System.out.println("Input: " + Arrays.toString(arr7));
        System.out.println("\nAll identical values, strictly increasing means length 1\n");

        int result7 = longestIncreasingSubsequence(arr7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result7 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the length of the longest strictly increasing ║");
        System.out.println("║           subsequence (not necessarily contiguous)           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Pick/Not-Pick With a 'Previous Index' State    ║");
        System.out.println("║    At each element, decide to include it (only if it beats   ║");
        System.out.println("║    the last picked value) or skip it                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Six Approaches, Cross-Verified:                             ║");
        System.out.println("║    1) Pure Recursion — pick/notPick with prev index tracking ║");
        System.out.println("║    2) Memoization — top-down, caches (curr, prev+1)          ║");
        System.out.println("║    3) Tabulation — bottom-up, full O(n×n) DP table           ║");
        System.out.println("║    4) Two-Array Space Optimization — rolling next/curr rows  ║");
        System.out.println("║    5) Optimized O(n²) — classic dp[i]=longest ending at i    ║");
        System.out.println("║    6) Optimized O(n log n) — binary search on tails array    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  longestIncreasingSubsequence() now calls all six and prints ║");
        System.out.println("║  each result, flagging any disagreement, before returning    ║");
        System.out.println("║  the final answer from the two-array space-optimized version.║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • 'prev' is offset by +1 in DP tables to allow index -1   ║");
        System.out.println("║    • Binary search approach doesn't reconstruct the actual   ║");
        System.out.println("║      subsequence, only its length                            ║");
        System.out.println("║    • Cross-checking approaches catches subtle DP bugs        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n log n) best case (binary search),      ║");
        System.out.println("║                    O(n²) for other DP variants               ║");
        System.out.println("║  Space Complexity: O(n²) tabulation, O(n) space-optimized    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}