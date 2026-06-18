package Recursion;

/*

    Description:
      Following program generates all subsequences of a string using three distinct
        recursive approaches, each exploring the binary include/exclude decision tree...

    Problem Statement:
      -> Given a string s of length n...
      -> A subsequence is formed by deleting zero or more characters from s...
      -> The relative order of remaining characters must be preserved...
      -> Characters need not be consecutive...
      -> Generate and return all 2ⁿ possible subsequences including the empty string...

    Key Insight:
      -> For each character in the string, exactly two choices exist:
           Include it in the current subsequence...
           Exclude it from the current subsequence...
      -> Making these binary choices for all n characters produces 2ⁿ subsequences...
      -> Recursion naturally models this decision tree by branching at every character...

    Example:
      -> s = "abc":
           All 8 subsequences: "", "a", "b", "c", "ab", "ac", "bc", "abc"...
           Each corresponds to a unique include/exclude pattern for a, b, c...
      -> s = "ab":
           All 4 subsequences: "", "a", "b", "ab"...
      -> s = "" (empty):
           Only 1 subsequence: "" (the empty subsequence)...

    Approach 1 - optimal() (Index-Based Recursion with ArrayList):
      -> Traverse string using an index idx starting at 0...
      -> At each index, two recursive calls are made:
           Exclude current character: optimal(idx+1, ans, s, li)...
           Include current character: optimal(idx+1, ans + s.charAt(idx), s, li)...
      -> Base case: idx >= s.length() → add ans to result list...
      -> Exclude is called first, giving empty-string-first ordering in result...

    Decision Tree For "ab" (Approach 1):
      -> (idx=0, ans=""):
           Exclude 'a' → (idx=1, ans="")...
             Exclude 'b' → (idx=2, ans="")  → add ""...
             Include 'b' → (idx=2, ans="b") → add "b"...
           Include 'a' → (idx=1, ans="a")...
             Exclude 'b' → (idx=2, ans="a")  → add "a"...
             Include 'b' → (idx=2, ans="ab") → add "ab"...
      -> Result order: ["", "b", "a", "ab"]...

    Approach 2 - getSubsequences() (String Reduction):
      -> Reduces the string by removing the first character at each recursive call...
      -> Recursively get all subsequences of the remaining suffix s[1:]...
      -> For each subsequence in the helper result:
           Add it as-is (exclude currChar)...
           Add currChar prepended to it (include currChar)...
      -> Base case: empty string → return list containing only ""...
      -> Builds result bottom-up by combining smaller subproblems...

    Step-by-Step Trace of getSubsequences("abc"):
      -> f("abc"): currChar='a', helper = f("bc")...
      -> f("bc"):  currChar='b', helper = f("c")...
      -> f("c"):   currChar='c', helper = f("")...
      -> f(""):    return [""]...
      -> f("c"):   from [""]:  add "" and "c" → return ["", "c"]...
      -> f("bc"):  from ["","c"]: add "", "c", "b", "bc" → return ["","c","b","bc"]...
      -> f("abc"): from each: add as-is and with 'a' → return ["","c","b","bc","a","ac","ab","abc"]...

    Approach 3 - printSubsequences() (Print While Building):
      -> Similar structure to approach 2 but prints directly instead of storing...
      -> Passes current built subsequence as currAns parameter...
      -> Include is tried first: printSubsequences(remaining, currAns + currChar)...
      -> Exclude is tried second: printSubsequences(remaining, currAns)...
      -> Base case: string empty → print currAns...
      -> No ArrayList needed → O(n) auxiliary space only...

    Comparison of All Three Approaches:
      -> optimal():
           Parameters: index + accumulating answer string + result list...
           Order: exclude-first (empty string appears first)...
           Stores results in external ArrayList...
      -> getSubsequences():
           Parameters: remaining suffix string...
           Order: exclude-first within each level, builds bottom-up...
           Returns ArrayList directly from each recursive frame...
      -> printSubsequences():
           Parameters: remaining string + current built answer...
           Order: include-first (longer strings printed first)...
           No storage → space-efficient, output only...

    Why 2ⁿ Subsequences?
      -> Each character independently contributes a binary choice...
      -> n independent binary decisions → 2 × 2 × ... × 2 (n times) = 2ⁿ outcomes...
      -> Example: "abc" → 2³ = 8 subsequences...
      -> Empty string "": 2⁰ = 1 subsequence (the empty string itself)...

    Handling Duplicate Characters:
      -> These approaches do not deduplicate by value...
      -> "aa" produces 4 entries: "", "a", "a", "aa" (two identical "a" entries)...
      -> Deduplication requires a HashSet or sorting-based approach...

    Edge Cases:
      -> Empty string → only one subsequence: the empty string ""...
      -> Single character → two subsequences: "" and that character...
      -> All identical characters → 2ⁿ subsequences with duplicate strings in result...
      -> Long strings → exponential growth in output size (caution with n > 20)...

    Time and Space Complexity:
      -> Time:  O(n × 2ⁿ) — 2ⁿ subsequences, each of average length n/2...
      -> Space (optimal + getSubsequences): O(n × 2ⁿ) for storing all subsequences...
      -> Space (printSubsequences):      O(n) recursion depth only, no storage...

    Applications:
      -> Power set generation in combinatorics...
      -> Subset-sum and knapsack problem enumeration...
      -> Brute-force search over all possible selections...
      -> Generating test cases for string-based problems...
      -> Foundation for backtracking and pruning algorithms...

*/

