package Math_Problems;

/*

    Description:
      Following program finds the maximum product obtainable from any three elements
        in an integer array, by sorting and evaluating exactly two candidate triplets...

    Problem Statement:
      -> Given an integer array nums of length at least 3 (may contain negatives)...
      -> Choose any three elements from the array...
      -> Return the maximum possible product of those three elements...

    Key Insight:
      -> After sorting, the maximum product must come from one of exactly two triplets:
           Candidate A: nums[n-1] × nums[n-2] × nums[n-3] (three largest elements)...
           Candidate B: nums[n-1] × nums[0] × nums[1] (largest × two smallest)...
      -> No other combination of three elements can exceed either of these two...
      -> Sorting exposes all four relevant boundary values in O(n log n)...

    Why Only Two Candidates Suffice:
      -> If all elements are non-negative: three largest always win (Candidate A)...
      -> If two large negatives exist: their product is a large positive...
           Multiplying by the largest element could dominate three positives (Candidate B)...
      -> Any other mix (one negative, two middle positives, etc.) is always dominated
           by either Candidate A or Candidate B...
      -> Math.max of both candidates handles all cases without special casing...

    Example:
      -> nums = [-10, -10, 1, 3, 2]:
           Sorted: [-10, -10, 1, 2, 3]...
           Candidate A: 1 × 2 × 3 = 6...
           Candidate B: (-10) × (-10) × 3 = 300...
           Result: max(6, 300) = 300...
      -> nums = [-5, -6, 4, 8, 9, 3]:
           Sorted: [-6, -5, 3, 4, 8, 9]...
           Candidate A: 4 × 8 × 9 = 288...
           Candidate B: (-6) × (-5) × 9 = 270...
           Result: max(288, 270) = 288...
      -> nums = [-4, -3, -2, -1]:
           Sorted: [-4, -3, -2, -1]...
           Candidate A: (-2) × (-3) × (-1) = -6...
           Candidate B: (-4) × (-3) × (-1) = -12...
           Result: max(-6, -12) = -6 (least negative)...

    Algorithm Steps:
      -> Sort nums in ascending order...
      -> Compute Candidate A = nums[n-1] × nums[n-2] × nums[n-3]...
      -> Compute Candidate B = nums[n-1] × nums[0] × nums[1]...
      -> Return Math.max(Candidate A, Candidate B)...

    Step-by-Step Trace (nums = [-10, -10, 1, 2, 3]):
      -> After sort: [-10, -10, 1, 2, 3], n=5...
      -> Candidate A: nums[4] × nums[3] × nums[2] = 3 × 2 × 1 = 6...
      -> Candidate B: nums[4] × nums[0] × nums[1] = 3 × (-10) × (-10) = 300...
      -> Math.max(6, 300) = 300...

    All Negatives Case:
      -> When all elements are negative, all three-element products are negative...
      -> Candidate A uses three values closest to zero (least negative) → maximum product...
      -> Candidate B uses most negative × second most negative × least negative...
           Two large negatives make a large positive, times a small negative = large negative → worse...
      -> Math.max correctly returns Candidate A (the least negative result)...

    Why Candidate B Uses nums[0] and nums[1]:
      -> nums[0] and nums[1] are the two smallest (most negative) values after sorting...
      -> Their product is the largest possible positive from two elements on the left side...
      -> Multiplying by nums[n-1] (the largest element) maximizes this combination...
      -> This is the only non-trivial candidate that could beat three positives...

    Edge Cases:
      -> Exactly three elements → both candidates evaluate to the same single triplet...
      -> All positive → Candidate A always wins...
      -> All negative → Candidate A (three largest = three least negative) wins...
      -> Contains zeros → zeros dampen products; sorted correctly by both candidates...
      -> One very large negative pair → Candidate B may dominate even large positives...

    Time and Space Complexity:
      -> Time:  O(n log n) — dominated by Arrays.sort()...
      -> Space: O(log n) to O(n) depending on Java's sort implementation (TimSort)...
               O(1) additional space for the two candidate variables...

    Applications:
      -> Maximum product subarray selection in competitive programming...
      -> Financial return maximization with both gain and loss assets...
      -> Optimal three-resource combination selection in optimization problems...
      -> Mathematical puzzles involving maximum products of signed integers...

*/

import java.util.Arrays;

public class Maximum_Product_Of_Three_Numbers {

    private static int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;

        return Math.max(nums[n - 1] * nums[n - 2] * nums[n - 3], nums[n - 1] * nums[0] * nums[1]);
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║          MAXIMUM PRODUCT OF THREE NUMBERS                    ║");
        System.out.println("║  Find the largest product obtainable from any three numbers  ║");
        System.out.println("║  in the array                                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: All Positive Numbers ===");
        int[] nums1 = {1, 2, 3};
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("\nSorted: [1, 2, 3]");
        System.out.println("Top three product: 1×2×3 = 6");
        System.out.println("Two smallest × largest: 1×2×3 = 6");
        System.out.println("Max of both: 6\n");

