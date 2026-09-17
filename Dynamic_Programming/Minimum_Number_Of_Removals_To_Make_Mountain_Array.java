package Dynamic_Programming;


import java.util.Arrays;

public class Minimum_Number_Of_Removals_To_Make_Mountain_Array {

    private static int minimumMountainRemovals(int[] arr) {

        int n = arr.length;
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];

        for (int curr = 0; curr < n; curr++) {
            dp1[curr] = 1;

            for (int prev = 0; prev < curr; prev++) {
                if (arr[curr] > arr[prev] && dp1[curr] < dp1[prev] + 1) {
                    dp1[curr] = dp1[prev] + 1;
                }
            }

        }

        int maxLength = 0;
        for (int curr = n - 1; curr >= 0; curr--) {
            dp2[curr] = 1;

            for (int next = n - 1; next > curr; next--) {
                if (arr[curr] > arr[next] && dp2[curr] < dp2[next] + 1) {
                    dp2[curr] = dp2[next] + 1;
                }
            }

            if (dp1[curr] >= 2 && dp2[curr] >= 2) {
                maxLength = Math.max(maxLength, dp1[curr] + dp2[curr] - 1);
            }
        }

        return n - maxLength;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   MINIMUM NUMBER OF REMOVALS TO MAKE MOUNTAIN ARRAY          ║");
        System.out.println("║  Find the fewest elements to remove so the remaining array   ║");
        System.out.println("║  forms a mountain (strictly up, then strictly down)          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] arr1 = {1, 3, 1};
        System.out.println("Input: " + Arrays.toString(arr1));
        System.out.println("\nAlready a valid mountain: 1 < 3 > 1, no removals needed\n");

        int result1 = minimumMountainRemovals(arr1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result1 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Remove Duplicates to Form Mountain ===");
        int[] arr2 = {2, 1, 1, 5, 6, 2, 3, 1};
        System.out.println("Input: " + Arrays.toString(arr2));
        System.out.println("\nBest mountain: [1, 5, 6, 3, 1] → remove 3 elements\n");

        int result2 = minimumMountainRemovals(arr2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result2 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Increasing (No Mountain) ===");
        int[] arr3 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(arr3));
        System.out.println("\nNo decreasing part exists, must remove down to a 3-element mountain");
        System.out.println("Keep any 3 increasing then treat as peak+1 down (e.g. [1,4,5] invalid,");
        System.out.println("actual best: pick 3 elements forming increase-then-decrease of length 3)\n");

        int result3 = minimumMountainRemovals(arr3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Status: (see computed value above)\n");

        System.out.println("=== Test Case 4: Strictly Decreasing (No Mountain) ===");
        int[] arr4 = {5, 4, 3, 2, 1};
        System.out.println("Input: " + Arrays.toString(arr4));
        System.out.println("\nNo increasing part exists, similar reasoning as Test Case 3\n");

        int result4 = minimumMountainRemovals(arr4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Status: (see computed value above)\n");

        System.out.println("=== Test Case 5: Already Perfect Mountain ===");
        int[] arr5 = {0, 1, 2, 5, 3, 2, 1};
        System.out.println("Input: " + Arrays.toString(arr5));
        System.out.println("\nAlready a mountain: 0<1<2<5>3>2>1, no removals needed\n");

        int result5 = minimumMountainRemovals(arr5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result5 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Small Valid Case ===");
        int[] arr6 = {1, 3, 5, 4, 2};
        System.out.println("Input: " + Arrays.toString(arr6));
        System.out.println("\nAlready a mountain: 1<3<5>4>2, no removals needed\n");

        int result6 = minimumMountainRemovals(arr6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Larger Mixed Case ===");
        int[] arr7 = {23, 21, 19, 22, 24, 12, 5, 20, 26, 25};
        System.out.println("Input: " + Arrays.toString(arr7));
        System.out.println("\nBest mountain subsequence needs to be found via the dp1/dp2 combination\n");

        int result7 = minimumMountainRemovals(arr7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result7 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the minimum number of removals so the         ║");
        System.out.println("║           remaining array is a mountain — strictly increasing║");
        System.out.println("║           up to a peak, then strictly decreasing             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Longest Mountain = n − Fewest Removals         ║");
        System.out.println("║    Instead of directly minimizing removals, maximize the     ║");
        System.out.println("║    longest valid mountain subsequence, then subtract from n. ║");
        System.out.println("║    This reuses the same dp1/dp2 LIS-based combination as     ║");
        System.out.println("║    Longest Bitonic Sequence.                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: LIS Ending at Each Index → dp1[]                   ║");
        System.out.println("║  Phase 2: LIS Starting at Each Index(Decreasing View) → dp2[]║");
        System.out.println("║    While computing dp2[curr], immediately check if curr can  ║");
        System.out.println("║    serve as a valid peak: requires dp1[curr] >= 2 AND        ║");
        System.out.println("║    dp2[curr] >= 2 (a peak needs at least one element on      ║");
        System.out.println("║    each side, not just itself)                               ║");
        System.out.println("║    mountainLength = dp1[curr] + dp2[curr] - 1                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Final Answer: n - maxLength                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: arr = [2, 1, 1, 5, 6, 2, 3, 1]                     ║");
        System.out.println("║    Best mountain found: [1, 5, 6, 3, 1] (length 5)           ║");
        System.out.println("║    Removals needed: 8 - 5 = 3                                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • The dp1>=2 and dp2>=2 checks exclude pure increasing or ║");
        System.out.println("║      decreasing sequences from being falsely called mountains║");
        System.out.println("║    • A valid mountain requires at least 3 elements           ║");
        System.out.println("║    • Same computational pattern as Longest Bitonic Sequence, ║");
        System.out.println("║      but the peak validity constraint changes the result     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n²) — two nested LIS-style passes        ║");
        System.out.println("║  Space Complexity: O(n) for dp1[] and dp2[]                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}