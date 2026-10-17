package Dynamic_Programming;

/*

    Description:
      Following program finds the maximum coins obtainable by bursting all balloons
        in the optimal order, using partition DP with three cross-verified approaches...

    Problem Statement:
      -> Given an array nums of balloon values...
      -> Bursting balloon i earns nums[i-1] × nums[i] × nums[i+1] coins (current neighbors)...
      -> Balloons at the boundary treat out-of-bounds neighbors as having value 1...
      -> Bursting a balloon removes it; its former neighbors become adjacent...
      -> Return the maximum total coins obtainable by bursting all balloons...

    Key Insight:
      -> Thinking about the first balloon to burst is hard: neighbors change after each burst...
      -> Instead, think about the LAST balloon to burst in each sub-range [left, right]...
      -> If balloon curr is the last burst in [left, right]:
           Its neighbors at that moment are the fixed boundaries arr[left-1] and arr[right+1]...
           (All other balloons in [left, right] have already been burst)...
           The two sub-ranges [left, curr-1] and [curr+1, right] are fully independent...
      -> This eliminates the "changing neighbors" problem entirely...

    Setup:
      -> Prepend 1 and append 1 to nums: arr = [1, nums[0], ..., nums[n-1], 1]...
      -> Real balloon indices run from 1 to n in arr...
      -> Virtual boundary balloons at arr[0]=1 and arr[n+1]=1 never get burst...

    Recursive Relation:
      -> f(left, right) = maximum coins from bursting all balloons in [left, right]...
      -> Base: left > right → return 0 (no balloons to burst)...
      -> For each curr from left to right (try as the last burst):
           coin = arr[left-1] × arr[curr] × arr[right+1]  ← burst curr last in this range...
                  + f(left, curr-1)                         ← burst left sub-range first...
                  + f(curr+1, right)                        ← burst right sub-range first...
      -> f(left, right) = max over all curr...
      -> Answer: f(1, n)...

    Why arr[left-1] × arr[curr] × arr[right+1] for Coins:
      -> When curr is the last burst in [left, right]:
           All balloons between left-1 and curr are already gone except arr[curr]...
           Left neighbor is arr[left-1] (boundary sentinel, never burst)...
           Right neighbor is arr[right+1] (boundary sentinel, never burst)...
           Coins = arr[left-1] × arr[curr] × arr[right+1]...
      -> These boundaries are fixed for this range regardless of which curr is chosen...

    Approach 1 - Recursive:
      -> Pure top-down without caching...
      -> Exponential recomputation of overlapping subproblems...
      -> Correct but slow → only usable for small inputs...
      -> Time: O(n! / pruning), Space: O(n) recursion stack...

    Approach 2 - Memoization (Top-Down DP):
      -> 2D dp array of size (n+1) × (n+1), initialized to -1...
      -> dp[left][right] caches maximum coins for range [left, right]...
      -> On cache hit: return stored result...
      -> Time: O(n³), Space: O(n²) + recursion stack...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[left][right] filled iteratively...
      -> Outer loop: left from n-2 down to 1 (process smaller sub-ranges first)...
      -> Inner loop: right from left up to n-2...
      -> Innermost loop: curr from left to right (try all last-burst choices)...
      -> Ensures dp[left][curr-1] and dp[curr+1][right] are already computed...
      -> Answer: dp[1][n-2] in tabulation (arr has n+2 elements, real balloons at 1..n)...
      -> Time: O(n³), Space: O(n²)...

    Step-by-Step Trace (nums = [3, 1, 5, 8]):
      -> arr = [1, 3, 1, 5, 8, 1], n=4...
      -> Single balloons (base cases):
           f(1,1): curr=1, coin=1×3×1=3...
           f(2,2): curr=2, coin=3×1×5=15...
           f(3,3): curr=3, coin=1×5×8=40...
           f(4,4): curr=4, coin=5×8×1=40...
      -> Pairs, triples built upward... f(1,4) = 167...

    Connection to Matrix Chain Multiplication and Stick Cutting:
      -> All three use partition DP: split [left, right] at k, combine sub-results...
      -> MCM: minimize cost, using dimensions at split point...
      -> Cut Stick: minimize cost, using segment length at boundaries...
      -> Burst Balloons: maximize coins, using boundary values at left-1 and right+1...
      -> Same O(n³) pattern, different cost function and objective direction...

    Edge Cases:
      -> Single balloon → f(1,1) = 1 × nums[0] × 1 = nums[0]...
      -> Two balloons → two choices for last burst, take maximum...
      -> All zeros → every burst earns 0 → total = 0...
      -> Zeros in array → optimal to burst zeros first (no coins lost from neighbors)...

    Time and Space Complexity:
      -> Time:  O(n³) — n² states × O(n) last-burst loop per state...
      -> Space: O(n²) for the dp table in both memoization and tabulation...

    Applications:
      -> Optimal task ordering where cost depends on surrounding completed tasks...
      -> Game theory problems with adaptive scoring based on board state...
      -> Resource allocation where removal order affects total collected value...
      -> Competitive programming interval DP with product-based scoring...

*/

