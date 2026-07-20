package Dynamic_Programming;

import java.util.Arrays;

public class Best_Time_To_Buy_And_Sell_Stock_II {

    private static int optimized(int[] prices) {
        int total_profit = 0;

        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] < prices[i + 1]) {
                total_profit += (prices[i + 1] - prices[i]);
            }
        }
        return total_profit;
    }

    private static int recursive(int idx, int[] prices, boolean bought) {

        if (idx == prices.length) {
            return 0;
        }

        if (! bought) {
            int buy = recursive(idx + 1, prices, ! bought) - prices[idx];
            int notBuy = recursive(idx + 1, prices, bought);
            return Math.max(buy, notBuy);
        } else {
            int sell = recursive(idx + 1, prices, ! bought) + prices[idx];
            int notSell = recursive(idx + 1, prices, bought);
            return Math.max(sell, notSell);
        }
    }


    private static int memoization(int idx, int[] prices, int bought, int[][] dp) {

        if (idx == prices.length) {
            return 0;
        }

        if (dp[idx][bought] != - 1) {
            return dp[idx][bought];
        }

        int profit = 0;
        if (bought == 0) {
            int buy = memoization(idx + 1, prices, 1, dp) - prices[idx];
            int notBuy = memoization(idx + 1, prices, 0, dp);
            profit = Math.max(buy, notBuy);
        } else {
            int sell = memoization(idx + 1, prices, 0, dp) + prices[idx];
            int notSell = memoization(idx + 1, prices, 1, dp);
            profit = Math.max(sell, notSell);
        }

        return dp[idx][bought] = profit;

    }

    private static int tabulation(int[] prices) {

        int n = prices.length;
        int[][] dp = new int[n + 1][2];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                int profit = 0;
                if (bought == 0) {
                    int buy = dp[idx + 1][1] - prices[idx];
                    int notBuy = dp[idx + 1][0];
                    profit = Math.max(buy, notBuy);
                } else {
                    int sell = dp[idx + 1][0] + prices[idx];
                    int notSell = dp[idx + 1][1];
                    profit = Math.max(sell, notSell);
                }
                dp[idx][bought] = profit;
            }
        }

        return dp[0][0];

    }

    private static int spaceOptimized(int[] prices) {

        int n = prices.length;
        int[] dp = new int[2];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                int profit = 0;
                if (bought == 0) {
                    int buy = dp[1] - prices[idx];
                    int notBuy = dp[0];
                    profit = Math.max(buy, notBuy);
                } else {
                    int sell = dp[0] + prices[idx];
                    int notSell = dp[1];
                    profit = Math.max(sell, notSell);
                }
                dp[bought] = profit;
            }
        }

        return dp[0];

    }

    private static int maxProfit(int[] prices) {

        int[][] dp = new int[prices.length][2];
        for (int[] temp : dp) {
            Arrays.fill(temp, - 1);
        }

        int recursiveResult = recursive(0, prices, false);
        int memoizationResult = memoization(0, prices, 0, dp);
        int tabulationResult = tabulation(prices);
        int spaceOptimizedResult = spaceOptimized(prices);
        int optimizedResult = optimized(prices);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult
            + " | spaceOptimized: " + spaceOptimizedResult
            + " | optimized: " + optimizedResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult
            || tabulationResult != spaceOptimizedResult || spaceOptimizedResult != optimizedResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return optimizedResult;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        BEST TIME TO BUY AND SELL STOCK II                    ║");
        System.out.println("║  Maximize profit with unlimited buy/sell transactions,       ║");
        System.out.println("║  holding at most one share at a time                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Zigzag ===");
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        System.out.println("Input: " + Arrays.toString(prices1));
        System.out.println("\nCatch every upward swing:");
        System.out.println("  Buy at 1, sell at 5 → profit 4");
        System.out.println("  Buy at 3, sell at 6 → profit 3");
        System.out.println("Total profit: 7\n");

        int result1 = maxProfit(prices1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 7");
        System.out.println("  Status: " + (result1 == 7 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Strictly Increasing ===");
        int[] prices2 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(prices2));
        System.out.println("\nEvery consecutive day is an upward swing");
        System.out.println("Buy at 1, sell at 5 (or accumulate each day) → profit 4\n");

        int result2 = maxProfit(prices2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result2 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Decreasing ===");
        int[] prices3 = {7, 6, 4, 3, 1};
        System.out.println("Input: " + Arrays.toString(prices3));
        System.out.println("\nNo upward swings anywhere, prices only fall");
        System.out.println("Best strategy: never buy → profit 0\n");

        int result3 = maxProfit(prices3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Day ===");
        int[] prices4 = {5};
        System.out.println("Input: " + Arrays.toString(prices4));
        System.out.println("\nOnly one day, no possible transaction → profit 0\n");

        int result4 = maxProfit(prices4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result4 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: All Same Price ===");
        int[] prices5 = {3, 3, 3, 3};
        System.out.println("Input: " + Arrays.toString(prices5));
        System.out.println("\nNo price difference between any consecutive days");
        System.out.println("No upward swing to capture → profit 0\n");

        int result5 = maxProfit(prices5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Two Days, Profitable ===");
        int[] prices6 = {1, 5};
        System.out.println("Input: " + Arrays.toString(prices6));
        System.out.println("\nBuy at 1, sell at 5 → profit 4\n");

        int result6 = maxProfit(prices6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result6 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Multiple Zigzags ===");
        int[] prices7 = {3, 8, 1, 9, 2, 10};
        System.out.println("Input: " + Arrays.toString(prices7));
        System.out.println("\nCatch every upward swing:");
        System.out.println("  Buy at 3, sell at 8 → profit 5");
        System.out.println("  Buy at 1, sell at 9 → profit 8");
        System.out.println("  Buy at 2, sell at 10 → profit 8");
        System.out.println("Total profit: 21\n");

        int result7 = maxProfit(prices7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 21");
        System.out.println("  Status: " + (result7 == 21 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Maximize profit with unlimited transactions,       ║");
        System.out.println("║           but only one share can be held at a time           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Greedy Slope-Catching Beats DP Here            ║");
        System.out.println("║    Any profitable run can be split into single-day           ║");
        System.out.println("║    upswings and summed — buying and selling every day        ║");
        System.out.println("║    prices rise gives the same total as holding longer.       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Five Approaches, Cross-Verified:                            ║");
        System.out.println("║    1) Pure Recursion — explores buy/sell/skip at each day    ║");
        System.out.println("║    2) Memoization — top-down, caches (idx, bought) results   ║");
        System.out.println("║    3) Tabulation — bottom-up, full O(n×2) DP table           ║");
        System.out.println("║    4) Space-Optimized — bottom-up, rolling 2-element array   ║");
        System.out.println("║    5) Greedy Optimized — sum every positive day-to-day delta ║");
        System.out.println("║                                                              ║");
        System.out.println("║  maxProfit() now calls all five and prints each result,      ║");
        System.out.println("║  flagging any disagreement, before returning the final       ║");
        System.out.println("║  answer from the greedy optimized approach.                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: prices = [7, 1, 5, 3, 6, 4]                        ║");
        System.out.println("║    Deltas: -6, +4, -2, +3, -2 → sum positives → 4 + 3 = 7    ║");
        System.out.println("║    All five approaches agree → 7                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • State = (day index, currently holding a share or not)   ║");
        System.out.println("║    • Greedy works because profit is additive across upswings ║");
        System.out.println("║    • Cross-checking approaches catches subtle DP bugs        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n) for greedy; O(n) for DP variants too  ║");
        System.out.println("║                    (recursion adds exponential overhead)     ║");
        System.out.println("║  Space Complexity: O(1) greedy/space-optimized, O(n) tabular ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
    }

}