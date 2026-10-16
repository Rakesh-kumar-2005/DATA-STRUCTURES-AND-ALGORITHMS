package Dynamic_Programming;

import java.util.Arrays;

public class Minimum_Cost_To_Cut_A_Stick {

    private static int memoization(int left, int right, int[] arr, int[][] dp) {
        if (left > right) {
            return 0;
        }

        if (dp[left][right] != - 1) {
            return dp[left][right];
        }

        int minCost = Integer.MAX_VALUE;
        for (int cut = left; cut <= right; cut++) {
            int cost = (arr[right + 1] - arr[left - 1])
                + memoization(left, cut - 1, arr, dp)
                + memoization(cut + 1, right, arr, dp);

            minCost = Math.min(minCost, cost);
        }

        return dp[left][right] = minCost;
    }

    private static int tabulation(int c, int[] arr) {

        int[][] dp = new int[c + 2][c + 2];

        for (int left = c; left >= 1; left--) {
            for (int right = left; right <= c; right++) {
                int minCost = Integer.MAX_VALUE;
                for (int cut = left; cut <= right; cut++) {
                    int cost = (arr[right + 1] - arr[left - 1]) + dp[left][cut - 1] + dp[cut + 1][right];
                    minCost = Math.min(minCost, cost);
                }

                dp[left][right] = minCost;
            }
        }

        return dp[1][c];
    }

    private static int minCost(int n, int[] cuts) {

        int m = cuts.length;
        int[] arr = new int[m + 2];

        arr[0] = 0;
        arr[m + 1] = n;

        for (int left = 0; left < m; left++) {
            arr[left + 1] = cuts[left];
        }

        Arrays.sort(arr);

        int[][] dp = new int[m + 2][m + 2];
        for (int[] temp : dp) {
            Arrays.fill(temp, - 1);
        }

        int memoizationResult = memoization(1, m, arr, dp);
        int tabulationResult = tabulation(m, arr);

        System.out.println("  [Verification] memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult);

        if (memoizationResult != tabulationResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return tabulationResult;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              MINIMUM COST TO CUT A STICK                     ║");
        System.out.println("║  Find the cheapest order to make all cuts, where each cut    ║");
        System.out.println("║  costs the length of the stick piece being cut               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int n1 = 7;
        int[] cuts1 = {1, 3, 4, 5};
        System.out.println("Stick length: " + n1 + ", cuts: " + Arrays.toString(cuts1));
        System.out.println("\nBest order: 3, 5, 1, 4 → costs 7 + 4 + 3 + 2 = 16\n");

        int result1 = minCost(n1, cuts1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 16");
        System.out.println("  Status: " + (result1 == 16 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Unsorted Cuts ===");
        int n2 = 9;
        int[] cuts2 = {5, 6, 1, 4, 2};
        System.out.println("Stick length: " + n2 + ", cuts: " + Arrays.toString(cuts2));
        System.out.println("\nCuts get sorted internally, minimum total cost is 22\n");

        int result2 = minCost(n2, cuts2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 22");
        System.out.println("  Status: " + (result2 == 22 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Cut ===");
        int n3 = 10;
        int[] cuts3 = {5};
        System.out.println("Stick length: " + n3 + ", cuts: " + Arrays.toString(cuts3));
        System.out.println("\nOnly one cut, cost equals full stick length = 10\n");

        int result3 = minCost(n3, cuts3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 10");
        System.out.println("  Status: " + (result3 == 10 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Three Cuts ===");
        int n4 = 10;
        int[] cuts4 = {2, 4, 7};
        System.out.println("Stick length: " + n4 + ", cuts: " + Arrays.toString(cuts4));
        System.out.println("\nCut at 4 first: 10, then [0,4] cut at 2: 4, [4,10] cut at 7: 6");
        System.out.println("Total: 10 + 4 + 6 = 20\n");

        int result4 = minCost(n4, cuts4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 20");
        System.out.println("  Status: " + (result4 == 20 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Tiny Stick ===");
        int n5 = 2;
        int[] cuts5 = {1};
        System.out.println("Stick length: " + n5 + ", cuts: " + Arrays.toString(cuts5));
        System.out.println("\nOne cut on a stick of length 2 → cost 2\n");

        int result5 = minCost(n5, cuts5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result5 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Two Symmetric Cuts ===");
        int n6 = 8;
        int[] cuts6 = {2, 6};
        System.out.println("Stick length: " + n6 + ", cuts: " + Arrays.toString(cuts6));
        System.out.println("\nCut at 2 first: 8, then [2,8] cut at 6: 6 → total 14");
        System.out.println("Cut at 6 first gives the same total 14\n");

        int result6 = minCost(n6, cuts6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 14");
        System.out.println("  Status: " + (result6 == 14 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Evenly Spaced Cuts ===");
        int n7 = 20;
        int[] cuts7 = {5, 10, 15};
        System.out.println("Stick length: " + n7 + ", cuts: " + Arrays.toString(cuts7));
        System.out.println("\nCut at middle (10) first: 20, then two halves of 10 each need one cut");
        System.out.println("Total: 20 + 10 + 10 = 40\n");

        int result7 = minCost(n7, cuts7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 40");
        System.out.println("  Status: " + (result7 == 40 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Cut a stick at given positions in any order,       ║");
        System.out.println("║           where each cut costs the current piece's length    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Think of the LAST Cut in Each Segment          ║");
        System.out.println("║    If cut k is the last one made in segment [left..right],   ║");
        System.out.println("║    the two sides become independent subproblems. The cost    ║");
        System.out.println("║    of cutting that segment is arr[right+1] - arr[left-1].    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Setup:                                                      ║");
        System.out.println("║    arr = [0, sorted cuts..., n]                              ║");
        System.out.println("║    Cuts are indexed 1..m, with 0 and n as the boundaries     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recurrence:                                                 ║");
        System.out.println("║    cost = (arr[right+1] - arr[left-1])                       ║");
        System.out.println("║           + f(left, cut-1) + f(cut+1, right)                 ║");
        System.out.println("║    f(left, right) = min over all cut in [left, right]        ║");
        System.out.println("║    Base: left > right → 0                                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Approaches, Cross-Verified:                             ║");
        System.out.println("║    1) Memoization — top-down, caches dp[left][right]         ║");
        System.out.println("║    2) Tabulation — bottom-up, left from c down to 1,         ║");
        System.out.println("║       right from left up to c                                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  minCost() calls both, prints each result, flags any         ║");
        System.out.println("║  disagreement, and returns the tabulation answer.            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: n = 7, cuts = [1, 3, 4, 5]                         ║");
        System.out.println("║    arr = [0, 1, 3, 4, 5, 7]                                  ║");
        System.out.println("║    Optimal order yields 7 + 4 + 3 + 2 = 16                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Sorting cuts is essential so segments are contiguous    ║");
        System.out.println("║    • Same partition-DP pattern as Matrix Chain Multiplication║");
        System.out.println("║    • Answer lives at dp[1][m]                                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(m³) — m² states, O(m) cut loop each      ║");
        System.out.println("║  Space Complexity: O(m²) for the dp table                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}