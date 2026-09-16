package BackTracking;

/*

    Description:
      Following program verifies whether a given n×n grid represents a valid knight's tour,
        where each cell's value indicates the step order in which a knight visits it...

    Problem Statement:
      -> Given an n×n integer grid where each cell contains a unique value from 0 to n²-1...
      -> A valid knight's tour starts at the cell labeled 0 (must be grid[0][0])...
      -> From each cell labeled k, the knight must be able to reach the cell labeled k+1
           using a standard chess knight move (L-shape: 2+1 or 1+2 squares)...
      -> Return true if the grid represents a valid knight's tour, false otherwise...

    Key Insight:
      -> This problem does NOT generate a tour; it verifies one already encoded in the grid...
      -> At each step k, exactly one cell in the grid is labeled k+1...
      -> The question is simply: can the knight reach that specific cell from position k?...
      -> Since values are unique, at most one of the 8 knight moves leads to k+1...
      -> Recursion effectively follows a single deterministic path, not a branching tree...

    Example:
      -> grid = [[0,3,6],[5,8,1],[2,7,4]]:
           Start (0,0)=0 → find cell with value 1 = (1,2)... knight reachable? Yes (2+1 move)...
           (1,2)=1 → find cell with value 2 = (2,0)... reachable? Yes...
           Continue until reaching value 8 = n²-1 → true...
      -> grid = [[0,3,6],[5,8,1],[2,4,7]]:
           Some step along the chain cannot be reached by a knight move → false...
      -> grid = [[1,3,6],[5,8,0],[2,7,4]]:
           grid[0][0] = 1 ≠ 0 → immediately return false...

    Algorithm Steps:
      -> If grid[0][0] != 0: return false immediately...
      -> Call helper(grid, row=0, col=0, curr=0, n, finalValue=n²-1)...
      -> helper recursively:
           Base case: if curr == finalValue → return true (all steps verified)...
           For each of 8 knight moves:
             Compute newRow = row + move[0], newCol = col + move[1]...
             If within bounds AND grid[newRow][newCol] == curr+1:
               Recurse: helper(grid, newRow, newCol, curr+1, n, finalValue)...
               If recursion returns true → propagate true upward...
           If no valid move found → return false...

    Eight Knight Moves:
      -> {-2, +1}: two up, one right (North-East)...
      -> {-2, -1}: two up, one left (North-West)...
      -> {-1, +2}: one up, two right (East-North)...
      -> {+1, +2}: one down, two right (East-South)...
      -> {+2, +1}: two down, one right (South-East)...
      -> {+2, -1}: two down, one left (South-West)...
      -> {-1, -2}: one up, two left (West-North)...
      -> {+1, -2}: one down, two left (West-South)...

    Why Recursion Is Effectively Deterministic:
      -> Grid values 0 to n²-1 are unique in a valid tour...
      -> At each step, only one cell has the value curr+1...
      -> At most one of the 8 knight moves can land on that specific cell...
      -> Backtracking exists in code but practically never backtracks...
      -> The path is linear (O(n²) steps), not exponential...

    Boundary Check:
      -> newRow >= 0 AND newRow < n: vertical bounds...
      -> newCol >= 0 AND newCol < n: horizontal bounds...
      -> All three conditions must hold before checking grid[newRow][newCol]...
      -> Short-circuit evaluation ensures no out-of-bounds access...

    Edge Cases:
      -> Single cell grid (n=1): grid[0][0] must be 0, curr=0 equals finalValue=0 → true...
      -> grid[0][0] != 0: return false without any recursion...
      -> Grid with duplicate values: false paths eventually hit a dead end → false...
      -> Sequential row-major values: cells adjacent in sequence aren't knight-reachable → false...

    Why finalValue = n*n - 1:
      -> Grid has n² cells labeled 0 through n²-1...
      -> The tour ends when the knight has visited all cells → last label = n²-1...
      -> curr reaching finalValue means every step 0 through n²-1 was validated...

    Time and Space Complexity:
      -> Time:  O(n²) — each of the n² steps checks at most 8 moves: O(8 × n²) = O(n²)...
      -> Space: O(n²) — recursion depth up to n²-1 steps deep in the call stack...

    Applications:
      -> Chess engine validation of knight tour solutions...
      -> Graph traversal verification in Hamiltonian path problems...
      -> Puzzle validation in grid-based board games...
      -> Competitive programming problems on knight tour verification...

*/

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
