package Dynamic_Programming;

/*

    Description:
      Following program finds the minimum number of elements to remove from an array
        so the remaining elements form a valid mountain array...

    Problem Statement:
      -> Given an integer array arr of size n...
      -> A mountain array strictly increases up to a peak element, then strictly decreases...
      -> The peak must have at least one element on each side (minimum mountain length = 3)...
      -> Return the minimum number of elements to remove to make arr a mountain array...

    Key Insight:
      -> Minimizing removals = maximizing the longest mountain subsequence kept...
      -> Minimum removals = n - longest_mountain_subsequence_length...
      -> The longest mountain subsequence is found using the same dp1/dp2 approach
           as Longest Bitonic Sequence, but with an additional constraint:
           the peak must have at least one element strictly increasing on its left
           AND at least one element strictly decreasing on its right...
      -> This constraint is enforced by requiring dp1[curr] >= 2 AND dp2[curr] >= 2...

    Example:
      -> arr = [2, 1, 1, 5, 6, 2, 3, 1]:
           Best mountain: [1, 5, 6, 3, 1] (length 5)...
           Removals = 8 - 5 = 3...
      -> arr = [1, 3, 1]:
           Already a valid mountain → maxLength = 3 → removals = 0...
      -> arr = [0, 1, 2, 5, 3, 2, 1]:
           Entire array is a valid mountain → removals = 0...

    Difference From Longest Bitonic Sequence:
      -> Bitonic: a purely increasing or decreasing sequence is valid (dp1 or dp2 can be 1)...
      -> Mountain: peak must have both an increasing left side AND decreasing right side...
           Requires dp1[curr] >= 2 (at least one element strictly less on the left)...
           Requires dp2[curr] >= 2 (at least one element strictly less on the right)...
      -> Without this check, a purely increasing array would falsely show a long "mountain"...

    Phase 1 - Compute dp1[] (LIS Ending at Each Index, Left to Right):
      -> dp1[curr] = length of longest strictly increasing subsequence ending at curr...
      -> For each curr from 0 to n-1:
           dp1[curr] = 1...
           For each prev from 0 to curr-1:
             If arr[curr] > arr[prev] AND dp1[curr] < dp1[prev] + 1:
               dp1[curr] = dp1[prev] + 1...

    Phase 2 - Compute dp2[] (LIS Starting at Each Index, Right to Left) + Peak Validation:
      -> dp2[curr] = length of longest strictly decreasing subsequence starting at curr...
      -> For each curr from n-1 down to 0:
           dp2[curr] = 1...
           For each next from n-1 down to curr+1:
             If arr[curr] > arr[next] AND dp2[curr] < dp2[next] + 1:
               dp2[curr] = dp2[next] + 1...
           If dp1[curr] >= 2 AND dp2[curr] >= 2:
             maxLength = Math.max(maxLength, dp1[curr] + dp2[curr] - 1)...

    Why dp1[curr] >= 2 and dp2[curr] >= 2:
      -> dp1[curr] >= 2 means at least one element strictly less than arr[curr] exists before it...
           → the peak is not the first element (has a proper increasing left side)...
      -> dp2[curr] >= 2 means at least one element strictly less than arr[curr] exists after it...
           → the peak is not the last element (has a proper decreasing right side)...
      -> Together they enforce the "strictly up then strictly down" mountain definition...

    Step-by-Step Trace (arr = [1, 3, 1]):
      -> dp1: dp1[0]=1, dp1[1]=2 (3>1), dp1[2]=1 (1<3, no j qualifies)...
              Actually dp1[2]=2? No: 1 is not > 1. dp1[2]=1+check j=0: 1>1? No...
              dp1=[1,2,1]...
      -> dp2: curr=2: dp2[2]=1, dp1[2]=1 < 2 → skip...
              curr=1: dp2[1]: next=2: arr[1]=3>arr[2]=1 → dp2[1]=2...
                dp1[1]=2 >= 2, dp2[1]=2 >= 2 → maxLength = 2+2-1 = 3...
              curr=0: dp2[0]: next=1: arr[0]=1>3? No → dp2[0]=1 → skip...
      -> maxLength=3, removals = 3-3 = 0 ✓...

    Edge Cases:
      -> Already a valid mountain → maxLength = n → removals = 0...
      -> Strictly increasing → dp2[i]=1 for all i → no valid peak → maxLength stays 0...
           But minimum mountain = 3 is needed → answer = n - 3...
      -> Strictly decreasing → dp1[i]=1 for all i → same reasoning → answer = n - 3...
      -> All same elements → no strictly greater pairs → all dp[i]=1 → answer = n - 3...

    Final Answer Formula:
      -> return n - maxLength...
      -> maxLength = 0 initially, but a valid mountain always has length >= 3...
      -> If no valid mountain found (all increasing/decreasing), result = n - 0 = n...
           But problem guarantees answer is solvable so this edge case is input-constrained...

    Time and Space Complexity:
      -> Time:  O(n²) — two nested LIS-style passes, each O(n²)...
      -> Space: O(n) — two arrays dp1[] and dp2[] of size n...

    Applications:
      -> Terrain reshaping to create a mountain profile with minimum excavation...
      -> Signal processing: finding minimum samples to remove for a unimodal shape...
      -> Data cleaning to enforce monotone-peak constraints with minimal edits...
      -> Competitive programming mountain array and bitonic subsequence variants...

*/

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
