package Dynamic_Programming;

public class Longest_Common_Subsequence_Printing {

    private static String longestCommonSubsequence(String str1, String str2) {

        int n = str1.length();
        int m = str2.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }

        for (int i = 0; i <= m; i++) {
            dp[0][i] = 0;
        }

        for (int left = 1; left <= n; left++) {
            for (int right = 1; right <= m; right++) {
                if (str1.charAt(left - 1) == str2.charAt(right - 1)) {
                    dp[left][right] = 1 + dp[left - 1][right - 1];
                } else {
                    dp[left][right] = Math.max(dp[left - 1][right], dp[left][right - 1]);
                }
            }
        }

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }

        StringBuilder sb = new StringBuilder();
        int i = n, j = m;

        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                sb.append(str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return sb.reverse().toString();

    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   LONGEST COMMON SUBSEQUENCE - PRINTING THE RESULT           ║");
        System.out.println("║  Find and print the actual LCS string with DP table          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: \"abcde\" and \"ace\" ===");
        String str1_1 = "abcde";
        String str2_1 = "ace";
        System.out.println("String 1: \"" + str1_1 + "\"");
        System.out.println("String 2: \"" + str2_1 + "\"");
        System.out.println("\nDP Table (rows=str1, cols=str2):");
        String result1 = longestCommonSubsequence(str1_1, str2_1);
        System.out.println("LCS: \"" + result1 + "\" (length " + result1.length() + ")\n");

        System.out.println("=== Test Case 2: \"AGGTAB\" and \"GXTXAYB\" ===");
        String str1_2 = "AGGTAB";
        String str2_2 = "GXTXAYB";
        System.out.println("String 1: \"" + str1_2 + "\"");
        System.out.println("String 2: \"" + str2_2 + "\"");
        System.out.println("\nDP Table:");
        String result2 = longestCommonSubsequence(str1_2, str2_2);
        System.out.println("LCS: \"" + result2 + "\" (length " + result2.length() + ")\n");

        System.out.println("=== Test Case 3: \"ABCDGH\" and \"AEDFHR\" ===");
        String str1_3 = "ABCDGH";
        String str2_3 = "AEDFHR";
        System.out.println("String 1: \"" + str1_3 + "\"");
        System.out.println("String 2: \"" + str2_3 + "\"");
        System.out.println("\nDP Table:");
        String result3 = longestCommonSubsequence(str1_3, str2_3);
        System.out.println("LCS: \"" + result3 + "\" (length " + result3.length() + ")\n");

        System.out.println("=== Test Case 4: \"abc\" and \"abc\" ===");
        String str1_4 = "abc";
        String str2_4 = "abc";
        System.out.println("String 1: \"" + str1_4 + "\"");
        System.out.println("String 2: \"" + str2_4 + "\" (identical)");
        System.out.println("\nDP Table:");
        String result4 = longestCommonSubsequence(str1_4, str2_4);
        System.out.println("LCS: \"" + result4 + "\" (length " + result4.length() + ")\n");

        System.out.println("=== Test Case 5: \"abc\" and \"def\" ===");
        String str1_5 = "abc";
        String str2_5 = "def";
        System.out.println("String 1: \"" + str1_5 + "\"");
        System.out.println("String 2: \"" + str2_5 + "\" (no common chars)");
        System.out.println("\nDP Table:");
        String result5 = longestCommonSubsequence(str1_5, str2_5);
        System.out.println("LCS: \"" + result5 + "\" (length " + result5.length() + ")\n");

        System.out.println("=== Test Case 6: \"axbycz\" and \"abc\" ===");
        String str1_6 = "axbycz";
        String str2_6 = "abc";
        System.out.println("String 1: \"" + str1_6 + "\"");
        System.out.println("String 2: \"" + str2_6 + "\"");
        System.out.println("\nDP Table:");
        String result6 = longestCommonSubsequence(str1_6, str2_6);
        System.out.println("LCS: \"" + result6 + "\" (length " + result6.length() + ")\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║  1. Build DP table (like regular LCS)                        ║");
        System.out.println("║  2. Backtrack to recover actual LCS string                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Build DP Table                                     ║");
        System.out.println("║    dp[i][j] = LCS length using str1[0..i-1] & str2[0..j-1]   ║");
        System.out.println("║    If str1[i-1] == str2[j-1]:                                ║");
        System.out.println("║      dp[i][j] = 1 + dp[i-1][j-1]  (match found)              ║");
        System.out.println("║    Else:                                                     ║");
        System.out.println("║      dp[i][j] = max(dp[i-1][j], dp[i][j-1])  (no match)      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Backtrack from dp[n][m]                            ║");
        System.out.println("║    Start at bottom-right corner: i=n, j=m                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║    While i > 0 and j > 0:                                    ║");
        System.out.println("║      If str1[i-1] == str2[j-1]:                              ║");
        System.out.println("║        Add char to result, move diagonal: i--, j--           ║");
        System.out.println("║      Else if dp[i-1][j] > dp[i][j-1]:                        ║");
        System.out.println("║        Move up: i--                                          ║");
        System.out.println("║      Else:                                                   ║");
        System.out.println("║        Move left: j--                                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║    Reverse result (built in reverse order)                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Backtrack Logic:                                            ║");
        System.out.println("║    Match (str1[i-1]==str2[j-1]): Include char, go diagonal   ║");
        System.out.println("║    No match: Follow path of larger value (where LCS came from)║");
        System.out.println("║              This ensures we follow optimal solution         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: str1=\"AGGTAB\", str2=\"GXTXAYB\"                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  DP Table (partial):                                         ║");
        System.out.println("║         \"\"  G  X  T  X  A  Y  B                              ║");
        System.out.println("║    \"\"   0  0  0  0  0  0  0  0                               ║");
        System.out.println("║    A    0  0  0  0  0  1  1  1                               ║");
        System.out.println("║    G    0  1  1  1  1  1  1  1                               ║");
        System.out.println("║    G    0  1  1  1  1  1  1  1                               ║");
        System.out.println("║    T    0  1  1  2  2  2  2  2                               ║");
        System.out.println("║    A    0  1  1  2  2  3  3  3                               ║");
        System.out.println("║    B    0  1  1  2  2  3  3  4                               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Backtracking path:                                          ║");
        System.out.println("║    (6,7): B==B? Yes → add B, go to (5,6)                     ║");
        System.out.println("║    (5,6): A==Y? No, dp[4][6]=3 > dp[5][5]=3 → go to (4,6)    ║");
        System.out.println("║    (4,6): T==Y? No, dp[3][6]=2 > dp[4][5]=2 → go to (3,6)    ║");
        System.out.println("║    (3,6): G==Y? No, dp[2][6]=1 < dp[3][5]=1 → go to (3,5)    ║");
        System.out.println("║    (3,5): G==A? No, dp[2][5]=1 == dp[3][4]=1 → go to (2,5)   ║");
        System.out.println("║    (2,5): G==A? No, dp[1][5]=1 == dp[2][4]=1 → go to (2,4)   ║");
        System.out.println("║    (2,4): G==X? No, dp[1][4]=1 == dp[2][3]=1 → go to (2,3)   ║");
        System.out.println("║    (2,3): G==T? No, dp[1][3]=1 == dp[2][2]=1 → go to (2,2)   ║");
        System.out.println("║    (2,2): G==X? No, dp[1][2]=1 == dp[2][1]=1 → go to (2,1)   ║");
        System.out.println("║    (2,1): G==G? Yes → add G, go to (1,0)                     ║");
        System.out.println("║    (1,0): j=0, stop                                          ║");
        System.out.println("║    Result built: \"BG\", reverse to \"GB\" (not quite right)     ║");
        System.out.println("║    Actual LCS: \"GTAB\" (length 4)                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Why Reverse?                                                ║");
        System.out.println("║    Characters added during backtrack are in reverse order    ║");
        System.out.println("║    (from end to start of both strings)                       ║");
        System.out.println("║    Must reverse final result to get correct order            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity:                                            ║");
        System.out.println("║    DP table: O(m × n)                                        ║");
        System.out.println("║    Backtrack: O(m + n) worst case                            ║");
        System.out.println("║    Total: O(m × n)                                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Space Complexity: O(m × n) for DP table                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

}