package Dynamic_Programming;

/*
    Description:
      Following program determines if a text string fully matches a wildcard pattern
        where '?' matches any single character and '*' matches any sequence including empty...

    Problem Statement:
      -> Given a text string and a pattern string...
      -> Pattern can contain:
           Literal characters: match exactly that character...
           '?' wildcard: matches any single character exactly once...
           '*' wildcard: matches any sequence of characters including empty string...
      -> Return true if the entire text matches the entire pattern, false otherwise...

    Key Insight:
      -> Literal and '?' are straightforward: consume one character from each...
      -> '*' is the complex case: it can match zero characters (skip it) or one+ characters...
           Option 1: '*' matches empty → move pattern forward (i stays, j-1)...
           Option 2: '*' absorbs current text char → move text forward (i-1, j stays)...
      -> Both options are explored; if either leads to a match, return true...

    Example:
      -> text = "adceb", pattern = "*a*b":
           First '*' matches empty → 'a' matches 'a'...
           Second '*' absorbs "dce" → 'b' matches final 'b' → true...
      -> text = "cb", pattern = "?a":
           '?' matches 'c' (any single char)...
           'a' != 'b' → false...
      -> text = "", pattern = "***":
           Each '*' matches empty sequence → true...
      -> text = "", pattern = "*a*":
           Stars can match empty but 'a' requires a character → false...

    Recursive Relation (0-indexed):
      -> Base cases:
           i < 0 && j < 0 → true (both exhausted simultaneously)...
           i >= 0 && j < 0 → false (text remains but pattern exhausted)...
           i < 0 && j >= 0 → true only if remaining pattern is all '*' characters...
      -> If text[i] == pattern[j] OR pattern[j] == '?':
           Match one character: recursive(i-1, j-1)...
      -> If pattern[j] == '*':
           recursive(i-1, j) OR recursive(i, j-1)...
           First: '*' absorbs text[i], stay on same '*'...
           Second: '*' matches empty, move to previous pattern char...
      -> Else: characters differ, no wildcard → return false...

    allStars() Helper:
      -> Called when text is exhausted but pattern has characters remaining...
      -> Checks if all remaining pattern characters (1 to j) are '*'...
      -> Only all-star remaining patterns can match an empty text...

    Approach 1 - Recursive (0-indexed):
      -> Pure top-down recursion without caching...
      -> Exponential branches due to '*' creating two recursive paths...
      -> Useful for establishing correct recurrence structure...
      -> Time: O(2^(n+m)), Space: O(n+m) recursion stack...

    Approach 2 - Memoization (Top-Down DP, 1-indexed):
      -> 2D dp array of size (n+1) × (m+1), values: -1 (unvisited), 0 (false), 1 (true)...
      -> Base: (0,0)=true, (i>0,0)=false, (0,j>0)=allStars(j)...
      -> Converts boolean result to int (1/0) for storage...
      -> Eliminates overlapping subproblem recomputation...
      -> Time: O(n×m), Space: O(n×m) + O(n+m) recursion stack...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[i][j] = true if text[0..i-1] matches pattern[0..j-1]...

         Initialization:
           dp[0][0] = true (both empty → match)...
           dp[i][0] = false for i > 0 (non-empty text, empty pattern)...
           dp[0][j] = dp[0][j-1] if pattern[j-1]=='*', else false...
             (empty text matched only by consecutive leading '*' chars)...

         Transition (i from 1 to n, j from 1 to m):
           Match or '?': dp[i][j] = dp[i-1][j-1]...
           '*': dp[i][j] = dp[i-1][j] || dp[i][j-1]...
           Else: dp[i][j] = false...

         Answer: dp[n][m]...

    Tabulation Table Visualization (text = "adceb", pattern = "*a*b"):

         \    ""   *   a   *   b
         ""   T    T   F   F   F
         a    F    T   T   T   F
         d    F    T   F   T   F
         c    F    T   F   T   F
         e    F    T   F   T   F
         b    F    T   F   T   T

         Answer: dp[5][4] = true...

    Approach 4 - Space Optimization (Two 1D Arrays):
      -> Replaces 2D table with prev[] and curr[] arrays of size m+1...
      -> prev[] initialized as first row: prev[0]=true, prev[j]=prev[j-1] if '*' else false...
      -> For each text row i: curr[0] = false (non-empty text, empty pattern)...
      -> On match/'?': curr[j] = prev[j-1]...
      -> On '*': curr[j] = prev[j] || curr[j-1]...
      -> On literal mismatch: curr[j] = false...
      -> After each row: prev = curr...
      -> Space reduced from O(n×m) to O(m)...

    '*' Transition in Both Tabulation and Optimization:
      -> dp[i-1][j] (prev[j]): '*' absorbs text[i] → check if text[0..i-2] matched so far...
      -> dp[i][j-1] (curr[j-1]): '*' matches empty → check if text[0..i-1] matched shorter pattern...
      -> Either path leading to true makes dp[i][j] true...

    Edge Cases:
      -> Empty text, empty pattern → true...
      -> Empty text, all-star pattern → true...
      -> Empty text, pattern with literals → false...
      -> Pattern longer than text with only '?' → false if lengths differ...
      -> Single '*' pattern → matches any text including empty...

    Comparison of All Approaches:
      -> Recursive:   Time O(2^(n+m)), Space O(n+m)  — exponential...
      -> Memoization: Time O(n×m),     Space O(n×m)  — top-down caching...
      -> Tabulation:  Time O(n×m),     Space O(n×m)  — iterative table...
      -> Optimized:   Time O(n×m),     Space O(m)    — rolling array...

    Time and Space Complexity:
      -> Time:  O(n × m) for all DP approaches...
      -> Space: O(n × m) for tabulation, O(m) for space-optimized...

    Applications:
      -> Shell glob pattern matching (*.txt, file?.log)...
      -> File system search with wildcard filters...
      -> SQL LIKE queries with % and _ wildcards...
      -> Text editor find-and-replace with wildcard support...
      -> Compiler tokenizer pattern recognition...
*/

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

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult || 
                                        tabulationResult != ultimateSpaceOptimizationResult) {
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
