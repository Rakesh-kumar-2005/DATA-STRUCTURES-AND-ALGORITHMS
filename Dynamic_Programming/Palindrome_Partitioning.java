package Dynamic_Programming;

import java.util.Arrays;

public class Palindrome_Partitioning {

    private static boolean isPalindrome(String st, int curr, int j) {

        while (curr < j) {
            if (st.charAt(curr) != st.charAt(j)) {
                return false;
            }
            curr++;
            j--;
        }

        return true;
    }

    private static int recursive(String st, int curr, int n) {

        if (curr == n) {
            return 0;
        }

        int minCost = Integer.MAX_VALUE;
        for (int idx = curr; idx < n; idx++) {
            if (isPalindrome(st, curr, idx)) {
                int cost = 1 + recursive(st, idx + 1, n);
                minCost = Math.min(minCost, cost);
            }
        }

        return minCost;
    }

    private static int memoization(String st, int curr, int n, int[] dp) {

        if (curr == n) {
            return 0;
        }

        if (dp[curr] != - 1) {
            return dp[curr];
        }

        int minCost = Integer.MAX_VALUE;
        for (int idx = curr; idx < n; idx++) {
            if (isPalindrome(st, curr, idx)) {
                int cost = 1 + memoization(st, idx + 1, n, dp);
                minCost = Math.min(minCost, cost);
            }
        }

        return dp[curr] = minCost;
    }

    private static int tabulation(String st, int n) {

        int[] dp = new int[n + 1];

        for (int curr = n - 1; curr >= 0; curr--) {
            int minCost = Integer.MAX_VALUE;

            for (int idx = curr; idx < n; idx++) {
                if (isPalindrome(st, curr, idx)) {
                    int cost = 1 + dp[idx + 1];
                    minCost = Math.min(minCost, cost);
                }
            }

            dp[curr] = minCost;
        }

        return dp[0] - 1;
    }

    private static int minCut(String st) {

        int n = st.length();

        int[] dp = new int[n];
        Arrays.fill(dp, - 1);

        int recursiveResult = recursive(st, 0, n) - 1;
        int memoizationResult = memoization(st, 0, n, dp) - 1;
        int tabulationResult = tabulation(st, n);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return tabulationResult;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  PALINDROME PARTITIONING                     ║");
        System.out.println("║  Find the minimum number of cuts needed so that every piece  ║");
        System.out.println("║  of the string is a palindrome                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        String st1 = "aab";
        System.out.println("String: \"" + st1 + "\"");
        System.out.println("\nPartition: \"aa\" | \"b\" → 1 cut\n");

        int result1 = minCut(st1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result1 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Single Character ===");
        String st2 = "a";
        System.out.println("String: \"" + st2 + "\"");
        System.out.println("\nA single character is already a palindrome → 0 cuts\n");

        int result2 = minCut(st2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result2 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Already a Palindrome ===");
        String st3 = "racecar";
        System.out.println("String: \"" + st3 + "\"");
        System.out.println("\nWhole string is a palindrome → 0 cuts\n");

        int result3 = minCut(st3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Two Different Characters ===");
        String st4 = "ab";
        System.out.println("String: \"" + st4 + "\"");
        System.out.println("\nPartition: \"a\" | \"b\" → 1 cut\n");

        int result4 = minCut(st4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result4 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: All Distinct Characters ===");
        String st5 = "abcd";
        System.out.println("String: \"" + st5 + "\"");
        System.out.println("\nNo palindrome longer than 1 → every char separate → 3 cuts\n");

        int result5 = minCut(st5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result5 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: All Same Characters ===");
        String st6 = "aaaa";
        System.out.println("String: \"" + st6 + "\"");
        System.out.println("\nWhole string is a palindrome → 0 cuts\n");

        int result6 = minCut(st6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Multiple Palindromic Pieces ===");
        String st7 = "abacdc";
        System.out.println("String: \"" + st7 + "\"");
        System.out.println("\nPartition: \"aba\" | \"cdc\" → 1 cut\n");

        int result7 = minCut(st7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result7 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                           ║");
        System.out.println("║  ─────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Partition a string into palindromic pieces using    ║");
        System.out.println("║           the fewest cuts                                     ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Key Insight: Front Partitioning                              ║");
        System.out.println("║    From index curr, try every end idx such that               ║");
        System.out.println("║    st[curr..idx] is a palindrome, then solve the rest         ║");
        System.out.println("║    starting at idx+1. Each piece costs 1.                     ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Recurrence:                                                  ║");
        System.out.println("║    f(curr) = min over palindromic st[curr..idx] of            ║");
        System.out.println("║              1 + f(idx+1)                                     ║");
        System.out.println("║    Base: f(n) = 0                                             ║");
        System.out.println("║    Answer = f(0) - 1 (pieces counted, but cuts = pieces - 1)  ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Three Approaches, Cross-Verified:                            ║");
        System.out.println("║    1) Recursive — tries every palindromic prefix              ║");
        System.out.println("║    2) Memoization — top-down, caches dp[curr]                 ║");
        System.out.println("║    3) Tabulation — bottom-up, curr from n-1 down to 0         ║");
        System.out.println("║                                                               ║");
        System.out.println("║  minCut() calls all three, prints each result, flags any      ║");
        System.out.println("║  disagreement, and returns the tabulation answer.             ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Example: \"aab\"                                               ║");
        System.out.println("║    f(2)=1 (\"b\"), f(1)=2 (\"a\"+\"b\"), f(0)=min(1+f(1), 1+f(2))   ║");
        System.out.println("║    =min(3, 2)=2 → cuts = 2 - 1 = 1                            ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Properties:                                                  ║");
        System.out.println("║    • isPalindrome uses two pointers, O(n) per check           ║");
        System.out.println("║    • The -1 converts piece count into cut count               ║");
        System.out.println("║    • Same partition pattern as Matrix Chain Multiplication    ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Time Complexity: O(n³) — n states, n choices, O(n) check     ║");
        System.out.println("║  Space Complexity: O(n) for dp (recursion adds O(n) stack)    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

    }

}