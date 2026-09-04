package Arrays;

/*

    Description:
      Following program finds the smallest index i where the score (max of nums[0..i]
        minus min of nums[i..n-1]) is at most k, using precomputed suffix minimums...

    Problem Statement:
      -> Given an integer array nums and an integer k...
      -> For each index i, define score(i) = max(nums[0..i]) - min(nums[i..n-1])...
      -> Find and return the smallest index i such that score(i) <= k...
      -> Return -1 if no such index exists...

    Key Insight:
      -> score(i) requires the prefix maximum up to i and suffix minimum from i...
      -> Prefix maximum can be tracked as a running variable during a left-to-right scan...
      -> Suffix minimum cannot be computed on the fly left-to-right → precompute right-to-left...
      -> Building minArr[] first then scanning once left-to-right achieves O(n) total time...

    Example:
      -> nums = [2, 1, 3], k = 1:
           minArr = [1, 1, 3]...
           i=0: leftMax=2, score = 2-1 = 1 <= 1 → return 0...
      -> nums = [10, 1, 10, 1, 10], k = 5:
           minArr = [1, 1, 1, 1, 10]...
           i=0: leftMax=10, score = 10-1 = 9 > 5...
           i=1: leftMax=10, score = 10-1 = 9 > 5...
           i=2: leftMax=10, score = 10-1 = 9 > 5...
           i=3: leftMax=10, score = 10-1 = 9 > 5...
           i=4: leftMax=10, score = 10-10 = 0 <= 5 → return 4...
      -> nums = [5, 4, 3, 2, 1], k = 0:
           minArr = [1, 1, 1, 1, 1]...
           leftMax stays 5 throughout, score = 5-1 = 4 > 0 always → return -1...

    Two-Phase Algorithm:

      Phase 1 - Build Suffix Minimum Array (Right to Left):
        -> minArr[n-1] = nums[n-1]...
        -> For i from n-2 down to 0:
             minArr[i] = Math.min(minArr[i+1], nums[i])...
        -> minArr[i] now holds the minimum of nums[i..n-1] for every i...

      Phase 2 - Scan Left to Right, Check Score:
        -> Initialize leftMax = 0...
        -> For i from 0 to n-1:
             leftMax = Math.max(leftMax, nums[i])...
             score = leftMax - minArr[i]...
             If score <= k: return i immediately...
        -> If loop completes: return -1...

    Step-by-Step Trace (nums = [3, 1, 4, 1, 5, 9, 2, 6], k = 1):
      -> Phase 1 (right to left):
           minArr[7]=6, minArr[6]=2, minArr[5]=2, minArr[4]=2...
           minArr[3]=1, minArr[2]=1, minArr[1]=1, minArr[0]=1...
           minArr = [1, 1, 1, 1, 2, 2, 2, 6]...
      -> Phase 2 (left to right):
           i=0: leftMax=3, score=3-1=2 > 1...
           i=1: leftMax=3, score=3-1=2 > 1...
           i=2: leftMax=4, score=4-1=3 > 1...
           i=3: leftMax=4, score=4-1=3 > 1...
           i=4: leftMax=5, score=5-2=3 > 1...
           i=5: leftMax=9, score=9-2=7 > 1...
           i=6: leftMax=9, score=9-2=7 > 1...
           i=7: leftMax=9, score=9-6=3 > 1...
           No index qualifies → return -1...

    Why leftMax is Monotonically Non-Decreasing:
      -> leftMax = Math.max(leftMax, nums[i]) updates only upward...
      -> Once a new maximum is encountered, it persists for all future indices...
      -> This means score(i) can only increase or stay same as i grows...
      -> However, minArr[i] can also increase as we move right → net effect on score varies...

    Why Return the First Qualifying Index:
      -> The problem asks for the smallest index i (leftmost) satisfying the condition...
      -> Scanning left to right and returning at the first match guarantees smallest index...
      -> No need to continue scanning after the first qualifying index is found...

    Edge Cases:
      -> Single element → leftMax = nums[0] = minArr[0] → score = 0 ≤ k → return 0...
      -> All same elements → score = 0 at every index → return 0...
      -> Strictly increasing → minArr[0] = nums[0] = leftMax at i=0 → score = 0 → return 0...
      -> Strictly decreasing → leftMax = nums[0] throughout, minArr[i] decreases → high score → -1...
      -> k very large → score(0) likely qualifies immediately → return 0...

    Time and Space Complexity:
      -> Time:  O(n) — one right-to-left pass for minArr, one left-to-right pass for scoring...
      -> Space: O(n) — minArr array of size n...

    Applications:
      -> Stability analysis of data streams with tolerance thresholds...
      -> Finding the first position where a range narrows within acceptable bounds...
      -> Sensor data monitoring where acceptable spread between extremes must be met...
      -> Competitive programming range-query problems with prefix/suffix aggregates...

*/

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
