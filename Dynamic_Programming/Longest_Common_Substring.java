package Dynamic_Programming;

/*

    Description:
      Following program finds the length of the Longest Common Substring (LCP)
        between two strings, where matched characters must be consecutive in both...

    Problem Statement:
      -> Given two strings str1 and str2...
      -> A substring is a contiguous sequence of characters within a string...
      -> Find the length of the longest substring that appears in both strings...
      -> Characters must be consecutive in both strings (unlike subsequence)...
      -> Return 0 if no common substring exists...

    Key Difference From Longest Common Subsequence:
      -> LCS (Subsequence): characters can be non-consecutive, skip allowed...
           "abc" vs "axc" → LCS = "ac" (length 2)...
      -> LCP (Substring): characters must be consecutive, no skipping allowed...
           "abc" vs "axc" → LCP = "a" (length 1)...
      -> In LCS mismatch: dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])...
      -> In LCP mismatch: dp[i][j] = 0 (streak broken, reset to zero)...

    Key Insight:
      -> dp[i][j] represents the length of the common substring ending exactly
           at str1[i-1] and str2[j-1]...
      -> On match: extend the diagonal value by 1 (continue the streak)...
      -> On mismatch: reset to 0 (consecutive requirement broken)...
      -> Answer is the maximum dp[i][j] seen across the entire table...
      -> The answer is tracked globally, not just at dp[n][m]...

    Example:
      -> str1 = "abcde", str2 = "cde":
           dp[3][1]: c==c → 1...
           dp[4][2]: d==d → 2...
           dp[5][3]: e==e → 3...
           Longest common substring = "cde", length = 3...
      -> str1 = "programming", str2 = "gaming":
           "amin" appears in both (positions 7-10 and 1-4 respectively)...
           Length = 4...

    Recurrence Relation:
      -> If str1[i-1] == str2[j-1]:
           dp[i][j] = 1 + dp[i-1][j-1]...
           ans = Math.max(ans, dp[i][j])...
      -> Else:
           dp[i][j] = 0 (implicitly, via Java array initialization or no assignment)...

    Why dp[i][j] Represents "Ending At" Not "Up To":
      -> "Ending at" forces the substring to be contiguous...
      -> If a mismatch occurs, the current streak must end...
      -> A new streak can only start fresh from the next matching pair...
      -> The global maximum captures the longest streak ever achieved...

    Tabulation Table Visualization (str1 = "abcde", str2 = "cde"):

         \    ""   c   d   e
         ""    0   0   0   0
         a     0   0   0   0
         b     0   0   0   0
         c     0   1   0   0
         d     0   0   2   0
         e     0   0   0   3

         Maximum value in table = 3 → LCP = "cde"...

    Why Answer Is Not dp[n][m]:
      -> dp[n][m] only captures the substring ending at the last characters...
      -> Longest common substring may occur in the middle of both strings...
      -> Must track the global maximum across all cells during table construction...

    Space Optimization (Two 1D Arrays):
      -> Each cell dp[i][j] only depends on dp[i-1][j-1] (diagonal predecessor)...
      -> Two arrays prev[] and curr[] are sufficient...
      -> On match: curr[j] = 1 + prev[j-1]...
      -> On mismatch: curr[j] stays 0 (curr initialized to 0 each iteration)...
      -> After each row: assign prev = curr...
      -> Space reduced from O(n × m) to O(m)...

    Why No Further Optimization To Single Array?
      -> On match, curr[j] needs prev[j-1] (diagonally above-left)...
      -> If using a single array updated left-to-right, prev[j-1] gets overwritten...
      -> Two arrays preserve the diagonal value correctly before it is updated...

    Edge Cases:
      -> Identical strings → entire string is the common substring → return n...
      -> No common characters → all dp values remain 0 → return 0...
      -> Single character match → dp[i][j] = 1 at one cell → return 1...
      -> One string is a substring of the other → return length of shorter string...

    Comparison Summary: Substring vs Subsequence DP:
      -> LCS mismatch: dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])...
                       Tries skipping one character from either string...
      -> LCP mismatch: dp[i][j] = 0...
                       Breaks the current streak, no skipping allowed...
      -> LCS answer:   dp[n][m] always holds the result...
      -> LCP answer:   global maximum tracked during table fill, not dp[n][m]...

    Time and Space Complexity:
      -> Tabulation:
           Time:  O(n × m)...
           Space: O(n × m)...
      -> Space Optimized:
           Time:  O(n × m)...
           Space: O(m) — two arrays of size m+1...

    Applications:
      -> Plagiarism detection finding verbatim copied text segments...
      -> DNA sequence analysis identifying shared genetic regions...
      -> String matching in search engines and text editors...
      -> Finding common patterns in log files or code bases...
      -> Bioinformatics alignment of protein or nucleotide sequences...

*/

