package Dynamic_Programming;

/*

    Description:
      Following program counts the number of distinct subsequences of string s
        that exactly equal string t, using five approaches from recursion to 1D DP...

    Problem Statement:
      -> Given two strings s and t...
      -> Count the number of distinct ways to select characters from s (in order)
           such that the selected subsequence equals t exactly...
      -> Different selections using different index combinations count separately...
      -> Return the total count of such distinct subsequences...

    Key Insight:
      -> At each character s[i], decide whether to use it to match t[j] or skip it...
      -> If s[i] == t[j]: two choices exist — use this match or skip s[i]...
           Use it: both pointers move (i-1, j-1)...
           Skip it: only s pointer moves (i-1, j unchanged)...
           Add both counts together...
      -> If s[i] != t[j]: only choice is to skip s[i] → (i-1, j unchanged)...
      -> This is a counting problem, not an optimization problem → use addition, not max...

    Example:
      -> s = "rabbbit", t = "rabbit":
           Three 'b's in s, but t needs only two → one 'b' can be skipped...
           Three ways to choose which 'b' to exclude → count = 3...
      -> s = "babgbag", t = "bag":
           Multiple positions for 'b', 'a', 'g' → count = 5...
      -> s = "a", t = "" (empty):
           Empty string is a subsequence of any string in exactly one way → count = 1...

    Recursive Relation:
      -> Base case 1: j < 0 (t fully matched) → return 1 (one valid way found)...
      -> Base case 2: i < 0 (s exhausted before t) → return 0 (impossible)...
      -> If s[i] == t[j]:
           count(i, j) = count(i-1, j-1) + count(i-1, j)...
           First term: use s[i] to match t[j]...
           Second term: skip s[i] and try to match t[j] with earlier chars...
      -> If s[i] != t[j]:
           count(i, j) = count(i-1, j)...
           Only option: skip s[i]...

    Base Case Initialization (Tabulation):
      -> dp[i][0] = 1 for all i: empty t has exactly one subsequence in any prefix of s...
      -> dp[0][j] = 0 for j > 0: non-empty t cannot be matched from empty s...

    Approach 1 - Recursive:
      -> Pure recursion with 0-based indices (i, j) starting at (n-1, m-1)...
      -> No caching → recomputes overlapping subproblems repeatedly...
      -> Time: O(2^(n+m)), Space: O(n+m) recursion stack...

    Approach 2 - Memoization (Top-Down DP):
      -> 1-indexed variant: (i, j) starting at (n, m)...
      -> 2D dp array of size (n+1) × (m+1), initialized to -1...
      -> Check dp[i][j] != -1 before recursing...
      -> On match: dp[i][j] = memo(i-1, j-1) + memo(i-1, j)...
      -> On mismatch: dp[i][j] = memo(i-1, j)...
      -> Time: O(n×m), Space: O(n×m) + O(n+m) recursion stack...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[i][j] = number of distinct subsequences of s[0..i-1] that equal t[0..j-1]...
      -> Initialize dp[i][0] = 1, dp[0][j] = 0 for j > 0...
      -> Transition (i from 1 to n, j from 1 to m):
           If s[i-1] == t[j-1]: dp[i][j] = dp[i-1][j-1] + dp[i-1][j]...
           Else: dp[i][j] = dp[i-1][j]...
      -> Answer: dp[n][m]...

    Tabulation Table Visualization (s = "rabbbit", t = "rabbit"):

         \    ""   r   a   b   b   i   t
         ""    1   0   0   0   0   0   0
         r     1   1   0   0   0   0   0
         a     1   1   1   0   0   0   0
         b     1   1   1   1   0   0   0
         b     1   1   1   2   1   0   0
         b     1   1   1   3   3   0   0
         i     1   1   1   3   3   3   0
         t     1   1   1   3   3   3   3

         Answer: dp[7][6] = 3...

    Approach 4 - Space Optimization (Two 1D Arrays):
      -> Each row dp[i] depends only on dp[i-1] → use prev[] and curr[]...
      -> prev[0] = 1 always (empty t case)...
      -> curr[0] = 1 always...
      -> On match: curr[j] = prev[j-1] + prev[j]...
      -> On mismatch: curr[j] = prev[j]...
      -> After each row: prev = curr...
      -> Space reduced from O(n×m) to O(m)...

    Approach 5 - Ultimate Space Optimization (Single 1D Array):
      -> Uses only one array prev[] updated in-place...
      -> CRITICAL: Traverse j from RIGHT to LEFT (j = m down to 1)...
      -> On match: prev[j] = prev[j-1] + prev[j] (prev[j-1] not yet overwritten)...
      -> On mismatch: prev[j] unchanged (no update needed)...
      -> Right-to-left ensures prev[j-1] still holds the value from the previous row...
      -> Space: O(m)...

    Why Right-to-Left for 1D Optimization:
      -> On match, update uses prev[j-1] (diagonal from previous row)...
      -> Left-to-right would overwrite prev[j-1] before it's used by prev[j]...
      -> Right-to-left preserves prev[j-1] value until after prev[j] is updated...
      -> This is the same reasoning as 0/1 Knapsack 1D optimization...

    Edge Cases:
      -> t is empty string → dp[i][0] = 1 → exactly one way (choose nothing)...
      -> s is empty, t is not → dp[0][j] = 0 → no way to match...
      -> s == t → exactly one way (use every character of s in order)...
      -> No common characters → all mismatch branches → count = 0...
      -> s shorter than t → impossible → count = 0...

    Comparison of All Approaches:
      -> Recursive:          Time O(2^(n+m)), Space O(n+m)  — exponential...
      -> Memoization:        Time O(n×m),     Space O(n×m)  — top-down...
      -> Tabulation:         Time O(n×m),     Space O(n×m)  — iterative...
      -> Two Array Optimized:Time O(n×m),     Space O(m)    — reduced space...
      -> One Array Optimized:Time O(n×m),     Space O(m)    — optimal space...

    Time and Space Complexity:
      -> Time:  O(n × m) for all DP approaches...
      -> Space: O(n × m) for tabulation, O(m) for space-optimized versions...

    Applications:
      -> Counting sequence patterns in DNA or protein databases...
      -> Number of ways to embed one string inside another in order...
      -> Combinatorial counting in string processing problems...
      -> Competitive programming pattern-matching counting problems...

*/

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