import java.util.Arrays;

public class Burst_Balloons {

    private static int recursion(int[] arr, int left, int right) {

        if (left > right) {
            return 0;
        }

        int maxCoin = Integer.MIN_VALUE;
        for (int curr = left; curr <= right; curr++) {

            int coin = arr[left - 1] * arr[curr] * arr[right + 1]
                + recursion(arr, left, curr - 1)
                + recursion(arr, curr + 1, right);

            maxCoin = Math.max(coin, maxCoin);
        }

        return maxCoin;
    }

    private static int memoization(int[] arr, int left, int right, int[][] dp) {

        if (left > right) {
            return 0;
        }

        if (dp[left][right] != - 1) {
            return dp[left][right];
        }

        int maxCoin = Integer.MIN_VALUE;
        for (int curr = left; curr <= right; curr++) {

            int coin = arr[left - 1] * arr[curr] * arr[right + 1]
                + memoization(arr, left, curr - 1, dp)
                + memoization(arr, curr + 1, right, dp);

            maxCoin = Math.max(coin, maxCoin);
        }

        return dp[left][right] = maxCoin;
    }

    private static int tabulation(int[] arr) {

        int n = arr.length;
        int[][] dp = new int[n][n];

        for (int left = n - 2; left >= 1; left--) {
            for (int right = left; right <= n - 2; right++) {

                int maxCoin = Integer.MIN_VALUE;
                for (int curr = left; curr <= right; curr++) {

                    int coin = arr[left - 1] * arr[curr] * arr[right + 1]
                        + dp[left][curr - 1]
                        + dp[curr + 1][right];

                    maxCoin = Math.max(coin, maxCoin);
                }

                dp[left][right] = maxCoin;
            }
        }

        return dp[1][n - 2];
    }

