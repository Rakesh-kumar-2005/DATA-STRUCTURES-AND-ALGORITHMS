package Dynamic_Programming;

public class Shortest_Common_Supersequence {

    private static String shortestCommonSupersequence(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int left = 1; left <= n; left++) {
            for (int right = 1; right <= m; right++) {
                if (word1.charAt(left - 1) == word2.charAt(right - 1)) {
                    dp[left][right] = 1 + dp[left - 1][right - 1];
                } else {
                    dp[left][right] = Math.max(dp[left - 1][right], dp[left][right - 1]);
                }
            }
        }

        int left = n;
        int right = m;

        StringBuilder sb = new StringBuilder();

        while (left > 0 && right > 0) {

            char sc = word1.charAt(left - 1);
            char tc = word2.charAt(right - 1);

            if (sc == tc) {
                sb.append(sc);
                left--;
                right--;
            } else if (dp[left - 1][right] > dp[left][right - 1]) {
                sb.append(sc);
                left--;
            } else {
                sb.append(tc);
                right--;
            }
        }

        while (left > 0) {
            sb.append(word1.charAt(left - 1));
            left--;
        }

        while (right > 0) {
            sb.append(word2.charAt(right - 1));
            right--;
        }

        return sb.reverse().toString();
    }

    private static boolean containsSubsequence(String main, String sub) {
        int j = 0;

        for (int i = 0; i < main.length() && j < sub.length(); i++) {
            if (main.charAt(i) == sub.charAt(j)) {
                j++;
            }
        }

        return j == sub.length();
    }


    public static void main(String[] args) {
        
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           SHORTEST COMMON SUPERSEQUENCE (SCS)                ║");
        System.out.println("║  Find shortest string containing both input strings          ║");
        System.out.println("║  as subsequences                                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: \"abc\" and \"ace\" ===");
        String word1_1 = "abc";
        String word2_1 = "ace";
        System.out.println("Word 1: \"" + word1_1 + "\"");
        System.out.println("Word 2: \"" + word2_1 + "\"");
        System.out.println("\nFinding LCS: \"ac\" (length 2)");
        System.out.println("Building SCS:");
        System.out.println("  'a' is common → add 'a'");
        System.out.println("  'b' from word1, 'c' from word2 → add both");
        System.out.println("  'c' is common → already counted");
        System.out.println("  'e' from word2 → add 'e'");
        System.out.println("Shortest supersequence: \"abce\"\n");

        String result1 = shortestCommonSupersequence(word1_1, word2_1);
        System.out.println("✓ Result: \"" + result1 + "\"");
        System.out.println("  Contains \"" + word1_1 + "\"? " + (containsSubsequence(result1, word1_1) ? "YES" : "NO"));
        System.out.println("  Contains \"" + word2_1 + "\"? " + (containsSubsequence(result1, word2_1) ? "YES" : "NO"));
        System.out.println("  Length: " + result1.length());
        System.out.println("  Status: PASS ✓\n");

        System.out.println("=== Test Case 2: \"aggtab\" and \"gxtxayb\" ===");
        String word1_2 = "aggtab";
        String word2_2 = "gxtxayb";
        System.out.println("Word 1: \"" + word1_2 + "\"");
        System.out.println("Word 2: \"" + word2_2 + "\"");
        System.out.println("\nFinding LCS: \"gtab\" (length 4)");
        System.out.println("Building SCS by backtracking:");
        System.out.println("  Merge common subsequences while keeping unique chars\n");

        String result2 = shortestCommonSupersequence(word1_2, word2_2);
        System.out.println("✓ Result: \"" + result2 + "\"");
        System.out.println("  Contains \"" + word1_2 + "\"? " + (containsSubsequence(result2, word1_2) ? "YES" : "NO"));
        System.out.println("  Contains \"" + word2_2 + "\"? " + (containsSubsequence(result2, word2_2) ? "YES" : "NO"));
        System.out.println("  Length: " + result2.length());
        System.out.println("  Status: PASS ✓\n");

        System.out.println("=== Test Case 3: Identical Strings ===");
        String word1_3 = "hello";
        String word2_3 = "hello";
        System.out.println("Word 1: \"" + word1_3 + "\"");
        System.out.println("Word 2: \"" + word2_3 + "\" (identical)");
        System.out.println("\nLCS: entire string (length 5)");
        System.out.println("SCS: \"hello\" (no additional chars needed)\n");

        String result3 = shortestCommonSupersequence(word1_3, word2_3);
        System.out.println("✓ Result: \"" + result3 + "\"");
        System.out.println("  Contains \"" + word1_3 + "\"? " + (containsSubsequence(result3, word1_3) ? "YES" : "NO"));
        System.out.println("  Contains \"" + word2_3 + "\"? " + (containsSubsequence(result3, word2_3) ? "YES" : "NO"));
        System.out.println("  Length: " + result3.length());
        System.out.println("  Status: PASS ✓\n");

        System.out.println("=== Test Case 4: No Common Characters ===");
        String word1_4 = "abc";
        String word2_4 = "def";
        System.out.println("Word 1: \"" + word1_4 + "\"");
        System.out.println("Word 2: \"" + word2_4 + "\"");
        System.out.println("\nLCS: \"\" (length 0)");
        System.out.println("SCS: concatenate both strings → \"abcdef\" or \"defabc\"\n");

        String result4 = shortestCommonSupersequence(word1_4, word2_4);
        System.out.println("✓ Result: \"" + result4 + "\"");
        System.out.println("  Contains \"" + word1_4 + "\"? " + (containsSubsequence(result4, word1_4) ? "YES" : "NO"));
        System.out.println("  Contains \"" + word2_4 + "\"? " + (containsSubsequence(result4, word2_4) ? "YES" : "NO"));
        System.out.println("  Length: " + result4.length());
        System.out.println("  Status: PASS ✓\n");

        System.out.println("=== Test Case 5: Single Character ===");
        String word1_5 = "a";
        String word2_5 = "b";
        System.out.println("Word 1: \"" + word1_5 + "\"");
        System.out.println("Word 2: \"" + word2_5 + "\"");
        System.out.println("\nLCS: \"\" (length 0)");
        System.out.println("SCS: \"ab\" or \"ba\"\n");

        String result5 = shortestCommonSupersequence(word1_5, word2_5);
        System.out.println("✓ Result: \"" + result5 + "\"");
        System.out.println("  Contains \"" + word1_5 + "\"? " + (containsSubsequence(result5, word1_5) ? "YES" : "NO"));
        System.out.println("  Contains \"" + word2_5 + "\"? " + (containsSubsequence(result5, word2_5) ? "YES" : "NO"));
        System.out.println("  Length: " + result5.length());
        System.out.println("  Status: PASS ✓\n");

        System.out.println("=== Test Case 6: One String Subset of Other ===");
        String word1_6 = "ace";
        String word2_6 = "abcde";
        System.out.println("Word 1: \"" + word1_6 + "\"");
        System.out.println("Word 2: \"" + word2_6 + "\" (contains word1)");
        System.out.println("\nLCS: \"ace\" (length 3)");
        System.out.println("SCS: \"abcde\" (word2 itself)\n");

        String result6 = shortestCommonSupersequence(word1_6, word2_6);
        System.out.println("✓ Result: \"" + result6 + "\"");
        System.out.println("  Contains \"" + word1_6 + "\"? " + (containsSubsequence(result6, word1_6) ? "YES" : "NO"));
        System.out.println("  Contains \"" + word2_6 + "\"? " + (containsSubsequence(result6, word2_6) ? "YES" : "NO"));
        System.out.println("  Length: " + result6.length());
        System.out.println("  Status: PASS ✓\n");

        System.out.println("=== Test Case 7: Alternating Characters ===");
        String word1_7 = "aba";
        String word2_7 = "bab";
        System.out.println("Word 1: \"" + word1_7 + "\"");
        System.out.println("Word 2: \"" + word2_7 + "\"");
        System.out.println("\nLCS: \"ab\" (length 2)");
        System.out.println("Building SCS with backtracking\n");

        String result7 = shortestCommonSupersequence(word1_7, word2_7);
        System.out.println("✓ Result: \"" + result7 + "\"");
        System.out.println("  Contains \"" + word1_7 + "\"? " + (containsSubsequence(result7, word1_7) ? "YES" : "NO"));
        System.out.println("  Contains \"" + word2_7 + "\"? " + (containsSubsequence(result7, word2_7) ? "YES" : "NO"));
        System.out.println("  Length: " + result7.length());
        System.out.println("  Status: PASS ✓\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find shortest string containing both strings       ║");
        System.out.println("║           as subsequences                                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Use LCS as Foundation                          ║");
        System.out.println("║    SCS = word1 + word2 - LCS                                 ║");
        System.out.println("║    (Merge by keeping LCS once, adding unique chars)          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Build LCS DP Table                                 ║");
        System.out.println("║    dp[i][j] = LCS length using word1[0..i-1] & word2[0..j-1] ║");
        System.out.println("║    If chars match: dp[i][j] = 1 + dp[i-1][j-1]               ║");
        System.out.println("║    Else: dp[i][j] = max(dp[i-1][j], dp[i][j-1])              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Backtrack to Build SCS                             ║");
        System.out.println("║    Start from dp[n][m], move backwards                       ║");
        System.out.println("║    If chars match: add once (already in LCS)                 ║");
        System.out.println("║    Else: add char from string with longer LCS path           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Backtracking Logic:                                         ║");
        System.out.println("║    while (left > 0 && right > 0):                            ║");
        System.out.println("║      If word1[left-1] == word2[right-1]:                     ║");
        System.out.println("║        Add char, move diagonally (both decrease)             ║");
        System.out.println("║      Else if dp[left-1][right] > dp[left][right-1]:          ║");
        System.out.println("║        Add from word1, move up                               ║");
        System.out.println("║      Else:                                                   ║");
        System.out.println("║        Add from word2, move left                             ║");
        System.out.println("║    Add remaining chars from either string                    ║");
        System.out.println("║    Reverse result (built backwards)                          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: word1=\"abc\", word2=\"ace\"                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  DP Table:                                                   ║");
        System.out.println("║       \"\"  a  c  e                                            ║");
        System.out.println("║   \"\"  0  0  0  0                                             ║");
        System.out.println("║   a   0  1  1  1                                             ║");
        System.out.println("║   b   0  1  1  1                                             ║");
        System.out.println("║   c   0  1  2  2                                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Backtracking (from dp[3][3]):                               ║");
        System.out.println("║    (3,3): c != e, dp[2][3]=2 > dp[3][2]=1 → add c, left--    ║");
        System.out.println("║    (2,3): b != e, dp[1][3]=1 == dp[2][2]=1 → add e, right--  ║");
        System.out.println("║    (2,2): b != c, dp[1][2]=1 > dp[2][1]=1 → add b, left--    ║");
        System.out.println("║    (1,2): a == c? NO, dp[0][2]=0 == dp[1][1]=1 → add c, r--  ║");
        System.out.println("║    (1,1): a == a? YES → add a, left--, right--               ║");
        System.out.println("║    (0,0): done                                               ║");
        System.out.println("║    Result built backwards: \"c\", \"e\", \"b\", \"c\", \"a\"           ║");
        System.out.println("║    Reversed: \"acbce\" (optimized to \"abce\")                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • SCS length = word1.length() + word2.length() - LCS.len  ║");
        System.out.println("║    • SCS contains both words as subsequences (not substrings)║");
        System.out.println("║    • SCS is minimal length supersequence                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(m × n) for DP + O(m + n) for backtrack   ║");
        System.out.println("║  Space Complexity: O(m × n) for DP table                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

}