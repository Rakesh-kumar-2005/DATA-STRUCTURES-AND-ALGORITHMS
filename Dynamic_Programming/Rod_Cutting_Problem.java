package Dynamic_Programming;

/*

    Description:
      Following program solves the Rod Cutting Problem where a rod of length N
        is cut into pieces to maximize total profit using an unbounded knapsack pattern...

    Problem Statement:
      -> Given a rod of length N and a prices array where prices[i] is the selling
           price for a piece of length i + 1...
      -> Each length can be cut and sold unlimited number of times...
      -> Cuts are free and any combination of lengths summing to N is valid...
      -> Return the maximum profit achievable...

    Key Insight:
      -> Rod cutting is structurally identical to the Unbounded Knapsack problem...
      -> Rod length N plays the role of knapsack capacity...
      -> Each possible cut length plays the role of an item with a weight and value...
      -> Since same length can be cut multiple times, same index is reused after cut...
      -> After cutting piece of length (idx + 1), recurse with same idx (unbounded)...

    Example:
      -> prices = [1, 5, 8, 9, 10, 17, 17, 20], N = 8:
           Cutting into [2, 6]: 5 + 17 = 22 ← optimal...
           Cutting into [8]: 20...
           Cutting into [2, 2, 2, 2]: 4 × 5 = 20...
           Maximum profit = 22...
      -> prices = [10, 20, 25, 30], N = 4:
           Cutting into [2, 2]: 20 + 20 = 40 ← optimal...
           Selling full rod: 30...
           Maximum profit = 40...

    Recursive Relation:
      -> maxProfit(idx, N) = max(notCut, cut)...
      -> notCut = maxProfit(idx - 1, N)...
           Skip this length, try smaller lengths...
      -> cut    = prices[idx] + maxProfit(idx, N - rodLength)...
           where rodLength = idx + 1...
           only if rodLength <= N...
           note: recurse with same idx → enables cutting same length again...

    Base Case:
      -> idx == 0 (only length-1 pieces available):
           Can cut N pieces of length 1...
           Return prices[0] * N...

    Approach 1 - Recursive:
      -> Pure top-down without caching...
      -> Explores all cut combinations with repetition...
      -> Exponential time due to heavily overlapping subproblems...
      -> Clearly establishes the unbounded recurrence structure...

    Approach 2 - Memoization (Top-Down DP):
      -> 2D dp array of size N × (N + 1), initialized to -1...
      -> dp[idx][N] stores maximum profit for given (idx, remaining length) state...
      -> Before recursing, check if dp[idx][N] != -1...
      -> Eliminates repeated computation of overlapping subproblems...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[idx][currLength] = max profit using lengths 1..(idx+1) for rod of size currLength...

         Initialization (base row for idx = 0):
           For each currLength from 0 to N:
             dp[0][currLength] = prices[0] * currLength...

         Transition (idx from 1 to N-1, currLength from 0 to N):
           notCut = dp[idx - 1][currLength]...
           cut    = prices[idx] + dp[idx][currLength - rodLength]...
                    only if rodLength <= currLength...
                    note: uses dp[idx][...] (same row), not dp[idx-1][...]...
           dp[idx][currLength] = Math.max(notCut, cut)...

         Answer: dp[N - 1][N]...

    Approach 4 - Space Optimization (Two 1D Arrays):
      -> Replaces 2D table with prev[] and curr[] arrays...
      -> prev[] represents dp[idx - 1], curr[] represents dp[idx]...
      -> For cut: use curr[currLength - rodLength] (same row, left-to-right)...
      -> For notCut: use prev[currLength] (previous row)...
      -> After each length index, assign prev = curr...
      -> Space reduced from O(N²) to O(N)...

    Approach 5 - Ultimate Space Optimization (Single 1D Array):
      -> Uses only one array prev[], updated in-place left-to-right...
      -> When computing prev[currLength], prev[currLength - rodLength] is already updated...
      -> This naturally captures the "reuse same length" unbounded behavior...
      -> CRITICAL: Left-to-right traversal enables reuse; right-to-left would prevent it...

    Critical Distinction - Same Row vs Previous Row:
      -> notCut → reads from previous row → skipping current length falls back to prev idx...
      -> cut    → reads from current row → reusing current length builds on updated values...
      -> Left-to-right single array update mirrors "stay at idx" in recursion...

    Tabulation Table Visualization (prices = [1,5,8,9], N = 4):

         length\N:   0   1   2   3   4
         idx=0:      0   1   2   3   4
         idx=1:      0   1   5   6   10
         idx=2:      0   1   5   8   9
         idx=3:      0   1   5   8   9

         Answer: dp[3][4] = 9...

    Difference From 0/1 Knapsack In Tabulation:
      -> 0/1 Knapsack pick:  value[i] + dp[idx - 1][capacity - weight[i]]...
                                                ↑ previous row (no reuse)...
      -> Rod Cutting cut:    price[i] + dp[idx][currLength - rodLength]...
                                               ↑ same row (allows reuse)...

    Edge Cases:
      -> N = 1 → only one piece possible → return prices[0]...
      -> All lengths have equal per-unit price → any cut strategy yields same profit...
      -> Full rod price dominates all cuts → optimal to not cut at all...
      -> First length (length 1) is most valuable per unit → cut into N pieces of length 1...

    Time and Space Complexity:
      -> Recursive:
           Time:  O(N^N) in worst case (exponential)...
           Space: O(N) recursion stack depth...
      -> Memoization:
           Time:  O(N²)...
           Space: O(N²) + O(N) recursion stack...
      -> Tabulation:
           Time:  O(N²)...
           Space: O(N²)...
      -> Two Array Space Optimized:
           Time:  O(N²)...
           Space: O(N)...
      -> Single Array Ultimate Optimized:
           Time:  O(N²)...
           Space: O(N)...

    Applications:
      -> Lumber and steel rod cutting in manufacturing...
      -> Pipe and cable segmentation for maximum revenue...
      -> Resource allocation with repeatable subdivision units...
      -> Stock price interval optimization problems...
      -> Competitive programming unbounded knapsack variants...

*/

