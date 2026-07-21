package Dynamic_Programming;

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