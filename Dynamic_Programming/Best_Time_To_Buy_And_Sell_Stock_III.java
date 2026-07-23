package Dynamic_Programming;

/*

    Description:
      Following program maximizes stock trading profit with at most two transactions,
        using five approaches from pure recursion to a greedy O(1) space solution...

    Problem Statement:
      -> Given an array prices where prices[i] is the stock price on day i...
      -> You may complete at most two transactions total...
      -> You must sell before buying again (hold at most one share at a time)...
      -> Transactions may not overlap...
      -> Return the maximum total profit achievable across both transactions...

    Key Insight:
      -> Add a transaction counter (time) to the state alongside the holding flag...
      -> time starts at 2 (two sells remaining) and decrements by 1 on each sell...
      -> When time == 0, no more sells allowed → return 0...
      -> Buying does not consume a transaction, only selling does...
      -> DP state: (day index, currently holding, sells remaining)...

    Example:
      -> prices = [3, 3, 5, 0, 0, 3, 1, 4]:
           Transaction 1: buy at 0, sell at 3 → profit 3...
           Transaction 2: buy at 1, sell at 4 → profit 3...
           Total = 6...
      -> prices = [1, 2, 3, 4, 5]:
           Only one profitable run → one transaction suffices → profit 4...
           Second transaction adds nothing → total = 4...
      -> prices = [7, 6, 4, 3, 1]:
           No upward swings → no transaction profitable → profit 0...

    Recursive Relation:
      -> Base: idx == prices.length OR time == 0 → return 0...
      -> If not holding (bought == 0):
           buy    = recursive(idx+1, prices, 1, time) - prices[idx]...
           notBuy = recursive(idx+1, prices, 0, time)...
           profit = Math.max(buy, notBuy)...
      -> If holding (bought == 1):
           sell    = recursive(idx+1, prices, 0, time-1) + prices[idx]...
           notSell = recursive(idx+1, prices, 1, time)...
           profit = Math.max(sell, notSell)...

    Why time Decrements Only on Sell:
      -> A transaction is counted as complete upon selling, not buying...
      -> Decrementing on buy would prevent using the same buy in multiple sell-price comparisons...
      -> time=2 allows up to 2 sells, enforcing the "at most 2 transactions" constraint...

    Approach 1 - Recursive:
      -> State: (idx, bought, time) all 0-based or boolean...
      -> Explores all buy/sell/skip paths at every day...
      -> Exponential due to overlapping subproblems and branching at every step...
      -> Time: O(2^n), Space: O(n) recursion stack...

    Approach 2 - Memoization (Top-Down DP):
      -> 3D dp array of size n × 2 × 3, initialized to -1...
      -> dp[idx][bought][time] stores max profit from that state forward...
      -> Check cache before recursing, store result after computing...
      -> Time: O(n×2×3) = O(n), Space: O(n)...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[idx][bought][time] = max profit from day idx with given state...
      -> Fill right to left: idx from n-1 down to 0...
      -> time iterates 1 to 2 (skip time=0 since it always returns 0)...
      -> Base: dp[n][*][*] = 0 (no days left)...
      -> Transition same as recursion but iterative...
      -> Answer: dp[0][0][2]...
      -> Time: O(n), Space: O(n×2×3) = O(n)...

    Approach 4 - Space Optimization (2×3 Rolling Array):
      -> Replaces 3D dp table with a 2D array dp[2][3]...
      -> dp[bought][time] = max profit from current day with given state...
      -> Fill right to left per day, overwriting in-place...
      -> Answer: dp[0][2]...
      -> Time: O(n), Space: O(1) effectively (fixed 6-cell array)...

    Approach 5 - Greedy (Two Independent Buy/Profit Trackers):
      -> buy1 = cheapest price seen so far for first transaction...
      -> profit1 = best profit achievable from one transaction up to today...
      -> buy2 = effective cost of second buy = price - profit1...
                (accounts for first transaction's profit offsetting second buy)...
      -> profit2 = best profit from two transactions up to today...
      -> For each price:
           buy1 = Math.min(buy1, price)...
           profit1 = Math.max(profit1, price - buy1)...
           buy2 = Math.min(buy2, price - profit1)...
           profit2 = Math.max(profit2, price - buy2)...
      -> Return profit2...

    Greedy Intuition for buy2:
      -> buy2 = price - profit1 represents the net cost of the second buy...
      -> If profit1 is high, the effective cost of the second buy is lower...
      -> This interleaves the two transactions without explicit separation...
      -> profit2 automatically becomes the combined maximum of both transactions...

    DP Table Dimensions Explained:
      -> n+1 days (including base case day n)...
      -> 2 holding states: 0 (not holding), 1 (holding)...
      -> 3 time values: 0, 1, 2 (remaining sells)...
      -> Total states: (n+1) × 2 × 3...

    Edge Cases:
      -> Single day → no transaction possible → profit 0...
      -> All same prices → no profitable transaction → profit 0...
      -> Strictly increasing → one transaction captures all profit → profit = last - first...
      -> Strictly decreasing → no profitable day → profit 0...
      -> Two distinct profitable runs → both transactions contribute → sum of both profits...

    Comparison of All Approaches:
      -> Recursive:       Time O(2^n), Space O(n)  — exponential...
      -> Memoization:     Time O(n),   Space O(n)  — cached recursion...
      -> Tabulation:      Time O(n),   Space O(n)  — iterative DP...
      -> Space Optimized: Time O(n),   Space O(1)  — rolling 2×3 array...
      -> Greedy:          Time O(n),   Space O(1)  — four variables only...

    Time and Space Complexity:
      -> Time:  O(n) for all approaches except recursive (O(2^n))...
      -> Space: O(1) for greedy and space-optimized, O(n) for others...

    Applications:
      -> Two-trade stock strategy optimization in algorithmic trading...
      -> Resource scheduling with at most two investment windows...
      -> Foundation for Stock IV (at most k transactions)...
      -> Teaching example of extending DP state space with constraint dimensions...

*/

