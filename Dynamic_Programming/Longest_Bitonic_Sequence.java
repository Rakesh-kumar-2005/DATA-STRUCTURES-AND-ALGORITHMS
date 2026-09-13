package Dynamic_Programming;

/*

    Description:
      Following program finds the length of the longest bitonic subsequence of an array,
        where a bitonic sequence first strictly increases then strictly decreases...

    Problem Statement:
      -> Given an integer array arr of size n...
      -> A bitonic subsequence strictly increases up to a peak element, then strictly decreases...
      -> A purely increasing or purely decreasing sequence is also considered bitonic...
      -> Elements need not be contiguous but must preserve original relative order...
      -> Return the length of the longest such bitonic subsequence...

    Key Insight:
      -> Split the problem into two LIS-style DP computations:
           dp1[i] = length of longest strictly increasing subsequence ending at index i...
           dp2[i] = length of longest strictly decreasing subsequence starting at index i...
                  = equivalently, LIS from right-to-left, ending at i when scanning backwards...
      -> At each index i (treated as the peak):
           bitonic_length(i) = dp1[i] + dp2[i] - 1 (subtract 1 to avoid double-counting the peak)...
      -> Take the maximum bitonic length across all possible peak positions...

    Example:
      -> arr = [1, 11, 2, 10, 4, 5, 2, 1]:
           dp1 = [1, 2, 2, 3, 3, 4, 2, 1] (LIS ending at each index)...
           dp2 = [1, 1, 1, 4, 3, 3, 2, 1] (decreasing subseq starting at each index)...
           Peak at index 3 (value=10): dp1[3]+dp2[3]-1 = 3+4-1 = 6...
           Result: 6, sequence: [1, 2, 10, 4, 2, 1]...
      -> arr = [1, 3, 5, 4, 2]:
           dp1 = [1, 2, 3, 3, 2], dp2 = [1, 1, 3, 2, 1]...
           Peak at index 2 (value=5): 3+3-1 = 5 = entire array...

    Phase 1 - Compute dp1[] (Left to Right LIS):
      -> For each curr from 0 to n-1:
           dp1[curr] = 1 (every element is an increasing sequence of length 1)...
           For each prev from 0 to curr-1:
             If arr[curr] > arr[prev] AND dp1[curr] < dp1[prev] + 1:
               dp1[curr] = dp1[prev] + 1...

    Phase 2 - Compute dp2[] (Right to Left LIS, i.e., Decreasing from Left):
      -> For each curr from n-1 down to 0:
           dp2[curr] = 1...
           For each next from n-1 down to curr+1:
             If arr[curr] > arr[next] AND dp2[curr] < dp2[next] + 1:
               dp2[curr] = dp2[next] + 1...
           maxLength = Math.max(maxLength, dp1[curr] + dp2[curr] - 1)...

    Why dp2 Captures the Decreasing Half:
      -> dp2[i] measures: how many elements starting at i can form a strictly decreasing sequence...
      -> Looking rightward for elements smaller than arr[curr] (arr[curr] > arr[next]) is LIS mirrored...
      -> dp2[i] = length of longest decreasing subsequence starting at i...

    Why Subtract 1 in the Formula:
      -> dp1[i] counts the peak element as part of the increasing half...
      -> dp2[i] counts the peak element as part of the decreasing half...
      -> Simply adding dp1[i] + dp2[i] double-counts the peak element...
      -> Subtracting 1 removes the duplicate...

    Step-by-Step Trace (arr = [1, 3, 5, 4, 2]):
      -> Phase 1 dp1:
           curr=0: dp1[0]=1...
           curr=1: 3>1 → dp1[1]=2...
           curr=2: 5>3 → dp1[2]=3...
           curr=3: 4>1→2, 4>3→3 → dp1[3]=3...
           curr=4: 2>1 → dp1[4]=2...
           dp1 = [1, 2, 3, 3, 2]...
      -> Phase 2 dp2 + maxLength:
           curr=4: dp2[4]=1, maxLength=max(1, 2+1-1)=2...
           curr=3: 4>2 → dp2[3]=2, maxLength=max(2, 3+2-1)=4...
           curr=2: 5>4→2, 5>2→2 → dp2[2]=3, maxLength=max(4, 3+3-1)=5...
           curr=1: 3>2 → dp2[1]=2, maxLength=max(5, 2+2-1)=5...
           curr=0: 1>nothing → dp2[0]=1, maxLength=max(5, 1+1-1)=5...
           Result: 5...

    Degenerate Cases Handled Correctly:
      -> Purely increasing [1,2,3,4,5]: dp2[i]=1 everywhere, dp1[n-1]=n → n+1-1=n...
      -> Purely decreasing [5,4,3,2,1]: dp1[i]=1 everywhere, dp2[0]=n → 1+n-1=n...
      -> Single element: dp1[0]=1, dp2[0]=1 → 1+1-1=1...
      -> All same elements: no prev satisfies strictly greater → all dp[i]=1 → result=1...

    Edge Cases:
      -> Single element → both dp values = 1 → result = 1...
      -> All same → no strictly greater pairs → all dp[i]=1 → result = 1...
      -> Strictly increasing → dp2[i]=1 for all, dp1 builds normally → result = n...
      -> Strictly decreasing → dp1[i]=1 for all, dp2 builds normally → result = n...

    Time and Space Complexity:
      -> Time:  O(n²) — two nested loops for dp1 and two nested loops for dp2...
      -> Space: O(n) — two arrays dp1[] and dp2[] of size n...

    Applications:
      -> Terrain profile analysis for mountain-shaped landforms...
      -> Finding valley and ridge patterns in time-series sensor data...
      -> Competitive programming bitonic subsequence length problems...
      -> Amplitude envelope detection in signal processing...

*/

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
