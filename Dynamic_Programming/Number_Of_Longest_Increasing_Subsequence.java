package Dynamic_Programming;

/*

    Description:
      Following program counts the number of distinct longest increasing subsequences
        of an integer array, by tracking both the LIS length and the count at each index...

    Problem Statement:
      -> Given an integer array nums...
      -> Find the length of the longest strictly increasing subsequence (LIS)...
      -> Count how many distinct subsequences achieve exactly that maximum LIS length...
      -> Return the count modulo 10^9 + 7...

    Key Insight:
      -> Extend the classic O(n²) LIS dp[] with a parallel count[] array...
      -> count[i] = number of distinct increasing subsequences of length dp[i] ending at i...
      -> When a new predecessor j gives a strictly longer path: reset count[i] = count[j]...
      -> When a predecessor j gives an equally long path: accumulate count[i] += count[j]...
      -> After all indices are processed, sum count[i] for all i where dp[i] == maxLen...

    Example:
      -> nums = [1, 3, 5, 4, 7]:
           dp    = [1, 2, 3, 3, 4]...
           count = [1, 1, 1, 1, 2]...
           maxLen = 4, result = count[4] = 2...
           Two LIS: [1,3,5,7] and [1,3,4,7]...
      -> nums = [2, 2, 2, 2, 2]:
           No element extends another (strictly increasing fails for equal values)...
           dp = [1,1,1,1,1], count = [1,1,1,1,1]...
           maxLen = 1, result = 5 (each element is its own LIS)...
      -> nums = [1, 2, 3, 4, 5]:
           Only one LIS (the full array) → count = 1...

    Transition Logic (for each pair where nums[i] > nums[j]):
      -> Case 1: dp[j] + 1 > dp[i] (found a strictly longer LIS ending at i):
           dp[i] = dp[j] + 1...
           count[i] = count[j]  ← discard old count, adopt predecessor's count...
      -> Case 2: dp[j] + 1 == dp[i] (found an equally long LIS ending at i):
           count[i] += count[j]  ← accumulate all paths of equal length...
      -> Case 3: dp[j] + 1 < dp[i] (shorter than current best):
           No update needed → skip...

    Why Reset count[i] on Strictly Better Predecessor:
      -> When dp[i] updates to a new maximum, all previous counts are invalidated...
      -> The old dp[i] was tracking a shorter LIS → those counts are no longer relevant...
      -> Only paths of the current best length dp[i] should be counted...
      -> Setting count[i] = count[j] starts fresh with the new best predecessor's ways...

    Why Accumulate count[i] on Equal Length Predecessor:
      -> Multiple predecessors j1, j2, ... can all give an LIS of the same length at i...
      -> Each brings count[j_k] distinct subsequences ending at i...
      -> Total distinct subsequences = sum of all their counts...
      -> This is additive: count[i] += count[j] for each tying predecessor...

    Global Aggregation:
      -> Track maxLen (maximum dp[i] seen) and result (running sum of count[i] at maxLen)...
      -> If dp[i] > maxLen: update maxLen = dp[i], result = count[i]...
      -> If dp[i] == maxLen: result += count[i]...
      -> Final result is the total count of distinct LIS of maximum length...

    Step-by-Step Trace (nums = [1, 3, 5, 4, 7]):
      -> i=0: dp[0]=1, count[0]=1, maxLen=1, result=1...
      -> i=1 (3): j=0(1<3): dp=2, count=1 → maxLen=2, result=1...
      -> i=2 (5): j=1(3<5): dp=3, count=1 → maxLen=3, result=1...
      -> i=3 (4): j=1(3<4): dp=3, count=1 → maxLen=3, result=1+1=2...
      -> i=4 (7): j=2(5<7,dp=3+1=4): dp=4,count=1...
                   j=3(4<7,dp=3+1=4): dp=4 same, count=1+1=2...
                   dp[4]=4 > maxLen=3 → maxLen=4, result=2...
      -> Return 2...

    Modulo Arithmetic:
      -> MOD = 1_000_000_007L (10^9 + 7, a standard prime modulus)...
      -> Applied when accumulating count[i] += count[j] to prevent integer overflow...
      -> Applied when adding count[i] to result...
      -> For very large inputs with exponentially many LIS paths, count can exceed int range...

    Edge Cases:
      -> Single element → dp[0]=1, count[0]=1 → result=1...
      -> All same elements → no pair satisfies strictly increasing → all dp[i]=1, count[i]=1 → result=n...
      -> Strictly increasing → one unique LIS → result=1...
      -> Strictly decreasing → all dp[i]=1, count[i]=1 → result=n...
      -> Duplicate values in the middle → multiple equal-length paths accumulate in count...

    Time and Space Complexity:
      -> Time:  O(n²) — nested loop over all index pairs (i, j) with j < i...
      -> Space: O(n) — two arrays dp[] and count[] of size n...

    Applications:
      -> Counting optimal alignment paths in bioinformatics sequence analysis...
      -> Number of shortest paths through a DAG with monotone edge weights...
      -> Enumerating all optimal solutions in constrained subsequence problems...
      -> Competitive programming combinatorial counting on sequences...

*/

