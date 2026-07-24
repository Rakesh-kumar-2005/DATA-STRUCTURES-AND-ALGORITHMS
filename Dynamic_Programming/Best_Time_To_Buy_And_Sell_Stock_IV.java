package Dynamic_Programming;

import java.util.Arrays;

public class Best_Time_To_Buy_And_Sell_Stock_IV {

    private static int recursive(int idx, int[] prices, int bought, int k) {

        if (idx == prices.length || k == 0) {
            return 0;
        }

        int profit = Integer.MIN_VALUE;
        if (bought == 0) {
            int buy = recursive(idx + 1, prices, 1, k) - prices[idx];
            int notBuy = recursive(idx + 1, prices, 0, k);
            profit = Math.max(buy, notBuy);
        } else {
            int sell = recursive(idx + 1, prices, 0, k - 1) + prices[idx];
            int notSell = recursive(idx + 1, prices, 1, k);
            profit = Math.max(sell, notSell);
        }

        return profit;

    }

    private static int memoization(int idx, int[] prices, int bought, int k, int[][][] dp) {

        if (idx == prices.length || k == 0) {
            return 0;
        }

        if (dp[idx][bought][k] != - 1) {
            return dp[idx][bought][k];
        }

        int profit = Integer.MIN_VALUE;
        if (bought == 0) {
            int buy = memoization(idx + 1, prices, 1, k, dp) - prices[idx];
            int notBuy = memoization(idx + 1, prices, 0, k, dp);
            profit = Math.max(buy, notBuy);
        } else {
            int sell = memoization(idx + 1, prices, 0, k - 1, dp) + prices[idx];
            int notSell = memoization(idx + 1, prices, 1, k, dp);
            profit = Math.max(sell, notSell);
        }

        return dp[idx][bought][k] = profit;

    }

