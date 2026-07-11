package Dynamic_Programming;

/*
    Description:
      Following program computes the Edit Distance (Levenshtein Distance) between
        two strings — the minimum number of single-character operations to transform
        word1 into word2...

    Problem Statement:
      -> Given two strings word1 and word2...
      -> Three operations are allowed, each with a cost of 1:
           Insert: add one character at any position...
           Delete: remove one character from any position...
           Replace: change one character to any other character...
      -> Return the minimum total operations needed to transform word1 into word2...

    Key Insight:
      -> At each pair of indices (i, j), compare word1[i] and word2[j]...
      -> If characters match: no operation needed → inherit cost from dp[i-1][j-1]...
      -> If characters differ: choose the cheapest of three operations:
           Insert (add a char to word1 to match word2[j]): 1 + dp[i][j-1]...
           Delete (remove word1[i] and try matching again): 1 + dp[i-1][j]...
           Replace (change word1[i] to word2[j]): 1 + dp[i-1][j-1]...
      -> Take the minimum of all three options...

    Example:
      -> word1 = "horse", word2 = "ros":
           horse → rorse (replace 'h' with 'r')...
           rorse → rose  (delete 'r')...
           rose  → ros   (delete 'e')...
           Minimum operations = 3...
      -> word1 = "abc", word2 = "abc":
           Identical → no operations needed → distance = 0...
      -> word1 = "kitten", word2 = "sitting":
           k→s (replace), e→i (replace), →g (insert) → distance = 3...

    Three Operations Explained:
      -> Insert (dp[i][j-1] + 1):
           We already matched word1[0..i] to word2[0..j-1]...
           Insert the character word2[j] at the end → one more operation...
      -> Delete (dp[i-1][j] + 1):
           Delete word1[i] from word1 → now match word1[0..i-1] to word2[0..j]...
      -> Replace (dp[i-1][j-1] + 1):
           Replace word1[i] with word2[j] → match word1[0..i-1] to word2[0..j-1]...

    Base Cases:
      -> dp[i][0] = i: converting word1[0..i-1] to empty string requires i deletions...
      -> dp[0][j] = j: converting empty string to word2[0..j-1] requires j insertions...

    Recursive Relation:
      -> If word1[i-1] == word2[j-1]:
           dp[i][j] = dp[i-1][j-1] (no cost, characters already match)...
      -> Else:
           dp[i][j] = 1 + Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1]))...
                                    insert          delete           replace...

    Approach 1 - Recursive (0-indexed):
      -> Uses 0-based indices, base cases when left==0 or right==0...
      -> Base: left==0 → return right+1 (insert right+1 chars)...
      -> Base: right==0 → return left+1 (delete left+1 chars)...
      -> Exponential recomputation of overlapping subproblems...
      -> Time: O(3^(n+m)), Space: O(n+m) recursion stack...

    Approach 2 - Memoization (Top-Down DP, 1-indexed):
      -> 1-indexed: base left==0 → return right, base right==0 → return left...
      -> 2D dp array of size (n+1) × (m+1), initialized to -1...
      -> Caches result of each (left, right) pair...
      -> On match: dp[i][j] = memo(i-1, j-1)...
      -> On mismatch: dp[i][j] = 1 + min(insert, delete, replace)...
      -> Time: O(n×m), Space: O(n×m) + O(n+m) recursion stack...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[i][j] = min operations to convert word1[0..i-1] to word2[0..j-1]...
      -> Initialize dp[i][0] = i, dp[0][j] = j...
      -> Transition (i from 1 to n, j from 1 to m):
           Match: dp[i][j] = dp[i-1][j-1]...
           Mismatch: dp[i][j] = 1 + min(dp[i][j-1], dp[i-1][j], dp[i-1][j-1])...
      -> Answer: dp[n][m]...

    Tabulation Table Visualization (word1 = "horse", word2 = "ros"):

         \    ""   r   o   s
         ""    0   1   2   3
         h     1   1   2   3
         o     2   2   1   2
         r     3   2   2   2
         s     4   3   3   2
         e     5   4   4   3

         Answer: dp[5][3] = 3...

    Approach 4 - Space Optimization (Two 1D Arrays):
      -> Each row dp[i] depends on dp[i-1] → use prev[] and curr[]...
      -> Initialize prev[j] = j for all j (base case row 0)...
      -> For each row i: curr[0] = i (base case column 0)...
      -> On match: curr[j] = prev[j-1]...
      -> On mismatch: curr[j] = 1 + min(curr[j-1], prev[j], prev[j-1])...
                                         insert      delete  replace...
      -> After each row: prev = curr...
      -> Space reduced from O(n×m) to O(m)...

    Mapping Operations to DP Transitions:
      -> curr[j-1]  (left in same row)  → insert: already handled word2[0..j-1] for same word1 prefix...
      -> prev[j]    (above in prev row) → delete: skip word1[i-1] and match remaining...
      -> prev[j-1]  (diagonal in prev)  → replace: substitute word1[i-1] with word2[j-1]...

    Edge Cases:
      -> Identical strings → all diagonal moves, no operations → distance = 0...
      -> One empty string → distance = length of the other string (all inserts or deletes)...
      -> Single character strings, different → distance = 1 (one replace)...
      -> One string is substring of other → minimum deletions or insertions only...

    Comparison of All Approaches:
      -> Recursive:   Time O(3^(n+m)), Space O(n+m)  — exponential, impractical...
      -> Memoization: Time O(n×m),     Space O(n×m)  — top-down with caching...
      -> Tabulation:  Time O(n×m),     Space O(n×m)  — iterative, no stack...
      -> Optimized:   Time O(n×m),     Space O(m)    — best space efficiency...

    Time and Space Complexity:
      -> Time:  O(n × m) for all DP approaches...
      -> Space: O(n × m) for tabulation, O(m) for space-optimized version...

    Applications:
      -> Spell checkers and autocorrect systems (closest word suggestions)...
      -> DNA sequence alignment in bioinformatics (mutation analysis)...
      -> Plagiarism detection measuring string similarity...
      -> Natural language processing for fuzzy string matching...
      -> Version control systems computing file diff operations...

*/

