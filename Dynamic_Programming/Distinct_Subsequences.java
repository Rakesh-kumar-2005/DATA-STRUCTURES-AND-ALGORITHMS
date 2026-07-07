package Dynamic_Programming;

import java.util.Arrays;

public class Distinct_Subsequences {


    private static int recursive(int i, int j, String s, String t) {
        if (j < 0) {
            return 1;
        }

        if (i < 0) {
            return 0;
        }

        if (s.charAt(i) == t.charAt(j)) {
            return recursive(i - 1, j - 1, s, t) + recursive(i - 1, j, s, t);
        }

        return recursive(i - 1, j, s, t);
    }

    private static int memoization(int i, int j, String s, String t, int[][] dp) {
        if (j == 0) {
            return 1;
        }

        if (i == 0) {
            return 0;
        }

        if (dp[i][j] != - 1) {
            return dp[i][j];
        }

        if (s.charAt(i - 1) == t.charAt(j - 1)) {
            return dp[i][j] = memoization(i - 1, j - 1, s, t, dp) + memoization(i - 1, j, s, t, dp);
        }

        return dp[i][j] = memoization(i - 1, j, s, t, dp);
    }

    private static int tabulation(String s, String t) {

        int n = s.length();
        int m = t.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {
            dp[0][i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][m];

    }

    private static int SpaceOptimized2D(String s, String t) {

        int n = s.length();
        int m = t.length();

        int[] prev = new int[m + 1];
        prev[0] = 1;

        for (int i = 1; i <= n; i++) {

            int[] curr = new int[m + 1];
            curr[0] = 1;

            for (int j = 1; j <= m; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    curr[j] = prev[j - 1] + prev[j];
                } else {
                    curr[j] = prev[j];
                }
            }

            prev = curr;
        }

        return prev[m];
    }

    private static int SpaceOptimized1D(String s, String t) {

        int n = s.length();
        int m = t.length();

        int[] prev = new int[m + 1];
        prev[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = m; j >= 1; j--) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    prev[j] = prev[j - 1] + prev[j];
                }
            }
        }

        return prev[m];

    }

    private static int numDistinct(String s, String t) {

        int n = s.length();
        int m = t.length();

        System.out.println("\n--- Testing Distinct Subsequences: \"" + s + "\" and \"" + t + "\" ---\n");

        // Recursive
        System.out.println("1. Recursive: O(2^(m+n))");
        int rec = recursive(n - 1, m - 1, s, t);
        System.out.println("   Result: " + rec);

        // Memoization
        System.out.println("2. Memoization: O(m×n)");
        int[][] dp = new int[n + 1][m + 1];
        for (int[] row : dp) {
            Arrays.fill(row, - 1);
        }
        int memo = memoization(n, m, s, t, dp);
        System.out.println("   Result: " + memo);

        // Tabulation
        System.out.println("3. Tabulation: O(m×n)");
        int tab = tabulation(s, t);
        System.out.println("   Result: " + tab);

        // Space Optimized 1D
        System.out.println("4. Space Optimized (2D): O(n)");
        int opt2 = SpaceOptimized2D(s, t);
        System.out.println("   Result: " + opt2);

        // Space Optimized 1D
        System.out.println("5. Space Optimized (1D): O(n)");
        int opt1 = SpaceOptimized1D(s, t);
        System.out.println("   Result: " + opt1 + "\n");

        return opt1;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║            DISTINCT SUBSEQUENCES                             ║");
        System.out.println("║  Count distinct subsequences of s that equal t               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: \"rabbbit\" and \"rabbit\" ===");
        System.out.println("s=\"rabbbit\", t=\"rabbit\"");
        System.out.println("One 'b' can be excluded → 3 distinct subsequences\n");
        int result1 = numDistinct("rabbbit", "rabbit");
        System.out.println("✓ Result: " + result1 + " (Expected: 3)\n");

        System.out.println("=== Test Case 2: \"babgbag\" and \"bag\" ===");
        System.out.println("s=\"babgbag\", t=\"bag\"");
        System.out.println("Multiple 'a' and 'b' positions → 5 distinct subsequences\n");
        int result2 = numDistinct("babgbag", "bag");
        System.out.println("✓ Result: " + result2 + " (Expected: 5)\n");

        System.out.println("=== Test Case 3: Single Character ===");
        System.out.println("s=\"a\", t=\"a\" → 1 way\n");
        int result3 = numDistinct("a", "a");
        System.out.println("✓ Result: " + result3 + " (Expected: 1)\n");

        System.out.println("=== Test Case 4: No Match ===");
        System.out.println("s=\"a\", t=\"b\" → 0 ways\n");
        int result4 = numDistinct("a", "b");
        System.out.println("✓ Result: " + result4 + " (Expected: 0)\n");

        System.out.println("=== Test Case 5: Empty Target ===");
        System.out.println("s=\"abc\", t=\"\" → 1 way (empty subsequence)\n");
        int result5 = numDistinct("abc", "");
        System.out.println("✓ Result: " + result5 + " (Expected: 1)\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM: DP with two strings                              ║");
        System.out.println("║  dp[i][j] = # of distinct subsequences of s[0..i-1] = t[..]  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recurrence:                                                 ║");
        System.out.println("║    If s[i-1]==t[j-1]: dp[i][j] = dp[i-1][j-1] + dp[i-1][j]   ║");
        System.out.println("║    Else: dp[i][j] = dp[i-1][j]                               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Base: dp[i][0]=1 (empty string), dp[0][j]=0 (j>0)           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time: O(m×n), Space: O(n) optimized                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}