    private static int maxCoins(int[] nums) {

        int n = nums.length;
        int[] arr = new int[n + 2];

        arr[0] = 1;
        arr[n + 1] = 1;

        for (int left = 0; left < n; left++) {
            arr[left + 1] = nums[left];
        }

        int[][] dp = new int[n + 1][n + 1];
        for (int temp[] : dp) {
            Arrays.fill(temp, - 1);
        }

        int recursionResult = recursion(arr, 1, n);
        int memoizationResult = memoization(arr, 1, n, dp);
        int tabulationResult = tabulation(arr);

        System.out.println("  [Verification] recursion: " + recursionResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult);

        if (recursionResult != memoizationResult || memoizationResult != tabulationResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return tabulationResult;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     BURST BALLOONS                           ║");
        System.out.println("║  Find the maximum coins collectable by bursting all balloons,║");
        System.out.println("║  where bursting i earns nums[i-1] * nums[i] * nums[i+1]      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] nums1 = {3, 1, 5, 8};
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("\nBurst order: 1, 5, 3, 8");
        System.out.println("3*1*5 + 3*5*8 + 1*3*8 + 1*8*1 = 15 + 120 + 24 + 8 = 167\n");

        int result1 = maxCoins(nums1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 167");
        System.out.println("  Status: " + (result1 == 167 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Two Balloons ===");
        int[] nums2 = {1, 5};
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("\nBurst 1 first: 1*1*5 = 5, then burst 5: 1*5*1 = 5 → total 10\n");

        int result2 = maxCoins(nums2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 10");
        System.out.println("  Status: " + (result2 == 10 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Balloon ===");
        int[] nums3 = {7};
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("\nOnly one balloon, neighbors are the virtual 1s → 1*7*1 = 7\n");

        int result3 = maxCoins(nums3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 7");
        System.out.println("  Status: " + (result3 == 7 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Contains a Zero ===");
        int[] nums4 = {0, 5};
        System.out.println("Input: " + Arrays.toString(nums4));
        System.out.println("\nBurst 0 first: 1*0*5 = 0, then burst 5: 1*5*1 = 5 → total 5\n");

        int result4 = maxCoins(nums4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result4 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: All Same Values ===");
        int[] nums5 = {2, 2, 2};
        System.out.println("Input: " + Arrays.toString(nums5));
        System.out.println("\nBurst middle first: 2*2*2 = 8, then 1*2*2 = 4, then 1*2*1 = 2");
        System.out.println("Total: 8 + 4 + 2 = 14\n");

        int result5 = maxCoins(nums5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 14");
        System.out.println("  Status: " + (result5 == 14 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Ascending Three ===");
        int[] nums6 = {1, 2, 3};
        System.out.println("Input: " + Arrays.toString(nums6));
        System.out.println("\nBurst 1, then 2, then 3: 1*1*2 + 1*2*3 + 1*3*1 = 2 + 6 + 3 = 11");
        System.out.println("Better: burst 2 first (1*2*3=6), then 1 (1*1*3=3), then 3 (1*3*1=3) = 12\n");

        int result6 = maxCoins(nums6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 12");
        System.out.println("  Status: " + (result6 == 12 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Four Equal Balloons ===");
        int[] nums7 = {1, 1, 1, 1};
        System.out.println("Input: " + Arrays.toString(nums7));
        System.out.println("\nEvery product is 1, four bursts → total 4\n");

        int result7 = maxCoins(nums7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result7 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Burst all balloons in the best order to maximize   ║");
        System.out.println("║           coins, where bursting i earns left*i*right         ║");
        System.out.println("║           using its current neighbors                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Think of the LAST Balloon Burst in a Range     ║");
        System.out.println("║    If balloon curr is the last one burst in [left..right],   ║");
        System.out.println("║    its neighbors are the fixed boundaries arr[left-1] and    ║");
        System.out.println("║    arr[right+1], and the two sides become independent.       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Setup:                                                      ║");
        System.out.println("║    arr = [1, nums..., 1] (virtual balloons of value 1)       ║");
        System.out.println("║    Real balloons are indexed 1..n                            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recurrence:                                                 ║");
        System.out.println("║    coin = arr[left-1]*arr[curr]*arr[right+1]                 ║");
        System.out.println("║           + f(left, curr-1) + f(curr+1, right)               ║");
        System.out.println("║    f(left, right) = max over all curr in [left, right]       ║");
        System.out.println("║    Base: left > right → 0                                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Three Approaches, Cross-Verified:                           ║");
        System.out.println("║    1) Recursion — tries every last-burst choice              ║");
        System.out.println("║    2) Memoization — top-down, caches dp[left][right]         ║");
        System.out.println("║    3) Tabulation — bottom-up, left from n down to 1,         ║");
        System.out.println("║       right from left up to n                                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  maxCoins() calls all three, prints each result, flags any   ║");
        System.out.println("║  disagreement, and returns the tabulation answer.            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: nums = [3, 1, 5, 8]                                ║");
        System.out.println("║    arr = [1, 3, 1, 5, 8, 1]                                  ║");
        System.out.println("║    Optimal order yields 167 coins                            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Same partition-DP pattern as Matrix Chain Multiplication║");
        System.out.println("║    • Answer lives at dp[1][n]                                ║");
        System.out.println("║    • Recursion is exponential, fine only for small inputs    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n³) — n² states, O(n) split loop each    ║");
        System.out.println("║  Space Complexity: O(n²) for the dp table                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