import java.util.Arrays;

public class Best_Time_To_Buy_And_Sell_Stock_III {

    private static int recursive(int idx, int[] prices, int bought, int time) {
        
        if (idx == prices.length || time == 0) {
            return 0;
        }

        int profit = Integer.MIN_VALUE;
        if (bought == 0) {
            int buy = recursive(idx + 1, prices, 1, time) - prices[idx];
            int notBuy = recursive(idx + 1, prices, 0, time);

            profit = Math.max(buy, notBuy);
        } else {
            int sell = recursive(idx + 1, prices, 0, time - 1) + prices[idx];
            int notSell = recursive(idx + 1, prices, 1, time);

            profit = Math.max(sell, notSell);
        }

        return profit;
    }

    private static int memoization(int idx, int[] prices, int bought, int time, int[][][] dp) {
        
        if (idx == prices.length || time == 0) {
            return 0;
        }

        if (dp[idx][bought][time] != - 1) {
            return dp[idx][bought][time];
        }

        int profit = Integer.MIN_VALUE;
        if (bought == 0) {
            int buy = memoization(idx + 1, prices, 1, time, dp) - prices[idx];
            int notBuy = memoization(idx + 1, prices, 0, time, dp);
            profit = Math.max(buy, notBuy);
        } else {
            int sell = memoization(idx + 1, prices, 0, time - 1, dp) + prices[idx];
            int notSell = memoization(idx + 1, prices, 1, time, dp);
            profit = Math.max(sell, notSell);
        }

        return dp[idx][bought][time] = profit;
    }