        int result1 = maximumProduct(nums1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result1 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Mix With Negatives ===");
        int[] nums2 = {1, 2, 3, 4};
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("\nSorted: [1, 2, 3, 4]");
        System.out.println("Top three product: 2×3×4 = 24");
        System.out.println("Two smallest × largest: 1×2×4 = 8");
        System.out.println("Max of both: 24\n");

        int result2 = maximumProduct(nums2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 24");
        System.out.println("  Status: " + (result2 == 24 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Two Large Negatives ===");
        int[] nums3 = {- 4, - 3, - 2, - 1};
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("\nSorted: [-4, -3, -2, -1]");
        System.out.println("Top three product: (-2)×(-3)×(-1) = -6");
        System.out.println("Two smallest × largest: (-4)×(-3)×(-1) = -12");
        System.out.println("Max of both: -6\n");

        int result3 = maximumProduct(nums3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: -6");
        System.out.println("  Status: " + (result3 == - 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Negatives Boosting Product ===");
        int[] nums4 = {- 10, - 10, 1, 3, 2};
        System.out.println("Input: " + Arrays.toString(nums4));
        System.out.println("\nSorted: [-10, -10, 1, 2, 3]");
        System.out.println("Top three product: 1×2×3 = 6");
        System.out.println("Two smallest × largest: (-10)×(-10)×3 = 300");
        System.out.println("Max of both: 300\n");

        int result4 = maximumProduct(nums4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 300");
        System.out.println("  Status: " + (result4 == 300 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Minimum Size Array ===");
        int[] nums5 = {- 1, - 2, - 3};
        System.out.println("Input: " + Arrays.toString(nums5));
        System.out.println("\nSorted: [-3, -2, -1]");
        System.out.println("Only one triple possible: (-3)×(-2)×(-1) = -6");
        System.out.println("Both formulas evaluate to the same triple\n");

        int result5 = maximumProduct(nums5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: -6");
        System.out.println("  Status: " + (result5 == - 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: All Zeros and Positives ===");
        int[] nums6 = {0, 0, 0, 5, 6};
        System.out.println("Input: " + Arrays.toString(nums6));
        System.out.println("\nSorted: [0, 0, 0, 5, 6]");
        System.out.println("Top three product: 0×5×6 = 0");
        System.out.println("Two smallest × largest: 0×0×6 = 0");
        System.out.println("Max of both: 0\n");

        int result6 = maximumProduct(nums6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Large Mixed Array ===");
        int[] nums7 = {- 5, - 6, 4, 8, 9, 3};
        System.out.println("Input: " + Arrays.toString(nums7));
        System.out.println("\nSorted: [-6, -5, 3, 4, 8, 9]");
        System.out.println("Top three product: 4×8×9 = 288");
        System.out.println("Two smallest × largest: (-6)×(-5)×9 = 270");
        System.out.println("Max of both: 288\n");

        int result7 = maximumProduct(nums7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 288");
        System.out.println("  Status: " + (result7 == 288 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the maximum product of any three numbers      ║");
        System.out.println("║           from the array                                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Only Two Candidate Triples Matter              ║");
        System.out.println("║    After sorting, the best product either comes from the     ║");
        System.out.println("║    three largest values, OR from the two smallest values     ║");
        System.out.println("║    (which could be large negatives) times the largest value  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Single Formula, Two Candidates:                             ║");
        System.out.println("║    Sort the array ascending                                  ║");
        System.out.println("║    Candidate 1: nums[n-1] × nums[n-2] × nums[n-3]            ║");
        System.out.println("║      (three largest positive-leaning values)                 ║");
        System.out.println("║    Candidate 2: nums[n-1] × nums[0] × nums[1]                ║");
        System.out.println("║      (largest value × two smallest, which may be negative)   ║");
        System.out.println("║    Return the max of the two candidates                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: nums = [-10, -10, 1, 2, 3]                         ║");
        System.out.println("║    Candidate 1: 1×2×3 = 6                                    ║");
        System.out.println("║    Candidate 2: (-10)×(-10)×3 = 300                          ║");
        System.out.println("║    Result: max(6, 300) = 300                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Two negatives multiply to a positive, boosting product  ║");
        System.out.println("║    • Sorting makes both candidate triples trivial to access  ║");
        System.out.println("║    • Requires array length >= 3                              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n log n) for sorting                     ║");
        System.out.println("║  Space Complexity: O(log n) to O(n) depending on sort impl   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
