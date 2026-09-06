package Dynamic_Programming;

/*

    Description:
      Following program reconstructs and prints the actual Longest Increasing Subsequence
        (LIS) by tracking a predecessor pointer array alongside the standard dp[] array...

    Problem Statement:
      -> Given an integer array arr...
      -> Find the longest strictly increasing subsequence (elements need not be contiguous)...
      -> Print the actual subsequence, not just its length...
      -> If multiple LIS of equal length exist, any one valid LIS is acceptable...

    Key Insight:
      -> The classic O(n²) dp[i] approach computes LIS length ending at each index...
      -> Adding a prev[] array records which index j caused each dp[i] to improve...
      -> After computing dp[], the index with maximum dp value is the LIS tail...
      -> Following prev[] pointers backward from the tail rebuilds the full subsequence...
      -> The self-loop condition prev[i] == i marks the LIS head (sequence start)...

    Example:
      -> arr = [3, 10, 2, 1, 20]:
           dp = [1, 2, 1, 1, 3]...
           prev = [0, 0, 2, 3, 1]...
           last_idx = 4 (dp[4]=3, value=20)...
           Chain: 4 → prev[4]=1 → prev[1]=0 → prev[0]=0 (self-loop, stop)...
           Collected: [20, 10, 3] → reversed: [3, 10, 20]...
      -> arr = [10, 9, 2, 5, 3, 7, 101, 18]:
           LIS = [2, 3, 7, 18] or [2, 3, 7, 101] depending on tie-breaking...
      -> arr = [5, 4, 3, 2, 1]:
           dp all 1, any single element qualifies as the LIS → e.g., [5]...

    dp[] Array Semantics:
      -> dp[i] = length of the longest strictly increasing subsequence ending at index i...
      -> Initialize all dp[i] = 1 (every single element is an LIS of length 1)...
      -> For each i from 1 to n-1:
           For each j from 0 to i-1:
             If arr[j] < arr[i] AND dp[j] + 1 > dp[i]:
               dp[i] = dp[j] + 1...
               prev[i] = j (remember the predecessor)...

    prev[] Array Semantics:
      -> prev[i] initialized to i (self-loop → marks potential LIS start)...
      -> When dp[i] improves through predecessor j: prev[i] = j...
      -> prev[i] stores the index of the previous element in the LIS ending at i...
      -> Self-loop (prev[last_idx] == last_idx) means this element started a new chain...

    Backtracking via prev[]:
      -> Start at last_idx (index with maximum dp value)...
      -> Add arr[last_idx] to list...
      -> Follow prev[last_idx] → prev[prev[last_idx]] → ... until prev[i] == i...
      -> Each iteration adds one more LIS element in reverse order...
      -> Reverse the collected list at the end to restore original sequence order...

    Step-by-Step Trace (arr = [0, 1, 0, 3, 2, 3]):
      -> Initialize: dp=[1,1,1,1,1,1], prev=[0,1,2,3,4,5]...
      -> i=1 (arr=1): j=0(arr=0<1,dp=2) → dp[1]=2, prev[1]=0...
      -> i=2 (arr=0): no j qualifies (nothing < 0 before)...
      -> i=3 (arr=3): j=1(arr=1<3,dp=3) → dp[3]=3, prev[3]=1...
      -> i=4 (arr=2): j=1(arr=1<2,dp=3) → dp[4]=3, prev[4]=1...
      -> i=5 (arr=3): j=4(arr=2<3,dp=4) → dp[5]=4, prev[5]=4...
      -> max=4, last_idx=5...
      -> Chain: 5→4→1→0→0(self-loop)...
      -> Collected: [arr[5]=3, arr[4]=2, arr[1]=1, arr[0]=0]...
      -> Reversed: [0, 1, 2, 3]...

    Why Reverse at the End:
      -> Backtracking follows prev[] from tail to head → elements collected in reverse...
      -> The last element of the LIS is collected first, the first element last...
      -> Collections.reverse() restores the correct left-to-right subsequence order...

    Why prev[i] = i Initially:
      -> Self-loop means "I have no predecessor, I am the start of this chain"...
      -> During backtracking, when prev[last_idx] == last_idx, we've reached the start...
      -> This avoids needing a separate sentinel value like -1...

    When Multiple LIS Exist:
      -> Tie-breaking depends on which j is encountered first in the inner loop...
      -> The algorithm consistently picks based on iteration order → deterministic result...
      -> Different valid LIS may be found depending on input arrangement...

    Edge Cases:
      -> Single element → dp[0]=1, prev[0]=0 (self-loop) → prints [arr[0]]...
      -> All same elements → no j satisfies arr[j] < arr[i] → all dp[i]=1 → single element...
      -> Strictly increasing → each i extends the LIS via i-1 → full array printed...
      -> Strictly decreasing → no j qualifies for any i → single element printed...

    Time and Space Complexity:
      -> Time:  O(n²) — double loop for filling dp[] and prev[]...
      -> Space: O(n) — dp[], prev[], and the result ArrayList...

    Applications:
      -> Extracting trend lines from time-series data in financial analysis...
      -> Finding the longest chain of dependent tasks in project scheduling...
      -> Reconstructing evolutionary sequences in bioinformatics...
      -> Debugging DP problems where the actual solution path (not just its length) matters...

*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Printing_Longest_Increasing_Subsequence {

    public static void longestIncreasingSubsequence(int arr[]) {

        int n = arr.length;
        int max = 0;

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int[] prev = new int[n];
        int last_idx = 0;

        for (int i = 0; i < n; i++) {
            prev[i] = i;
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > max) {
                max = dp[i];
                last_idx = i;
            }
        }

        ArrayList<Integer> li = new ArrayList<>();
        li.add(arr[last_idx]);
        while (prev[last_idx] != last_idx) {
            last_idx = prev[last_idx];
            li.add(arr[last_idx]);
        }

        Collections.reverse(li);
        System.out.println(li);

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║       PRINTING LONGEST INCREASING SUBSEQUENCE                ║");
        System.out.println("║  Reconstruct and print the actual longest strictly increasing║");
        System.out.println("║  subsequence, not just its length                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] arr1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Input: " + Arrays.toString(arr1));
        System.out.println("\nExpected LIS: [2, 3, 7, 18] or [2, 3, 7, 101] (length 4)\n");

        System.out.print("✓ Printed sequence: ");
        longestIncreasingSubsequence(arr1);
        System.out.println();

        System.out.println("=== Test Case 2: Strictly Increasing ===");
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(arr2));
        System.out.println("\nEntire array is already increasing, expected: [1, 2, 3, 4, 5]\n");

        System.out.print("✓ Printed sequence: ");
        longestIncreasingSubsequence(arr2);
        System.out.println();

        System.out.println("=== Test Case 3: Strictly Decreasing ===");
        int[] arr3 = {5, 4, 3, 2, 1};
        System.out.println("Input: " + Arrays.toString(arr3));
        System.out.println("\nNo increasing pair exists, expected: single-element sequence\n");

        System.out.print("✓ Printed sequence: ");
        longestIncreasingSubsequence(arr3);
        System.out.println();

        System.out.println("=== Test Case 4: All Same Elements ===");
        int[] arr4 = {7, 7, 7, 7};
        System.out.println("Input: " + Arrays.toString(arr4));
        System.out.println("\nStrictly increasing means duplicates can't extend it, expected: [7]\n");

        System.out.print("✓ Printed sequence: ");
        longestIncreasingSubsequence(arr4);
        System.out.println();

        System.out.println("=== Test Case 5: Single Element ===");
        int[] arr5 = {42};
        System.out.println("Input: " + Arrays.toString(arr5));
        System.out.println("\nOnly one element, expected: [42]\n");

        System.out.print("✓ Printed sequence: ");
        longestIncreasingSubsequence(arr5);
        System.out.println();

        System.out.println("=== Test Case 6: Zigzag Pattern ===");
        int[] arr6 = {0, 1, 0, 3, 2, 3};
        System.out.println("Input: " + Arrays.toString(arr6));
        System.out.println("\nExpected LIS: [0, 1, 2, 3] (length 4)\n");

        System.out.print("✓ Printed sequence: ");
        longestIncreasingSubsequence(arr6);
        System.out.println();

        System.out.println("=== Test Case 7: Multiple Valid LIS Paths ===");
        int[] arr7 = {3, 10, 2, 1, 20};
        System.out.println("Input: " + Arrays.toString(arr7));
        System.out.println("\nExpected LIS: [3, 10, 20] (length 3)\n");

        System.out.print("✓ Printed sequence: ");
        longestIncreasingSubsequence(arr7);
        System.out.println();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Reconstruct the actual longest strictly increasing ║");
        System.out.println("║           subsequence, not just its length                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Track a 'prev' Pointer Alongside dp[]          ║");
        System.out.println("║    Every time dp[i] improves via some earlier j, record      ║");
        System.out.println("║    prev[i] = j. The end of the LIS is the index with the     ║");
        System.out.println("║    highest dp value; following prev[] backwards rebuilds     ║");
        System.out.println("║    the full subsequence.                                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Steps:                                                      ║");
        System.out.println("║    1) dp[i] = length of LIS ending at i (default 1)          ║");
        System.out.println("║    2) prev[i] = i initially (self-loop marks the start)      ║");
        System.out.println("║    3) For each i, check all j < i: if arr[j] < arr[i] and    ║");
        System.out.println("║       dp[j]+1 > dp[i], update dp[i] and prev[i] = j          ║");
        System.out.println("║    4) Track last_idx = index with the largest dp value       ║");
        System.out.println("║    5) Walk backwards via prev[] from last_idx until it       ║");
        System.out.println("║       points to itself, collecting each arr value            ║");
        System.out.println("║    6) Reverse the collected list to get correct order        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: arr = [3, 10, 2, 1, 20]                            ║");
        System.out.println("║    dp = [1, 2, 1, 1, 3], last_idx = 4 (arr[4]=20, dp=3)      ║");
        System.out.println("║    prev chain: 4→1→0→0 (self-loop stops at 0)                ║");
        System.out.println("║    Collected backwards: [20, 10, 3] → reversed: [3, 10, 20]  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • When multiple valid LIS exist, the one found depends    ║");
        System.out.println("║      on which j is scanned first among ties                  ║");
        System.out.println("║    • prev[i] == i acts as the sentinel marking the sequence  ║");
        System.out.println("║      start during backtracking                               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n²) for building dp[] and prev[]         ║");
        System.out.println("║  Space Complexity: O(n) for dp[], prev[], and the result list║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
    }

}