    private static int tabulation(int[] prices) {

        int n = prices.length;
        int[][][] dp = new int[n + 1][2][3];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                for (int time = 1; time <= 2; time++) {

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

        return dp[0][0][2];
    }

    private static int ultimateSpaceOptimization(int[] prices) {

        int n = prices.length;
        int[][] dp = new int[2][3];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                for (int time = 1; time <= 2; time++) {

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

        return dp[0][2];
    }

    private static int greedy(int[] prices) {

        int buy1 = Integer.MAX_VALUE, buy2 = Integer.MAX_VALUE;
        int profit1 = 0, profit2 = 0;

        for (int price : prices) {
            buy1 = Math.min(buy1, price);
            profit1 = Math.max(profit1, price - buy1);

            buy2 = Math.min(buy2, price - profit1);
            profit2 = Math.max(profit2, price - buy2);
        }

        return profit2;
    }

    private static int maxProfit(int[] prices) {

        int[][][] dp = new int[prices.length][2][3];
        for (int[][] temp : dp) {
            for (int[] t : temp) {
                Arrays.fill(t, - 1);
            }
        }

        int recursiveResult = recursive(0, prices, 0, 2);
        int memoizationResult = memoization(0, prices, 0, 2, dp);
        int tabulationResult = tabulation(prices);
        int ultimateSpaceOptimizationResult = ultimateSpaceOptimization(prices);
        int greedyResult = greedy(prices);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult
            + " | ultimateSpaceOptimization: " + ultimateSpaceOptimizationResult
            + " | greedy: " + greedyResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult
            || tabulationResult != ultimateSpaceOptimizationResult || ultimateSpaceOptimizationResult != greedyResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return greedyResult;

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║       BEST TIME TO BUY AND SELL STOCK III                    ║");
        System.out.println("║  Maximize profit with at most TWO transactions,              ║");
        System.out.println("║  holding at most one share at a time                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Two-Peak Case ===");
        int[] prices1 = {3, 3, 5, 0, 0, 3, 1, 4};
        System.out.println("Input: " + Arrays.toString(prices1));
        System.out.println("\nTwo best transactions:");
        System.out.println("  Buy at 0, sell at 3 → profit 3");
        System.out.println("  Buy at 1, sell at 4 → profit 3");
        System.out.println("Total profit: 6\n");

        int result1 = maxProfit(prices1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result1 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Strictly Increasing ===");
        int[] prices2 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(prices2));
        System.out.println("\nOnly one profitable run exists, second transaction adds nothing");
        System.out.println("Buy at 1, sell at 5 → profit 4\n");

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
        int[] prices5 = {2, 2, 2, 2};
        System.out.println("Input: " + Arrays.toString(prices5));
        System.out.println("\nNo price difference anywhere, no profit possible\n");

        int result5 = maxProfit(prices5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: One Transaction Beats Splitting ===");
        int[] prices6 = {1, 2, 4, 2, 5, 7, 2, 4, 9, 0};
        System.out.println("Input: " + Arrays.toString(prices6));
        System.out.println("\nBest two transactions:");
        System.out.println("  Buy at 1, sell at 7 → profit 6");
        System.out.println("  Buy at 2, sell at 9 → profit 7");
        System.out.println("Total profit: 13\n");

        int result6 = maxProfit(prices6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 13");
        System.out.println("  Status: " + (result6 == 13 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Two Days, Profitable ===");
        int[] prices7 = {1, 5};
        System.out.println("Input: " + Arrays.toString(prices7));
        System.out.println("\nOnly one transaction is possible: buy at 1, sell at 5 → profit 4\n");

        int result7 = maxProfit(prices7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result7 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Maximize profit with AT MOST two transactions,     ║");
        System.out.println("║           only one share held at a time                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Track State With (day, holding, transactions)  ║");
        System.out.println("║    'time' represents remaining sells allowed (starts at 2)   ║");
        System.out.println("║    Each sell consumes one unit of 'time'                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Five Approaches, Cross-Verified:                            ║");
        System.out.println("║    1) Pure Recursion — explores buy/sell/skip with time left ║");
        System.out.println("║    2) Memoization — top-down, caches (idx,bought,time)       ║");
        System.out.println("║    3) Tabulation — bottom-up, full O(n×2×3) DP table         ║");
        System.out.println("║    4) Space-Optimized — bottom-up, rolling 2×3 array         ║");
        System.out.println("║    5) Greedy Optimized — tracks two buy/profit pairs directly║");
        System.out.println("║                                                              ║");
        System.out.println("║  maxProfit() now calls all five and prints each result,      ║");
        System.out.println("║  flagging any disagreement, before returning the final       ║");
        System.out.println("║  answer from the greedy optimized approach.                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • At most 2 transactions total, no overlap allowed        ║");
        System.out.println("║    • Cross-checking approaches catches subtle DP bugs        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n) greedy; O(n) for DP variants too      ║");
        System.out.println("║                    (recursion adds exponential overhead)     ║");
        System.out.println("║  Space Complexity: O(1) greedy/space-optimized, O(n) tabular ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

}
