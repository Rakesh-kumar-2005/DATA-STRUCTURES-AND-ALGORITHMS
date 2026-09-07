package Dynamic_Programming;

import java.util.ArrayList;
import java.util.Arrays;

public class Longest_Increasing_Subsequence_Binary {

    private static int lower_bound(ArrayList<Integer> list, int target) {

        int low = 0;
        int high = list.size() - 1;
        int idx = - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) >= target) {
                idx = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return idx;
    }

    private static int longestIncreasingSubsequence(int arr[]) {
        int n = arr.length;
        ArrayList<Integer> list = new ArrayList<>();

        list.add(arr[0]);
        int maxLen = 1;

        for (int i = 1; i < n; i++) {
            if (arr[i] > list.get(list.size() - 1)) {
                list.add(arr[i]);
                maxLen++;
            } else {
                int idx = lower_bound(list, arr[i]);
                list.set(idx, arr[i]);
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     LONGEST INCREASING SUBSEQUENCE (BINARY SEARCH)           ║");
        System.out.println("║  Find LIS length in O(n log n) using a tails array and       ║");
        System.out.println("║  binary search                                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] arr1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Input: " + Arrays.toString(arr1));
        System.out.println("\nLongest increasing subsequence: [2, 3, 7, 101] (length 4)\n");

        int result1 = longestIncreasingSubsequence(arr1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result1 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Strictly Increasing ===");
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(arr2));
        System.out.println("\nEntire array is already increasing → length 5\n");

        int result2 = longestIncreasingSubsequence(arr2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result2 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Strictly Decreasing ===");
        int[] arr3 = {5, 4, 3, 2, 1};
        System.out.println("Input: " + Arrays.toString(arr3));
        System.out.println("\nNo increasing pair exists, best is any single element → length 1\n");

        int result3 = longestIncreasingSubsequence(arr3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result3 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: All Same Elements ===");
        int[] arr4 = {7, 7, 7, 7};
        System.out.println("Input: " + Arrays.toString(arr4));
        System.out.println("\nStrictly increasing means duplicates can't extend it → length 1\n");

        int result4 = longestIncreasingSubsequence(arr4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result4 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Element ===");
        int[] arr5 = {42};
        System.out.println("Input: " + Arrays.toString(arr5));
        System.out.println("\nOnly one element → length 1\n");

        int result5 = longestIncreasingSubsequence(arr5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result5 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Zigzag Pattern ===");
        int[] arr6 = {0, 1, 0, 3, 2, 3};
        System.out.println("Input: " + Arrays.toString(arr6));
        System.out.println("\nLongest increasing subsequence: [0, 1, 2, 3] (length 4)\n");

        int result6 = longestIncreasingSubsequence(arr6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result6 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Multiple Valid LIS Paths ===");
        int[] arr7 = {3, 10, 2, 1, 20};
        System.out.println("Input: " + Arrays.toString(arr7));
        System.out.println("\nLongest increasing subsequence: [3, 10, 20] (length 3)\n");

        int result7 = longestIncreasingSubsequence(arr7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result7 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the length of the longest strictly increasing ║");
        System.out.println("║           subsequence in O(n log n) time                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Maintain a 'Tails' Array, Not the Real LIS     ║");
        System.out.println("║    list[i] holds the SMALLEST possible tail value for an     ║");
        System.out.println("║    increasing subsequence of length i+1. This list is NOT    ║");
        System.out.println("║    necessarily a valid subsequence itself — only its LENGTH  ║");
        System.out.println("║    is meaningful.                                            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Single Pass Logic:                                          ║");
        System.out.println("║    For each new value arr[i]:                                ║");
        System.out.println("║      If arr[i] > last element in list: extend list, maxLen++ ║");
        System.out.println("║      Else: binary search for the first element >= arr[i],    ║");
        System.out.println("║        replace it with arr[i] (keeps tails minimal)          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  lower_bound Logic:                                          ║");
        System.out.println("║    Binary search for the leftmost index where                ║");
        System.out.println("║    list.get(mid) >= target                                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: arr = [10, 9, 2, 5, 3, 7, 101, 18]                 ║");
        System.out.println("║    10 → [10]                                                 ║");
        System.out.println("║    9  → [9]  (replaces 10)                                   ║");
        System.out.println("║    2  → [2]  (replaces 9)                                    ║");
        System.out.println("║    5  → [2, 5]                                               ║");
        System.out.println("║    3  → [2, 3]  (replaces 5)                                 ║");
        System.out.println("║    7  → [2, 3, 7]                                            ║");
        System.out.println("║    101→ [2, 3, 7, 101]                                       ║");
        System.out.println("║    18 → [2, 3, 7, 18]  (replaces 101)                        ║");
        System.out.println("║    Final length: 4                                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • The tails list itself is NOT the actual LIS             ║");
        System.out.println("║    • Only the length is guaranteed correct, not the elements ║");
        System.out.println("║    • Assumes arr has at least one element (arr[0] used)      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n log n) — n insertions/replacements,    ║");
        System.out.println("║                    each with O(log n) binary search          ║");
        System.out.println("║  Space Complexity: O(n) for the tails array                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}