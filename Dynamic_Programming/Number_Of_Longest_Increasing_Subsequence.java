package Dynamic_Programming;

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