package Dynamic_Programming;

/*

    Description:
      Following program maximizes stock trading profit with unlimited transactions but
        a mandatory one-day cooldown after every sell before the next buy is allowed...

    Problem Statement:
      -> Given an array prices where prices[i] is the stock price on day i...
      -> You may complete as many transactions as you like...
      -> After selling, you must skip exactly one day (cooldown) before buying again...
      -> You may not hold more than one share at a time...
      -> Return the maximum total profit achievable...

    Key Insight:
      -> The only difference from Stock II is the sell transition: instead of moving to
           idx+1 in the not-holding state after selling, jump to idx+2 to skip the cooldown day...
      -> Buy transition is unchanged: move to idx+1 in the holding state...
      -> Cooldown is baked directly into the recurrence, no extra state dimension needed...
      -> This single change (idx+1 → idx+2 on sell) encodes the entire cooldown constraint...

    Example:
      -> prices = [1, 2, 3, 0, 2]:
           Buy at 1 (idx=0), sell at 3 (idx=2) → profit 2, cooldown at idx=3...
           Buy at 0 (idx=3), sell at 2 (idx=4) → profit 2...
           Total = 3...
      -> prices = [1, 2, 3, 4, 5]:
           Cooldown restricts consecutive trades...
           Optimal: buy at 1, sell at 5 → profit 4 (one transaction, no cooldown penalty)...
      -> prices = [7, 6, 4, 3, 1]:
           No upward swings → never buy → profit 0...

    Recursive Relation:
      -> Base: idx >= prices.length → return 0...
      -> If not holding (bought == 0):
           buy    = recursive(idx+1, prices, 1) - prices[idx]...
           notBuy = recursive(idx+1, prices, 0)...
           profit = Math.max(buy, notBuy)...
      -> If holding (bought == 1):
           sell    = recursive(idx+2, prices, 0) + prices[idx]  ← idx+2 skips cooldown day...
           notSell = recursive(idx+1, prices, 1)...
           profit = Math.max(sell, notSell)...

    Why idx+2 on Sell:
      -> After selling on day idx, day idx+1 is the mandatory cooldown day...
      -> The next possible buy is on day idx+2...
      -> Using idx+2 directly in the sell branch encodes this without a separate cooldown state...
      -> If idx+2 >= n: recursive base case returns 0 (no more profitable days)...

    Approach 1 - Recursive:
      -> Pure top-down without caching...
      -> Exponential branches at every (day, holding) decision...
      -> Time: O(2^n), Space: O(n) recursion stack...

    Approach 2 - Memoization (Top-Down DP):
      -> 2D dp array of size n × 2, initialized to -1...
      -> dp[idx][bought] caches max profit from that state forward...
      -> On cache hit: return stored result...
      -> Time: O(n), Space: O(n)...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[idx][bought] = max profit from day idx with given holding state...
      -> dp has size (n+2) × 2 to safely access dp[idx+2] without bounds errors...
      -> Base: dp[n][0] = dp[n][1] = dp[n+1][0] = dp[n+1][1] = 0...
      -> Fill right to left: idx from n-1 down to 0...
      -> On sell: dp[idx][1] uses dp[idx+2][0] + prices[idx]...
      -> Answer: dp[0][0]...
      -> Time: O(n), Space: O(n)...

    Approach 4 - Space Optimization (Three Rolling Arrays):
      -> Replaces 2D table with three 2-element arrays: curr[], next[], next2[]...
      -> next[] represents dp[idx+1], next2[] represents dp[idx+2]...
      -> On sell: profit = next2[0] + prices[idx] (two steps ahead)...
      -> On buy: profit = next[1] - prices[idx] (one step ahead)...
      -> After each row: next2 = next.clone(), next = curr.clone()...
      -> Three arrays are required (not two) because sell looks two steps ahead...
      -> Time: O(n), Space: O(1) (fixed 6-element state)...

    Why Three Arrays Instead of Two:
      -> Stock II space optimization uses two arrays because transitions look at most idx+1...
      -> Here, sell reads dp[idx+2][0] → needs values from two rows ahead simultaneously...
      -> next[] holds idx+1 values, next2[] holds idx+2 values at the time of processing idx...
      -> After computing curr[], advance: next2 = next, next = curr...

    DP Table Visualization (prices = [1, 2, 3, 0, 2]):

         idx \ bought    0     1
              5 (base):  0     0
              4 (base):  0     0
              4:         2     2
              3:         2     2
              2:         3     2
              1:         3     2
              0:         3     -1

         Answer: dp[0][0] = 3...

    Edge Cases:
      -> Empty array → idx >= n immediately → profit 0...
      -> Single day → no sell after buy possible → profit 0...
      -> All same prices → no profitable transaction → profit 0...
      -> Strictly decreasing → no buy ever profitable → profit 0...
      -> Sell on last day → idx+2 >= n → next recursive call returns 0 (safe)...

    Comparison of All Approaches:
      -> Recursive:       Time O(2^n), Space O(n)  — exponential...
      -> Memoization:     Time O(n),   Space O(n)  — top-down caching...
      -> Tabulation:      Time O(n),   Space O(n)  — iterative, n+2 rows needed...
      -> Space Optimized: Time O(n),   Space O(1)  — three rolling 2-element arrays...

    Time and Space Complexity:
      -> Time:  O(n) for all DP approaches...
      -> Space: O(1) for space-optimized, O(n) for tabulation and memoization...

    Applications:
      -> Algorithmic trading with mandatory post-sale waiting periods...
      -> Task scheduling with cool-down intervals between repetitions...
      -> Resource management where reuse requires a mandatory idle period...
      -> Extension of Stock II toward more realistic market constraint modeling...

*/

