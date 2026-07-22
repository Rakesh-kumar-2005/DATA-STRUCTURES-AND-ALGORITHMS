package Arrays;

/*

    Description:
      Following program shifts every element in a 2D grid k positions forward,
        treating the grid as a flattened row-major array with wrap-around...

    Problem Statement:
      -> Given an m×n integer grid and an integer k...
      -> Treat the grid as a single flattened array in row-major order...
      -> Shift every element k positions forward in that flattened array...
      -> Elements that shift past the end wrap around to the beginning...
      -> Return the resulting grid after the shift...

    Key Insight:
      -> Every element at 2D position (i, j) has a flat index = i×n + j...
      -> After shifting k positions, its new flat index = (i×n + j + k) % (m×n)...
      -> New row = newIndex / n, New column = newIndex % n...
      -> k can be reduced modulo total (m×n) since a full-length shift is a no-op...
      -> Two equivalent formulations exist: direct row/col arithmetic or flatten-shift-unflatten...

    Example:
      -> grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1:
           Flattened: [1, 2, 3, 4, 5, 6, 7, 8, 9]...
           Shifted by 1: [9, 1, 2, 3, 4, 5, 6, 7, 8]...
           Reshaped: [[9, 1, 2], [3, 4, 5], [6, 7, 8]]...
      -> grid = [[1,2],[3,4]], k = 4 (full wrap):
           Effective shift = 4 % 4 = 0 → grid unchanged...
      -> grid = [[1,2,3,4,5]], k = 2 (single row):
           Shifted array: [4, 5, 1, 2, 3] → [[4, 5, 1, 2, 3]]...

    Approach 1 - shiftGrid1 (Direct Row/Col Arithmetic):
      -> For each element at (i, j):
           newCol = (j + k) % n...
           newRow = (i + (j + k) / n) % m...
      -> (j + k) / n captures how many rows the column offset overflows...
      -> Adding that overflow to i gives the row after shift...
      -> Modulo m wraps the row if it exceeds the grid height...
      -> No need to normalize k separately — formula handles all cases...

    Approach 2 - shiftGrid2 (Flatten → Shift → Unflatten):
      -> Normalize: k = k % (m × n) to remove complete full-grid cycles...
      -> For each element at (i, j):
           flatIndex = i × n + j...
           newIndex  = (flatIndex + k) % (m × n)...
           newRow    = newIndex / n...
           newCol    = newIndex % n...
      -> Cleaner to reason about: purely index arithmetic on a 1D array...
      -> Both approaches produce identical results for all inputs...

    Step-by-Step Trace (grid = [[1,2],[3,4],[5,6]], k = 3):
      -> total = 6, k = 3 % 6 = 3...
      -> (0,0): flatIndex=0, newIndex=3, newRow=1, newCol=1 → ans[1][1]=1...
      -> (0,1): flatIndex=1, newIndex=4, newRow=2, newCol=0 → ans[2][0]=2...
      -> (1,0): flatIndex=2, newIndex=5, newRow=2, newCol=1 → ans[2][1]=3...
      -> (1,1): flatIndex=3, newIndex=0, newRow=0, newCol=0 → ans[0][0]=4...
      -> (2,0): flatIndex=4, newIndex=1, newRow=0, newCol=1 → ans[0][1]=5...
      -> (2,1): flatIndex=5, newIndex=2, newRow=1, newCol=0 → ans[1][0]=6...
      -> Result: [[4, 5], [6, 1], [2, 3]]...

    Why k % total is Applied in shiftGrid2 but Not shiftGrid1:
      -> shiftGrid2 uses (flatIndex + k) % total directly, which handles large k implicitly...
      -> The explicit k = k % total normalization in shiftGrid2 is an optional optimization...
      -> shiftGrid1's formula (j + k) / n could overflow for large k without normalization...
      -> For very large k, shiftGrid2 normalization prevents integer overflow more cleanly...

    Edge Cases:
      -> k = 0 → all newIndex == flatIndex → grid unchanged...
      -> k = m×n → full wrap, equivalent to k = 0 → grid unchanged...
      -> Single row grid → behaves exactly like 1D array rotation...
      -> Single column grid → each element moves down one row per k...
      -> k > m×n → effective shift = k % (m×n), multiple full wraps discarded...

    Output Structure:
      -> Result is an ArrayList<ArrayList<Integer>> initialized with zeros...
      -> Each element placed at its computed (newRow, newCol) using set()...
      -> Both approaches write to a fresh output grid (no in-place modification)...

    Time and Space Complexity:
      -> Time:  O(m × n) — every element visited and placed exactly once...
      -> Space: O(m × n) — new output grid of same dimensions allocated...

    Applications:
      -> Image scrolling or panning in 2D display buffers...
      -> Cyclic shift operations in matrix manipulation problems...
      -> Game board rotation or circular buffer visualization...
      -> Competitive programming grid transformation problems...

*/

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
