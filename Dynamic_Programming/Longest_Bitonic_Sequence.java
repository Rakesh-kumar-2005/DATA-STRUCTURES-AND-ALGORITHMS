package Dynamic_Programming;

import java.util.Arrays;

public class Longest_Bitonic_Sequence {

    private static int longestBitonicSequence(int[] arr, int n) {

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

        int maxLength = 1;
        for (int curr = n - 1; curr >= 0; curr--) {
            dp2[curr] = 1;

            for (int next = n - 1; next > curr; next--) {
                if (arr[curr] > arr[next] && dp2[curr] < dp2[next] + 1) {
                    dp2[curr] = dp2[next] + 1;
                }
            }

            maxLength = Math.max(maxLength, dp1[curr] + dp2[curr] - 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                LONGEST BITONIC SEQUENCE                      ║");
        System.out.println("║  Find the longest subsequence that first strictly increases  ║");
        System.out.println("║  then strictly decreases                                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] arr1 = {1, 11, 2, 10, 4, 5, 2, 1};
        System.out.println("Input: " + Arrays.toString(arr1));
        System.out.println("\nBitonic sequence: [1, 2, 10, 4, 2, 1] (length 6)\n");

        int result1 = longestBitonicSequence(arr1, arr1.length);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result1 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Strictly Increasing Only ===");
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(arr2));
        System.out.println("\nNo decreasing part exists, best bitonic is the increasing run itself\n");

        int result2 = longestBitonicSequence(arr2, arr2.length);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result2 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Decreasing Only ===");
        int[] arr3 = {5, 4, 3, 2, 1};
        System.out.println("Input: " + Arrays.toString(arr3));
        System.out.println("\nNo increasing part exists, best bitonic is the decreasing run itself\n");

        int result3 = longestBitonicSequence(arr3, arr3.length);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result3 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Element ===");
        int[] arr4 = {7};
        System.out.println("Input: " + Arrays.toString(arr4));
        System.out.println("\nOnly one element, trivially bitonic of length 1\n");

        int result4 = longestBitonicSequence(arr4, arr4.length);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result4 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: All Same Elements ===");
        int[] arr5 = {3, 3, 3, 3};
        System.out.println("Input: " + Arrays.toString(arr5));
        System.out.println("\nStrictly increasing/decreasing means duplicates can't extend it → length 1\n");

        int result5 = longestBitonicSequence(arr5, arr5.length);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result5 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Peak in the Middle ===");
        int[] arr6 = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        System.out.println("Input: " + Arrays.toString(arr6));
        System.out.println("\nA well-known bitonic test case with longest bitonic length 7\n");

        int result6 = longestBitonicSequence(arr6, arr6.length);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 7");
        System.out.println("  Status: " + (result6 == 7 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Simple Peak Shape ===");
        int[] arr7 = {1, 3, 5, 4, 2};
        System.out.println("Input: " + Arrays.toString(arr7));
        System.out.println("\nEntire array is bitonic: 1 -> 3 -> 5 -> 4 -> 2 (length 5)\n");

        int result7 = longestBitonicSequence(arr7, arr7.length);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result7 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the longest subsequence that strictly         ║");
        System.out.println("║           increases then strictly decreases (the peak        ║");
        System.out.println("║           itself belongs to both halves)                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Combine LIS From Both Directions               ║");
        System.out.println("║    dp1[i] = length of longest increasing subsequence         ║");
        System.out.println("║    ending at i (computed left to right)                      ║");
        System.out.println("║    dp2[i] = length of longest increasing subsequence         ║");
        System.out.println("║    ending at i when scanning right to left (equivalently,    ║");
        System.out.println("║    the longest decreasing subsequence starting at i)         ║");
        System.out.println("║    Bitonic length at i = dp1[i] + dp2[i] - 1                 ║");
        System.out.println("║    (subtract 1 because arr[i] is counted in both halves)     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Standard LIS Left to Right → dp1[]                 ║");
        System.out.println("║  Phase 2: LIS-Style Scan Right to Left → dp2[]               ║");
        System.out.println("║    For each curr from n-1 down to 0, look at next > curr     ║");
        System.out.println("║    where arr[curr] > arr[next] (decreasing from curr's view) ║");
        System.out.println("║    Track maxLength = max(maxLength, dp1[curr]+dp2[curr]-1)   ║");
        System.out.println("║    for every index during this same pass                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: arr = [1, 11, 2, 10, 4, 5, 2, 1]                   ║");
        System.out.println("║    Peak at value 10 (index 3): dp1=3 ([1,2,10]),             ║");
        System.out.println("║    dp2=4 ([10,4,2,1]) → bitonic length = 3+4-1 = 6           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • A purely increasing or purely decreasing array is a     ║");
        System.out.println("║      valid (degenerate) bitonic sequence                     ║");
        System.out.println("║    • The peak element is shared, hence the -1 adjustment     ║");
        System.out.println("║    • Every index is checked as a potential peak              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n²) — two nested LIS-style passes        ║");
        System.out.println("║  Space Complexity: O(n) for dp1[] and dp2[]                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}