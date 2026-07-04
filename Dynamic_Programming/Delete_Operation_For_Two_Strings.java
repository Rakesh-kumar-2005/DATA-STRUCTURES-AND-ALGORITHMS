package Dynamic_Programming;

public class Delete_Operation_For_Two_Strings {

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

        int n = str1.length();
        int m = str2.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int left = 1; left <= n; left++) {
            for (int right = 1; right <= m; right++) {

                if (str1.charAt(left - 1) == str2.charAt(right - 1)) {
                    dp[left][right] = 1 + dp[left - 1][right - 1];
                } else {
                    dp[left][right] = Math.max(dp[left - 1][right], dp[left][right - 1]);
                }
            }
        }

        return dp[n][m];
    }

    private static int ultimateSpaceOptimization(String str1, String str2) {

        int n = str1.length();
        int m = str2.length();

        int[] prev = new int[m + 1];

        for (int left = 1; left <= n; left++) {
            int[] curr = new int[m + 1];

            for (int right = 1; right <= m; right++) {

                if (str1.charAt(left - 1) == str2.charAt(right - 1)) {
                    curr[right] = 1 + prev[right - 1];
                } else {
                    curr[right] = Math.max(prev[right], curr[right - 1]);
                }
            }

            prev = curr;
        }

        return prev[m];
    }


    private static int minDistance(String word1, String word2) {

        System.out.println("\n--- Calculating Minimum Distance for \"" + word1 + "\" and \"" + word2 + "\" ---\n");

        // Approach 1: Recursive
        System.out.println("Approach 1: Recursive");
        System.out.println("  Calling: recursive(0, 0, \"" + word1 + "\", \"" + word2 + "\")");
        int lcs_recursive = recursive(word1.length() - 1, word2.length() - 1, word1, word2);
        System.out.println("  LCS Length: " + lcs_recursive);
        System.out.println("  Time: O(2^(m+n)), Space: O(m+n)\n");

        // Approach 2: Memoization
        System.out.println("Approach 2: Memoization");
        System.out.println("  Calling: memoization(1, 1, \"" + word1 + "\", \"" + word2 + "\", dp)");
        int[][] dp_memo = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                dp_memo[i][j] = - 1;
            }
        }

        int lcs_memo = memoization(word1.length(), word2.length(), word1, word2, dp_memo);
        System.out.println("  LCS Length: " + lcs_memo);
        System.out.println("  Time: O(m×n), Space: O(m×n)\n");

        // Approach 3: Tabulation
        System.out.println("Approach 3: Tabulation (2D DP)");
        System.out.println("  Building DP table for LCS");
        int lcs_tab = tabulation(word1, word2);
        System.out.println("  LCS Length: " + lcs_tab);
        System.out.println("  Time: O(m×n), Space: O(m×n)\n");

        // Approach 4: Space Optimized
        System.out.println("Approach 4: Space Optimized (1D arrays)");
        System.out.println("  Using only prev and curr arrays");
        int lcs_opt = ultimateSpaceOptimization(word1, word2);
        System.out.println("  LCS Length: " + lcs_opt);
        System.out.println("  Time: O(m×n), Space: O(n)\n");

        // Verify all approaches
        System.out.println("--- Verification ---");
        System.out.println("Recursive: " + lcs_recursive);
        System.out.println("Memoization: " + lcs_memo);
        System.out.println("Tabulation: " + lcs_tab);
        System.out.println("Space Optimized: " + lcs_opt);

        if (lcs_recursive == lcs_memo && lcs_memo == lcs_tab && lcs_tab == lcs_opt) {
            System.out.println("✓ All approaches match!\n");
        } else {
            System.out.println("✗ Approaches differ!\n");
        }

        // Calculate final result
        int min_distance = word1.length() + word2.length() - 2 * lcs_opt;
        System.out.println("--- Final Calculation ---");
        System.out.println("Word1 length: " + word1.length());
        System.out.println("Word2 length: " + word2.length());
        System.out.println("LCS length: " + lcs_opt);
        System.out.println("Min deletions = " + word1.length() + " + " + word2.length() + " - 2*" + lcs_opt);
        System.out.println("Min deletions = " + min_distance + "\n");

        return min_distance;

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║         DELETE OPERATION FOR TWO STRINGS                     ║");
        System.out.println("║  Find minimum deletions to make two strings equal            ║");
        System.out.println("║  Formula: deletions = len(s1) + len(s2) - 2*LCS(s1,s2)       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: \"sea\" and \"eat\" ===");
        String s1_1 = "sea";
        String s2_1 = "eat";
        System.out.println("Word 1: \"" + s1_1 + "\"");
        System.out.println("Word 2: \"" + s2_1 + "\"");
        System.out.println("\nFinding LCS(\"sea\", \"eat\") = \"ea\" (length 2)");
        System.out.println("Deletions = 3 + 3 - 2*2 = 2");
        System.out.println("Solution: Delete 's' from \"sea\", delete 't' from \"eat\" → both become \"ea\"\n");

        int result1 = minDistance(s1_1, s2_1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result1 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: \"leetcode\" and \"etco\" ===");
        String s1_2 = "leetcode";
        String s2_2 = "etco";
        System.out.println("Word 1: \"" + s1_2 + "\"");
        System.out.println("Word 2: \"" + s2_2 + "\"");
        System.out.println("\nFinding LCS(\"leetcode\", \"etco\") = \"etc\" (length 3)");
        System.out.println("Deletions = 8 + 4 - 2*3 = 6");
        System.out.println("Solution: Delete 'l','e','d','e' from word1 → \"etc\", delete 'o' from word2 → \"etc\"\n");

        int result2 = minDistance(s1_2, s2_2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result2 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Identical Strings ===");
        String s1_3 = "hello";
        String s2_3 = "hello";
        System.out.println("Word 1: \"" + s1_3 + "\"");
        System.out.println("Word 2: \"" + s2_3 + "\" (identical)");
        System.out.println("\nFinding LCS(\"hello\", \"hello\") = \"hello\" (length 5)");
        System.out.println("Deletions = 5 + 5 - 2*5 = 0");
        System.out.println("No deletions needed, strings already equal\n");

        int result3 = minDistance(s1_3, s2_3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: No Common Characters ===");
        String s1_4 = "abc";
        String s2_4 = "def";
        System.out.println("Word 1: \"" + s1_4 + "\"");
        System.out.println("Word 2: \"" + s2_4 + "\"");
        System.out.println("\nFinding LCS(\"abc\", \"def\") = \"\" (length 0)");
        System.out.println("Deletions = 3 + 3 - 2*0 = 6");
        System.out.println("Solution: Delete all characters from both strings\n");

        int result4 = minDistance(s1_4, s2_4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result4 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Character Match ===");
        String s1_5 = "ab";
        String s2_5 = "ba";
        System.out.println("Word 1: \"" + s1_5 + "\"");
        System.out.println("Word 2: \"" + s2_5 + "\"");
        System.out.println("\nFinding LCS(\"ab\", \"ba\") = \"a\" or \"b\" (length 1)");
        System.out.println("Deletions = 2 + 2 - 2*1 = 2");
        System.out.println("Solution: Delete one char from each to match\n");

        int result5 = minDistance(s1_5, s2_5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result5 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: One Empty String ===");
        String s1_6 = "abc";
        String s2_6 = "";
        System.out.println("Word 1: \"" + s1_6 + "\"");
        System.out.println("Word 2: \"" + s2_6 + "\" (empty)");
        System.out.println("\nFinding LCS(\"abc\", \"\") = \"\" (length 0)");
        System.out.println("Deletions = 3 + 0 - 2*0 = 3");
        System.out.println("Solution: Delete all characters from word1\n");

        int result6 = minDistance(s1_6, s2_6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result6 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Longer Common Subsequence ===");
        String s1_7 = "aggtab";
        String s2_7 = "gxtxayb";
        System.out.println("Word 1: \"" + s1_7 + "\"");
        System.out.println("Word 2: \"" + s2_7 + "\"");
        System.out.println("\nFinding LCS(\"aggtab\", \"gxtxayb\") = \"gtab\" (length 4)");
        System.out.println("Deletions = 6 + 7 - 2*4 = 5");
        System.out.println("Solution: Delete some chars to make both equal\n");

        int result7 = minDistance(s1_7, s2_7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result7 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Minimum deletions to make two strings equal        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: LCS Reduction                                  ║");
        System.out.println("║    minDeletions = len(s1) + len(s2) - 2*LCS(s1, s2)          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Why This Formula Works:                                     ║");
        System.out.println("║    • LCS chars are already common to both                    ║");
        System.out.println("║    • All other chars must be deleted                         ║");
        System.out.println("║    • Chars in LCS don't need deletion (count them once)      ║");
        System.out.println("║    • Non-LCS chars need deletion from both strings           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Derivation:                                                 ║");
        System.out.println("║    Total chars = len(s1) + len(s2)                           ║");
        System.out.println("║    LCS chars that remain = LCS_length                        ║");
        System.out.println("║    We count LCS twice (once per string)                      ║");
        System.out.println("║    Deletions = Total - 2*LCS                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: s1=\"sea\", s2=\"eat\"                                 ║");
        System.out.println("║    LCS(\"sea\", \"eat\") = \"ea\" (length 2)                       ║");
        System.out.println("║    String 1: \"sea\" → delete 's' → \"ea\"                       ║");
        System.out.println("║    String 2: \"eat\" → delete 't' → \"ea\"                       ║");
        System.out.println("║    Total deletions = 1 + 1 = 2                               ║");
        System.out.println("║    Formula: 3 + 3 - 2*2 = 6 - 4 = 2 ✓                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  DP Table: Same as LCS                                       ║");
        System.out.println("║    dp[i][j] = LCS length using s1[0..i-1] & s2[0..j-1]       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Algorithm:                                                  ║");
        System.out.println("║    1. Calculate LCS(s1, s2) using dynamic programming        ║");
        System.out.println("║    2. Apply formula: minDeletions = len(s1) + len(s2) - 2*LCS║");
        System.out.println("║    3. Return result                                          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(m × n) for LCS                           ║");
        System.out.println("║  Space Complexity:                                           ║");
        System.out.println("║    Tabulation: O(m × n)                                      ║");
        System.out.println("║    Space Optimized: O(n)                                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Edge Cases:                                                 ║");
        System.out.println("║    • Identical strings: LCS = length → 0 deletions           ║");
        System.out.println("║    • No common chars: LCS = 0 → len(s1)+len(s2) deletions    ║");
        System.out.println("║    • One empty string: LCS = 0 → len(s1) deletions           ║");
        System.out.println("║    • Single char strings: 0 or 2 deletions                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}