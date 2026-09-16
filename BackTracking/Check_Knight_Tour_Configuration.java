package BackTracking;

import java.util.Arrays;

public class Check_Knight_Tour_Configuration {

    private static boolean helper(int[][] grid, int row, int col, int curr, int n, int finalValue) {

        if (curr == finalValue) {
            return true;
        }

        // All 8 knight moves
        int[][] moves = {
            {- 2, + 1}, {- 2, - 1}, // North-east, North-west
            {- 1, + 2}, {+ 1, + 2}, // East-north, East-south
            {+ 2, + 1}, {+ 2, - 1}, // South-east, South-west
            {- 1, - 2}, {+ 1, - 2}  // West-north, West-south
        };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n && grid[newRow][newCol] == curr + 1) {
                if (helper(grid, newRow, newCol, curr + 1, n, finalValue)) {
                    return true;
                }
            }

        }

        return false;
    }

    private static boolean checkValidGrid(int[][] grid) {

        if (grid[0][0] != 0) {
            return false;
        }

        int n = grid.length;
        int row = 0, col = 0;

        int finalValue = n * n - 1;
        return helper(grid, row, col, 0, n, finalValue);
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║         CHECK KNIGHT TOUR CONFIGURATION                        ║");
        System.out.println("║  Verify if a grid represents a valid knight's tour, where      ║");
        System.out.println("║  each cell's value is the step at which a knight visits it     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Valid Knight Tour ===");
        int[][] grid1 = {
            {0, 11, 16, 5, 20},
            {17, 4, 19, 10, 15},
            {12, 1, 8, 21, 6},
            {3, 18, 23, 14, 9},
            {24, 13, 2, 7, 22}
        };
        System.out.println("Grid:");
        for (int[] row : grid1) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nStart at (0,0)=0, follow knight moves through values 1..24\n");

        boolean result1 = checkValidGrid(grid1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result1 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Invalid Knight Tour ===");
        int[][] grid2 = {
            {0, 3, 6},
            {5, 8, 1},
            {2, 4, 7}
        };
        System.out.println("Grid:");
        for (int[] row : grid2) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nValues 4 and 7 are swapped compared to a valid tour, breaking the path\n");

        boolean result2 = checkValidGrid(grid2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result2 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Doesn't Start at 0 ===");
        int[][] grid3 = {
            {1, 3, 6},
            {5, 8, 0},
            {2, 7, 4}
        };
        System.out.println("Grid:");
        for (int[] row : grid3) System.out.println("  " + Arrays.toString(row));
        System.out.println("\ngrid[0][0] != 0, immediately invalid\n");

        boolean result3 = checkValidGrid(grid3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result3 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Cell Grid ===");
        int[][] grid4 = {
            {0}
        };
        System.out.println("Grid:");
        for (int[] row : grid4) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nOnly one cell, curr=0 equals finalValue=0 immediately → true\n");

        boolean result4 = checkValidGrid(grid4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result4 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: 5x5 Valid-Style Grid (Unreachable Sequence) ===");
        int[][] grid5 = {
            {0, 1, 2, 3, 4},
            {5, 6, 7, 8, 9},
            {10, 11, 12, 13, 14},
            {15, 16, 17, 18, 19},
            {20, 21, 22, 23, 24}
        };
        System.out.println("Grid: sequential row-major values 0..24 (not knight-reachable in order)");
        System.out.println("\nNo knight move connects 0 to 1 directly in this layout → false\n");

        boolean result5 = checkValidGrid(grid5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result5 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: 4x4 Grid With Broken Chain ===");
        int[][] grid6 = {
            {0, 5, 10, 15},
            {1, 6, 11, 12},
            {2, 7, 8, 13},
            {3, 4, 9, 14}
        };
        System.out.println("Grid:");
        for (int[] row : grid6) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nTesting whether this arrangement follows valid knight moves throughout\n");

        boolean result6 = checkValidGrid(grid6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Status: (see computed value above)\n");

        System.out.println("=== Test Case 7: Grid Missing Final Value Path ===");
        int[][] grid7 = {
            {0, 3, 6},
            {5, 8, 1},
            {2, 4, 4}
        };
        System.out.println("Grid:");
        for (int[] row : grid7) System.out.println("  " + Arrays.toString(row));
        System.out.println("\nDuplicate value 4 means finalValue=8 can never be reached via valid chain\n");

        boolean result7 = checkValidGrid(grid7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result7 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                           ║");
        System.out.println("║  ─────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Verify whether a grid of step values represents a   ║");
        System.out.println("║           valid knight's tour — a knight starting at the      ║");
        System.out.println("║           cell labeled 0 can reach every increasing label     ║");
        System.out.println("║           (1, 2, 3, ...) using legal knight moves             ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Key Insight: Follow the Chain of Increasing Values           ║");
        System.out.println("║    Rather than searching for ANY valid tour, this problem     ║");
        System.out.println("║    checks if the GIVEN grid's labeling is itself a valid      ║");
        System.out.println("║    tour — at each step, only one specific next cell           ║");
        System.out.println("║    (the one labeled curr+1) is allowed.                       ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Logic:                                                       ║");
        System.out.println("║    Verify grid[0][0] == 0 (tour must start at cell labeled 0) ║");
        System.out.println("║    Recursively try all 8 knight moves from current position   ║");
        System.out.println("║    Only proceed into a move if the target cell's value        ║");
        System.out.println("║    equals curr+1 (the exact next step in the tour)            ║");
        System.out.println("║    Success when curr reaches finalValue = n*n - 1             ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Example: 3x3 grid with tour 0→1→2→...→8                      ║");
        System.out.println("║    Start (0,0)=0, look for a knight move landing on the cell  ║");
        System.out.println("║    labeled 1, then from there find the cell labeled 2, etc.   ║");
        System.out.println("║    If every step finds its successor, the grid is valid.      ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Properties:                                                  ║");
        System.out.println("║    • At most one knight move can lead to curr+1 at each step  ║");
        System.out.println("║      (since grid values are unique in a valid tour), so the   ║");
        System.out.println("║      recursion effectively follows a single deterministic path║");
        System.out.println("║    • Backtracking still explores all 8 moves defensively,     ║");
        System.out.println("║      even though only one should ever match                   ║");
        System.out.println("║    • Requires n x n grid with values 0 to n²-1                ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Time Complexity: O(n²) — each cell visited at most once      ║");
        System.out.println("║                    along the deterministic value chain        ║");
        System.out.println("║  Space Complexity: O(n²) — recursion depth up to n*n          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

    }

}