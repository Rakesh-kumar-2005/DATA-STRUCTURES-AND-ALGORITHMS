package Dynamic_Programming;

/*

    Description:
      Following program finds the length of the longest string chain where each word
        is formed by inserting exactly one character into the previous word...

    Problem Statement:
      -> Given an array of words (lowercase English strings)...
      -> Word A is a predecessor of word B if inserting exactly one character at any
           position in A produces B...
      -> A string chain is a sequence of words where each word is a predecessor of the next...
      -> Return the length of the longest possible string chain...

    Key Insight:
      -> Sorting words by length ensures predecessors always come before successors...
      -> This mirrors the LIS structure: "strictly shorter" replaces "strictly smaller"...
      -> dp[i] = longest chain ending at words[i], computed via the same O(n²) DP...
      -> The predecessor check reduces to: verify words[j] is a subsequence of words[i]
           with exactly one character removed (i.e., length difference = 1 and subsequence match)...

    Example:
      -> words = ["a", "b", "ba", "bca", "bda", "bdca"]:
           Sorted by length: a, b, ba, bca, bda, bdca...
           Chain: a → ba → bda → bdca (length 4)...
           dp = [1, 1, 2, 3, 3, 4]...
      -> words = ["xbc", "pcxbcf", "xb", "cxbc", "pcxbc"]:
           Chain: xb → xbc → cxbc → pcxbc → pcxbcf (length 5)...
      -> words = ["a", "abc"]:
           Length difference = 2, not 1 → no valid link → length 1...

    checkPossible(s1, s2) Logic:
      -> First check: s1.length() == s2.length() + 1 (s1 is exactly one longer)...
      -> Use two pointers (first on s1, second on s2)...
      -> Walk first through s1: if characters match, advance both pointers...
      -> If characters differ, advance only first (skip this s1 character)...
      -> After loop: valid predecessor if both pointers fully traversed (first==n, second==m)...
      -> This verifies s2 is a subsequence of s1 with exactly one character removed...

    Step-by-Step Trace of checkPossible("bda", "ba"):
      -> s1="bda" (len=3), s2="ba" (len=2), lengths differ by 1 ✓...
      -> first=0, second=0: s1[0]='b' == s2[0]='b' → first=1, second=1...
      -> first=1, second=1: s1[1]='d' != s2[1]='a' → first=2 (skip 'd')...
      -> first=2, second=1: s1[2]='a' == s2[1]='a' → first=3, second=2...
      -> first=3=n, second=2=m → both exhausted → return true...

    Algorithm Steps:
      -> Sort words by length in ascending order using a custom Comparator...
      -> Initialize dp[i] = 1 for all i (every word alone is a chain of length 1)...
      -> For each i from 1 to n-1:
           For each j from 0 to i-1:
             If checkPossible(words[i], words[j]) AND dp[j]+1 > dp[i]:
               dp[i] = dp[j] + 1...
           maxLength = Math.max(maxLength, dp[i])...
      -> Return maxLength...

    Why Sort by Length First:
      -> A predecessor must be strictly shorter (length - 1)...
      -> After sorting, every valid predecessor j has index j < i...
      -> The inner loop j from 0 to i-1 always covers all shorter words...
      -> Without sorting, predecessors could appear after successors in the array...

    Comparison With LIS:
      -> LIS: arr[j] < arr[i] (numerical ordering)...
      -> Longest String Chain: checkPossible(words[i], words[j]) (subsequence + length)...
      -> Both: dp[i] = max(dp[j] + 1) over all valid j < i...
      -> Both: sort first to enforce the predecessor-comes-before constraint...
      -> Identical DP recurrence structure, different "predecessor" predicate...

    Edge Cases:
      -> Single word → loop never executes → dp[0]=1 → maxLength=1...
      -> All same length → checkPossible always fails (length check) → all dp[i]=1 → 1...
      -> Length gap of 2 or more → checkPossible returns false immediately → no chain link...
      -> Two words, one predecessor of the other → chain length 2...

    Time and Space Complexity:
      -> Time:  O(n² × L) where n = number of words, L = average word length...
               n² pairs × O(L) for each checkPossible call...
      -> Space: O(n) for the dp array (sorting mutates words array in-place)...

    Applications:
      -> Word ladder and word evolution problems in NLP...
      -> DNA sequence extension analysis in bioinformatics...
      -> Version history chaining where each version adds one feature...
      -> Competitive programming problems on subsequence-based chain building...

*/

import java.util.Arrays;
import java.util.Comparator;

public class Longest_String_Chain {