import java.util.ArrayList;

public class Subsequences {

    private static void optimal(int idx, String ans, String s, ArrayList<String> li) {
        // Base case
        if (idx >= s.length()) {
            li.add(ans);
            return;
        }

        optimal(idx + 1, ans, s, li);
        optimal(idx + 1, ans + s.charAt(idx), s, li);

    }

    private static ArrayList<String> getSubsequences(String s) {

        ArrayList<String> ans = new ArrayList<>();
        if (s.isEmpty()) {
            ans.add("");
            return ans;
        }

        char currChar = s.charAt(0);
        ArrayList<String> helper = getSubsequences(s.substring(1));

        for (String temp : helper) {
            ans.add(temp);
            ans.add(currChar + temp);
        }
        return ans;
    }

    private static void printSubsequences(String s, String currAns) {

        if (s.isEmpty()) {
            System.out.print(currAns + ",");
            return;
        }
        char currChar = s.charAt(0);
        String remaining = s.substring(1);

        printSubsequences(remaining, currAns + currChar);
        printSubsequences(remaining, currAns);
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    SUBSEQUENCES                              ║");
        System.out.println("║  Generate all subsequences of a string                       ║");
        System.out.println("║  Subsequence: derived by deleting some/no elements           ║");
        System.out.println("║  Order maintained, but not necessarily consecutive           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Simple String \"ab\" ===");
        String s1 = "ab";
        System.out.println("Input: \"" + s1 + "\"");
        System.out.println("\nRecursive tree (including/excluding each character):");
        System.out.println("                \"\"");
        System.out.println("               /  \\");
        System.out.println("          exclude include");
        System.out.println("           'a'      'a'");
        System.out.println("          /  \\      /  \\");
        System.out.println("     \"\"  \"a\" \"b\" \"ab\"");
        System.out.println("\nAll subsequences: \"\", \"a\", \"b\", \"ab\"");
        System.out.println("Total count: 2² = 4\n");

        ArrayList<String> result1 = getSubsequences(s1);
        System.out.println("✓ Result via getSubsequences():");
        for (String temp : result1) {
            System.out.print("\"" + temp + "\" ");
        }
        System.out.println("\n  Status: " + (result1.size() == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Three Character String \"abc\" ===");
        String s2 = "abc";
        System.out.println("Input: \"" + s2 + "\"");
        System.out.println("\nDecision tree: For each character, include or exclude");
        System.out.println("Total subsequences: 2³ = 8\n");

        ArrayList<String> result2 = getSubsequences(s2);
        System.out.println("✓ Result via getSubsequences():");
        System.out.print("  [");
        for (int i = 0; i < result2.size(); i++) {
            System.out.print("\"" + result2.get(i) + "\"");
            if (i < result2.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("  Status: " + (result2.size() == 8 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Character \"x\" ===");
        String s3 = "x";
        System.out.println("Input: \"" + s3 + "\"");
        System.out.println("\nSubsequences: \"\", \"x\"");
        System.out.println("Total count: 2¹ = 2\n");

        ArrayList<String> result3 = getSubsequences(s3);
        System.out.println("✓ Result via getSubsequences():");
        for (String temp : result3) {
            System.out.print("\"" + temp + "\" ");
        }
        System.out.println("\n  Status: " + (result3.size() == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Empty String \"\" ===");
        String s4 = "";
        System.out.println("Input: \"\" (empty)");
        System.out.println("\nOnly one subsequence: \"\" (empty)");
        System.out.println("Total count: 2⁰ = 1\n");

        ArrayList<String> result4 = getSubsequences(s4);
        System.out.println("✓ Result via getSubsequences():");
        for (String temp : result4) {
            System.out.print("\"" + temp + "\" ");
        }
        System.out.println("\n  Status: " + (result4.size() == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Two Character String \"xy\" ===");
        String s5 = "xy";
        System.out.println("Input: \"" + s5 + "\"");
        System.out.println("\nDecision for each character:");
        System.out.println("  x: include or exclude → 2 choices");
        System.out.println("  y: include or exclude → 2 choices");
        System.out.println("Total: 2 × 2 = 4 subsequences");
        System.out.println("Subsequences: \"\", \"x\", \"y\", \"xy\"\n");

        ArrayList<String> result5 = getSubsequences(s5);
        System.out.println("✓ Result via getSubsequences():");
        for (String temp : result5) {
            System.out.print("\"" + temp + "\" ");
        }
        System.out.println("\n  Status: " + (result5.size() == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: String with Repeated Characters \"aa\" ===");
        String s6 = "aa";
        System.out.println("Input: \"" + s6 + "\"");
        System.out.println("\nSubsequences (even with duplicates):");
        System.out.println("  Both excluded: \"\"");
        System.out.println("  First 'a' included: \"a\"");
        System.out.println("  Second 'a' included: \"a\"");
        System.out.println("  Both included: \"aa\"");
        System.out.println("Note: Result may contain duplicates depending on implementation");
        System.out.println("Total count: 2² = 4 (with possible duplicates)\n");

        ArrayList<String> result6 = getSubsequences(s6);
        System.out.println("✓ Result via getSubsequences():");
        for (String temp : result6) {
            System.out.print("\"" + temp + "\" ");
        }
        System.out.println("\n  Status: " + (result6.size() == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: String \"abcd\" ===");
        String s7 = "abcd";
        System.out.println("Input: \"" + s7 + "\"");
        System.out.println("\nFor each of 4 characters: include or exclude");
        System.out.println("Total subsequences: 2⁴ = 16\n");

        ArrayList<String> result7 = getSubsequences(s7);
        System.out.println("✓ Result via getSubsequences():");
        System.out.println("  Count: " + result7.size());
        System.out.print("  Subsequences: ");
        for (int i = 0; i < result7.size(); i++) {
            System.out.print("\"" + result7.get(i) + "\"");
            if (i < result7.size() - 1) System.out.print(", ");
        }
        System.out.println("\n  Status: " + (result7.size() == 16 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Generate all subsequences of a string              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  What is a Subsequence?                                      ║");
        System.out.println("║    A sequence derived from original by:                      ║");
        System.out.println("║    • Deleting some or no elements                            ║");
        System.out.println("║    • Maintaining relative order of remaining elements        ║");
        System.out.println("║    • Elements need not be consecutive                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: String \"abc\"                                       ║");
        System.out.println("║    Subsequences: \"\", \"a\", \"b\", \"c\", \"ab\", \"ac\", \"bc\", \"abc\"  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight:                                                ║");
        System.out.println("║    For each character, we have 2 choices:                    ║");
        System.out.println("║      1. Include it in the subsequence                        ║");
        System.out.println("║      2. Exclude it from the subsequence                      ║");
        System.out.println("║    For n characters: 2ⁿ total subsequences                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  APPROACH 1: optimal() - Index-based Recursion                  ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Parameters: idx (current position), ans (current result)    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Logic:                                                      ║");
        System.out.println("║    Base case: if (idx >= s.length())                         ║");
        System.out.println("║      Found complete subsequence, add to result               ║");
        System.out.println("║                                                              ║");
        System.out.println("║    Recursive case:                                           ║");
        System.out.println("║      1. Call without including current character             ║");
        System.out.println("║         optimal(idx+1, ans, s, li)                              ║");
        System.out.println("║      2. Call with including current character                ║");
        System.out.println("║         optimal(idx+1, ans + s[idx], s, li)                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Decision tree for \"ab\":                                     ║");
        System.out.println("║                  (0, \"\")                                     ║");
        System.out.println("║                  /      \\                                    ║");
        System.out.println("║        exclude  /        \\  include                          ║");
        System.out.println("║              (1,\"\")    (1,\"a\")                               ║");
        System.out.println("║              /   \\      /   \\                                ║");
        System.out.println("║           (2,\") (2,\"b\")(2,\"a\")(2,\"ab\")                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  APPROACH 2: getSubsequences() - String Reduction            ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Logic: Reduce string by removing first character            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Algorithm:                                                  ║");
        System.out.println("║    Base case: if string is empty                             ║");
        System.out.println("║      return {\"\"} (one empty subsequence)                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║    Recursive case:                                           ║");
        System.out.println("║      1. Get first character: currChar = s[0]                 ║");
        System.out.println("║      2. Get all subsequences of remaining: helper = f(s[1:]) ║");
        System.out.println("║      3. For each subsequence in helper:                      ║");
        System.out.println("║         - Add it as is (exclude currChar)                    ║");
        System.out.println("║         - Add with currChar prefix (include currChar)        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: \"abc\"                                              ║");
        System.out.println("║    f(\"abc\"):                                                 ║");
        System.out.println("║      char = 'a', helper = f(\"bc\")                            ║");
        System.out.println("║      f(\"bc\"):                                                ║");
        System.out.println("║        char = 'b', helper = f(\"c\")                           ║");
        System.out.println("║        f(\"c\"):                                               ║");
        System.out.println("║          char = 'c', helper = f(\"\") = {\"\"}                   ║");
        System.out.println("║          return {\"\", \"c\"}                                    ║");
        System.out.println("║        return {\"\", \"c\", \"b\", \"bc\"}                           ║");
        System.out.println("║      return {\"\", \"c\", \"b\", \"bc\", \"a\", \"ac\", \"ab\", \"abc\"}     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  APPROACH 3: printSubsequences() - Print While Building      ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Logic: Similar to approach 2 but prints instead of storing  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Decision Order:                                             ║");
        System.out.println("║    First call: printSubsequences(remaining, currAns+currChar)║");
        System.out.println("║    Second call: printSubsequences(remaining, currAns)        ║");
        System.out.println("║    This gives include-first order                            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity:                                            ║");
        System.out.println("║    All approaches: O(n × 2ⁿ)                                 ║");
        System.out.println("║    n = string length                                         ║");
        System.out.println("║    2ⁿ = number of subsequences                               ║");
        System.out.println("║    n = average length of each subsequence                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Space Complexity:                                           ║");
        System.out.println("║    O(n) recursion depth + O(n × 2ⁿ) for storing all           ║");
        System.out.println("║    Approach 3 uses O(n) only (no storage)                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Comparison of Approaches:                                   ║");
        System.out.println("║    optimal():              Simple, modular, index-based         ║");
        System.out.println("║    getSubsequences():   Elegant, reduces string each time    ║");
        System.out.println("║    printSubsequences(): Space efficient, direct output       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Takeaways:                                              ║");
        System.out.println("║    • 2ⁿ subsequences for string of length n                   ║");
        System.out.println("║    • Binary choice for each character (include/exclude)      ║");
        System.out.println("║    • Multiple recursive approaches possible                  ║");
        System.out.println("║    • Explore all possibilities via recursion                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }


}