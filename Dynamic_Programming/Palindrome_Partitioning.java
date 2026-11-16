package Dynamic_Programming;

/*

    Description:
      Following program finds the minimum number of cuts needed to partition a string
        such that every piece is a palindrome, using three cross-verified approaches...

    Problem Statement:
      -> Given a string st of length n...
      -> Partition it into palindromic substrings using the minimum number of cuts...
      -> A single character is always a palindrome...
      -> The whole string is a valid single partition (0 cuts) if it is itself a palindrome...
      -> Return the minimum number of cuts (partitions - 1)...

    Key Insight:
      -> Front partition approach: from index curr, try every endpoint idx such that
           st[curr..idx] is a palindrome, then recursively solve the remaining suffix...
      -> Each palindromic prefix costs 1 piece (not 1 cut) in the recursive count...
      -> Cuts = total pieces - 1, so subtract 1 from the final piece count...
      -> State is just the starting index curr → 1D DP array suffices...

    Example:
      -> st = "aab":
           f(0): try "a"(palindrome)→1+f(1), try "aa"(palindrome)→1+f(2), try "aab"(no)...
           f(1) = 1+"b"→1+f(2) = 1+1 = 2...
           f(2) = 1+"b"→1+f(3) = 1+0 = 1...
           f(0) = min(1+2, 1+1) = min(3, 2) = 2 pieces → cuts = 2-1 = 1...
           Partition: "aa" | "b"...
      -> st = "racecar": entire string is palindrome → f(0)=1 → cuts = 0...
      -> st = "abcd": no two adjacent characters match → each char alone → f(0)=4 → cuts=3...

    isPalindrome(st, curr, j) Helper:
      -> Two pointer approach: compare st[curr] and st[j]...
      -> If any mismatch found: return false immediately...
      -> Advance curr forward and j backward until they meet or cross...
      -> Return true if all compared characters matched...
      -> Time per call: O(length of substring) = O(n) worst case...

    Recursive Relation:
      -> Base: curr == n → return 0 (no characters left, no piece needed)...
      -> For each idx from curr to n-1:
           If isPalindrome(st, curr, idx):
             cost = 1 + recursive(idx+1, n)...
             minCost = Math.min(minCost, cost)...
      -> Return minCost...
      -> Answer = recursive(0, n) - 1 (convert piece count to cut count)...

    Approach 1 - Recursive:
      -> Pure top-down without caching...
      -> Recomputes f(curr) multiple times for the same curr...
      -> Time: O(n² × n) = O(n³) without memoization (exponential calls in practice)...
      -> Space: O(n) recursion stack...

    Approach 2 - Memoization (Top-Down DP):
      -> 1D dp array of size n, initialized to -1...
      -> dp[curr] caches minimum piece count from position curr to end...
      -> Each unique curr computed once → O(n) states × O(n) choices × O(n) palindrome check...
      -> Time: O(n³), Space: O(n) + O(n) recursion stack...

    Approach 3 - Tabulation (Bottom-Up DP):
      -> dp[curr] = minimum piece count for suffix starting at curr...
      -> dp[n] = 0 (base case)...
      -> Fill right to left: curr from n-1 down to 0...
      -> For each idx from curr to n-1: if palindrome, dp[curr] = min(dp[curr], 1+dp[idx+1])...
      -> Answer: dp[0] - 1...
      -> Time: O(n³), Space: O(n)...

    Why Subtract 1 for Cuts:
      -> The DP counts number of palindromic pieces (each piece contributes 1 to cost)...
      -> Cuts = pieces - 1 (k pieces require k-1 cuts between them)...
      -> Example: "aa"|"b" = 2 pieces = 1 cut...
      -> Subtracting 1 at the end converts the piece count to the cut count...

    Why 1D DP Works (Unlike 2D):
      -> Each subproblem only depends on the starting index curr (not an end index)...
      -> Once a palindromic prefix st[curr..idx] is identified, the sub-problem is simply f(idx+1)...
      -> The end of the range is implicitly always n (end of string)...
      -> This distinguishes palindrome partitioning from Matrix Chain Multiplication (which is 2D)...

    Step-by-Step Trace (st = "abacdc"):
      -> f(5)=1 ("c"=palindrome), f(4)=1 ("d"), f(3)=2 ("d"|"c"), f(2)=min(1+f(3),1+f(5))=min(3,2)=2...
      -> f(1)=min(1+f(2),1+f(4))="b"→1+2=3, "ba"→no... wait "ba" is not palindrome...
      -> f(0)=min("a"→1+f(1), "aba"→1+f(3), "abacdc"→check...)...
           "aba" is palindrome: 1+f(3)=1+2=3...
           "a": 1+f(1)=1+min(...)...
           "cdc" in f(3): "c"→1+f(5)=2, "cd"→no, "cdc"→1+f(6)=1+0=1...
           f(3)=1 → f(0) via "aba": 1+1=2 pieces → cuts=1...

    Edge Cases:
      -> Single character → f(0)=1 → cuts=0...
      -> Entire string is a palindrome → f(0)=1 → cuts=0...
      -> All distinct characters → each char alone → f(0)=n → cuts=n-1...
      -> All same characters → entire string is palindrome → f(0)=1 → cuts=0...

    Time and Space Complexity:
      -> Time:  O(n³) — n states × n choices per state × O(n) palindrome check per choice...
      -> Space: O(n) for the 1D dp array (plus O(n) recursion stack for memoization)...

    Applications:
      -> Minimum edit operations on string sequences in text processing...
      -> DNA analysis for palindromic subsequence segmentation...
      -> Text compression where palindromic blocks have special encoding...
      -> Competitive programming partition DP problems with palindrome constraints...

*/
    