import java.util.Arrays;

public class Edit_Distance {

    private static int recursive(int left, int right, String word1, String word2) {
        if (left == 0) {
            return right + 1;
        }

        if (right == 0) {
            return left + 1;
        }

        if (word1.charAt(left) == word2.charAt(right)) {
            return recursive(left - 1, right - 1, word1, word2);
        }

        return 1 + Math.min(recursive(left - 1, right, word1, word2), Math.min(recursive(left, right - 1, word1, word2), recursive(left - 1, right - 1, word1, word2)));
    }

    private static int memoization(int left, int right, String word1, String word2, int[][] dp) {
        if (left == 0) {
            return right;
        }

        if (right == 0) {
            return left;
        }

        if (dp[left][right] != - 1) {
            return dp[left][right];
        }

        if (word1.charAt(left - 1) == word2.charAt(right - 1)) {
            return dp[left][right] = memoization(left - 1, right - 1, word1, word2, dp);
        }

        // If not equal, consider insert, delete, replace
        int insert = memoization(left, right - 1, word1, word2, dp);
        int delete = memoization(left - 1, right, word1, word2, dp);
        int replace = memoization(left - 1, right - 1, word1, word2, dp);

        return dp[left][right] = 1 + Math.min(insert, Math.min(delete, replace));
    }

    private static int tabulation(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int left = 1; left <= n; left++) {
            for (int right = 1; right <= m; right++) {
                if (word1.charAt(left - 1) == word2.charAt(right - 1)) {
                    dp[left][right] = dp[left - 1][right - 1];
                } else {
                    int insert = dp[left][right - 1];
                    int delete = dp[left - 1][right];
                    int replace = dp[left - 1][right - 1];

                    dp[left][right] = 1 + Math.min(insert, Math.min(delete, replace));
                }
            }
        }

        return dp[n][m];

    }

    private static int ultimateSpaceOptimization(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[] prev = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            prev[j] = j;
        }

        for (int left = 1; left <= n; left++) {
            int[] curr = new int[m + 1];
            curr[0] = left;
            for (int right = 1; right <= m; right++) {
                if (word1.charAt(left - 1) == word2.charAt(right - 1)) {
                    curr[right] = prev[right - 1];
                } else {
                    int insert = curr[right - 1];
                    int delete = prev[right];
                    int replace = prev[right - 1];

                    curr[right] = 1 + Math.min(insert, Math.min(delete, replace));
                }
            }
            prev = curr;
        }

        return prev[m];

    }


    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    EDIT DISTANCE (LEVENSHTEIN)               ║");
        System.out.println("║  Minimum edits: insert, delete, replace to transform s1→s2   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        testCase("horse", "ros", 3);
        testCase("intention", "execution", 5);
        testCase("abc", "abc", 0);
        testCase("a", "", 1);
        testCase("kitten", "sitting", 3);
        testCase("saturday", "sunday", 3);

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY INSIGHT: dp[i][j] = min edits for word1[0..i-1]→word2   ║");
        System.out.println("║  Recurrence:                                                 ║");
        System.out.println("║    Match: dp[i][j] = dp[i-1][j-1]                            ║");
        System.out.println("║    No match: dp[i][j] = 1 + min(                             ║");
        System.out.println("║      dp[i][j-1] insert,  dp[i-1][j] delete,                  ║");
        System.out.println("║      dp[i-1][j-1] replace)                                   ║");
        System.out.println("║  Time: O(m×n), Space: O(n) optimized                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
    }

    private static void testCase(String w1, String w2, int expected) {
        
        int result = minDistance(w1, w2);
        System.out.println("\"" + w1 + "\" → \"" + w2 + "\": " + result +
            (result == expected ? " ✓" : " ✗ (expected " + expected + ")"));
    }

    private static int minDistance(String word1, String word2) {
        
        int n = word1.length();
        int m = word2.length();

        System.out.println("\nRecursive: " + recursive(n - 1, m - 1, word1, word2));

        int[][] dp = new int[n + 1][m + 1];
        for (int[] temp : dp) Arrays.fill(temp, - 1);
        System.out.println("Memoization: " + memoization(n, m, word1, word2, dp));
        
        System.out.println("Tabulation: " + tabulation(word1, word2));
        System.out.println("Optimized: " + ultimateSpaceOptimization(word1, word2));

        return ultimateSpaceOptimization(word1, word2);
    }

}
