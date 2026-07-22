package Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Shift_2D_Grid {

    private static ArrayList<ArrayList<Integer>> shiftGrid1(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            ans.add(new ArrayList<>(Collections.nCopies(n, 0)));
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int newRow = (i + (j + k) / n) % m;
                int newCol = (j + k) % n;

                ans.get(newRow).set(newCol, grid[i][j]);
            }
        }


        return ans;
    }

    private static ArrayList<ArrayList<Integer>> shiftGrid2(int[][] grid, int k) {
        
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;

        // Normalize k
        k = k % total;

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            ans.add(new ArrayList<>(Collections.nCopies(n, 0)));
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int newIndex = (i * n + j + k) % total;
                int newRow = newIndex / n;
                int newCol = newIndex % n;
                ans.get(newRow).set(newCol, grid[i][j]);
            }
        }

        return ans;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     SHIFT 2D GRID                            ║");
        System.out.println("║  Shift every element k positions forward, treating the grid  ║");
        System.out.println("║  as a flattened, wrapping array                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Basic Shift ===");
        int[][] grid1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int k1 = 1;
        System.out.println("Input grid: " + Arrays.deepToString(grid1));
        System.out.println("k = " + k1);
        System.out.println("\nFlattened: [1,2,3,4,5,6,7,8,9], shift right by 1");
        System.out.println("Result: [9,1,2,3,4,5,6,7,8] reshaped back to 3x3\n");

        ArrayList<ArrayList<Integer>> result1a = shiftGrid1(grid1, k1);
        ArrayList<ArrayList<Integer>> result1b = shiftGrid2(grid1, k1);
        System.out.println("✓ shiftGrid1 Result: " + result1a);
        System.out.println("✓ shiftGrid2 Result: " + result1b);
        System.out.println("  Expected: [[9, 1, 2], [3, 4, 5], [6, 7, 8]]");
        System.out.println("  Status: " + (result1a.equals(result1b) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 2: Shift Equals Total Size ===");
        int[][] grid2 = {{3, 8, 1, 9}, {19, 7, 2, 5}, {4, 6, 11, 10}, {12, 0, 21, 13}};
        int k2 = 4;
        System.out.println("Input grid: " + Arrays.deepToString(grid2));
        System.out.println("k = " + k2);
        System.out.println("\nShifting by a full row length wraps row-by-row\n");

        ArrayList<ArrayList<Integer>> result2a = shiftGrid1(grid2, k2);
        ArrayList<ArrayList<Integer>> result2b = shiftGrid2(grid2, k2);
        System.out.println("✓ shiftGrid1 Result: " + result2a);
        System.out.println("✓ shiftGrid2 Result: " + result2b);
        System.out.println("  Status: " + (result2a.equals(result2b) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 3: k = 0 (No Shift) ===");
        int[][] grid3 = {{1, 2}, {3, 4}};
        int k3 = 0;
        System.out.println("Input grid: " + Arrays.deepToString(grid3));
        System.out.println("k = " + k3);
        System.out.println("\nNo shift means grid stays exactly the same\n");

        ArrayList<ArrayList<Integer>> result3a = shiftGrid1(grid3, k3);
        ArrayList<ArrayList<Integer>> result3b = shiftGrid2(grid3, k3);
        System.out.println("✓ shiftGrid1 Result: " + result3a);
        System.out.println("✓ shiftGrid2 Result: " + result3b);
        System.out.println("  Expected: [[1, 2], [3, 4]]");
        System.out.println("  Status: " + (result3a.equals(result3b) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 4: k Equals Total Elements (Full Wrap) ===");
        int[][] grid4 = {{1, 2, 3}, {4, 5, 6}};
        int k4 = 6;
        System.out.println("Input grid: " + Arrays.deepToString(grid4));
        System.out.println("k = " + k4 + " (equals total element count)");
        System.out.println("\nFull wrap-around lands back on the original grid\n");

        ArrayList<ArrayList<Integer>> result4a = shiftGrid1(grid4, k4);
        ArrayList<ArrayList<Integer>> result4b = shiftGrid2(grid4, k4);
        System.out.println("✓ shiftGrid1 Result: " + result4a);
        System.out.println("✓ shiftGrid2 Result: " + result4b);
        System.out.println("  Expected: [[1, 2, 3], [4, 5, 6]]");
        System.out.println("  Status: " + (result4a.equals(result4b) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 5: Single Row Grid ===");
        int[][] grid5 = {{1, 2, 3, 4, 5}};
        int k5 = 2;
        System.out.println("Input grid: " + Arrays.deepToString(grid5));
        System.out.println("k = " + k5);
        System.out.println("\nSingle row wraps like a simple array rotation\n");

        ArrayList<ArrayList<Integer>> result5a = shiftGrid1(grid5, k5);
        ArrayList<ArrayList<Integer>> result5b = shiftGrid2(grid5, k5);
        System.out.println("✓ shiftGrid1 Result: " + result5a);
        System.out.println("✓ shiftGrid2 Result: " + result5b);
        System.out.println("  Expected: [[4, 5, 1, 2, 3]]");
        System.out.println("  Status: " + (result5a.equals(result5b) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 6: Single Column Grid ===");
        int[][] grid6 = {{1}, {2}, {3}, {4}};
        int k6 = 1;
        System.out.println("Input grid: " + Arrays.deepToString(grid6));
        System.out.println("k = " + k6);
        System.out.println("\nSingle column shifts each value down one row\n");

        ArrayList<ArrayList<Integer>> result6a = shiftGrid1(grid6, k6);
        ArrayList<ArrayList<Integer>> result6b = shiftGrid2(grid6, k6);
        System.out.println("✓ shiftGrid1 Result: " + result6a);
        System.out.println("✓ shiftGrid2 Result: " + result6b);
        System.out.println("  Expected: [[4], [1], [2], [3]]");
        System.out.println("  Status: " + (result6a.equals(result6b) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 7: Large k (Multiple Wraps) ===");
        int[][] grid7 = {{1, 2}, {3, 4}, {5, 6}};
        int k7 = 9;
        System.out.println("Input grid: " + Arrays.deepToString(grid7));
        System.out.println("k = " + k7 + " (total elements = 6, so 9 wraps around 1.5 times)");
        System.out.println("\nEffective shift = 9 % 6 = 3\n");

        ArrayList<ArrayList<Integer>> result7a = shiftGrid1(grid7, k7);
        ArrayList<ArrayList<Integer>> result7b = shiftGrid2(grid7, k7);
        System.out.println("✓ shiftGrid1 Result: " + result7a);
        System.out.println("✓ shiftGrid2 Result: " + result7b);
        System.out.println("  Status: " + (result7a.equals(result7b) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Shift every grid element k steps forward in a      ║");
        System.out.println("║           flattened, row-major, wrap-around order            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Map Each Cell to Its New Flattened Position    ║");
        System.out.println("║    Treat the m×n grid as one long array of length m*n.       ║");
        System.out.println("║    Every cell's flattened index moves forward by k,          ║");
        System.out.println("║    wrapping via modulo when it exceeds total size.           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Equivalent Approaches:                                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  shiftGrid1: Row/Col Arithmetic Directly                     ║");
        System.out.println("║    newRow = (i + (j + k) / n) % m                            ║");
        System.out.println("║    newCol = (j + k) % n                                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  shiftGrid2: Flatten → Shift → Unflatten                     ║");
        System.out.println("║    k = k % total (normalize first)                           ║");
        System.out.println("║    newIndex = (i*n + j + k) % total                          ║");
        System.out.println("║    newRow = newIndex / n, newCol = newIndex % n              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1            ║");
        System.out.println("║    Flattened: [1,2,3,4,5,6,7,8,9]                            ║");
        System.out.println("║    Shifted:   [9,1,2,3,4,5,6,7,8]                            ║");
        System.out.println("║    Reshaped:  [[9,1,2],[3,4,5],[6,7,8]]                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Both methods must always produce identical output       ║");
        System.out.println("║    • k can exceed m*n; wrap-around handles it correctly      ║");
        System.out.println("║    • Grid dimensions (m, n) need not be equal                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(m × n) for both approaches               ║");
        System.out.println("║  Space Complexity: O(m × n) for the output grid              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}