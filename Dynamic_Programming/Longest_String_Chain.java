package Dynamic_Programming;

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