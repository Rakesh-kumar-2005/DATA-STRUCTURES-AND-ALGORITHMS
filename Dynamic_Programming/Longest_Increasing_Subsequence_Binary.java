package Dynamic_Programming;

/*

    Description:
      Following program finds the length of the Longest Increasing Subsequence (LIS)
        in O(n log n) time using a tails array maintained with binary search...

    Problem Statement:
      -> Given an integer array arr...
      -> Find the length of the longest strictly increasing subsequence...
      -> Elements need not be contiguous but must maintain original relative order...
      -> Return only the length, not the actual subsequence...

    Key Insight:
      -> Maintain a list where list[i] holds the smallest possible tail value
           for any increasing subsequence of length i+1 seen so far...
      -> This list is always sorted in ascending order (invariant maintained throughout)...
      -> The list itself is NOT necessarily a valid LIS; only its size is meaningful...
      -> Keeping tails minimal maximizes the chance of future extensions...
      -> Binary search on this sorted list gives O(log n) per element → O(n log n) total...

    Example:
      -> arr = [10, 9, 2, 5, 3, 7, 101, 18]:
           10  → list=[10]...
           9   → replace 10 with 9  → list=[9]...
           2   → replace 9 with 2   → list=[2]...
           5   → extend             → list=[2, 5]...
           3   → replace 5 with 3   → list=[2, 3]...
           7   → extend             → list=[2, 3, 7]...
           101 → extend             → list=[2, 3, 7, 101]...
           18  → replace 101 with 18→ list=[2, 3, 7, 18]...
           LIS length = 4...
      -> Note: [2, 3, 7, 18] is NOT a valid LIS of the array, but its size = 4 is correct...

    Algorithm Steps:
      -> Initialize list with arr[0], maxLen = 1...
      -> For each arr[i] from index 1 to n-1:
           If arr[i] > list.getLast(): append arr[i], maxLen++...
           Else: find lower_bound position (first index ≥ arr[i]), replace list[pos] with arr[i]...
      -> Return maxLen...

    lower_bound() Binary Search:
      -> Finds the leftmost index in list where list[mid] >= target...
      -> Standard binary search: when list[mid] >= target, record idx and search left half...
      -> When list[mid] < target, search right half...
      -> Returns the index of the first element >= target...
      -> This is the position where arr[i] should be placed to minimize the tail...

    Why Replace Instead of Insert:
      -> Replacing keeps the list size unchanged (LIS length doesn't grow)...
      -> A smaller tail at the same position allows more elements to extend the LIS later...
      -> Example: list=[2, 5] and arr[i]=3 → replace 5 with 3 → list=[2, 3]...
           Now both 4 and 5 can extend the LIS (only 5+ could before)...
      -> This greedy replacement is key to the O(n log n) correctness...

    Why the List Stays Sorted:
      -> Initially list = [arr[0]], trivially sorted...
      -> Extend case: arr[i] > last element → appended at end → still sorted...
      -> Replace case: lower_bound finds first element >= arr[i], replaces it with arr[i]...
           list[pos-1] < arr[i] (otherwise pos would be smaller)...
           arr[i] <= list[pos] (by definition of lower_bound)...
           After replacement: list[pos-1] < arr[i] = list[pos] ≤ list[pos+1]...
           Sorted invariant maintained...

    Step-by-Step Trace (arr = [0, 1, 0, 3, 2, 3]):
      -> list=[0], maxLen=1...
      -> arr[1]=1 > 0: list=[0,1], maxLen=2...
      -> arr[2]=0: lower_bound(list,0)=0 → replace: list=[0,1]...
      -> arr[3]=3 > 1: list=[0,1,3], maxLen=3...
      -> arr[4]=2: lower_bound(list,2)=2 → replace: list=[0,1,2]...
      -> arr[5]=3 > 2: list=[0,1,2,3], maxLen=4...
      -> Result: 4...

    Difference From O(n²) dp Approach:
      -> O(n²): dp[i] = LIS ending at index i → can reconstruct actual subsequence...
      -> O(n log n): tails list → only length is correct, actual sequence not directly retrievable...
      -> O(n log n) is strictly better for length-only queries on large inputs...
      -> For LIS reconstruction, the O(n²) approach with prev[] is needed...

    Edge Cases:
      -> Single element → list=[arr[0]], maxLen=1, loop never executes → return 1...
      -> All same elements → each element's lower_bound = 0, replaces list[0] → maxLen stays 1...
      -> Strictly increasing → every element extends the list → maxLen = n...
      -> Strictly decreasing → every element replaces list[0] → maxLen stays 1...

    Time and Space Complexity:
      -> Time:  O(n log n) — n elements, each requiring O(log n) binary search...
      -> Space: O(n) — tails ArrayList grows up to LIS length (at most n)...

    Applications:
      -> Finding the longest trend in stock or sensor data...
      -> Chain scheduling where each task requires the previous to complete first...
      -> Box stacking and envelope nesting problems (reducible to LIS)...
      -> Competitive programming where O(n²) is too slow for large inputs...

*/

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
