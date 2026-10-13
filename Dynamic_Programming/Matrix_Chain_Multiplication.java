package Dynamic_Programming;

import java.util.Arrays;

public class Matrix_Chain_Multiplication {

    private static int memoization(int i, int j, int[] nums, int[][] dp) {

        if (i == j) {
            return 0;
        }

        if (dp[i][j] != - 1) {
            return dp[i][j];
        }

        int miniStep = 2147483647;
        for (int k = i; k < j; k++) {
            int step = nums[i - 1] * nums[k] * nums[j] + memoization(i, k, nums, dp) + memoization(k + 1, j, nums, dp);

            miniStep = Math.min(miniStep, step);
        }

        return dp[i][j] = miniStep;
    }

    private static int tabulation(int[] nums, int N) {

        int[][] dp = new int[N][N];

        for (int i = N - 1; i >= 1; i--) {
            for (int j = i + 1; j < N; j++) {

                int miniStep = 2147483647;
                for (int k = i; k < j; k++) {
                    int step = nums[i - 1] * nums[k] * nums[j] + dp[i][k] + dp[k + 1][j];
                    miniStep = Math.min(miniStep, step);
                }

                dp[i][j] = miniStep;

            }
        }

        return dp[1][N - 1];
    }

    private static int matrixMultiplication(int[] nums) {

        int n = nums.length;
        int[][] dp = new int[n][n];

        for (int[] temp : dp) {
            Arrays.fill(temp, - 1);
        }

        int memoizationResult = memoization(1, n - 1, nums, dp);
        int tabulationResult = tabulation(nums, n);

        System.out.println("  [Verification] memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult);

        if (memoizationResult != tabulationResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return tabulationResult;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              MATRIX CHAIN MULTIPLICATION                     ║");
        System.out.println("║  Find the minimum number of scalar multiplications needed    ║");
        System.out.println("║  to multiply a chain of matrices                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Four Matrices ===");
        int[] nums1 = {10, 30, 5, 60};
        System.out.println("Dimensions: " + Arrays.toString(nums1));
        System.out.println("\nMatrices: A(10x30), B(30x5), C(5x60)");
        System.out.println("(AB)C = 10*30*5 + 10*5*60 = 1500 + 3000 = 4500\n");

        int result1 = matrixMultiplication(nums1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 4500");
        System.out.println("  Status: " + (result1 == 4500 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Two Matrices Only ===");
        int[] nums2 = {10, 20, 30};
        System.out.println("Dimensions: " + Arrays.toString(nums2));
        System.out.println("\nOnly one multiplication possible: 10*20*30 = 6000\n");

        int result2 = matrixMultiplication(nums2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 6000");
        System.out.println("  Status: " + (result2 == 6000 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Matrix ===");
        int[] nums3 = {10, 20};
        System.out.println("Dimensions: " + Arrays.toString(nums3));
        System.out.println("\nOnly one matrix, no multiplication needed → 0\n");

        int result3 = matrixMultiplication(nums3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Five Matrices ===");
        int[] nums4 = {40, 20, 30, 10, 30};
        System.out.println("Dimensions: " + Arrays.toString(nums4));
        System.out.println("\nOptimal parenthesization: ((A(BC))D) → cost 26000\n");

        int result4 = matrixMultiplication(nums4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 26000");
        System.out.println("  Status: " + (result4 == 26000 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Another Five Matrices ===");
        int[] nums5 = {10, 20, 30, 40, 30};
        System.out.println("Dimensions: " + Arrays.toString(nums5));
        System.out.println("\nOptimal parenthesization gives cost 30000\n");

        int result5 = matrixMultiplication(nums5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 30000");
        System.out.println("  Status: " + (result5 == 30000 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Uniform Dimensions ===");
        int[] nums6 = {2, 2, 2, 2};
        System.out.println("Dimensions: " + Arrays.toString(nums6));
        System.out.println("\nAll matrices 2x2, each multiplication costs 8, two needed → 16\n");

        int result6 = matrixMultiplication(nums6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 16");
        System.out.println("  Status: " + (result6 == 16 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Skewed Dimensions ===");
        int[] nums7 = {1, 2, 3, 4};
        System.out.println("Dimensions: " + Arrays.toString(nums7));
        System.out.println("\n(AB)C = 1*2*3 + 1*3*4 = 6 + 12 = 18\n");

        int result7 = matrixMultiplication(nums7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 18");
        System.out.println("  Status: " + (result7 == 18 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                            ║");
        System.out.println("║  ──────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the cheapest order to multiply a chain of       ║");
        System.out.println("║           matrices, where matrix i has size nums[i-1]×nums[i]  ║");
        System.out.println("║                                                                ║");
        System.out.println("║  Key Insight: Try Every Split Point (Partition DP)             ║");
        System.out.println("║    For chain i..j, split at every k between i and j-1.         ║");
        System.out.println("║    Cost = cost(i..k) + cost(k+1..j) + nums[i-1]*nums[k]*nums[j]║");
        System.out.println("║    Take the minimum over all k.                                ║");
        System.out.println("║                                                                ║");
        System.out.println("║  Two Approaches, Cross-Verified:                               ║");
        System.out.println("║    1) Memoization — top-down, caches dp[i][j]                  ║");
        System.out.println("║    2) Tabulation — bottom-up, i from N-1 down to 1,            ║");
        System.out.println("║       j from i+1 up to N-1                                     ║");
        System.out.println("║                                                                ║");
        System.out.println("║  matrixMultiplication() calls both, prints each result,        ║");
        System.out.println("║  flags any disagreement, and returns the tabulation answer.    ║");
        System.out.println("║                                                                ║");
        System.out.println("║  Example: nums = [10, 30, 5, 60]                               ║");
        System.out.println("║    Split (AB)C: 10*30*5 + 10*5*60 = 1500 + 3000 = 4500         ║");
        System.out.println("║    Split A(BC): 30*5*60 + 10*30*60 = 9000 + 18000 = 27000      ║");
        System.out.println("║    Minimum = 4500                                              ║");
        System.out.println("║                                                                ║");
        System.out.println("║  Properties:                                                   ║");
        System.out.println("║    • Base case: i == j means a single matrix, cost 0           ║");
        System.out.println("║    • Answer lives at dp[1][N-1]                                ║");
        System.out.println("║    • Parenthesization changes cost, not the final result       ║");
        System.out.println("║                                                                ║");
        System.out.println("║  Time Complexity: O(N³) — N² states, O(N) split loop each      ║");
        System.out.println("║  Space Complexity: O(N²) for the dp table                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

    }

}