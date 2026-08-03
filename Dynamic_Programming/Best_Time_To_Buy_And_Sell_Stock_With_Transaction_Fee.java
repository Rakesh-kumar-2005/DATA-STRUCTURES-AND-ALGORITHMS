package Dynamic_Programming;

import java.util.Arrays;

public class Best_Time_To_Buy_And_Sell_Stock_With_Transaction_Fee {

    private static int recursive(int idx, int[] prices, int bought, int fee) {

        if (idx >= prices.length) {
            return 0;
        }

        int profit = 0;
        if (bought == 0) {
            int buy = recursive(idx + 1, prices, 1, fee) - prices[idx] - fee;
            int notBuy = recursive(idx + 1, prices, 0, fee);
            profit = Math.max(buy, notBuy);
        } else {
            int sell = recursive(idx + 1, prices, 0, fee) + prices[idx];
            int notSell = recursive(idx + 1, prices, 1, fee);
            profit = Math.max(sell, notSell);
        }

        return profit;
    }

    private static int memoization(int idx, int[] prices, int bought, int[][] dp, int fee) {

        if (idx >= prices.length) {
            return 0;
        }

        if (dp[idx][bought] != - 1) {
            return dp[idx][bought];
        }

        int profit = 0;
        if (bought == 0) {
            int buy = memoization(idx + 1, prices, 1, dp, fee) - prices[idx] - fee;
            int notBuy = memoization(idx + 1, prices, 0, dp, fee);
            profit = Math.max(buy, notBuy);
        } else {
            int sell = memoization(idx + 1, prices, 0, dp, fee) + prices[idx];
            int notSell = memoization(idx + 1, prices, 1, dp, fee);
            profit = Math.max(sell, notSell);
        }

        return dp[idx][bought] = profit;
    }

    private static int tabulation(int[] prices, int fee) {

        int n = prices.length;
        int[][] dp = new int[n + 1][2];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {

                int profit = 0;
                if (bought == 0) {
                    int buy = dp[idx + 1][1] - prices[idx] - fee;
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

    private static int ultimateSpaceOptimization(int[] prices, int fee) {

        int n = prices.length;
        int[] dp = new int[2];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                int profit = 0;
                if (bought == 0) {
                    int buy = dp[1] - prices[idx] - fee;
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

    private static int maxProfit(int[] prices, int fee) {

        int n = prices.length;
        int[][] dp = new int[n][2];

        for (int[] a : dp) {
            Arrays.fill(a, - 1);
        }

        int recursiveResult = recursive(0, prices, 0, fee);
        int memoizationResult = memoization(0, prices, 0, dp, fee);
        int tabulationResult = tabulation(prices, fee);
        int ultimateSpaceOptimizationResult = ultimateSpaceOptimization(prices, fee);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult
            + " | ultimateSpaceOptimization: " + ultimateSpaceOptimizationResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult
            || tabulationResult != ultimateSpaceOptimizationResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return ultimateSpaceOptimizationResult;

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  BEST TIME TO BUY AND SELL STOCK WITH TRANSACTION FEE        ║");
        System.out.println("║  Maximize profit with unlimited transactions, each sell      ║");
        System.out.println("║  costing a fixed transaction fee                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Fee Case ===");
        int[] prices1 = {1, 3, 2, 8, 4, 9};
        int fee1 = 2;
        System.out.println("Input: " + Arrays.toString(prices1) + ", fee=" + fee1);
        System.out.println("\nBuy at 1, sell at 8 → profit (8-1-2)=5");
        System.out.println("Buy at 4, sell at 9 → profit (9-4-2)=3");
        System.out.println("Total profit: 8\n");

        int result1 = maxProfit(prices1, fee1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 8");
        System.out.println("  Status: " + (result1 == 8 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Single Transaction ===");
        int[] prices2 = {1, 3, 7, 5, 10, 3};
        int fee2 = 3;
        System.out.println("Input: " + Arrays.toString(prices2) + ", fee=" + fee2);
        System.out.println("\nBuy at 1, sell at 10 → profit (10-1-3)=6\n");

        int result2 = maxProfit(prices2, fee2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result2 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Decreasing ===");
        int[] prices3 = {7, 6, 4, 3, 1};
        int fee3 = 1;
        System.out.println("Input: " + Arrays.toString(prices3) + ", fee=" + fee3);
        System.out.println("\nNo upward swings, best strategy: never buy → profit 0\n");

        int result3 = maxProfit(prices3, fee3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Fee Exceeds Any Gain ===");
        int[] prices4 = {1, 2};
        int fee4 = 5;
        System.out.println("Input: " + Arrays.toString(prices4) + ", fee=" + fee4);
        System.out.println("\nOnly gain is 1, fee of 5 makes any trade unprofitable → profit 0\n");

        int result4 = maxProfit(prices4, fee4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result4 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Day ===");
        int[] prices5 = {5};
        int fee5 = 1;
        System.out.println("Input: " + Arrays.toString(prices5) + ", fee=" + fee5);
        System.out.println("\nOnly one day, no possible transaction → profit 0\n");

        int result5 = maxProfit(prices5, fee5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Zero Fee (Reduces to Stock II) ===");
        int[] prices6 = {7, 1, 5, 3, 6, 4};
        int fee6 = 0;
        System.out.println("Input: " + Arrays.toString(prices6) + ", fee=" + fee6);
        System.out.println("\nWith no fee, every profitable swing is captured");
        System.out.println("Buy at 1, sell at 5 → 4; Buy at 3, sell at 6 → 3");
        System.out.println("Total profit: 7\n");

        int result6 = maxProfit(prices6, fee6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 7");
        System.out.println("  Status: " + (result6 == 7 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: All Same Price ===");
        int[] prices7 = {4, 4, 4, 4};
        int fee7 = 1;
        System.out.println("Input: " + Arrays.toString(prices7) + ", fee=" + fee7);
        System.out.println("\nNo price difference anywhere, no profit possible\n");

        int result7 = maxProfit(prices7, fee7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result7 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Maximize profit with unlimited transactions,       ║");
        System.out.println("║           but each completed sell incurs a fixed fee         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Deduct Fee at the Buy Step                     ║");
        System.out.println("║    Subtracting the fee when buying (rather than selling)     ║");
        System.out.println("║    simplifies the recurrence — the fee only needs to be      ║");
        System.out.println("║    paid once per completed round trip.                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  State = (day index, currently holding a share or not)       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: prices = [1,3,2,8,4,9], fee = 2                    ║");
        System.out.println("║    Buy 1 → -1-2=-3, sell 8 → +8, profit so far = 5           ║");
        System.out.println("║    Buy 4 → -4-2=-6, sell 9 → +9, profit added = 3            ║");
        System.out.println("║    Total: 8                                                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n) for the active space-optimized DP     ║");
        System.out.println("║  Space Complexity: O(1) for the active space-optimized DP    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}