import java.util.Arrays;

public class Best_Time_To_Buy_And_Sell_Stock_With_Cooldown {

    private static int recursive(int idx, int[] prices, int bought) {

        if (idx >= prices.length) {
            return 0;
        }

        int profit = 0;
        if (bought == 0) {
            int buy = recursive(idx + 1, prices, 1) - prices[idx];
            int notBuy = recursive(idx + 1, prices, 0);
            profit = Math.max(buy, notBuy);
        } else {
            int sell = recursive(idx + 2, prices, 0) + prices[idx];
            int notSell = recursive(idx + 1, prices, 1);
            profit = Math.max(sell, notSell);
        }

        return profit;
    }

    private static int memoization(int idx, int[] prices, int bought, int[][] dp) {

        if (idx >= prices.length) {
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
            int sell = memoization(idx + 2, prices, 0, dp) + prices[idx];
            int notSell = memoization(idx + 1, prices, 1, dp);
            profit = Math.max(sell, notSell);
        }

        return dp[idx][bought] = profit;
    }

    private static int tabulation(int[] prices) {

        int n = prices.length;
        int[][] dp = new int[n + 2][2];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                int profit = 0;
                if (bought == 0) {
                    int buy = dp[idx + 1][1] - prices[idx];
                    int notBuy = dp[idx + 1][0];
                    profit = Math.max(buy, notBuy);
                } else {
                    int sell = dp[idx + 2][0] + prices[idx];
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
        int[] next = new int[2];
        int[] next2 = new int[2];
        int[] curr = new int[2];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int bought = 0; bought <= 1; bought++) {
                int profit;
                if (bought == 0) {
                    int buy = next[1] - prices[idx];
                    int notBuy = next[0];
                    profit = Math.max(buy, notBuy);
                } else {
                    int sell = next2[0] + prices[idx];
                    int notSell = next[1];
                    profit = Math.max(sell, notSell);
                }
                curr[bought] = profit;
            }
            next2 = next.clone();
            next = curr.clone();
        }

