package Dynamic_Programming;

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
