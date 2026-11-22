package Dynamic_Programming;

import java.util.Arrays;

public class Count_Square_Submatrices_With_All_Ones {

    private static int countSquares(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        int[][] ans = new int[n][m];
        int sum = 0;

        for (int i = 0; i < n; i++) {
            ans[i][0] = matrix[i][0];
            sum += ans[i][0];
        }

        for (int j = 1; j < m; j++) {
            ans[0][j] = matrix[0][j];
            sum += ans[0][j];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] == 1) {
                    ans[i][j] = 1 + Math.min(ans[i - 1][j - 1],
                        Math.min(ans[i - 1][j], ans[i][j - 1]));
                    sum += ans[i][j];
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║          COUNT SQUARE SUBMATRICES WITH ALL ONES              ║");
        System.out.println("║  Count every square submatrix (of any size) that contains    ║");
        System.out.println("║  only 1's                                                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[][] matrix1 = {
            {0, 1, 1, 1},
            {1, 1, 1, 1},
            {0, 1, 1, 1}
        };
        System.out.println("Matrix: " + Arrays.deepToString(matrix1));
        System.out.println("\n10 squares of size 1, 4 of size 2, 1 of size 3? No: 3x3 impossible (3 rows, col 0 has 0s)");
        System.out.println("10 squares of size 1 + 4 squares of size 2 = 14\n");

        int result1 = countSquares(matrix1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 15");
        System.out.println("  Status: " + (result1 == 15 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Mixed Matrix ===");
        int[][] matrix2 = {
            {1, 0, 1},
            {1, 1, 0},
            {1, 1, 0}
        };
        System.out.println("Matrix: " + Arrays.deepToString(matrix2));
        System.out.println("\n7 squares of size 1 + 1 square of size 2 = 8\n");

        int result2 = countSquares(matrix2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 7");
        System.out.println("  Status: " + (result2 == 7 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: All Ones 2x2 ===");
        int[][] matrix3 = {
            {1, 1},
            {1, 1}
        };
        System.out.println("Matrix: " + Arrays.deepToString(matrix3));
        System.out.println("\n4 squares of size 1 + 1 square of size 2 = 5\n");

        int result3 = countSquares(matrix3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result3 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: All Zeros ===");
        int[][] matrix4 = {
            {0, 0},
            {0, 0}
        };
        System.out.println("Matrix: " + Arrays.deepToString(matrix4));
        System.out.println("\nNo 1's anywhere → 0\n");

        int result4 = countSquares(matrix4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result4 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Cell ===");
        int[][] matrix5 = {{1}};
        System.out.println("Matrix: " + Arrays.deepToString(matrix5));
        System.out.println("\nOne cell of 1 → 1 square\n");

        int result5 = countSquares(matrix5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result5 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Single Row ===");
        int[][] matrix6 = {{1, 1, 0, 1}};
        System.out.println("Matrix: " + Arrays.deepToString(matrix6));
        System.out.println("\nOnly size-1 squares possible: three 1's → 3\n");

        int result6 = countSquares(matrix6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result6 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Full 3x3 ===");
        int[][] matrix7 = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };
        System.out.println("Matrix: " + Arrays.deepToString(matrix7));
        System.out.println("\n9 (size 1) + 4 (size 2) + 1 (size 3) = 14\n");

        int result7 = countSquares(matrix7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 14");
        System.out.println("  Status: " + (result7 == 14 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Count all square submatrices made only of 1's      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: ans[i][j] = Largest Square Ending at (i, j)    ║");
        System.out.println("║    If a square of side s ends (bottom-right) at (i,j), then  ║");
        System.out.println("║    s squares (sizes 1..s) end at that cell. So summing all   ║");
        System.out.println("║    ans[i][j] values gives the total count.                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recurrence:                                                 ║");
        System.out.println("║    If matrix[i][j] == 1:                                     ║");
        System.out.println("║      ans[i][j] = 1 + min(top, left, top-left)                ║");
        System.out.println("║    Else: ans[i][j] = 0                                       ║");
        System.out.println("║    First row and first column copy the matrix values         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: Test Case 3 (all ones 2x2)                         ║");
        System.out.println("║    ans = [[1,1],[1,2]] → sum = 1+1+1+2 = 5                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Same DP as Maximal Square, but sums instead of max      ║");
        System.out.println("║    • Border cells can only form squares of size 1            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n × m) — single pass over the matrix     ║");
        System.out.println("║  Space Complexity: O(n × m) for the ans table                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}