package Dynamic_Programming;

/*

    Description:
      Following program counts all square submatrices containing only 1's in a binary
        matrix, using a DP table where each cell stores the largest square ending there...

    Problem Statement:
      -> Given a binary matrix of 0s and 1s...
      -> Count every square submatrix (of any size from 1×1 to min(n,m)×min(n,m))
           that contains only 1's...
      -> A 1×1 square is the smallest; all sizes are counted separately...
      -> Return the total count across all sizes and positions...

    Key Insight:
      -> Define ans[i][j] = side length of the largest all-1's square whose bottom-right
           corner is at (i, j)...
      -> A square of side s ending at (i, j) also contains squares of sides 1, 2, ..., s-1
           ending at the same cell...
      -> Therefore, the number of squares ending at (i, j) = ans[i][j]...
      -> Summing all ans[i][j] values gives the total count of all squares...

    Recurrence Relation:
      -> First row and first column: ans[i][j] = matrix[i][j] directly...
           (can only form a 1×1 square at the boundary)...
      -> For i >= 1 and j >= 1:
           If matrix[i][j] == 1:
             ans[i][j] = 1 + Math.min(ans[i-1][j-1], Math.min(ans[i-1][j], ans[i][j-1]))...
           Else: ans[i][j] = 0 (no square ends at a 0 cell)...

    Why min(top, left, top-left) + 1:
      -> ans[i-1][j] = largest square above → constrains how tall the square can be...
      -> ans[i][j-1] = largest square to the left → constrains how wide the square can be...
      -> ans[i-1][j-1] = largest square diagonally above-left → constrains the corner...
      -> The side length of a valid square at (i,j) = 1 + minimum of the three neighbors...
      -> All three must accommodate a square of that size for it to be valid...

    Example:
      -> matrix = [[1,1],[1,1]] (all ones 2×2):
           ans[0][0]=1, ans[0][1]=1 (first row)...
           ans[1][0]=1 (first column)...
           ans[1][1]=1+min(1,1,1)=2...
           sum = 1+1+1+2 = 5...
           Correctly counts: four 1×1 squares + one 2×2 square = 5...
      -> matrix = [[1,1,1],[1,1,1],[1,1,1]] (full 3×3):
           ans = [[1,1,1],[1,2,2],[1,2,3]]...
           sum = 1+1+1+1+2+2+1+2+3 = 14...
           9 size-1 + 4 size-2 + 1 size-3 = 14 ✓...

    Initialization (First Row and Column):
      -> ans[i][0] = matrix[i][0] for all i (first column)...
      -> ans[0][j] = matrix[0][j] for all j starting from j=1 (first row, skip [0][0])...
      -> These are initialized separately and added to sum during initialization...
      -> Inner loop only processes i >= 1, j >= 1...

    Step-by-Step Trace (matrix = [[0,1,1,1],[1,1,1,1],[0,1,1,1]]):
      -> First column: ans[0][0]=0, ans[1][0]=1, ans[2][0]=0...
      -> First row: ans[0][1]=1, ans[0][2]=1, ans[0][3]=1...
      -> Inner cells:
           ans[1][1] = 1+min(0,0,1)=1...
           ans[1][2] = 1+min(1,1,1)=2...
           ans[1][3] = 1+min(1,1,2)=2...
           ans[2][1] = 1+min(1,1,1)=2...
           ans[2][2] = 1+min(2,2,1)=2...
           ans[2][3] = 1+min(2,2,2)=3...
      -> sum = 0+1+0 + 1+1+1 + 1+2+2 + 2+2+3 = 15 ✓...

    Difference From Maximal Square:
      -> Maximal Square: track only the maximum ans[i][j] value → find the largest square...
      -> Count Squares: sum all ans[i][j] values → count all squares of all sizes...
      -> The DP table computation is identical; only the aggregation differs...

    Edge Cases:
      -> All zeros → all ans[i][j] = 0 → sum = 0...
      -> All ones n×m → sum = Σ(k² × (n-k+1) × (m-k+1)) for k from 1 to min(n,m)...
      -> Single cell with 1 → ans[0][0]=1, sum=1...
      -> Single row → no cell has i>=1 → inner loop never executes → sum = count of 1s in row...
      -> Single column → similarly → sum = count of 1s in column...

    Time and Space Complexity:
      -> Time:  O(n × m) — two nested loops, each cell visited exactly once...
      -> Space: O(n × m) — ans table same size as matrix...
               Could be reduced to O(m) with two rolling rows, but not implemented here...

    Applications:
      -> Image analysis: counting all uniform square patches in a binary image...
      -> Grid-based game validation: counting valid square territories...
      -> Data density analysis: counting compact square clusters in boolean datasets...
      -> Competitive programming combinatorial grid counting problems...

*/

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