public class Longest_Common_Substring {

    private static int tabulation(String str1, String str2) {

        int n = str1.length();
        int m = str2.length();

        int[][] dp = new int[n + 1][m + 1];
        int ans = 0;

        for (int left = 1; left <= n; left++) {
            for (int right = 1; right <= m; right++) {
                if (str1.charAt(left - 1) == str2.charAt(right - 1)) {
                    dp[left][right] = 1 + dp[left - 1][right - 1];
                    ans = Math.max(ans, dp[left][right]);
                }
            }
        }

        return ans;
    }

    private static int spaceOptimized(String str1, String str2) {

        int n = str1.length();
        int m = str2.length();

        int[] prev = new int[m + 1];
        int ans = 0;

        for (int left = 1; left <= n; left++) {
            int[] curr = new int[m + 1];
            for (int right = 1; right <= m; right++) {
                if (str1.charAt(left - 1) == str2.charAt(right - 1)) {
                    curr[right] = 1 + prev[right - 1];
                    ans = Math.max(ans, curr[right]);
                }
            }
            prev = curr;
        }

        return ans;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║         LONGEST COMMON SUBSTRING (LCS)                       ║");
        System.out.println("║  Find longest substring present in both strings              ║");
        System.out.println("║  Characters MUST be consecutive (unlike subsequence)         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Basic Substring ===");
        String str1_1 = "abcde";
        String str2_1 = "cde";
        System.out.println("String 1: \"" + str1_1 + "\"");
        System.out.println("String 2: \"" + str2_1 + "\"");
        System.out.println("\nCommon substrings: \"c\", \"cd\", \"cde\"");
        System.out.println("Longest: \"cde\" (length 3)\n");

        int result1_tab = tabulation(str1_1, str2_1);
        int result1_opt = spaceOptimized(str1_1, str2_1);

        System.out.println("✓ Tabulation Result: " + result1_tab);
        System.out.println("✓ Space Optimized Result: " + result1_opt);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result1_tab == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: No Common Substring ===");
        String str1_2 = "abc";
        String str2_2 = "def";
        System.out.println("String 1: \"" + str1_2 + "\"");
        System.out.println("String 2: \"" + str2_2 + "\"");
        System.out.println("\nNo common characters");
        System.out.println("Longest substring: \"\" (length 0)\n");

        int result2_tab = tabulation(str1_2, str2_2);
        int result2_opt = spaceOptimized(str1_2, str2_2);

        System.out.println("✓ Tabulation Result: " + result2_tab);
        System.out.println("✓ Space Optimized Result: " + result2_opt);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result2_tab == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Identical Strings ===");
        String str1_3 = "hello";
        String str2_3 = "hello";
        System.out.println("String 1: \"" + str1_3 + "\"");
        System.out.println("String 2: \"" + str2_3 + "\" (identical)");
        System.out.println("\nEntire string is common substring");
        System.out.println("Longest: \"hello\" (length 5)\n");

        int result3_tab = tabulation(str1_3, str2_3);
        int result3_opt = spaceOptimized(str1_3, str2_3);

        System.out.println("✓ Tabulation Result: " + result3_tab);
        System.out.println("✓ Space Optimized Result: " + result3_opt);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result3_tab == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Multiple Common Substrings ===");
        String str1_4 = "oxcpqrsvwf";
        String str2_4 = "shmtulqrypy";
        System.out.println("String 1: \"" + str1_4 + "\"");
        System.out.println("String 2: \"" + str2_4 + "\"");
        System.out.println("\nCommon substrings: \"q\", \"r\", \"qr\"");
        System.out.println("Longest: \"qr\" (length 2)\n");

        int result4_tab = tabulation(str1_4, str2_4);
        int result4_opt = spaceOptimized(str1_4, str2_4);

        System.out.println("✓ Tabulation Result: " + result4_tab);
        System.out.println("✓ Space Optimized Result: " + result4_opt);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result4_tab == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Character Match ===");
        String str1_5 = "abc";
        String str2_5 = "dea";
        System.out.println("String 1: \"" + str1_5 + "\"");
        System.out.println("String 2: \"" + str2_5 + "\"");
        System.out.println("\nCommon substrings: \"a\"");
        System.out.println("Longest: \"a\" (length 1)\n");

        int result5_tab = tabulation(str1_5, str2_5);
        int result5_opt = spaceOptimized(str1_5, str2_5);

        System.out.println("✓ Tabulation Result: " + result5_tab);
        System.out.println("✓ Space Optimized Result: " + result5_opt);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result5_tab == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Substring at Different Positions ===");
        String str1_6 = "programming";
        String str2_6 = "gaming";
        System.out.println("String 1: \"" + str1_6 + "\"");
        System.out.println("String 2: \"" + str2_6 + "\"");
        System.out.println("\nCommon substrings: \"g\", \"a\", \"m\", \"i\", \"n\", \"gam\", \"amin\"");
        System.out.println("Longest: \"amin\" (length 4)\n");

        int result6_tab = tabulation(str1_6, str2_6);
        int result6_opt = spaceOptimized(str1_6, str2_6);

        System.out.println("✓ Tabulation Result: " + result6_tab);
        System.out.println("✓ Space Optimized Result: " + result6_opt);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result6_tab == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find longest substring in both strings             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Difference from LCS (Longest Common Subsequence):       ║");
        System.out.println("║   Subsequence: \"abc\" vs \"axc\" → LCS = \"ac\" (non-consecutive) ║");
        System.out.println("║   Substring: \"abc\" vs \"axc\" → LCS = \"a\" (must be consecutive)║");
        System.out.println("║                                                              ║");
        System.out.println("║  DP Table Definition:                                        ║");
        System.out.println("║    dp[i][j] = length of common substring ending at           ║");
        System.out.println("║              str1[i-1] and str2[j-1]                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recurrence Relation:                                        ║");
        System.out.println("║    If str1[i-1] == str2[j-1]:                                ║");
        System.out.println("║      dp[i][j] = 1 + dp[i-1][j-1]  (extend substring)         ║");
        System.out.println("║      ans = max(ans, dp[i][j])                                ║");
        System.out.println("║    Else:                                                     ║");
        System.out.println("║      dp[i][j] = 0  (no substring ending here)                ║");
        System.out.println("║      ^^^ KEY DIFFERENCE: Reset to 0, not max(...)            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Why Reset to 0?                                             ║");
        System.out.println("║    Substring requires CONSECUTIVE characters                 ║");
        System.out.println("║    If chars don't match, substring breaks                    ║");
        System.out.println("║    Can't skip a character and continue (unlike subsequence)  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: str1=\"oxcpqrsvwf\", str2=\"shmtulqrypy\"              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  DP Table (partial):                                         ║");
        System.out.println("║         \"\"  s  h  m  t  u  l  q  r  y  p  y                  ║");
        System.out.println("║    \"\"   0  0  0  0  0  0  0  0  0  0  0  0                   ║");
        System.out.println("║    o    0  0  0  0  0  0  0  0  0  0  0  0                   ║");
        System.out.println("║    x    0  0  0  0  0  0  0  0  0  0  0  0                   ║");
        System.out.println("║    c    0  0  0  0  0  0  0  0  0  0  0  0                   ║");
        System.out.println("║    p    0  0  0  0  0  0  0  0  0  0  1  1                   ║");
        System.out.println("║    q    0  0  0  0  0  0  0  1  0  0  0  0  (break here!)    ║");
        System.out.println("║    r    0  0  0  0  0  0  0  0  2  0  0  0  (new substring)  ║");
        System.out.println("║    s    0  1  0  0  0  0  0  0  0  0  0  0  (single char)    ║");
        System.out.println("║    v    0  0  0  0  0  0  0  0  0  0  0  0                   ║");
        System.out.println("║    w    0  0  0  0  0  0  0  0  0  0  0  0                   ║");
        System.out.println("║    f    0  0  0  0  0  0  0  0  0  0  0  0                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Result: dp[6][8] = 2 (substring \"qr\")                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Notice: When 'q' from str1 doesn't match 'r' from str2,     ║");
        System.out.println("║          dp resets to 0 (breaks consecutive requirement)     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Space Optimization:                                         ║");
        System.out.println("║    Only need previous row: prev[j-1] for dp[i-1][j-1]        ║");
        System.out.println("║    Use two arrays: prev and curr                             ║");
        System.out.println("║    When str1[i-1] == str2[j-1]:                              ║");
        System.out.println("║      curr[j] = 1 + prev[j-1]  (diagonal value)               ║");
        System.out.println("║    Else:                                                     ║");
        System.out.println("║      curr[j] = 0 (automatically, since curr initialized to 0)║");
        System.out.println("║                                                              ║");
        System.out.println("║  Complexity:                                                 ║");
        System.out.println("║    Time:  O(m × n) where m,n = string lengths                ║");
        System.out.println("║    Space: O(m × n) tabulation, O(n) optimized                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Comparison: Substring vs Subsequence                        ║");
        System.out.println("║    LCS:   dp[i][j] = max(dp[i-1][j], dp[i][j-1])             ║");
        System.out.println("║    LCP:   dp[i][j] = 0 when not matching                     ║");
        System.out.println("║    Key: Substring resets, Subsequence tries both directions  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
 
    }

}
