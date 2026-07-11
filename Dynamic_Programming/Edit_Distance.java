package Dynamic_Programming;

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
