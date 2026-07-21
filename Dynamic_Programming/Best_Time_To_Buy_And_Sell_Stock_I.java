package Dynamic_Programming;

/*

    Description:
      Following program finds the maximum profit from exactly one buy and one sell
        transaction, using a single pass that tracks the running minimum price...

    Problem Statement:
      -> Given an array prices where prices[i] is the stock price on day i...
      -> You may complete at most one transaction (one buy and one sell)...
      -> The sell must happen on a day strictly after the buy day...
      -> Return the maximum profit achievable, or 0 if no profit is possible...

    Key Insight:
      -> For any sell day, the best possible profit = price[sell] - min(prices[0..sell-1])...
      -> As we scan left to right, maintain the running minimum price seen so far...
      -> At each day, compute potential profit if we sell today using that running minimum...
      -> Track the global maximum of all such potential profits across all days...
      -> No need to store actual buy/sell indices, only the values matter...

    Example:
      -> prices = [7, 1, 5, 3, 6, 4]:
           day 0: min_cp=7, profit=max(0, 0)=0...
           day 1: min_cp=1, profit=max(0, 0)=0...
           day 2: min_cp=1, profit=max(0, 4)=4...
           day 3: min_cp=1, profit=max(4, 2)=4...
           day 4: min_cp=1, profit=max(4, 5)=5...
           day 5: min_cp=1, profit=max(5, 3)=5...
           Result: 5 (buy at 1, sell at 6)...
      -> prices = [7, 6, 4, 3, 1]:
           Running minimum decreases each day, price - min_cp always = 0...
           Result: 0 (no profitable transaction exists)...
      -> prices = [3, 8, 1, 9]:
           day 0: min_cp=3, profit=0...
           day 1: min_cp=3, profit=5...
           day 2: min_cp=1, profit=5 (profit doesn't drop when min updates)...
           day 3: min_cp=1, profit=max(5, 8)=8...
           Result: 8 (buy at 1, sell at 9)...

    Algorithm Steps:
      -> Initialize min_cp = Integer.MAX_VALUE (no price seen yet)...
      -> Initialize total_profit = 0 (no transaction yet)...
      -> For each price in prices:
           Update min_cp = Math.min(min_cp, price)...
           Update total_profit = Math.max(total_profit, price - min_cp)...
      -> Return total_profit...

    Why Initialize total_profit to 0:
      -> 0 represents the profit of making no transaction at all...
      -> If all prices are decreasing, price - min_cp is always 0 or negative...
      -> Math.max(total_profit, price - min_cp) keeps it at 0 in that case...
      -> Guarantees result is never negative even when no profit is possible...

    Why min_cp Updates Before Profit Computation:
      -> Order matters: min_cp = Math.min(min_cp, price) first...
      -> Then profit = Math.max(profit, price - min_cp)...
      -> When price equals the new minimum: price - min_cp = 0 → no profit recorded...
      -> This correctly avoids counting same-day buy and sell as profitable...
      -> Actually same-day buy/sell is allowed (profit = 0), no issue either way...

    Step-by-Step Trace (prices = [7, 1, 5, 3, 6, 4]):
      -> price=7: min_cp=7, profit=max(0, 7-7)=0...
      -> price=1: min_cp=1, profit=max(0, 1-1)=0...
      -> price=5: min_cp=1, profit=max(0, 5-1)=4...
      -> price=3: min_cp=1, profit=max(4, 3-1)=4...
      -> price=6: min_cp=1, profit=max(4, 6-1)=5...
      -> price=4: min_cp=1, profit=max(5, 4-1)=5...
      -> Result: 5...

    Difference From Stock II:
      -> Stock I: exactly one transaction → track running min, one sell decision...
      -> Stock II: unlimited transactions → sum all positive daily deltas...
      -> Stock I cannot recapture missed gains after a cheaper buy opportunity appears...
      -> Stock II captures every upswing by buying and selling on consecutive days...

    Edge Cases:
      -> Single day → no sell possible after buy → profit stays 0...
      -> All same prices → price - min_cp = 0 always → result = 0...
      -> Strictly increasing → buy on day 0 sell on last day → max spread...
      -> Strictly decreasing → running min always at current price → profit always 0...
      -> Dip followed by peak → running min captures the dip, peak gives max profit...

    Time and Space Complexity:
      -> Time:  O(n) — single linear pass through the prices array...
      -> Space: O(1) — only two tracking variables: min_cp and total_profit...

    Applications:
      -> Single-transaction stock profit maximization...
      -> Maximum profit from one buy and one sell in commodity markets...
      -> Finding the maximum difference between a later element and an earlier element...
      -> Foundation for harder variants: two transactions, k transactions, cooldown, fees...

*/

