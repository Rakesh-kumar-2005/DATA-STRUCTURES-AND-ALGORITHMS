package Dynamic_Programming;

import java.util.Arrays;

public class Wildcard_Matching {

    private static boolean allStars(int j, String pattern) {
        for (int t = 1; t <= j; t++) {
            if (pattern.charAt(t - 1) != '*') {
                return false;
            }
        }

        return true;
    }

    private static boolean recursive(int i, int j, String text, String pattern) {

        if (i < 0 && j < 0) {
            return true;
        }

        if (i >= 0 && j < 0) {
            return false;
        }

        if (i < 0 && j >= 0) {
            return allStars(j, pattern);
        }

        if (text.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '?') {
            return recursive(i - 1, j - 1, text, pattern);
        }

        if (pattern.charAt(j) == '*') {
            return recursive(i - 1, j, text, pattern) || recursive(i, j - 1, text, pattern);
        }

        return false;
    }

    private static boolean memoization(int i, int j, String text, String pattern, int[][] dp) {

        if (i == 0 && j == 0) {
            return true;
        }

        if (i > 0 && j == 0) {
            return false;
        }

        if (i == 0 && j > 0) {
            return allStars(j, pattern);
        }

        if (dp[i][j] != - 1) {
            return dp[i][j] == 1;
        }

        if (text.charAt(i - 1) == pattern.charAt(j - 1) || pattern.charAt(j - 1) == '?') {
            dp[i][j] = memoization(i - 1, j - 1, text, pattern, dp) ? 1 : 0;
        } else if (pattern.charAt(j - 1) == '*') {
            dp[i][j] = (memoization(i - 1, j, text, pattern, dp) || memoization(i, j - 1, text, pattern, dp)) ? 1 : 0;
        } else {
            dp[i][j] = 0;
        }

        return dp[i][j] == 1;
    }

    private static boolean tabulation(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;

        for (int i = 1; i <= n; i++) {
            dp[i][0] = false;
        }

        for (int j = 1; j <= m; j++) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            } else {
                dp[0][j] = false;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text.charAt(i - 1) == pattern.charAt(j - 1) || pattern.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pattern.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[n][m];

    }

    private static boolean ultimateSpaceOptimization(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        boolean[] prev = new boolean[m + 1];
        prev[0] = true;

        for (int j = 1; j <= m; j++) {
            if (pattern.charAt(j - 1) == '*') {
                prev[j] = prev[j - 1];
            } else {
                prev[j] = false;
            }
        }

        for (int i = 1; i <= n; i++) {
            boolean[] curr = new boolean[m + 1];
            for (int j = 1; j <= m; j++) {
                if (text.charAt(i - 1) == pattern.charAt(j - 1) || pattern.charAt(j - 1) == '?') {
                    curr[j] = prev[j - 1];
                } else if (pattern.charAt(j - 1) == '*') {
                    curr[j] = prev[j] || curr[j - 1];
                } else {
                    curr[j] = false;
                }
            }

            prev = curr;
        }

        return prev[m];

    }

