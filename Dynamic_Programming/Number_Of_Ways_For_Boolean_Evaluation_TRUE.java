package Dynamic_Programming;

import java.util.Arrays;

public class Number_Of_Ways_For_Boolean_Evaluation_TRUE {

    static final int mod = 1000000007;

    private static int memoization(int left, int right, int isTrue, String expression, int[][][] dp) {

        if (left > right) {
            return 0;
        }

        if (left == right) {
            if (isTrue == 1) {
                return expression.charAt(left) == 'T' ? 1 : 0;
            } else {
                return expression.charAt(left) == 'F' ? 1 : 0;
            }
        }

        if (dp[left][right][isTrue] != - 1) {
            return dp[left][right][isTrue];
        }

        long ways = 0;
        for (int idx = left + 1; idx <= right - 1; idx += 2) {

            long leftTrue = memoization(left, idx - 1, 1, expression, dp);
            long leftFalse = memoization(left, idx - 1, 0, expression, dp);
            long rightTrue = memoization(idx + 1, right, 1, expression, dp);
            long rightFalse = memoization(idx + 1, right, 0, expression, dp);

            char currOperator = expression.charAt(idx);

            if (currOperator == '&') {
                if (isTrue == 1) {
                    ways = (ways + (leftTrue * rightTrue) % mod) % mod;
                } else {
                    ways = (ways + (leftTrue * rightFalse) % mod + (leftFalse * rightTrue) % mod + (leftFalse * rightFalse) % mod) % mod;
                }
            } else if (currOperator == '|') {
                if (isTrue == 1) {
                    ways = (ways + (leftTrue * rightTrue) % mod + (leftTrue * rightFalse) % mod + (leftFalse * rightTrue) % mod) % mod;
                } else {
                    ways = (ways + (leftFalse * rightFalse) % mod) % mod;
                }
            } else {
                if (isTrue == 1) {
                    ways = (ways + (leftTrue * rightFalse) % mod + (leftFalse * rightTrue) % mod) % mod;
                } else {
                    ways = (ways + (leftTrue * rightTrue) % mod + (leftFalse * rightFalse) % mod) % mod;
                }
            }
        }

        return dp[left][right][isTrue] = (int) ways;
    }

    public static int evaluateExp(String exp) {

        int n = exp.length();
        int[][][] dp = new int[n][n][2];
        int isTrue = 1;

        for (int[][] a : dp) {
            for (int[] b : a) {
                Arrays.fill(b, - 1);
            }
        }

        return memoization(0, n - 1, isTrue, exp, dp);
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     NUMBER OF WAYS FOR BOOLEAN EVALUATION (TRUE)             ║");
        System.out.println("║  Count the ways to parenthesize a boolean expression so it   ║");
        System.out.println("║  evaluates to true (result taken modulo 1e9+7)               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        String exp1 = "T|F&T^F";
        System.out.println("Expression: \"" + exp1 + "\"");
        System.out.println("\nTry every operator as the LAST one evaluated and combine");
        System.out.println("the true/false counts of the left and right parts\n");

        int result1 = evaluateExp(exp1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result1 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Single True ===");
        String exp2 = "T";
        System.out.println("Expression: \"" + exp2 + "\"");
        System.out.println("\nSingle operand 'T' is already true → 1 way\n");

        int result2 = evaluateExp(exp2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result2 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single False ===");
        String exp3 = "F";
        System.out.println("Expression: \"" + exp3 + "\"");
        System.out.println("\nSingle operand 'F' can never be true → 0 ways\n");

        int result3 = evaluateExp(exp3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Simple AND ===");
        String exp4 = "T&T";
        System.out.println("Expression: \"" + exp4 + "\"");
        System.out.println("\nTrue AND True = True → 1 way\n");

        int result4 = evaluateExp(exp4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result4 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Simple XOR ===");
        String exp5 = "T^F";
        System.out.println("Expression: \"" + exp5 + "\"");
        System.out.println("\nTrue XOR False = True → 1 way\n");

        int result5 = evaluateExp(exp5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result5 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: AND With False ===");
        String exp6 = "F&T";
        System.out.println("Expression: \"" + exp6 + "\"");
        System.out.println("\nFalse AND True = False → 0 ways\n");

        int result6 = evaluateExp(exp6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Three Operands, Mixed ===");
        String exp7 = "T^T^T";
        System.out.println("Expression: \"" + exp7 + "\"");
        System.out.println("\n(T^T)^T = F^T = T and T^(T^T) = T^F = T → 2 ways\n");

        int result7 = evaluateExp(exp7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result7 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                           ║");
        System.out.println("║  ─────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Count parenthesizations of a boolean expression     ║");
        System.out.println("║           (operands T/F, operators & | ^) that evaluate to    ║");
        System.out.println("║           true, modulo 1e9+7                                  ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Key Insight: Pick the LAST Operator Evaluated                ║");
        System.out.println("║    Splitting at operator idx makes the left and right parts   ║");
        System.out.println("║    independent subproblems. Track both true and false counts  ║");
        System.out.println("║    for each part, since the operator mixes them.              ║");
        System.out.println("║                                                               ║");
        System.out.println("║  State: dp[left][right][isTrue]                               ║");
        System.out.println("║    isTrue = 1 → ways the range evaluates to true              ║");
        System.out.println("║    isTrue = 0 → ways the range evaluates to false             ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Combination Rules (L = left part, R = right part):           ║");
        System.out.println("║    &  true : LT*RT                                            ║");
        System.out.println("║       false: LT*RF + LF*RT + LF*RF                            ║");
        System.out.println("║    |  true : LT*RT + LT*RF + LF*RT                            ║");
        System.out.println("║       false: LF*RF                                            ║");
        System.out.println("║    ^  true : LT*RF + LF*RT                                    ║");
        System.out.println("║       false: LT*RT + LF*RF                                    ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Base Cases:                                                  ║");
        System.out.println("║    left > right → 0                                           ║");
        System.out.println("║    left == right → 1 if the operand matches isTrue, else 0    ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Example: \"T^T^T\"                                           ║");
        System.out.println("║    Split at first ^: (T)^(T^T) → T^F = T ✓                    ║");
        System.out.println("║    Split at second ^: (T^T)^(T) → F^T = T ✓                   ║");
        System.out.println("║    Total: 2 ways                                              ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Properties:                                                  ║");
        System.out.println("║    • Operators sit at odd indices, so idx steps by 2          ║");
        System.out.println("║    • Products are done in long, then reduced by mod           ║");
        System.out.println("║    • Same partition-DP pattern as Matrix Chain Multiplication ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Time Complexity: O(n³) — n² states, O(n) split loop each     ║");
        System.out.println("║  Space Complexity: O(n²) for the dp table (times 2 states)    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
    }

}