package Dynamic_Programming;

/*

    Description:
      Following program maximizes stock trading profit with at most k transactions,
        generalizing Stock III by replacing the fixed limit of 2 with a parameter k...

    Problem Statement:
      -> Given an array prices where prices[i] is the stock price on day i...
      -> Given an integer k representing the maximum number of transactions allowed...
      -> You must sell before buying again (hold at most one share at a time)...
      -> Transactions may not overlap...
      -> Return the maximum total profit achievable using at most k transactions...

    Key Insight:
      -> This is a direct generalization of Stock III (at most 2 transactions)...
      -> Add k as a third dimension to the DP state alongside (day, holding)...
      -> State: (idx, bought, k) where k = number of sells remaining...
      -> Each sell decrements k by 1...
      -> When k == 0, no more sells allowed → return 0 immediately...
      -> When idx == prices.length, no more days → return 0...

    Example:
      -> k=2, prices = [3, 3, 5, 0, 0, 3, 1, 4]:
           Transaction 1: buy at 0, sell at 3 → profit 3...
           Transaction 2: buy at 1, sell at 4 → profit 3...
           Total = 6...
      -> k=3, prices = [3, 8, 1, 9, 2, 10]:
           Transaction 1: buy at 3, sell at 8 → profit 5...
           Transaction 2: buy at 1, sell at 9 → profit 8...
           Transaction 3: buy at 2, sell at 10 → profit 8...
           Total = 21...
      -> k=1, prices = [7, 6, 4, 3, 1]:
           No upward swings → profit 0...

    Recursive Relation:
      -> Base: idx == prices.length OR k == 0 → return 0...
      -> If not holding (bought == 0):
           buy    = recursive(idx+1, prices, 1, k) - prices[idx]...
           notBuy = recursive(idx+1, prices, 0, k)...
           profit = Math.max(buy, notBuy)...
      -> If holding (bought == 1):
           sell    = recursive(idx+1, prices, 0, k-1) + prices[idx]...
           notSell = recursive(idx+1, prices, 1, k)...
           profit = Math.max(sell, notSell)...

    Difference From Stock III:
      -> Stock III: hardcoded time = 2, 3D dp[n][2][3]...
      -> Stock IV: parameterized k, 3D dp[n][2][k+1]...
      -> Stock III's greedy approach does not generalize cleanly to arbitrary k...
      -> Stock IV uses DP as the definitive solution...

    Approach 1 - Recursive:
      -> Explores all buy/sell/skip combinations at every (day, holding, k) state...
      -> Exponential branches due to overlapping subproblems...
      -> Time: O(2^n), Space: O(n) recursion stack...

    Approach 2 - Memoization (Top-Down DP):
      -> 3D dp array of size n × 2 × (k+1), initialized to -1...
      -> dp[idx][bought][k] caches max profit from that state forward...
      -> On cache hit: return stored result immediately...
      -> Time: O(n × k), Space: O(n × k) + O(n) recursion stack...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[idx][bought][time] = max profit from day idx with given (holding, sells remaining)...
      -> Fill right to left: idx from n-1 down to 0...
      -> Inner loops: bought (0 to 1), time (1 to k)...
      -> time=0 base case: dp[*][*][0] = 0 (no sells left)...
      -> Base: dp[n][*][*] = 0 (no days left)...
      -> Answer: dp[0][0][k]...
      -> Time: O(n × k), Space: O(n × k)...

    Approach 4 - Space Optimization (2×(k+1) Rolling Array):
      -> Replaces 3D dp table with a 2D array dp[2][k+1]...
      -> dp[bought][time] = max profit from current day with given (holding, sells remaining)...
      -> Fill right to left per day, overwriting in-place...
      -> Must iterate time from 1 to k (skip time=0 since base = 0)...
      -> Answer: dp[0][k] after processing all days...
      -> Time: O(n × k), Space: O(k)...

    DP Transition (Same for Tabulation and Space Opt):
      -> bought == 0 (can buy):
           buy    = dp[idx+1][1][time] - prices[idx]  (buy today)...
           notBuy = dp[idx+1][0][time]                (skip today)...
           dp[idx][0][time] = Math.max(buy, notBuy)...
      -> bought == 1 (can sell):
           sell    = dp[idx+1][0][time-1] + prices[idx]  (sell today)...
           notSell = dp[idx+1][1][time]                  (hold today)...
           dp[idx][1][time] = Math.max(sell, notSell)...

    DP Table Dimension Explanation:
      -> First dimension: n+1 (days including base case at day n)...
      -> Second dimension: 2 (holding state: 0=not holding, 1=holding)...
      -> Third dimension: k+1 (remaining sells: 0 to k)...
      -> Total states: (n+1) × 2 × (k+1)...

    Edge Cases:
      -> k = 0 → no transactions allowed → profit = 0...
      -> Single day → no sell after buy possible → profit = 0...
      -> k >= n/2 → equivalent to unlimited transactions (Stock II)...
      -> All same prices → no profitable trade → profit = 0...
      -> Strictly increasing → one transaction captures full gain regardless of k ≥ 1...

    Comparison of All Approaches:
      -> Recursive:       Time O(2^n),   Space O(n)    — exponential...
      -> Memoization:     Time O(n×k),   Space O(n×k)  — cached recursion...
      -> Tabulation:      Time O(n×k),   Space O(n×k)  — iterative DP...
      -> Space Optimized: Time O(n×k),   Space O(k)    — rolling 2×(k+1) array...

    Time and Space Complexity:
      -> Time:  O(n × k) for all DP approaches...
      -> Space: O(k) for space-optimized, O(n × k) for tabulation and memoization...

    Applications:
      -> Algorithmic trading with a fixed maximum transaction budget...
      -> Resource allocation problems with at most k investment intervals...
      -> Generalized foundation for all stock variants (I, II, III, IV, cooldown, fee)...
      -> Teaching progression: Stock I → II → III (k=2) → IV (arbitrary k)...

*/

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
