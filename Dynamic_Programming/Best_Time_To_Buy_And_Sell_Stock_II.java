package Dynamic_Programming;

/*

    Description:
      Following program maximizes stock trading profit with unlimited transactions,
        using five approaches from pure recursion to a greedy O(1) space solution...

    Problem Statement:
      -> Given an array prices where prices[i] is the stock price on day i...
      -> You may buy and sell the stock unlimited number of times...
      -> You must sell the stock before buying again (hold at most one share at a time)...
      -> Transactions on the same day are allowed (sell then buy immediately)...
      -> Return the maximum total profit achievable...

    Key Insight:
      -> Any profitable multi-day holding can be decomposed into consecutive single-day gains...
      -> Holding from day i to day k = sum of all positive daily deltas between i and k...
      -> Example: buy day 1 (price 1), sell day 3 (price 5) → profit 4...
                  = (prices[2]-prices[1]) + (prices[3]-prices[2]) = 2 + 2 = 4...
      -> Therefore: summing every positive consecutive day difference gives the same result...
      -> This reduces the problem to a single O(n) greedy pass...

    Example:
      -> prices = [7, 1, 5, 3, 6, 4]:
           Daily deltas: -6, +4, -2, +3, -2...
           Sum positive deltas: 4 + 3 = 7...
           Result: 7 (buy at 1 sell at 5, buy at 3 sell at 6)...
      -> prices = [1, 2, 3, 4, 5]:
           Deltas: +1, +1, +1, +1 → sum = 4...
           Same as buying at 1 and selling at 5...
      -> prices = [7, 6, 4, 3, 1]:
           All deltas negative → no profitable day → sum = 0...

    Greedy Approach (Optimized):
      -> For each consecutive pair (prices[i], prices[i+1]):
           If prices[i] < prices[i+1]: add the difference to total_profit...
           Otherwise: skip (no gain from this pair)...
      -> Single pass, no extra space, captures all profitable upswings...
      -> Time: O(n), Space: O(1)...

    Recursive Approach:
      -> State: (idx, bought) where bought is a boolean...
      -> At each day, two cases:
           Not holding (bought=false): choose to buy or skip...
             buy    = recursive(idx+1, true)  - prices[idx]...
             notBuy = recursive(idx+1, false)...
             Return max(buy, notBuy)...
           Holding (bought=true): choose to sell or hold...
             sell    = recursive(idx+1, false) + prices[idx]...
             notSell = recursive(idx+1, true)...
             Return max(sell, notSell)...
      -> Base: idx == prices.length → return 0...
      -> Time: O(2^n), Space: O(n) recursion stack...

    Memoization (Top-Down DP):
      -> Converts boolean bought to int (0 or 1) for array indexing...
      -> 2D dp array of size n × 2, initialized to -1...
      -> dp[idx][bought] stores maximum profit from day idx with given holding state...
      -> Same logic as recursion but cached...
      -> Time: O(n), Space: O(n)...

    Tabulation (Bottom-Up DP):
      -> dp[idx][bought] = max profit starting from day idx with given state...
      -> Fill from right to left (idx = n-1 down to 0)...
      -> Base: dp[n][0] = dp[n][1] = 0 (no days left → no profit)...
      -> Transition same as recursion but iterative...
      -> Time: O(n), Space: O(n)...

    Space Optimization (Single 2-Element Array):
      -> Replaces full dp table with a 2-element array dp[2]...
      -> dp[0] = max profit when not holding, dp[1] = max profit when holding...
      -> Fill right to left, updating both states per iteration...
      -> Note: Both states updated per iteration without interference...
      -> Time: O(n), Space: O(1)...

    DP State Transition Table:

         State     | Buy               | Sell              | Skip
         ----------|-------------------|-------------------|------------------
         bought=0  | dp[idx+1][1]-price| N/A               | dp[idx+1][0]...
         bought=1  | N/A               | dp[idx+1][0]+price| dp[idx+1][1]...

    Why Greedy Equals DP Here:
      -> Profit is additive: no penalty for number of transactions...
      -> Holding longer vs trading daily yields identical results...
      -> All five approaches converge to the same answer for this problem...
      -> This equivalence does NOT hold when transaction count is limited...

    Edge Cases:
      -> Single day → no transaction possible → return 0...
      -> All same prices → no positive delta → return 0...
      -> Strictly increasing → all deltas positive → capture every day...
      -> Strictly decreasing → all deltas negative → return 0...
      -> Two days, increasing → one transaction → return price[1] - price[0]...

    Comparison of All Approaches:
      -> Recursive:       Time O(2^n), Space O(n)  — exponential...
      -> Memoization:     Time O(n),   Space O(n)  — cached recursion...
      -> Tabulation:      Time O(n),   Space O(n)  — iterative DP...
      -> Space Optimized: Time O(n),   Space O(1)  — rolling 2-element array...
      -> Greedy:          Time O(n),   Space O(1)  — single pass, no DP needed...

    Time and Space Complexity:
      -> Time:  O(n) for all approaches except recursive (O(2^n))...
      -> Space: O(1) for greedy and space-optimized, O(n) for others...

    Applications:
      -> Algorithmic trading strategy simulation with no transaction limits...
      -> Resource allocation problems where all gains can be captured greedily...
      -> Teaching example of greedy vs DP equivalence under relaxed constraints...
      -> Foundation for harder variants: limited transactions, cooldown, fees...

*/

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