    private static boolean checkPossible(String s1, String s2) {

        if (s1.length() != s2.length() + 1) {
            return false;
        }

        int n = s1.length();
        int m = s2.length();

        int first = 0;
        int second = 0;

        while (first < n) {
            if (second < m && s1.charAt(first) == s2.charAt(second)) {
                first++;
                second++;
            } else {
                first++;
            }
        }

        if (first == n && second == m) {
            return true;
        }

        return false;
    }

    private static int longestStrChain(String[] words) {

        int n = words.length;
        int[] dp = new int[n];
        int maxLength = 1;

        Arrays.fill(dp, 1);
        Arrays.sort(words, new Comparator<String>() {

            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (checkPossible(words[i], words[j]) && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                }
            }

            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  LONGEST STRING CHAIN                        ║");
        System.out.println("║  Find the longest chain where each word is formed by adding  ║");
        System.out.println("║  exactly one character to the previous word                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        String[] words1 = {"a", "b", "ba", "bca", "bda", "bdca"};
        System.out.println("Input: " + Arrays.toString(words1));
        System.out.println("\nChain: a -> ba -> bda -> bdca (length 4)\n");

        int result1 = longestStrChain(words1.clone());
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result1 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: No Valid Chains ===");
        String[] words2 = {"xbc", "pcxbcf", "xb", "cxbc", "pcxbc"};
        System.out.println("Input: " + Arrays.toString(words2));
        System.out.println("\nChain: xb -> xbc -> cxbc -> pcxbc -> pcxbcf (length 5)\n");

        int result2 = longestStrChain(words2.clone());
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result2 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Word ===");
        String[] words3 = {"abcd"};
        System.out.println("Input: " + Arrays.toString(words3));
        System.out.println("\nOnly one word, chain length is trivially 1\n");

        int result3 = longestStrChain(words3.clone());
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result3 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: All Same Length, No Chain ===");
        String[] words4 = {"abc", "def", "ghi"};
        System.out.println("Input: " + Arrays.toString(words4));
        System.out.println("\nAll words same length, no predecessor relation possible → length 1\n");

        int result4 = longestStrChain(words4.clone());
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result4 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Simple Two-Link Chain ===");
        String[] words5 = {"a", "ab"};
        System.out.println("Input: " + Arrays.toString(words5));
        System.out.println("\nChain: a -> ab (length 2)\n");

        int result5 = longestStrChain(words5.clone());
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result5 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Length Gap Skips a Link ===");
        String[] words6 = {"a", "abc"};
        System.out.println("Input: " + Arrays.toString(words6));
        System.out.println("\nLength difference is 2, not exactly 1 → no valid link, chain length 1\n");

        int result6 = longestStrChain(words6.clone());
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result6 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the longest chain of words where each next    ║");
        System.out.println("║           word is formed by inserting exactly one character  ║");
        System.out.println("║           into the previous word (as a subsequence check)    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Sort by Length First, Then Do LIS-Style DP     ║");
        System.out.println("║    A word can only extend a chain from a shorter word, so    ║");
        System.out.println("║    sorting by length guarantees predecessors are processed   ║");
        System.out.println("║    before their successors — just like LIS.                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: checkPossible — Verify a Valid Predecessor         ║");
        System.out.println("║    Require s1.length() == s2.length() + 1                    ║");
        System.out.println("║    Greedily match s2 as a subsequence within s1              ║");
        System.out.println("║    Both pointers must fully traverse for a valid match       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: DP Over Sorted Words                               ║");
        System.out.println("║    dp[i]: length of longest chain ending at word i(default 1)║");
        System.out.println("║    For each i, check all j < i: if words[j] can precede      ║");
        System.out.println("║    words[i] and dp[j]+1 > dp[i], update dp[i]                ║");
        System.out.println("║    Track the running maximum across all dp[i]                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: words = [\"a\",\"b\",\"ba\",\"bca\",\"bda\",\"bdca\"]          ║");
        System.out.println("║    Sorted by length: a,b (len1), ba (len2), bca,bda (len3),  ║");
        System.out.println("║    bdca (len4)                                               ║");
        System.out.println("║    a -> ba -> bda -> bdca forms the longest chain (length 4) ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • checkPossible only allows a length difference of exactly║");
        System.out.println("║      1 character                                             ║");
        System.out.println("║    • Sorting mutates the input words array in place          ║");
        System.out.println("║    • Multiple valid longest chains may exist                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n² × L) — n² word pairs, L for the       ║");
        System.out.println("║                    subsequence check per pair                ║");
        System.out.println("║  Space Complexity: O(n) for the dp array                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
