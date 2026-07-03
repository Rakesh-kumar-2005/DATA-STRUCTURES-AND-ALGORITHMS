package Dynamic_Programming;

public class Minimum_Insertion_Steps_To_Make_A_String_Palindrome {


    private int recursive(int left, int right, String str1, String str2) {
        if (left < 0 || right < 0) {
            return 0;
        }

        if (str1.charAt(left) == str2.charAt(right)) {
            return 1 + recursive(left - 1, right - 1, str1, str2);
        }

        return Math.max(recursive(left - 1, right, str1, str2), recursive(left, right - 1, str1, str2));
    }

    private int memoization(int left, int right, String str1, String str2, int[][] dp) {
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

    private int tabulation(String str1, String str2) {

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

    private int ultimateSpaceOptimization(String str1, String str2) {

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


    public int minInsertions(String s) {
        String rev = new StringBuilder(s).reverse().toString();

        System.out.println("\n--- Calling All Approaches ---\n");

        // Approach 1: Recursive (0-based indexing)
        System.out.println("Approach 1: Recursive");
        System.out.println("  Calling: recursive(0, 0, \"" + s + "\", \"" + rev + "\")");
        int lps_recursive = recursive(s.length() - 1, rev.length() - 1, s, rev);
        System.out.println("  LPS Length: " + lps_recursive);
        System.out.println("  Time: O(2^(m+n)), Space: O(m+n) recursion stack\n");

        // Approach 2: Memoization (1-based indexing for DP array)
        System.out.println("Approach 2: Memoization");
        System.out.println("  Calling: memoization(1, 1, \"" + s + "\", \"" + rev + "\", dp)");
        int[][] dp_memo = new int[s.length() + 1][rev.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j <= rev.length(); j++) {
                dp_memo[i][j] = - 1;
            }
        }
        int lps_memo = memoization(1, 1, s, rev, dp_memo);
        System.out.println("  LPS Length: " + lps_memo);
        System.out.println("  Time: O(m×n), Space: O(m×n) for memo table\n");

        // Approach 3: Tabulation (1-based indexing)
        System.out.println("Approach 3: Tabulation (2D DP)");
        System.out.println("  Building DP table for LCS");
        int lps_tab = tabulation(s, rev);
        System.out.println("  LPS Length: " + lps_tab);
        System.out.println("  Time: O(m×n), Space: O(m×n) for DP table\n");

        // Approach 4: Space Optimized (1-based indexing)
        System.out.println("Approach 4: Space Optimized (1D arrays)");
        System.out.println("  Using only prev and curr arrays");
        int lps_opt = ultimateSpaceOptimization(s, rev);
        System.out.println("  LPS Length: " + lps_opt);
        System.out.println("  Time: O(m×n), Space: O(n) optimized\n");

        // Verify all approaches give same result
        System.out.println("--- Verification ---");
        System.out.println("Recursive: " + lps_recursive);
        System.out.println("Memoization: " + lps_memo);
        System.out.println("Tabulation: " + lps_tab);
        System.out.println("Space Optimized: " + lps_opt);

        if (lps_recursive == lps_memo && lps_memo == lps_tab && lps_tab == lps_opt) {
            System.out.println("✓ All approaches match!\n");
        } else {
            System.out.println("✗ Approaches differ!\n");
        }

        // Calculate and return result
        int min_insertions = s.length() - lps_opt;
        System.out.println("--- Final Calculation ---");
        System.out.println("String length: " + s.length());
        System.out.println("LPS length: " + lps_opt);
        System.out.println("Minimum insertions: " + s.length() + " - " + lps_opt + " = " + min_insertions + "\n");

        return min_insertions;
    }

    public static void main(String[] args) {
        
        Minimum_Insertion_Steps_To_Make_A_String_Palindrome solver = new Minimum_Insertion_Steps_To_Make_A_String_Palindrome();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  MINIMUM INSERTION STEPS TO MAKE A STRING PALINDROME         ║");
        System.out.println("║  Find minimum insertions needed using LCS approach           ║");
        System.out.println("║  Formula: insertions = length - LPS(string)                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: \"zzab\" ===");
        String s1 = "zzab";
        System.out.println("String: \"" + s1 + "\"");
        System.out.println("Reversed: \"" + reverseString(s1) + "\"");
        System.out.println("\nLCS(\"zzab\", \"bazz\") = \"zz\" (length 2)");
        System.out.println("Insertions needed: 4 - 2 = 2");
        System.out.println("One solution: Insert 'b' at start, 'a' at end → \"bzaazb\"\n");

        int result1 = solver.minInsertions(s1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result1 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: \"abcda\" ===");
        String s2 = "abcda";
        System.out.println("String: \"" + s2 + "\"");
        System.out.println("Reversed: \"" + reverseString(s2) + "\"");
        System.out.println("\nLCS(\"abcda\", \"adcba\") = \"aca\" (length 3)");
        System.out.println("Insertions needed: 5 - 3 = 2");
        System.out.println("One solution: Insert 'd' and 'b' → \"adcbacda\"\n");

        int result2 = solver.minInsertions(s2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result2 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: \"a\" (Single Character) ===");
        String s3 = "a";
        System.out.println("String: \"" + s3 + "\"");
        System.out.println("Already palindrome");
        System.out.println("\nLCS(\"a\", \"a\") = \"a\" (length 1)");
        System.out.println("Insertions needed: 1 - 1 = 0\n");

        int result3 = solver.minInsertions(s3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: \"racecar\" (Complete Palindrome) ===");
        String s4 = "racecar";
        System.out.println("String: \"" + s4 + "\"");
        System.out.println("Already complete palindrome");
        System.out.println("\nLCS(\"racecar\", \"racecar\") = \"racecar\" (length 7)");
        System.out.println("Insertions needed: 7 - 7 = 0\n");

        int result4 = solver.minInsertions(s4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result4 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: \"abcdef\" (No Common Substring) ===");
        String s5 = "abcdef";
        System.out.println("String: \"" + s5 + "\"");
        System.out.println("Reversed: \"" + reverseString(s5) + "\"");
        System.out.println("\nLCS(\"abcdef\", \"fedcba\") = single chars, max LPS = 1");
        System.out.println("Insertions needed: 6 - 1 = 5");
        System.out.println("Solution: Insert \"edcb\" → \"abcdcbafedcba\" is not right");
        System.out.println("Actually: Insert \"fedcb\" at start → \"fedcbabcdef\"\n");

        int result5 = solver.minInsertions(s5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result5 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: \"ab\" ===");
        String s6 = "ab";
        System.out.println("String: \"" + s6 + "\"");
        System.out.println("Reversed: \"" + reverseString(s6) + "\"");
        System.out.println("\nLCS(\"ab\", \"ba\") = \"a\" or \"b\" (length 1)");
        System.out.println("Insertions needed: 2 - 1 = 1");
        System.out.println("Solution: Insert 'b' at start → \"bab\"\n");

        int result6 = solver.minInsertions(s6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result6 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: \"abacabad\" ===");
        String s7 = "abacabad";
        System.out.println("String: \"" + s7 + "\"");
        System.out.println("Reversed: \"" + reverseString(s7) + "\"");
        System.out.println("\nLCS(\"abacabad\", \"dabaacba\") = \"abacaba\" (length 7)");
        System.out.println("Insertions needed: 8 - 7 = 1");
        System.out.println("Solution: Insert 'd' → \"abacabadaba\"\n");

        int result7 = solver.minInsertions(s7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result7 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 8: \"aa\" (Already Palindrome) ===");
        String s8 = "aa";
        System.out.println("String: \"" + s8 + "\"");
        System.out.println("Already palindrome");
        System.out.println("\nLCS(\"aa\", \"aa\") = \"aa\" (length 2)");
        System.out.println("Insertions needed: 2 - 2 = 0\n");

        int result8 = solver.minInsertions(s8);
        System.out.println("✓ Result: " + result8);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result8 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find minimum insertions to make string palindrome  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Reduction to LCS Problem                       ║");
        System.out.println("║    LPS (Longest Palindromic Subsequence) =                   ║");
        System.out.println("║    LCS(string, reverse(string))                              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Why This Works:                                             ║");
        System.out.println("║    • Palindrome reads same forwards and backwards            ║");
        System.out.println("║    • Characters in common between string and its reverse     ║");
        System.out.println("║      form palindromic pattern                                ║");
        System.out.println("║    • Characters NOT in LPS must be inserted                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Formula:                                                    ║");
        System.out.println("║    min_insertions = string_length - LPS_length               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Derivation:                                                 ║");
        System.out.println("║    • LPS = longest palindromic subsequence                   ║");
        System.out.println("║    • Characters in LPS already form palindrome               ║");
        System.out.println("║    • Remaining chars need insertions to balance them         ║");
        System.out.println("║    • Each non-LPS char needs matching char inserted          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: \"zzab\"                                             ║");
        System.out.println("║    String: \"zzab\"                                            ║");
        System.out.println("║    Reverse: \"bazz\"                                           ║");
        System.out.println("║    LCS (LPS): \"zz\" (length 2)                                ║");
        System.out.println("║    Chars not in LPS: \"a\", \"b\" (need insertion)               ║");
        System.out.println("║    Insertions = 4 - 2 = 2                                    ║");
        System.out.println("║    Result: \"bzaazb\" (insert 'b' and 'a')                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Algorithm Steps:                                            ║");
        System.out.println("║    1. Create reversed string: rev = reverse(s)               ║");
        System.out.println("║    2. Find LCS(s, rev) using DP                              ║");
        System.out.println("║       dp[i][j] = LCS length using s[0..i-1] & rev[0..j-1]    ║");
        System.out.println("║    3. Return s.length() - LCS_length                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  DP Recurrence:                                              ║");
        System.out.println("║    If s[i-1] == rev[j-1]:                                    ║");
        System.out.println("║      dp[i][j] = 1 + dp[i-1][j-1]                             ║");
        System.out.println("║    Else:                                                     ║");
        System.out.println("║      dp[i][j] = max(dp[i-1][j], dp[i][j-1])                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recursive vs Memoization vs Tabulation:                     ║");
        System.out.println("║    Recursive: Simple logic, exponential time O(2^(m+n))      ║");
        System.out.println("║    Memoization: Top-down DP, O(m×n) with O(m×n) space        ║");
        System.out.println("║    Tabulation: Bottom-up DP, O(m×n) with O(m×n) space        ║");
        System.out.println("║    Space Optimized: O(m×n) time, O(n) space (1D arrays)      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n²) where n = string length              ║");
        System.out.println("║  Space Complexity:                                           ║");
        System.out.println("║    Tabulation: O(n²)                                         ║");
        System.out.println("║    Space Optimized: O(n)                                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Edge Cases:                                                 ║");
        System.out.println("║    • Single character: Already palindrome → 0 insertions     ║");
        System.out.println("║    • Already palindrome: LPS = length → 0 insertions         ║");
        System.out.println("║    • All different chars: LPS = 1 → length-1 insertions      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

    private static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

}
