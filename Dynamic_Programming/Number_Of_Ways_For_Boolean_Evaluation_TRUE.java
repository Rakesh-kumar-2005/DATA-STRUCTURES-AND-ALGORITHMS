package Dynamic_Programming;

/*

    Description:
      Following program counts the number of ways to parenthesize a boolean expression
        so that it evaluates to true, using partition DP with memoization...

    Problem Statement:
      -> Given a boolean expression string containing operands T (true) and F (false)
           and operators & (AND), | (OR), ^ (XOR) in alternating positions...
      -> Count the number of distinct parenthesizations that make the expression evaluate to true...
      -> Return the count modulo 10^9 + 7...

    Key Insight:
      -> For each operator at index idx, treat it as the last operator evaluated...
      -> This splits the expression into independent left [left, idx-1] and right [idx+1, right] parts...
      -> Both true and false counts of each part are needed to determine the combined result...
      -> The operator's truth table determines which (leftTrue, rightTrue/False) combinations matter...
      -> State includes isTrue to count ways for both outcomes in one unified DP...

    Expression Structure:
      -> Operands (T or F) sit at even indices: 0, 2, 4, ...
      -> Operators (&, |, ^) sit at odd indices: 1, 3, 5, ...
      -> For a range [left, right], operators are at odd indices from left+1 to right-1...
      -> Inner loop: idx from left+1 to right-1 stepping by 2 (operator positions only)...

    Combination Rules for Each Operator:
      -> AND (&):
           True  when: LT × RT (both true)...
           False when: LT × RF + LF × RT + LF × RF (at least one false)...
      -> OR (|):
           True  when: LT × RT + LT × RF + LF × RT (at least one true)...
           False when: LF × RF (both false)...
      -> XOR (^):
           True  when: LT × RF + LF × RT (exactly one true, one false)...
           False when: LT × RT + LF × RF (both same)...

    Example:
      -> exp = "T|F&T^F":
           Operators at indices 1 (|), 3 (&), 5 (^)...
           Split at |: (T) | (F&T^F) → T | ? = T for any value of right...
           Split at &: (T|F) & (T^F) → ? & T...
           Split at ^: (T|F&T) ^ (F) → ? ^ F...
           Combining all valid parenthesizations → 3 ways evaluate to true...
      -> exp = "T^T^T":
           Split at first ^: (T) ^ (T^T) → T ^ F = T → 1 way...
           Split at second ^: (T^T) ^ (T) → F ^ T = T → 1 way...
           Total = 2 ways...

    Recursive Relation:
      -> Base: left > right → return 0 (invalid range)...
      -> Base: left == right → return 1 if expression.charAt(left) matches isTrue, else 0...
      -> For each operator at idx from left+1 to right-1 step 2:
           Compute leftTrue, leftFalse, rightTrue, rightFalse recursively...
           Apply operator's combination formula for the isTrue target...
           Accumulate ways modulo MOD...
      -> Return dp[left][right][isTrue] = ways...

    State Space:
      -> dp[left][right][isTrue] = number of ways for range [left, right] to evaluate to isTrue...
      -> left and right range from 0 to n-1 → n² range pairs...
      -> isTrue is either 0 or 1 → 2 outcome values...
      -> Total states: n² × 2 = O(n²)...

    Step-by-Step Trace (exp = "T&T"):
      -> n=3, operators at index 1...
      -> Base: dp[0][0][1]=1 (T is true), dp[2][2][1]=1 (T is true)...
      -> dp[0][2][1]: idx=1 (&):
           leftTrue=1, leftFalse=0, rightTrue=1, rightFalse=0...
           AND true: LT × RT = 1 × 1 = 1...
           ways = 1 → dp[0][2][1] = 1...
      -> Result: 1...

    Modulo Arithmetic:
      -> MOD = 1_000_000_007 (10^9 + 7, standard prime modulus)...
      -> Products leftTrue × rightTrue can overflow int → computed as long before mod...
      -> Each intermediate sum is reduced by MOD before accumulating...
      -> Final result stored back as int after modulo...

    Why Both isTrue=0 and isTrue=1 Are Needed:
      -> The operator combines left and right counts from both outcomes...
      -> Example: AND true = LT × RT requires only the true counts...
      -> Example: AND false = LT × RF + LF × RT + LF × RF requires both true and false counts...
      -> Without tracking false counts, most operators cannot be correctly evaluated...

    Edge Cases:
      -> Single operand T → isTrue=1 returns 1...
      -> Single operand F → isTrue=1 returns 0...
      -> F&T → AND requires both true → 0 ways to be true...
      -> T|T → OR with any true → 1 way (only one parenthesization exists)...

    Time and Space Complexity:
      -> Time:  O(n³) — n² states × O(n) operator loop per state...
      -> Space: O(n² × 2) = O(n²) for the dp table...

    Applications:
      -> Counting valid parse trees for boolean formulas in logic programming...
      -> Propositional logic satisfiability counting with fixed operators...
      -> Competitive programming partition DP on alternating operator/operand strings...
      -> Compiler optimization: analyzing the number of equivalent expression trees...

*/

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
