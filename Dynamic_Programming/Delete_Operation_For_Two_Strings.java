package Dynamic_Programming;

/*

    Description:
      Following program finds the minimum number of character deletions required
        to make two strings equal, by reducing the problem to finding the LCS...

    Problem Statement:
      -> Given two strings str1 and str2...
      -> Delete characters from either string (at any position) to make them identical...
      -> Each deletion removes exactly one character...
      -> Return the minimum total number of deletions from both strings combined...

    Core Reduction Insight:
      -> The characters that should remain in both strings form their LCS...
      -> LCS characters are already common to both and need no deletion...
      -> Every character outside the LCS must be deleted from its respective string...
      -> Deletions from str1 = n - LCS, Deletions from str2 = m - LCS...
      -> Total minimum deletions = (n - LCS) + (m - LCS) = n + m - 2 × LCS...

    Why the Formula Works:
      -> LCS(str1, str2) is the longest sequence common to both in order...
      -> Keeping LCS characters and deleting the rest makes both strings identical...
      -> No smaller set of remaining characters can be common to both (by definition of LCS)...
      -> Any other choice of characters to keep would require more deletions...

    Example:
      -> str1 = "sea", str2 = "eat":
           LCS("sea", "eat") = "ea" (length 2)...
           Delete 's' from "sea" → "ea"...
           Delete 't' from "eat" → "ea"...
           Total deletions = 1 + 1 = 2...
           Formula: 3 + 3 - 2×2 = 2 ✓...
      -> str1 = "abc", str2 = "def":
           LCS = "" (length 0)...
           Delete all 3 from str1, all 3 from str2 → both become ""...
           Formula: 3 + 3 - 2×0 = 6...
      -> str1 = "hello", str2 = "hello" (identical):
           LCS = "hello" (length 5)...
           Formula: 5 + 5 - 2×5 = 0 → no deletions needed...

    Recurrence Relation (Standard LCS):
      -> If str1[i-1] == str2[j-1]:
           dp[i][j] = 1 + dp[i-1][j-1]...
      -> Else:
           dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])...
      -> Final answer: n + m - 2 × dp[n][m]...

    Approach 1 - Recursive:
      -> Pure top-down without caching...
      -> Exponential time due to overlapping subproblems...
      -> Time: O(2^(n+m)), Space: O(n+m) recursion stack...

    Approach 2 - Memoization (Top-Down DP):
      -> 2D dp array of size (n+1) × (m+1), initialized to -1...
      -> Caches each (left, right) result to avoid recomputation...
      -> Time: O(n×m), Space: O(n×m) + O(n+m) recursion stack...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> Builds dp table iteratively from (1,1) to (n,m)...
      -> dp[i][j] = LCS length of str1[0..i-1] and str2[0..j-1]...
      -> Final answer: n + m - 2 × dp[n][m]...
      -> Time: O(n×m), Space: O(n×m)...

    Approach 4 - Space Optimized (Two 1D Arrays):
      -> Replaces 2D dp with prev[] and curr[] of size m+1...
      -> On match: curr[j] = 1 + prev[j-1]...
      -> On mismatch: curr[j] = Math.max(prev[j], curr[j-1])...
      -> After each row: prev = curr...
      -> Final answer: n + m - 2 × prev[m]...
      -> Time: O(n×m), Space: O(m)...

    DP Table Visualization (str1 = "sea", str2 = "eat"):

         \    ""   e   a   t
         ""    0   0   0   0
         s     0   0   0   0
         e     0   1   1   1
         a     0   1   2   2

         LCS = dp[3][3] = 2...
         Deletions = 3 + 3 - 2×2 = 2...

    Comparison With Related Problems:
      -> Minimum Insertions to make palindrome: n - LPS(s)...
      -> Minimum Deletions to make palindrome: n - LPS(s)...
      -> Minimum Deletions to make two strings equal: n + m - 2×LCS(s1, s2)...
      -> All three reduce to LCS or LPS computation...

    Edge Cases:
      -> Identical strings → LCS = n = m → deletions = 0...
      -> No common characters → LCS = 0 → deletions = n + m (delete everything)...
      -> One empty string → LCS = 0 → deletions = length of the non-empty string...
      -> Single character strings, both equal → LCS = 1 → deletions = 0...
      -> Single character strings, both different → LCS = 0 → deletions = 2...

    Comparison of All Approaches:
      -> Recursive:   Time O(2^(n+m)), Space O(n+m) — exponential, unusable for large input...
      -> Memoization: Time O(n×m),     Space O(n×m) — fast, top-down with stack...
      -> Tabulation:  Time O(n×m),     Space O(n×m) — iterative, no stack overhead...
      -> Optimized:   Time O(n×m),     Space O(m)   — best space efficiency...

    Time and Space Complexity:
      -> Time:  O(n × m) for all DP approaches...
      -> Space: O(n × m) for tabulation, O(m) for space-optimized version...

    Applications:
      -> Sequence alignment in DNA or protein comparison (bioinformatics)...
      -> Minimum edit distance for spell-checking and autocorrect systems...
      -> Version control diff tools showing minimum changes between file versions...
      -> Natural language processing for text normalization and deduplication...

*/

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