import java.util.Arrays;

public class Palindrome_Partitioning {

    private static boolean isPalindrome(String st, int curr, int j) {

        while (curr < j) {
            if (st.charAt(curr) != st.charAt(j)) {
                return false;
            }
            curr++;
            j--;
        }

        return true;
    }

    private static int recursive(String st, int curr, int n) {

        if (curr == n) {
            return 0;
        }

        int minCost = Integer.MAX_VALUE;
        for (int idx = curr; idx < n; idx++) {
            if (isPalindrome(st, curr, idx)) {
                int cost = 1 + recursive(st, idx + 1, n);
                minCost = Math.min(minCost, cost);
            }
        }

        return minCost;
    }

    private static int memoization(String st, int curr, int n, int[] dp) {

        if (curr == n) {
            return 0;
        }

        if (dp[curr] != - 1) {
            return dp[curr];
        }

        int minCost = Integer.MAX_VALUE;
        for (int idx = curr; idx < n; idx++) {
            if (isPalindrome(st, curr, idx)) {
                int cost = 1 + memoization(st, idx + 1, n, dp);
                minCost = Math.min(minCost, cost);
            }
        }

        return dp[curr] = minCost;
    }

    private static int tabulation(String st, int n) {

        int[] dp = new int[n + 1];

        for (int curr = n - 1; curr >= 0; curr--) {
            int minCost = Integer.MAX_VALUE;

            for (int idx = curr; idx < n; idx++) {
                if (isPalindrome(st, curr, idx)) {
                    int cost = 1 + dp[idx + 1];
                    minCost = Math.min(minCost, cost);
                }
            }

            dp[curr] = minCost;
        }

        return dp[0] - 1;
    }

