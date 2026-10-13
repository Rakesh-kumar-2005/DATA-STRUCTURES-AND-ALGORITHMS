package Dynamic_Programming;

/*

    Description:
      Following program finds the minimum number of scalar multiplications required
        to multiply a chain of matrices, using partition DP with memoization and tabulation...

    Problem Statement:
      -> Given an array nums of size N where nums[i-1] × nums[i] represents matrix i's dimensions...
      -> There are N-1 matrices: matrix 1 is nums[0]×nums[1], matrix 2 is nums[1]×nums[2], etc...
      -> Different parenthesizations yield different multiplication costs...
      -> The cost of multiplying a p×q matrix by a q×r matrix is p×q×r scalar multiplications...
      -> Return the minimum total scalar multiplications needed to compute the product...

    Key Insight:
      -> The problem has optimal substructure: the optimal parenthesization of a chain
           can be split at any position k into two independently optimal sub-chains...
      -> Try every possible split point k between i and j-1...
      -> Cost(i, j) = min over all k of: Cost(i, k) + Cost(k+1, j) + nums[i-1] × nums[k] × nums[j]...
      -> Base case: Cost(i, i) = 0 (single matrix, no multiplication needed)...
      -> The third term nums[i-1] × nums[k] × nums[j] is the cost of merging the two sub-results...

    Example:
      -> nums = [10, 30, 5, 60] → three matrices: A(10×30), B(30×5), C(5×60):
           Split at k=1 → (A)(BC): Cost(A) + Cost(BC) + 10×30×60 = 0 + 9000 + 18000 = 27000...
           Split at k=2 → (AB)(C): Cost(AB) + Cost(C) + 10×5×60 = 1500 + 0 + 3000 = 4500...
           Minimum = 4500...
      -> nums = [40, 20, 30, 10, 30] → four matrices:
           Optimal parenthesization → cost = 26000...
      -> nums = [10, 20] → single matrix → cost = 0...

    Recursive Relation:
      -> memoization(i, j, nums, dp):
           Base: i == j → return 0...
           For each k from i to j-1:
             step = nums[i-1] × nums[k] × nums[j] + memo(i, k) + memo(k+1, j)...
             miniStep = Math.min(miniStep, step)...
           Return dp[i][j] = miniStep...

    Index Convention:
      -> nums is 0-indexed: nums[0], nums[1], ..., nums[N-1]...
      -> Matrices are 1-indexed: matrix i spans nums[i-1] to nums[i]...
      -> Chain from i to j uses rows = nums[i-1], shared dimension = nums[k], cols = nums[j]...
      -> Split at k means left chain uses nums[i-1]×nums[k] and right uses nums[k]×nums[j]...

    Approach 1 - Memoization (Top-Down DP):
      -> 2D dp array of size N × N, initialized to -1...
      -> dp[i][j] caches minimum cost for chain from matrix i to matrix j...
      -> Start with memoization(1, N-1, nums, dp)...
      -> Time: O(N³), Space: O(N²) + O(N²) recursion stack in worst case...

    Approach 2 - Tabulation (Bottom-Up DP):
      -> dp[i][j] = minimum cost to multiply matrices i through j...
      -> Fill diagonally: outer loop i from N-1 down to 1, inner loop j from i+1 to N-1...
      -> This ensures sub-chains (i,k) and (k+1,j) are computed before (i,j)...
      -> Answer: dp[1][N-1]...
      -> Time: O(N³), Space: O(N²)...

    Why Iterate i from N-1 Down to 1 in Tabulation:
      -> dp[i][j] depends on dp[i][k] and dp[k+1][j] where k is between i and j-1...
      -> dp[k+1][j] has a larger i-index (k+1 > i) → already filled when we process i...
      -> Going from larger i to smaller i ensures all dependencies are pre-computed...

    Step-by-Step Trace (nums = [1, 2, 3, 4]):
      -> Three matrices: A(1×2), B(2×3), C(3×4)...
      -> dp[1][1]=0, dp[2][2]=0, dp[3][3]=0 (base cases)...
      -> dp[1][2]: k=1: 1×2×3 + 0 + 0 = 6 → dp[1][2]=6...
      -> dp[2][3]: k=2: 2×3×4 + 0 + 0 = 24 → dp[2][3]=24...
      -> dp[1][3]: k=1: 1×2×4 + dp[1][1] + dp[2][3] = 8+0+24=32...
                   k=2: 1×3×4 + dp[1][2] + dp[3][3] = 12+6+0=18...
                   miniStep = min(32, 18) = 18 → dp[1][3]=18...
      -> Return dp[1][3] = 18...

    Why nums[i-1] × nums[k] × nums[j] for Merge Cost:
      -> After splitting at k: left result is of dimension nums[i-1] × nums[k]...
      -> Right result is of dimension nums[k] × nums[j]...
      -> Multiplying them costs nums[i-1] × nums[k] × nums[j] scalar operations...

    Edge Cases:
      -> Single matrix (N=2) → dp[1][1]=0 → answer = 0...
      -> Two matrices (N=3) → one split only, one multiplication → nums[0]×nums[1]×nums[2]...
      -> All equal dimensions → all parenthesizations cost the same...
      -> Highly skewed dimensions → optimal parenthesization can differ significantly from naive...

    Time and Space Complexity:
      -> Time:  O(N³) — O(N²) states × O(N) split loop per state...
      -> Space: O(N²) for the dp table in both approaches...

    Applications:
      -> Database query optimization with multiple join operations...
      -> Compiler expression tree optimization for chained operations...
      -> Scientific computing where matrix chain products are common...
      -> Deep learning backpropagation with chained tensor multiplications...

*/

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