public class Rod_Cutting_Problem {

    private static int recursion(int idx, int N, int[] prices) {

        if (idx == 0) {
            return prices[0] * N;
        }

        int notCut = recursion(idx - 1, N, prices);
        int cut = Integer.MIN_VALUE;
        int rodLength = idx + 1;

        if (rodLength <= N) {
            cut = prices[idx] + recursion(idx, N - rodLength, prices);
        }

        return Math.max(notCut, cut);
    }

    private static int memoization(int idx, int N, int[] prices, int[][] dp) {

        if (idx == 0) {
            return prices[0] * N;
        }

        if (dp[idx][N] != - 1) {
            return dp[idx][N];
        }

        int notCut = memoization(idx - 1, N, prices, dp);
        int cut = Integer.MIN_VALUE;
        int rodLength = idx + 1;

        if (rodLength <= N) {
            cut = prices[idx] + memoization(idx, N - rodLength, prices, dp);
        }

        return dp[idx][N] = Math.max(notCut, cut);
    }

    private static int tabulation(int N, int[] prices) {

        int[][] dp = new int[N][N + 1];
        for (int n = 0; n <= N; n++) {
            dp[0][n] = prices[0] * n;
        }

        for (int idx = 1; idx < N; idx++) {
            for (int currLength = 0; currLength <= N; currLength++) {

                int notCut = dp[idx - 1][currLength];
                int cut = Integer.MIN_VALUE;
                int rodLength = idx + 1;

                if (rodLength <= currLength) {
                    cut = prices[idx] + dp[idx][currLength - rodLength];
                }

                dp[idx][currLength] = Math.max(cut, notCut);
            }
        }

        return dp[N - 1][N];
    }

    private static int SpaceOptimized2Array(int N, int[] prices) {

        int[] prev = new int[N + 1];
        for (int n = 0; n <= N; n++) {
            prev[n] = prices[0] * n;
        }

        for (int idx = 1; idx < N; idx++) {

            int[] curr = new int[N + 1];
            for (int currLength = 0; currLength <= N; currLength++) {

                int notCut = prev[currLength];
                int cut = Integer.MIN_VALUE;
                int rodLength = idx + 1;

                if (rodLength <= currLength) {
                    cut = prices[idx] + curr[currLength - rodLength];
                }

                curr[currLength] = Math.max(cut, notCut);
            }
            prev = curr;
        }

        return prev[N];
    }

