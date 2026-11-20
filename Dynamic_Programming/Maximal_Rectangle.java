package Dynamic_Programming;

import java.util.Arrays;
import java.util.Stack;

public class Maximal_Rectangle {

    private static int largestAreaInHistogram(int[] histgram) {

        Stack<Integer> st = new Stack<>();
        int maxArea = 0;
        int n = histgram.length;

        for (int i = 0; i <= n; i++) {
            while (! st.isEmpty() && (i == n || histgram[st.peek()] >= histgram[i])) {

                int height = histgram[st.pop()];
                int width;

                if (st.isEmpty()) {
                    width = i;
                } else {
                    width = i - st.peek() - 1;
                }

                int area = height * width;
                maxArea = Math.max(maxArea, area);
            }

            st.push(i);
        }

        return maxArea;
    }

    private static int maximalRectangle(char[][] matrix) {

        if (matrix == null || matrix.length == 0 ||
            matrix[0].length == 0) {
            return 0;
        }

        int n = matrix.length;
        int m = matrix[0].length;

        int[] heights = new int[m];
        int maxArea = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '1') {
                    heights[j]++;
                } else {
                    heights[j] = 0;
                }
            }

            maxArea = Math.max(maxArea, largestAreaInHistogram(heights));
        }

        return maxArea;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     MAXIMAL RECTANGLE                        ║");
        System.out.println("║  Find the largest rectangle containing only 1's in a binary  ║");
        System.out.println("║  matrix, using a histogram built row by row                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        char[][] matrix1 = {
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
        };
        System.out.println("Matrix:");
        for (char[] row : matrix1) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nHeights per row: [1,0,1,0,0] → [2,0,2,1,1] → [3,1,3,2,2] → [4,0,0,3,0]");
        System.out.println("Best histogram area is in row 2: height 2 × width 3 = 6\n");

        int result1 = maximalRectangle(matrix1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result1 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Single Zero ===");
        char[][] matrix2 = {{'0'}};
        System.out.println("Matrix:");
        for (char[] row : matrix2) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nNo 1's anywhere → area 0\n");

        int result2 = maximalRectangle(matrix2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result2 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single One ===");
        char[][] matrix3 = {{'1'}};
        System.out.println("Matrix:");
        for (char[] row : matrix3) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nOne cell of 1 → area 1\n");

        int result3 = maximalRectangle(matrix3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result3 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: All Ones ===");
        char[][] matrix4 = {
            {'1', '1', '1'},
            {'1', '1', '1'}
        };
        System.out.println("Matrix:");
        for (char[] row : matrix4) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nWhole 2×3 matrix is a rectangle of 1's → area 6\n");

        int result4 = maximalRectangle(matrix4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result4 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: All Zeros ===");
        char[][] matrix5 = {
            {'0', '0'},
            {'0', '0'}
        };
        System.out.println("Matrix:");
        for (char[] row : matrix5) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nEvery height resets to 0 → area 0\n");

        int result5 = maximalRectangle(matrix5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Empty Matrix ===");
        char[][] matrix6 = new char[0][0];
        System.out.println("Matrix: [] (empty)");
        System.out.println("\nGuard clause returns 0 immediately\n");

        int result6 = maximalRectangle(matrix6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Single Row With a Break ===");
        char[][] matrix7 = {
            {'1', '1', '0', '1'}
        };
        System.out.println("Matrix:");
        for (char[] row : matrix7) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nHeights = [1,1,0,1], best run of 1's is the first two cells → area 2\n");

        int result7 = maximalRectangle(matrix7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result7 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the largest rectangle made only of 1's in a   ║");
        System.out.println("║           binary matrix and return its area                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Reduce Each Row to a Histogram Problem         ║");
        System.out.println("║    For every row, heights[j] = consecutive 1's ending at     ║");
        System.out.println("║    that row in column j. The best rectangle with its bottom  ║");
        System.out.println("║    edge on this row is the largest rectangle in that         ║");
        System.out.println("║    histogram.                                                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Build Heights Row by Row                           ║");
        System.out.println("║    If matrix[i][j] == '1': heights[j]++                      ║");
        System.out.println("║    Else: heights[j] = 0 (the column's streak is broken)      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Largest Rectangle in Histogram (Monotonic Stack)   ║");
        System.out.println("║    Stack holds indices with increasing heights               ║");
        System.out.println("║    When histogram[i] <= histogram[top] (or i == n), pop:     ║");
        System.out.println("║      height = histogram[popped]                              ║");
        System.out.println("║      width = i if stack empty, else i - stack.peek() - 1     ║");
        System.out.println("║      area = height × width                                   ║");
        System.out.println("║    i == n acts as a sentinel to flush the remaining stack    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: matrix from Test Case 1                            ║");
        System.out.println("║    Row 0 heights [1,0,1,0,0] → best area 1                   ║");
        System.out.println("║    Row 1 heights [2,0,2,1,1] → best area 3                   ║");
        System.out.println("║    Row 2 heights [3,1,3,2,2] → best area 6                   ║");
        System.out.println("║    Row 3 heights [4,0,0,3,0] → best area 4                   ║");
        System.out.println("║    Result: max(1, 3, 6, 4) = 6                               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Empty or null matrix is guarded and returns 0           ║");
        System.out.println("║    • Each row's histogram is solved independently            ║");
        System.out.println("║    • Same stack technique as Largest Rectangle in Histogram  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n × m) — O(m) histogram work per row     ║");
        System.out.println("║  Space Complexity: O(m) for heights[] and the stack          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}