    private static boolean isMatch(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        int[][] dp = new int[n + 1][m + 1];
        for (int[] temp : dp) {
            Arrays.fill(temp, - 1);
        }

        boolean recursiveResult = recursive(n - 1, m - 1, text, pattern);
        boolean memoizationResult = memoization(n, m, text, pattern, dp);
        boolean tabulationResult = tabulation(text, pattern);
        boolean ultimateSpaceOptimizationResult = ultimateSpaceOptimization(text, pattern);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult
            + " | ultimateSpaceOptimization: " + ultimateSpaceOptimizationResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult || tabulationResult != ultimateSpaceOptimizationResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return ultimateSpaceOptimizationResult;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   WILDCARD MATCHING                          ║");
        System.out.println("║  Match text against pattern where '?' matches any single     ║");
        System.out.println("║  char and '*' matches any sequence (including empty)         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Simple Star ===");
        String text1 = "aa";
        String pattern1 = "*";
        System.out.println("Text: \"" + text1 + "\"");
        System.out.println("Pattern: \"" + pattern1 + "\"");
        System.out.println("\n'*' matches any sequence, including empty");
        System.out.println("So '*' alone matches every possible text\n");

        boolean result1 = isMatch(text1, pattern1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result1 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Mismatch Without Wildcards ===");
        String text2 = "cb";
        String pattern2 = "?a";
        System.out.println("Text: \"" + text2 + "\"");
        System.out.println("Pattern: \"" + pattern2 + "\"");
        System.out.println("\n'?' matches 'c' (any single char)");
        System.out.println("But 'a' != 'b' → no match\n");

        boolean result2 = isMatch(text2, pattern2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result2 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Star Absorbing Middle Section ===");
        String text3 = "adceb";
        String pattern3 = "*a*b";
        System.out.println("Text: \"" + text3 + "\"");
        System.out.println("Pattern: \"" + pattern3 + "\"");
        System.out.println("\nFirst '*' matches empty, 'a' matches 'a'");
        System.out.println("Second '*' absorbs \"dce\", 'b' matches final 'b'\n");

        boolean result3 = isMatch(text3, pattern3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result3 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Empty Text, All Stars Pattern ===");
        String text4 = "";
        String pattern4 = "***";
        System.out.println("Text: \"" + text4 + "\" (empty)");
        System.out.println("Pattern: \"" + pattern4 + "\"");
        System.out.println("\nAll stars can each match empty sequences");
        System.out.println("So empty text matches an all-star pattern\n");

        boolean result4 = isMatch(text4, pattern4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result4 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Empty Text, Non-Star Pattern ===");
        String text5 = "";
        String pattern5 = "*a*";
        System.out.println("Text: \"" + text5 + "\" (empty)");
        System.out.println("Pattern: \"" + pattern5 + "\"");
        System.out.println("\nStars can match empty, but literal 'a' needs a char");
        System.out.println("Empty text has no char for 'a' to match\n");

        boolean result5 = isMatch(text5, pattern5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result5 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Exact Match, No Wildcards ===");
        String text6 = "hello";
        String pattern6 = "hello";
        System.out.println("Text: \"" + text6 + "\"");
        System.out.println("Pattern: \"" + pattern6 + "\"");
        System.out.println("\nEvery character matches literally, position by position\n");

        boolean result6 = isMatch(text6, pattern6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result6 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Question Marks Only ===");
        String text7 = "abc";
        String pattern7 = "???";
        System.out.println("Text: \"" + text7 + "\"");
        System.out.println("Pattern: \"" + pattern7 + "\"");
        System.out.println("\nEach '?' matches exactly one character, lengths match (3 == 3)\n");

        boolean result7 = isMatch(text7, pattern7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result7 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Determine if text matches pattern where '?' is a   ║");
        System.out.println("║           single-char wildcard and '*' matches any sequence  ║");
        System.out.println("║           (including the empty sequence)                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: '*' Has Two Choices at Each Step               ║");
        System.out.println("║    Either it absorbs the current text character and stays,   ║");
        System.out.println("║    or it gives up and lets the pattern move on empty         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Four Approaches, Cross-Verified:                            ║");
        System.out.println("║    1) Pure Recursion — exponential, explores all branches    ║");
        System.out.println("║    2) Memoization — top-down, caches (i,j) results           ║");
        System.out.println("║    3) Tabulation — bottom-up, full O(n×m) DP table           ║");
        System.out.println("║    4) Space-Optimized — bottom-up, rolling O(m) array        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  isMatch() now calls all four and prints each result,        ║");
        System.out.println("║  flagging any disagreement, before returning the final       ║");
        System.out.println("║  answer from the space-optimized approach.                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: text=\"adceb\", pattern=\"*a*b\"                       ║");
        System.out.println("║    All four approaches agree → true                          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • '?' always consumes exactly one character               ║");
        System.out.println("║    • '*' can consume zero or many characters                 ║");
        System.out.println("║    • Cross-checking approaches catches subtle DP bugs        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n × m) dominated by tabulation/space-opt ║");
        System.out.println("║                    (recursion adds exponential overhead)     ║");
        System.out.println("║  Space Complexity: O(n × m) worst-case across combined calls ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}