package Arrays;

import java.util.Arrays;

public class Smallest_Stable_Index {

    private static int firstStableIndex(int[] nums, int k) {

        int n = nums.length;
        int leftMax = 0;

        int[] minArr = new int[n];
        minArr[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            minArr[i] = Math.min(minArr[i + 1], nums[i]);
        }

        for (int i = 0; i < n; i++) {
            leftMax = Math.max(leftMax, nums[i]);
            int score = leftMax - minArr[i];
            if (score <= k) {
                return i;
            }
        }

        return - 1;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  SMALLEST STABLE INDEX                       ║");
        System.out.println("║  Find the smallest index i where (max of nums[0..i]) minus   ║");
        System.out.println("║  (min of nums[i..n-1]) is at most k                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: No Stable Index (Tight k) ===");
        int[] nums1 = {3, 1, 4, 1, 5, 9, 2, 6};
        int k1 = 1;
        System.out.println("Input: " + Arrays.toString(nums1) + ", k=" + k1);
        System.out.println("\nsuffix mins = [1,1,1,1,2,2,2,6]");
        System.out.println("At every i, leftMax - suffixMin exceeds 1 → no stable index\n");

        int result1 = firstStableIndex(nums1, k1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: -1");
        System.out.println("  Status: " + (result1 == - 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: All Same Elements ===");
        int[] nums2 = {5, 5, 5, 5};
        int k2 = 0;
        System.out.println("Input: " + Arrays.toString(nums2) + ", k=" + k2);
        System.out.println("\nEvery leftMax equals every suffixMin (all 5's), score=0 at i=0\n");

        int result2 = firstStableIndex(nums2, k2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result2 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Increasing ===");
        int[] nums3 = {1, 2, 3, 4, 5};
        int k3 = 0;
        System.out.println("Input: " + Arrays.toString(nums3) + ", k=" + k3);
        System.out.println("\nsuffixMin[0]=1, leftMax[0]=1 → score=0 at i=0\n");

        int result3 = firstStableIndex(nums3, k3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Strictly Decreasing, No Stable Index ===");
        int[] nums4 = {5, 4, 3, 2, 1};
        int k4 = 0;
        System.out.println("Input: " + Arrays.toString(nums4) + ", k=" + k4);
        System.out.println("\nsuffixMin stays 1 while leftMax stays 5, score=4 at every i → -1\n");

        int result4 = firstStableIndex(nums4, k4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: -1");
        System.out.println("  Status: " + (result4 == - 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Element ===");
        int[] nums5 = {7};
        int k5 = 0;
        System.out.println("Input: " + Arrays.toString(nums5) + ", k=" + k5);
        System.out.println("\nOnly one element, leftMax=suffixMin=7 → score=0 at i=0\n");

        int result5 = firstStableIndex(nums5, k5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Stable at First Index ===");
        int[] nums6 = {2, 1, 3};
        int k6 = 1;
        System.out.println("Input: " + Arrays.toString(nums6) + ", k=" + k6);
        System.out.println("\nsuffixMin[0]=1, leftMax[0]=2, score=1<=1 → return 0\n");

        int result6 = firstStableIndex(nums6, k6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Stable Only at Last Index ===");
        int[] nums7 = {10, 1, 10, 1, 10};
        int k7 = 5;
        System.out.println("Input: " + Arrays.toString(nums7) + ", k=" + k7);
        System.out.println("\nEarly indices give score=9 (>5), only at i=4 does");
        System.out.println("leftMax=10 and suffixMin=10, score=0<=5 → return 4\n");

        int result7 = firstStableIndex(nums7, k7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result7 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the smallest index i such that the max of     ║");
        System.out.println("║           nums[0..i] minus the min of nums[i..n-1] is <= k   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Precompute Suffix Minimums, Scan Prefix Max    ║");
        System.out.println("║    Build minArr[i] = min(nums[i..n-1]) in one right-to-left  ║");
        System.out.println("║    pass, then walk left-to-right tracking a running max      ║");
        System.out.println("║    (leftMax) and checking the score at each index.           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Build Suffix Minimum Array                         ║");
        System.out.println("║    minArr[n-1] = nums[n-1]                                   ║");
        System.out.println("║    minArr[i] = min(minArr[i+1], nums[i]) for i from n-2 to 0 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Scan Left to Right, Check Score                    ║");
        System.out.println("║    leftMax = max(leftMax, nums[i])                           ║");
        System.out.println("║    score = leftMax - minArr[i]                               ║");
        System.out.println("║    If score <= k, return i immediately                       ║");
        System.out.println("║    If no index qualifies, return -1                          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: nums = [10, 1, 10, 1, 10], k = 5                   ║");
        System.out.println("║    minArr = [1, 1, 1, 1, 10]                                 ║");
        System.out.println("║    i=0..3: leftMax=10, score=9 (>5)                          ║");
        System.out.println("║    i=4: leftMax=10, minArr[4]=10, score=0 (<=5) → return 4   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • leftMax is monotonically non-decreasing during the scan ║");
        System.out.println("║    • minArr is monotonically non-decreasing right-to-left    ║");
        System.out.println("║    • Returns the FIRST qualifying index, not necessarily the ║");
        System.out.println("║      one with the smallest score                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n) — two linear passes over the array    ║");
        System.out.println("║  Space Complexity: O(n) — for the suffix minimum array       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}