    private static int tabulation(int k, int[] prices) {

        int n = prices.length;
        int[][][] dp = new int[n + 1][2][k + 1];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                for (int time = 1; time <= k; time++) {
                    int profit = Integer.MIN_VALUE;
                    if (bought == 0) {
                        int buy = dp[idx + 1][1][time] - prices[idx];
                        int notBuy = dp[idx + 1][0][time];
                        profit = Math.max(buy, notBuy);
                    } else {
                        int sell = dp[idx + 1][0][time - 1] + prices[idx];
                        int notSell = dp[idx + 1][1][time];
                        profit = Math.max(sell, notSell);
                    }
                    dp[idx][bought][time] = profit;
                }
            }
        }

        return dp[0][0][k];
    }

    private static int spaceOptimized(int k, int[] prices) {

        int n = prices.length;
        int[][] dp = new int[2][k + 1];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                for (int time = 1; time <= k; time++) {
                    int profit = Integer.MIN_VALUE;
                    if (bought == 0) {
                        int buy = dp[1][time] - prices[idx];
                        int notBuy = dp[0][time];
                        profit = Math.max(buy, notBuy);
                    } else {
                        int sell = dp[0][time - 1] + prices[idx];
                        int notSell = dp[1][time];
                        profit = Math.max(sell, notSell);
                    }
                    dp[bought][time] = profit;
                }
            }
        }

        return dp[0][k];
    }

    private static int maxProfit(int k, int[] prices) {

        int n = prices.length;
        int[][][] dp = new int[n + 1][2][k + 1];

        for (int[][] a : dp) {
            for (int[] b : a) {
                Arrays.fill(b, - 1);
            }
        }

        int recursiveResult = recursive(0, prices, 0, k);
        int memoizationResult = memoization(0, prices, 0, k, dp);
        int tabulationResult = tabulation(k, prices);
        int spaceOptimizedResult = spaceOptimized(k, prices);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult
            + " | spaceOptimized: " + spaceOptimizedResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult
            || tabulationResult != spaceOptimizedResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return spaceOptimizedResult;

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        BEST TIME TO BUY AND SELL STOCK IV                    ║");
        System.out.println("║  Maximize profit with at most K transactions,                ║");
        System.out.println("║  holding at most one share at a time                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: k = 2, Two-Peak Case ===");
        int k1 = 2;
        int[] prices1 = {3, 3, 5, 0, 0, 3, 1, 4};
        System.out.println("k = " + k1);
        System.out.println("Input: " + Arrays.toString(prices1));
        System.out.println("\nTwo best transactions:");
        System.out.println("  Buy at 0, sell at 3 → profit 3");
        System.out.println("  Buy at 1, sell at 4 → profit 3");
        System.out.println("Total profit: 6\n");

        int result1 = maxProfit(k1, prices1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result1 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: k = 2, Strictly Increasing ===");
        int k2 = 2;
        int[] prices2 = {1, 2, 3, 4, 5};
        System.out.println("k = " + k2);
        System.out.println("Input: " + Arrays.toString(prices2));
        System.out.println("\nOnly one profitable run exists, second transaction adds nothing");
        System.out.println("Buy at 1, sell at 5 → profit 4\n");

        int result2 = maxProfit(k2, prices2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result2 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: k = 1, Strictly Decreasing ===");
        int k3 = 1;
        int[] prices3 = {7, 6, 4, 3, 1};
        System.out.println("k = " + k3);
        System.out.println("Input: " + Arrays.toString(prices3));
        System.out.println("\nNo upward swings anywhere, prices only fall");
        System.out.println("Best strategy: never buy → profit 0\n");

        int result3 = maxProfit(k3, prices3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: k = 2, Single Day ===");
        int k4 = 2;
        int[] prices4 = {5};
        System.out.println("k = " + k4);
        System.out.println("Input: " + Arrays.toString(prices4));
        System.out.println("\nOnly one day, no possible transaction → profit 0\n");

        int result4 = maxProfit(k4, prices4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result4 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: k = 2, All Same Price ===");
        int k5 = 2;
        int[] prices5 = {2, 2, 2, 2};
        System.out.println("k = " + k5);
        System.out.println("Input: " + Arrays.toString(prices5));
        System.out.println("\nNo price difference anywhere, no profit possible\n");

        int result5 = maxProfit(k5, prices5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: k = 2, Multiple Profitable Swings ===");
        int k6 = 2;
        int[] prices6 = {1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
        System.out.println("k = " + k6);
        System.out.println("Input: " + Arrays.toString(prices6));
        System.out.println("\nBest two transactions:");
        System.out.println("  Buy at 1, sell at 7 → profit 6");
        System.out.println("  Buy at 2, sell at 9 → profit 7");
        System.out.println("Total profit: 13\n");

        int result6 = maxProfit(k6, prices6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 13");
        System.out.println("  Status: " + (result6 == 13 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: k = 3, Zigzag ===");
        int k7 = 3;
        int[] prices7 = {3, 8, 1, 9, 2, 10};
        System.out.println("k = " + k7);
        System.out.println("Input: " + Arrays.toString(prices7));
        System.out.println("\nThree best transactions:");
        System.out.println("  Buy at 3, sell at 8 → profit 5");
        System.out.println("  Buy at 1, sell at 9 → profit 8");
        System.out.println("  Buy at 2, sell at 10 → profit 8");
        System.out.println("Total profit: 21\n");

        int result7 = maxProfit(k7, prices7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 21");
        System.out.println("  Status: " + (result7 == 21 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Maximize profit with AT MOST k transactions,       ║");
        System.out.println("║           only one share held at a time                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Generalizes Stock III With a k Dimension       ║");
        System.out.println("║    State = (day, holding, transactions remaining)            ║");
        System.out.println("║    Each completed sell consumes one unit of k                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Four Approaches, Cross-Verified:                            ║");
        System.out.println("║    1) Pure Recursion — explores buy/sell/skip with k left    ║");
        System.out.println("║    2) Memoization — top-down, caches (idx,bought,k)          ║");
        System.out.println("║    3) Tabulation — bottom-up, full O(n×2×k) DP table         ║");
        System.out.println("║    4) Space-Optimized — bottom-up, rolling 2×k array         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  maxProfit() now calls all four and prints each result,      ║");
        System.out.println("║  flagging any disagreement, before returning the final       ║");
        System.out.println("║  answer from the space-optimized approach.                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • At most k transactions total, no overlap allowed        ║");
        System.out.println("║    • Cross-checking approaches catches subtle DP bugs        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n × k) for DP variants                   ║");
        System.out.println("║                    (recursion adds exponential overhead)     ║");
        System.out.println("║  Space Complexity: O(k) space-optimized, O(n × k) tabular    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}