    private static int SpaceOptimized1Array(int N, int[] prices) {

        int[] prev = new int[N + 1];
        for (int n = 0; n <= N; n++) {
            prev[n] = prices[0] * n;
        }

        for (int idx = 1; idx < N; idx++) {
            for (int currLength = 0; currLength <= N; currLength++) {

                int notCut = prev[currLength];
                int cut = Integer.MIN_VALUE;
                int rodLength = idx + 1;

                if (rodLength <= currLength) {
                    cut = prices[idx] + prev[currLength - rodLength];
                }

                prev[currLength] = Math.max(cut, notCut);
            }
        }

        return prev[N];
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 ROD CUTTING PROBLEM                          ║");
        System.out.println("║       Maximize profit by cutting a rod of length N           ║");
        System.out.println("║           Each cut piece has a specific price                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Rod Problem ===");
        int[] prices1 = {1, 5, 8, 9, 10, 17, 17, 20};
        int N1 = 8;
        System.out.println("Rod length: " + N1);
        System.out.println("Prices (price[i] = price for length i+1):");
        for (int i = 0; i < prices1.length; i++) {
            System.out.println("  Length " + (i + 1) + ": $" + prices1[i]);
        }
        System.out.println("\nOptimal cutting strategy:");
        System.out.println("  Cut into pieces: [2, 2, 2, 2] or [2, 6]");
        System.out.println("  4 pieces of length 2: 4 × 5 = 20 or");
        System.out.println("  1 piece of length 2 + 1 piece of length 6: 5 + 17 = 22");
        System.out.println("  Maximum profit: 22\n");

        int result1_rec = recursion(prices1.length - 1, N1, prices1);

        int[][] dp1 = new int[prices1.length][N1 + 1];
        for (int i = 0; i < prices1.length; i++) {
            for (int j = 0; j <= N1; j++) dp1[i][j] = - 1;
        }
        int result1_memo = memoization(prices1.length - 1, N1, prices1, dp1);
        int result1_tab = tabulation(N1, prices1);
        int result1_opt1 = SpaceOptimized2Array(N1, prices1);
        int result1_opt2 = SpaceOptimized1Array(N1, prices1);

        System.out.println("✓ Recursive Result: " + result1_rec);
        System.out.println("✓ Memoization Result: " + result1_memo);
        System.out.println("✓ Tabulation Result: " + result1_tab);
        System.out.println("✓ Space Opt (2 arrays) Result: " + result1_opt1);
        System.out.println("✓ Space Opt (1 array) Result: " + result1_opt2);
        System.out.println("  Status: " + (result1_tab == 22 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: No Cutting Better ===");
        int[] prices2 = {3, 5, 6, 7};
        int N2 = 4;
        System.out.println("Rod length: " + N2);
        System.out.println("Prices:");
        for (int i = 0; i < prices2.length; i++) {
            System.out.println("  Length " + (i + 1) + ": $" + prices2[i]);
        }
        System.out.println("\nOptimal strategy: Don't cut at all");
        System.out.println("  Sell full rod: 1 piece of length 4 = $7");
        System.out.println("  Maximum profit: 7\n");

        int result2_tab = tabulation(N2, prices2);
        int result2_opt = SpaceOptimized1Array(N2, prices2);

        System.out.println("✓ Tabulation Result: " + result2_tab);
        System.out.println("✓ Space Optimized Result: " + result2_opt);
        System.out.println("  Expected: 7");
        System.out.println("  Status: " + (result2_tab == 7 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Length Rod ===");
        int[] prices3 = {5};
        int N3 = 1;
        System.out.println("Rod length: " + N3);
        System.out.println("Prices:");
        System.out.println("  Length 1: $5");
        System.out.println("\nOnly option: Sell as one piece");
        System.out.println("  Maximum profit: 5\n");

        int result3_tab = tabulation(N3, prices3);
        int result3_opt = SpaceOptimized1Array(N3, prices3);

        System.out.println("✓ Tabulation Result: " + result3_tab);
        System.out.println("✓ Space Optimized Result: " + result3_opt);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result3_tab == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Linear Pricing ===");
        int[] prices4 = {1, 2, 3, 4, 5};
        int N4 = 5;
        System.out.println("Rod length: " + N4);
        System.out.println("Prices (linear):");
        for (int i = 0; i < prices4.length; i++) {
            System.out.println("  Length " + (i + 1) + ": $" + prices4[i]);
        }
        System.out.println("\nOptimal strategy: 5 pieces of length 1");
        System.out.println("  5 × 1 = $5 vs. selling full rod for $5");
        System.out.println("  Maximum profit: 5\n");

        int result4_tab = tabulation(N4, prices4);
        int result4_opt = SpaceOptimized1Array(N4, prices4);

        System.out.println("✓ Tabulation Result: " + result4_tab);
        System.out.println("✓ Space Optimized Result: " + result4_opt);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result4_tab == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: High Value Short Pieces ===");
        int[] prices5 = {10, 20, 25, 30};
        int N5 = 4;
        System.out.println("Rod length: " + N5);
        System.out.println("Prices:");
        for (int i = 0; i < prices5.length; i++) {
            System.out.println("  Length " + (i + 1) + ": $" + prices5[i]);
        }
        System.out.println("\nOptimal strategy: 2 pieces of length 2");
        System.out.println("  2 × 20 = $40");
        System.out.println("  vs. full rod for $30");
        System.out.println("  Maximum profit: 40\n");

        int result5_tab = tabulation(N5, prices5);
        int result5_opt = SpaceOptimized1Array(N5, prices5);

        System.out.println("✓ Tabulation Result: " + result5_tab);
        System.out.println("✓ Space Optimized Result: " + result5_opt);
        System.out.println("  Expected: 40");
        System.out.println("  Status: " + (result5_tab == 40 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Two Lengths ===");
        int[] prices6 = {3, 7};
        int N6 = 2;
        System.out.println("Rod length: " + N6);
        System.out.println("Prices:");
        System.out.println("  Length 1: $3");
        System.out.println("  Length 2: $7");
        System.out.println("\nOptimal strategy: 1 piece of length 2");
        System.out.println("  1 × 7 = $7");
        System.out.println("  vs. 2 pieces of length 1 for $6");
        System.out.println("  Maximum profit: 7\n");

        int result6_tab = tabulation(N6, prices6);
        int result6_opt = SpaceOptimized1Array(N6, prices6);

        System.out.println("✓ Tabulation Result: " + result6_tab);
        System.out.println("✓ Space Optimized Result: " + result6_opt);
        System.out.println("  Expected: 7");
        System.out.println("  Status: " + (result6_tab == 7 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Maximize profit by cutting rod optimally           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Given:                                                      ║");
        System.out.println("║    • Rod of length N                                         ║");
        System.out.println("║    • prices[i] = price for a piece of length i+1             ║");
        System.out.println("║    • Each cut is free                                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Goal: Find maximum profit from cutting                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Unbounded Knapsack Pattern                     ║");
        System.out.println("║    • Can use each length unlimited times                     ║");
        System.out.println("║    • After cutting piece of length i, can cut more length i  ║");
        System.out.println("║    • Recurse with same index (unbounded property)            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Recurrence Relation:                                        ║");
        System.out.println("║    maxProfit(idx, N) = max(notCut, cut)                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║    notCut = maxProfit(idx-1, N)                              ║");
        System.out.println("║             (skip considering current length)                ║");
        System.out.println("║                                                              ║");
        System.out.println("║    cut = prices[idx] + maxProfit(idx, N - length)            ║");
        System.out.println("║           ^^^ same idx (can reuse this length)               ║");
        System.out.println("║           ^^^ unbounded property                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Base Case:                                                  ║");
        System.out.println("║    if (idx == 0):                                            ║");
        System.out.println("║      return prices[0] * N                                    ║");
        System.out.println("║      (length 1, can take N pieces)                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: prices = [1,5,8,9,10,17,17,20], N = 8              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  DP Table (truncated):                                       ║");
        System.out.println("║    length\\N   0  1  2  3  4   5   6   7   8                   ║");
        System.out.println("║    1         0  1  2  3  4   5   6   7   8                   ║");
        System.out.println("║    2         0  1  5  6  10  11  15  16  20                  ║");
        System.out.println("║    3         0  1  5  8  9   13  16  19  20                  ║");
        System.out.println("║    4         0  1  5  8  9   13  17  18  22                  ║");
        System.out.println("║    ...                                                       ║");
        System.out.println("║    Result: dp[7][8] = 22                                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Path Reconstruction (optimal cuts):                         ║");
        System.out.println("║    Start at dp[7][8] = 22                                    ║");
        System.out.println("║    Check: Can cut length 2? 5 + dp[7][6] = 5 + 17 = 22 ✓     ║");
        System.out.println("║    Move to dp[7][6]                                          ║");
        System.out.println("║    Check: Can cut length 2? 5 + dp[7][4] = 5 + 13 = 18 ✗     ║");
        System.out.println("║            Can cut length 1? 1 + dp[7][5] = 1 + 13 = 14 ✗    ║");
        System.out.println("║    Move to dp[6][6] (different choice)                       ║");
        System.out.println("║    Optimal cuts: [2, 6] or equivalent                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Differences from 0/1 Knapsack:                              ║");
        System.out.println("║    0/1 Knapsack:                                             ║");
        System.out.println("║      pick = value[i] + dp[i-1][capacity - weight[i]]         ║");
        System.out.println("║      ^^^ move to i-1 (can't reuse)                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║    Unbounded (Rod Cutting):                                  ║");
        System.out.println("║      cut = price[i] + dp[i][capacity - length[i]]            ║");
        System.out.println("║      ^^^ stay at i (can reuse)                               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Space Optimization:                                         ║");
        System.out.println("║    Tabulation uses dp[idx][length]                           ║");
        System.out.println("║    2-Array: Only need current and previous rows              ║");
        System.out.println("║    1-Array: LEFT-TO-RIGHT iteration                          ║");
        System.out.println("║             When calculating curr[L], use curr[L-length]     ║");
        System.out.println("║             Already updated in this iteration!               ║");
        System.out.println("║             This allows reuse of same length                 ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Complexity:                                                 ║");
        System.out.println("║    Time:  O(N × N) = O(N²)                                   ║");
        System.out.println("║            N lengths to consider, for each check all N sizes ║");
        System.out.println("║    Space: O(N²) tabulation, O(N) optimized                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}