import java.util.Arrays;

public class Best_Time_To_Buy_And_Sell_Stock_I {

    private static int maxProfit(int[] prices) {

        int total_profit = 0;
        int min_cp = Integer.MAX_VALUE;

        for (int price : prices) {
            min_cp = Math.min(min_cp, price);
            total_profit = Math.max(total_profit, price - min_cp);
        }

        return total_profit;

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        BEST TIME TO BUY AND SELL STOCK I                     ║");
        System.out.println("║  Maximize profit with exactly one buy and one sell           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Dip Then Rise ===");
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        System.out.println("Input: " + Arrays.toString(prices1));
        System.out.println("\nTrack running minimum, check profit at each day:");
        System.out.println("  Buy at 1 (lowest before the peak)");
        System.out.println("  Sell at 6 → profit 5\n");

        int result1 = maxProfit(prices1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result1 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Strictly Decreasing ===");
        int[] prices2 = {7, 6, 4, 3, 1};
        System.out.println("Input: " + Arrays.toString(prices2));
        System.out.println("\nPrices only fall, no profitable sell exists");
        System.out.println("Best strategy: no transaction → profit 0\n");

        int result2 = maxProfit(prices2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result2 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Increasing ===");
        int[] prices3 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(prices3));
        System.out.println("\nBuy on day 1 at the lowest price, sell at the peak");
        System.out.println("Buy at 1, sell at 5 → profit 4\n");

        int result3 = maxProfit(prices3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result3 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

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
        System.out.println("\nNo price difference anywhere, no profit possible\n");

        int result5 = maxProfit(prices5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Dip in the Middle ===");
        int[] prices6 = {3, 8, 1, 9};
        System.out.println("Input: " + Arrays.toString(prices6));
        System.out.println("\nRunning min updates to 1 after the dip");
        System.out.println("Buy at 1, sell at 9 → profit 8 (beats buying at 3)\n");

        int result6 = maxProfit(prices6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 8");
        System.out.println("  Status: " + (result6 == 8 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Two Days, Profitable ===");
        int[] prices7 = {1, 5};
        System.out.println("Input: " + Arrays.toString(prices7));
        System.out.println("\nBuy at 1, sell at 5 → profit 4\n");

        int result7 = maxProfit(prices7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result7 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Maximize profit with exactly ONE buy and ONE sell, ║");
        System.out.println("║           sell must happen after the buy                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Track the Cheapest Price Seen So Far           ║");
        System.out.println("║    At every day, the best possible sell profit only          ║");
        System.out.println("║    depends on the lowest price seen up to that point.        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Single Pass Logic:                                          ║");
        System.out.println("║    min_cp = running minimum price seen so far                ║");
        System.out.println("║    For each price:                                           ║");
        System.out.println("║      min_cp = min(min_cp, price)                             ║");
        System.out.println("║      total_profit = max(total_profit, price - min_cp)        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: prices = [3, 8, 1, 9]                              ║");
        System.out.println("║    day0: min_cp=3, profit=max(0,0)=0                         ║");
        System.out.println("║    day1: min_cp=3, profit=max(0,5)=5                         ║");
        System.out.println("║    day2: min_cp=1, profit=max(5,0)=5                         ║");
        System.out.println("║    day3: min_cp=1, profit=max(5,8)=8                         ║");
        System.out.println("║    Result: 8                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Only one transaction allowed (unlike Stock II)          ║");
        System.out.println("║    • No need to track actual buy/sell days, just the value   ║");
        System.out.println("║    • If prices only fall, profit correctly stays 0           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n) — single pass through prices          ║");
        System.out.println("║  Space Complexity: O(1) — only two tracking variables        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
