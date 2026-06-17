package Dynamic_Programming;

public class Longest_Common_Subsequence {

    private static int recursive(int left, int right, String str1, String str2) {
        if (left < 0 || right < 0) {
            return 0;
        }

        if (str1.charAt(left) == str2.charAt(right)) {
            return 1 + recursive(left - 1, right - 1, str1, str2);
        }

        return Math.max(recursive(left - 1, right, str1, str2), recursive(left, right - 1, str1, str2));
    }

    private static int memoization(int left, int right, String str1, String str2, int[][] dp) {
        if (left == 0 || right == 0) {
            return 0;
        }

        if (dp[left][right] != - 1) {
            return dp[left][right];
        }

        if (str1.charAt(left - 1) == str2.charAt(right - 1)) {
            return dp[left][right] = 1 + memoization(left - 1, right - 1, str1, str2, dp);
        }

        return dp[left][right] = Math.max(memoization(left - 1, right, str1, str2, dp), memoization(left, right - 1, str1, str2, dp));
    }

    private static int tabulation(String str1, String str2) {

        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = 0;
        }

        for (int i = 0; i <= n; i++) {
            dp[0][i] = 0;
        }

        for (int left = 1; left <= m; left++) {
            for (int right = 1; right <= n; right++) {
                if (str1.charAt(left - 1) == str2.charAt(right - 1)) {
                    dp[left][right] = 1 + dp[left - 1][right - 1];
                } else {
                    dp[left][right] = Math.max(dp[left - 1][right], dp[left][right - 1]);
                }
            }
        }

