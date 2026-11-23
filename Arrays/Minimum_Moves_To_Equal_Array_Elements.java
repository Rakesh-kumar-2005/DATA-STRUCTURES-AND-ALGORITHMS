package Arrays;

import java.util.Arrays;

public class Minimum_Moves_To_Equal_Array_Elements {

    private static int minMoves2(int[] nums) {

        int n = nums.length;
        Arrays.sort(nums);

        int median = nums[n / 2];
        int moves = 0;

        for (int num : nums) {
            moves += Math.abs(median - num);
        }

        return moves;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║          MINIMUM MOVES TO EQUAL ARRAY ELEMENTS II            ║");
        System.out.println("║  Find the minimum number of +1/-1 moves needed to make all   ║");
        System.out.println("║  array elements equal                                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] nums1 = {1, 2, 3};
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("\nSorted: [1, 2, 3], median = 2");
        System.out.println("Moves: |2-1| + |2-2| + |2-3| = 1 + 0 + 1 = 2\n");

        int result1 = minMoves2(nums1.clone());
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result1 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Outlier Present ===");
        int[] nums2 = {1, 10, 2, 9};
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("\nSorted: [1, 2, 9, 10], median = nums[2] = 9");
        System.out.println("Moves: 8 + 7 + 0 + 1 = 16\n");

        int result2 = minMoves2(nums2.clone());
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 16");
        System.out.println("  Status: " + (result2 == 16 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Element ===");
        int[] nums3 = {5};
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("\nAlready equal, no moves needed\n");

        int result3 = minMoves2(nums3.clone());
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: All Same Elements ===");
        int[] nums4 = {4, 4, 4, 4};
        System.out.println("Input: " + Arrays.toString(nums4));
        System.out.println("\nAll elements already equal → 0 moves\n");

        int result4 = minMoves2(nums4.clone());
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result4 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Negative Numbers ===");
        int[] nums5 = {- 3, - 1, 0, 2};
        System.out.println("Input: " + Arrays.toString(nums5));
        System.out.println("\nSorted: [-3, -1, 0, 2], median = nums[2] = 0");
        System.out.println("Moves: 3 + 1 + 0 + 2 = 6\n");

        int result5 = minMoves2(nums5.clone());
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result5 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Two Elements ===");
        int[] nums6 = {1, 100};
        System.out.println("Input: " + Arrays.toString(nums6));
        System.out.println("\nSorted: [1, 100], median = nums[1] = 100");
        System.out.println("Moves: 99 + 0 = 99 (any target between gives the same total)\n");

        int result6 = minMoves2(nums6.clone());
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 99");
        System.out.println("  Status: " + (result6 == 99 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Unsorted Larger Array ===");
        int[] nums7 = {7, 1, 5, 3, 9};
        System.out.println("Input: " + Arrays.toString(nums7));
        System.out.println("\nSorted: [1, 3, 5, 7, 9], median = 5");
        System.out.println("Moves: 4 + 2 + 0 + 2 + 4 = 12\n");

        int result7 = minMoves2(nums7.clone());
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 12");
        System.out.println("  Status: " + (result7 == 12 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Make all elements equal using the fewest +1/-1     ║");
        System.out.println("║           moves on single elements                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: The Median Minimizes Total Absolute Distance   ║");
        System.out.println("║    The sum of |x - target| is smallest when target is the    ║");
        System.out.println("║    median. Moving the target away from it increases distance ║");
        System.out.println("║    to more elements than it decreases.                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Sort and Pick the Median                           ║");
        System.out.println("║    Arrays.sort(nums), median = nums[n / 2]                   ║");
        System.out.println("║    (for even n, either middle element works)                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Sum the Distances                                  ║");
        System.out.println("║    moves = sum of |median - num| over all elements           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: nums = [1, 10, 2, 9]                               ║");
        System.out.println("║    Sorted: [1, 2, 9, 10], median = 9                         ║");
        System.out.println("║    Moves: 8 + 7 + 0 + 1 = 16                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • The mean is NOT optimal here, the median is             ║");
        System.out.println("║    • Arrays.sort mutates the input, so tests pass clones     ║");
        System.out.println("║    • Even-length arrays have two valid medians, same cost    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n log n) for sorting                     ║");
        System.out.println("║  Space Complexity: O(log n) to O(n) depending on sort impl   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

}