import java.util.Arrays;

public class Number_Of_Longest_Increasing_Subsequence {

    private static int findNumberOfLIS(int[] nums) {

        int n = nums.length;
        int[] dp = new int[n];
        int[] count = new int[n];

        long MOD = 1_000_000_000_007L;
        int maxLen = 1;
        long result = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            count[i] = 1;

            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {

                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        count[i] = count[j];
                    } else if (dp[j] + 1 == dp[i]) {
                        count[i] = (int) (((long) count[i] + count[j]) % MOD);
                    }

                }
            }

            if (dp[i] > maxLen) {
                maxLen = dp[i];
                result = count[i];
            } else if (dp[i] == maxLen) {
                result = (result + count[i]) % MOD;
            }

        }

        return (int) result;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║       NUMBER OF LONGEST INCREASING SUBSEQUENCES              ║");
        System.out.println("║  Count how many distinct subsequences achieve the longest    ║");
        System.out.println("║  strictly increasing subsequence length                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] nums1 = {1, 3, 5, 4, 7};
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("\nLIS length is 4. Two LIS's achieve it: [1,3,5,7] and [1,3,4,7]\n");

        int result1 = findNumberOfLIS(nums1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result1 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: All Same Elements ===");
        int[] nums2 = {2, 2, 2, 2, 2};
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("\nStrictly increasing means no element extends another");
        System.out.println("Every single element is its own LIS of length 1 → count = 5\n");

        int result2 = findNumberOfLIS(nums2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result2 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Increasing ===");
        int[] nums3 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("\nOnly one LIS exists (the whole array) → count = 1\n");

        int result3 = findNumberOfLIS(nums3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result3 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Element ===");
        int[] nums4 = {7};
        System.out.println("Input: " + Arrays.toString(nums4));
        System.out.println("\nOnly one element, trivially one LIS of length 1 → count = 1\n");

        int result4 = findNumberOfLIS(nums4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result4 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Strictly Decreasing ===");
        int[] nums5 = {5, 4, 3, 2, 1};
        System.out.println("Input: " + Arrays.toString(nums5));
        System.out.println("\nNo increasing pair exists, every element is its own LIS of length 1");
        System.out.println("→ count = 5\n");

        int result5 = findNumberOfLIS(nums5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result5 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Multiple Ties in the Middle ===");
        int[] nums6 = {1, 3, 5, 4, 7, 8};
        System.out.println("Input: " + Arrays.toString(nums6));
        System.out.println("\nLIS length is 5. Two paths reach it: [1,3,5,7,8] and [1,3,4,7,8]\n");

        int result6 = findNumberOfLIS(nums6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result6 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Zigzag Pattern ===");
        int[] nums7 = {2, 2, 2, 2, 2, 2, 2, 2, 2};
        System.out.println("Input: " + Arrays.toString(nums7));
        System.out.println("\nAll identical values, each element is its own LIS of length 1");
        System.out.println("→ count = 9\n");

        int result7 = findNumberOfLIS(nums7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 9");
        System.out.println("  Status: " + (result7 == 9 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Count how many distinct subsequences achieve the   ║");
        System.out.println("║           maximum LIS length (not just find the length)      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Track BOTH Length and Count at Each Index      ║");
        System.out.println("║    dp[i] = length of longest increasing subsequence ending   ║");
        System.out.println("║    at i. count[i] = how many distinct subsequences of that   ║");
        System.out.println("║    exact length end at i. When a new best predecessor is     ║");
        System.out.println("║    found, reset the count; when a tying predecessor is       ║");
        System.out.println("║    found, add its count instead of overwriting.              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Transition for each pair (i, j) where nums[i] > nums[j]:    ║");
        System.out.println("║    If dp[j]+1 > dp[i]: dp[i] = dp[j]+1, count[i] = count[j]  ║");
        System.out.println("║      (found a strictly longer path, discard old count)       ║");
        System.out.println("║    Else if dp[j]+1 == dp[i]: count[i] += count[j]            ║");
        System.out.println("║      (found an equally long path, accumulate ways)           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Global Aggregation:                                         ║");
        System.out.println("║    Track maxLen and result (sum of count[i] where dp[i]      ║");
        System.out.println("║    equals maxLen) across the whole array                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: nums = [1, 3, 5, 4, 7]                             ║");
        System.out.println("║    dp = [1, 2, 3, 3, 4], count = [1, 1, 1, 1, 2]             ║");
        System.out.println("║    maxLen = 4, result = count[4] = 2                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • MOD = 1e9+7 guards against overflow on large inputs     ║");
        System.out.println("║    • count[i] can itself be a sum of multiple prior counts   ║");
        System.out.println("║    • Every element starts as its own LIS of length 1         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n²) — nested loop over all index pairs   ║");
        System.out.println("║  Space Complexity: O(n) for dp[] and count[]                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