        return dp[m][n];
    }

    private static int ultimateSpaceOptimization(String str1, String str2) {

        int m = str1.length();
        int n = str2.length();
        int[] prev = new int[n + 1];

        for (int left = 1; left <= m; left++) {
            int[] curr = new int[n + 1];

            for (int right = 1; right <= n; right++) {
                if (str1.charAt(left - 1) == str2.charAt(right - 1)) {
                    curr[right] = 1 + prev[right - 1];
                } else {
                    curr[right] = Math.max(prev[right], curr[right - 1]);
                }
            }

            prev = curr;
        }

        return prev[n];
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           LONGEST COMMON SUBSEQUENCE (LCS)                   ║");
        System.out.println("║  Find longest sequence of characters in same order           ║");
        System.out.println("║  Characters need not be consecutive                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Basic LCS ===");
        String str1_1 = "abcde";
        String str2_1 = "ace";
        System.out.println("String 1: \"" + str1_1 + "\"");
        System.out.println("String 2: \"" + str2_1 + "\"");
        System.out.println("\nFinding LCS:");
        System.out.println("  Common chars in order: a(1,1), c(3,2), e(5,3)");
        System.out.println("  LCS: \"ace\" (length 3)\n");

        int result1_rec = recursive(str1_1.length() - 1, str2_1.length() - 1, str1_1, str2_1);

        int[][] dp1 = new int[str1_1.length() + 1][str2_1.length() + 1];
        for (int i = 0; i <= str1_1.length(); i++) {
            for (int j = 0; j <= str2_1.length(); j++) dp1[i][j] = - 1;
        }
        int result1_memo = memoization(str1_1.length(), str2_1.length(), str1_1, str2_1, dp1);
        int result1_tab = tabulation(str1_1, str2_1);
        int result1_opt = ultimateSpaceOptimization(str1_1, str2_1);

        System.out.println("✓ Recursive Result: " + result1_rec);
        System.out.println("✓ Memoization Result: " + result1_memo);
        System.out.println("✓ Tabulation Result: " + result1_tab);
        System.out.println("✓ Space Optimized Result: " + result1_opt);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result1_tab == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Identical Strings ===");
        String str1_2 = "abc";
        String str2_2 = "abc";
        System.out.println("String 1: \"" + str1_2 + "\"");
        System.out.println("String 2: \"" + str2_2 + "\"");
        System.out.println("\nBoth strings are identical");
        System.out.println("LCS: \"abc\" (entire string, length 3)\n");

        int result2_tab = tabulation(str1_2, str2_2);
        int result2_opt = ultimateSpaceOptimization(str1_2, str2_2);

        System.out.println("✓ Tabulation Result: " + result2_tab);
        System.out.println("✓ Space Optimized Result: " + result2_opt);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result2_tab == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: No Common Subsequence ===");
        String str1_3 = "abc";
        String str2_3 = "def";
        System.out.println("String 1: \"" + str1_3 + "\"");
        System.out.println("String 2: \"" + str2_3 + "\"");
        System.out.println("\nNo common characters");
        System.out.println("LCS: \"\" (empty, length 0)\n");

        int result3_tab = tabulation(str1_3, str2_3);
        int result3_opt = ultimateSpaceOptimization(str1_3, str2_3);

        System.out.println("✓ Tabulation Result: " + result3_tab);
        System.out.println("✓ Space Optimized Result: " + result3_opt);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3_tab == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: One Empty String ===");
        String str1_4 = "";
        String str2_4 = "abc";
        System.out.println("String 1: \"" + str1_4 + "\" (empty)");
        System.out.println("String 2: \"" + str2_4 + "\"");
        System.out.println("\nOne string is empty");
        System.out.println("LCS: \"\" (empty, length 0)\n");

        int result4_tab = tabulation(str1_4, str2_4);
        int result4_opt = ultimateSpaceOptimization(str1_4, str2_4);

        System.out.println("✓ Tabulation Result: " + result4_tab);
        System.out.println("✓ Space Optimized Result: " + result4_opt);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result4_tab == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Character ===");
        String str1_5 = "a";
        String str2_5 = "a";
        System.out.println("String 1: \"" + str1_5 + "\"");
        System.out.println("String 2: \"" + str2_5 + "\"");
        System.out.println("\nBoth are single matching character");
        System.out.println("LCS: \"a\" (length 1)\n");

        int result5_tab = tabulation(str1_5, str2_5);
        int result5_opt = ultimateSpaceOptimization(str1_5, str2_5);

        System.out.println("✓ Tabulation Result: " + result5_tab);
        System.out.println("✓ Space Optimized Result: " + result5_opt);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result5_tab == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Interleaved Strings ===");
        String str1_6 = "axbycz";
        String str2_6 = "abc";
        System.out.println("String 1: \"" + str1_6 + "\"");
        System.out.println("String 2: \"" + str2_6 + "\"");
        System.out.println("\nFinding LCS:");
        System.out.println("  Common chars in order: a(1,1), b(3,2), c(5,3)");
        System.out.println("  LCS: \"abc\" (length 3)\n");

        int result6_tab = tabulation(str1_6, str2_6);
        int result6_opt = ultimateSpaceOptimization(str1_6, str2_6);

        System.out.println("✓ Tabulation Result: " + result6_tab);
        System.out.println("✓ Space Optimized Result: " + result6_opt);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result6_tab == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Longer Strings ===");
        String str1_7 = "AGGTAB";
        String str2_7 = "GXTXAYB";
        System.out.println("String 1: \"" + str1_7 + "\"");
        System.out.println("String 2: \"" + str2_7 + "\"");
        System.out.println("\nFinding LCS:");
        System.out.println("  Possible LCS: \"GTAB\" (length 4)");
        System.out.println("  Chars: G(2,2), T(4,4), A(5,5), B(6,7)\n");

        int result7_tab = tabulation(str1_7, str2_7);
        int result7_opt = ultimateSpaceOptimization(str1_7, str2_7);

        System.out.println("✓ Tabulation Result: " + result7_tab);
        System.out.println("✓ Space Optimized Result: " + result7_opt);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result7_tab == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 8: Repeated Characters ===");
        String str1_8 = "aaa";
        String str2_8 = "aa";
        System.out.println("String 1: \"" + str1_8 + "\"");
        System.out.println("String 2: \"" + str2_8 + "\"");
        System.out.println("\nFinding LCS:");
        System.out.println("  LCS: \"aa\" (length 2)");
        System.out.println("  Take 2 'a's from both\n");

        int result8_tab = tabulation(str1_8, str2_8);
        int result8_opt = ultimateSpaceOptimization(str1_8, str2_8);

        System.out.println("✓ Tabulation Result: " + result8_tab);
        System.out.println("✓ Space Optimized Result: " + result8_opt);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result8_tab == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find longest common subsequence in two strings     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Definition:                                                 ║");
        System.out.println("║    Subsequence: characters in same order, not consecutive    ║");
        System.out.println("║    LCS: longest such sequence present in both strings        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example vs Substring:                                       ║");
        System.out.println("║    LCS(\"abcde\", \"ace\") = 3 (\"ace\" not consecutive in 1st)    ║");
        System.out.println("║    Substring requires consecutive characters                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recurrence Relation:                                        ║");
        System.out.println("║    If str1[i] == str2[j]:                                    ║");
        System.out.println("║      lcs(i,j) = 1 + lcs(i-1, j-1)                            ║");
        System.out.println("║      (match found, include + recurse on rest)                ║");
        System.out.println("║    Else:                                                     ║");
        System.out.println("║      lcs(i,j) = max(lcs(i-1,j), lcs(i,j-1))                  ║");
        System.out.println("║      (no match, try excluding one char from each)            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Base Cases:                                                 ║");
        System.out.println("║    If i < 0 or j < 0: return 0 (recursive approach)          ║");
        System.out.println("║    If i == 0 or j == 0: return 0 (memoization/tabulation)    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  DP Table Example: str1=\"abc\", str2=\"ac\"                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║         \"\"  a  c                                             ║");
        System.out.println("║    \"\"   0  0  0                                              ║");
        System.out.println("║    a    0  1  1  (a==a: 1+dp[-1,-1]=1)                       ║");
        System.out.println("║    b    0  1  1  (b!=a, b!=c: max(1,1)=1)                    ║");
        System.out.println("║    c    0  1  2  (c==c: 1+dp[1,0]=2)                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Result: dp[3][2] = 2 (LCS is \"ac\")                          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Tabulation Algorithm:                                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Create 2D array dp[m+1][n+1] (m,n = string lengths)         ║");
        System.out.println("║  Initialize first row and column to 0                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  For each position (i,j) from (1,1) to (m,n):                ║");
        System.out.println("║    If str1[i-1] == str2[j-1]:                                ║");
        System.out.println("║      dp[i][j] = 1 + dp[i-1][j-1]                             ║");
        System.out.println("║    Else:                                                     ║");
        System.out.println("║      dp[i][j] = max(dp[i-1][j], dp[i][j-1])                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Return dp[m][n]                                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Space Optimization (1D Array):                              ║");
        System.out.println("║    Only need previous row to compute current row             ║");
        System.out.println("║    Use two 1D arrays: prev and curr                          ║");
        System.out.println("║    When computing curr[j]:                                   ║");
        System.out.println("║      curr[j-1] already updated (from curr)                   ║");
        System.out.println("║      prev[j-1] is diagonal value needed                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Why Optimization Works:                                     ║");
        System.out.println("║    For position (i,j), only need:                            ║");
        System.out.println("║      dp[i-1][j] (above)     → prev[j]                        ║");
        System.out.println("║      dp[i][j-1] (left)      → curr[j-1]                      ║");
        System.out.println("║      dp[i-1][j-1] (diag)    → prev[j-1] before update        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Approaches Comparison:                                      ║");
        System.out.println("║    Recursive:     O(2^(m+n)) time, O(m+n) space (stack)      ║");
        System.out.println("║    Memoization:   O(m×n) time, O(m×n) space (memo table)     ║");
        System.out.println("║    Tabulation:    O(m×n) time, O(m×n) space (DP table)       ║");
        System.out.println("║    Optimized:     O(m×n) time, O(n) space (1D arrays)        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recovering the LCS String:                                  ║");
        System.out.println("║    Start from dp[m][n], backtrack:                           ║");
        System.out.println("║    if str1[i-1] == str2[j-1]:                                ║");
        System.out.println("║      Add char to result, move diagonally (i-1, j-1)          ║");
        System.out.println("║    Else:                                                     ║");
        System.out.println("║      Move to max value direction                             ║");
        System.out.println("║    Reverse result to get LCS                                 ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Complexity:                                                 ║");
        System.out.println("║    Time:  O(m × n) where m,n = string lengths                ║");
        System.out.println("║    Space: O(m × n) full DP, O(n) optimized                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}