    private static int minCut(String st) {

        int n = st.length();

        int[] dp = new int[n];
        Arrays.fill(dp, - 1);

        int recursiveResult = recursive(st, 0, n) - 1;
        int memoizationResult = memoization(st, 0, n, dp) - 1;
        int tabulationResult = tabulation(st, n);

        System.out.println("  [Verification] recursive: " + recursiveResult
            + " | memoization: " + memoizationResult
            + " | tabulation: " + tabulationResult);

        if (recursiveResult != memoizationResult || memoizationResult != tabulationResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return tabulationResult;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  PALINDROME PARTITIONING                     ║");
        System.out.println("║  Find the minimum number of cuts needed so that every piece  ║");
        System.out.println("║  of the string is a palindrome                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        String st1 = "aab";
        System.out.println("String: \"" + st1 + "\"");
        System.out.println("\nPartition: \"aa\" | \"b\" → 1 cut\n");

        int result1 = minCut(st1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result1 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Single Character ===");
        String st2 = "a";
        System.out.println("String: \"" + st2 + "\"");
        System.out.println("\nA single character is already a palindrome → 0 cuts\n");

        int result2 = minCut(st2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result2 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Already a Palindrome ===");
        String st3 = "racecar";
        System.out.println("String: \"" + st3 + "\"");
        System.out.println("\nWhole string is a palindrome → 0 cuts\n");

        int result3 = minCut(st3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Two Different Characters ===");
        String st4 = "ab";
        System.out.println("String: \"" + st4 + "\"");
        System.out.println("\nPartition: \"a\" | \"b\" → 1 cut\n");

        int result4 = minCut(st4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result4 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: All Distinct Characters ===");
        String st5 = "abcd";
        System.out.println("String: \"" + st5 + "\"");
        System.out.println("\nNo palindrome longer than 1 → every char separate → 3 cuts\n");

        int result5 = minCut(st5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result5 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: All Same Characters ===");
        String st6 = "aaaa";
        System.out.println("String: \"" + st6 + "\"");
        System.out.println("\nWhole string is a palindrome → 0 cuts\n");

        int result6 = minCut(st6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Multiple Palindromic Pieces ===");
        String st7 = "abacdc";
        System.out.println("String: \"" + st7 + "\"");
        System.out.println("\nPartition: \"aba\" | \"cdc\" → 1 cut\n");

        int result7 = minCut(st7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result7 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                           ║");
        System.out.println("║  ─────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Partition a string into palindromic pieces using    ║");
        System.out.println("║           the fewest cuts                                     ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Key Insight: Front Partitioning                              ║");
        System.out.println("║    From index curr, try every end idx such that               ║");
        System.out.println("║    st[curr..idx] is a palindrome, then solve the rest         ║");
        System.out.println("║    starting at idx+1. Each piece costs 1.                     ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Recurrence:                                                  ║");
        System.out.println("║    f(curr) = min over palindromic st[curr..idx] of            ║");
        System.out.println("║              1 + f(idx+1)                                     ║");
        System.out.println("║    Base: f(n) = 0                                             ║");
        System.out.println("║    Answer = f(0) - 1 (pieces counted, but cuts = pieces - 1)  ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Three Approaches, Cross-Verified:                            ║");
        System.out.println("║    1) Recursive — tries every palindromic prefix              ║");
        System.out.println("║    2) Memoization — top-down, caches dp[curr]                 ║");
        System.out.println("║    3) Tabulation — bottom-up, curr from n-1 down to 0         ║");
        System.out.println("║                                                               ║");
        System.out.println("║  minCut() calls all three, prints each result, flags any      ║");
        System.out.println("║  disagreement, and returns the tabulation answer.             ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Example: \"aab\"                                               ║");
        System.out.println("║    f(2)=1 (\"b\"), f(1)=2 (\"a\"+\"b\"), f(0)=min(1+f(1), 1+f(2))   ║");
        System.out.println("║    =min(3, 2)=2 → cuts = 2 - 1 = 1                            ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Properties:                                                  ║");
        System.out.println("║    • isPalindrome uses two pointers, O(n) per check           ║");
        System.out.println("║    • The -1 converts piece count into cut count               ║");
        System.out.println("║    • Same partition pattern as Matrix Chain Multiplication    ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Time Complexity: O(n³) — n states, n choices, O(n) check     ║");
        System.out.println("║  Space Complexity: O(n) for dp (recursion adds O(n) stack)    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

    }

}