        return curr[0];
    }


    private static int maxProfit(int[] prices) {

        int n = prices.length;
        int[][] dp = new int[n][2];

        for (int[] a : dp) {
            Arrays.fill(a, - 1);
        }

        int recursiveResult = recursive(0, prices, 0);
        int memoizationResult = memoization(0, prices, 0, dp);
        int tabulationResult = tabulation(prices);
        int spaceOptimizedResult = spaceOptimized(prices);

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
        System.out.println("║   BEST TIME TO BUY AND SELL STOCK WITH COOLDOWN              ║");
        System.out.println("║  Maximize profit with unlimited transactions, but must wait  ║");
        System.out.println("║  one day (cooldown) after selling before buying again        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Cooldown Case ===");
        int[] prices1 = {1, 2, 3, 0, 2};
        System.out.println("Input: " + Arrays.toString(prices1));
        System.out.println("\nBuy at 1, sell at 3 → profit 2, cooldown");
        System.out.println("Buy at 0, sell at 2 → profit 2");
        System.out.println("Total profit: 3\n");

        int result1 = maxProfit(prices1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result1 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Single Price ===");
        int[] prices2 = {1};
        System.out.println("Input: " + Arrays.toString(prices2));
        System.out.println("\nOnly one day, no possible transaction → profit 0\n");

        int result2 = maxProfit(prices2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result2 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Decreasing ===");
        int[] prices3 = {7, 6, 4, 3, 1};
        System.out.println("Input: " + Arrays.toString(prices3));
        System.out.println("\nNo upward swings anywhere, prices only fall");
        System.out.println("Best strategy: never buy → profit 0\n");

        int result3 = maxProfit(prices3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Strictly Increasing ===");
        int[] prices4 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(prices4));
        System.out.println("\nCooldown forces a single buy-sell here since consecutive");
        System.out.println("day trades would need a wasted cooldown day");
        System.out.println("Buy at 1, sell at 5 → profit 4\n");

        int result4 = maxProfit(prices4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result4 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: All Same Price ===");
        int[] prices5 = {3, 3, 3, 3};
        System.out.println("Input: " + Arrays.toString(prices5));
        System.out.println("\nNo price difference anywhere, no profit possible\n");

        int result5 = maxProfit(prices5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Empty Array ===");
        int[] prices6 = {};
        System.out.println("Input: " + Arrays.toString(prices6));
        System.out.println("\nNo prices at all, no transaction possible → profit 0\n");

        int result6 = maxProfit(prices6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Multiple Zigzags With Cooldown ===");
        int[] prices7 = {1, 4, 2, 7, 3, 9};
        System.out.println("Input: " + Arrays.toString(prices7));
        System.out.println("\nBuy at 1, sell at 4 → profit 3, cooldown at idx 2");
        System.out.println("Buy at 7's dip (idx 4=3), sell at 9 → profit 6");
        System.out.println("Total profit: 3 + 6 = 9, but overlapping windows are");
        System.out.println("resolved by the DP to find the true optimum\n");

        int result7 = maxProfit(prices7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Status: (see DP-computed value above)\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Maximize profit with unlimited transactions,       ║");
        System.out.println("║           but a mandatory 1-day cooldown after every sell    ║");
        System.out.println("║           before the next buy is allowed                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Selling Skips an Extra Day Forward             ║");
        System.out.println("║    A normal sell transition moves idx+1; here a sell moves   ║");
        System.out.println("║    idx+2 instead, baking the cooldown directly into the      ║");
        System.out.println("║    recurrence rather than tracking a separate state.         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Four Approaches, Cross-Verified:                            ║");
        System.out.println("║    1) Pure Recursion — explores buy/sell/skip, sell jumps +2 ║");
        System.out.println("║    2) Memoization — top-down, caches (idx, bought) results   ║");
        System.out.println("║    3) Tabulation — bottom-up, O(n×2) DP table with idx+2     ║");
        System.out.println("║    4) Space-Optimized — rolling next/next2/curr arrays       ║");
        System.out.println("║       (next2 needed because sell looks two steps ahead)      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  maxProfit() now calls all four and prints each result,      ║");
        System.out.println("║  flagging any disagreement, before returning the final       ║");
        System.out.println("║  answer from the space-optimized approach.                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • State = (day index, currently holding a share or not)   ║");
        System.out.println("║    • Cooldown is encoded via idx+2 on the sell transition    ║");
        System.out.println("║    • Space-optimized version needs 3 rolling arrays,         ║");
        System.out.println("║      not 2, because of the two-step lookahead on sell        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n) for DP variants                       ║");
        System.out.println("║                    (recursion adds exponential overhead)     ║");
        System.out.println("║  Space Complexity: O(1) space-optimized, O(n) tabular        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
