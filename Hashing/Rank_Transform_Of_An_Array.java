package Hashing;

/*

    Description:
      Following program transforms each element of an array into its rank when
        the array is sorted, with equal elements receiving the same rank...

    Problem Statement:
      -> Given an integer array arr...
      -> Replace each element with its rank in the sorted order...
      -> Rank starts at 1 for the smallest element...
      -> Equal elements always receive the same rank...
      -> Ranks increase by 1 for each new distinct value (no gaps in ranking)...
      -> Return the transformed array preserving the original element positions...

    Key Insight:
      -> Sorting a copy reveals relative order without disturbing original positions...
      -> A HashMap maps each distinct value to its rank during one pass over sorted copy...
      -> Duplicates are handled naturally: containsKey() check skips already-ranked values...
      -> A second pass rebuilds the answer by looking up each original element in the map...

    Example:
      -> arr = [40, 10, 20, 30]:
           Sorted copy: [10, 20, 30, 40]...
           Map: {10→1, 20→2, 30→3, 40→4}...
           Result: [4, 1, 2, 3]...
      -> arr = [37, 12, 28, 9, 100, 56, 80, 5, 12]:
           Sorted copy: [5, 9, 12, 12, 28, 37, 56, 80, 100]...
           Map: {5→1, 9→2, 12→3, 28→4, 37→5, 56→6, 80→7, 100→8}...
           Result: [5, 3, 4, 2, 8, 6, 7, 1, 3]...
      -> arr = [100, 100, 100]:
           All duplicates → all receive rank 1...
           Result: [1, 1, 1]...

    Two-Phase Algorithm:

      Phase 1 - Build Value-to-Rank Map:
        -> Create a sorted copy of arr using Arrays.copyOf and Arrays.sort...
        -> Initialize rank = 1...
        -> Walk through sorted copy left to right:
             If value not in map: put(value, rank), then increment rank...
             If value already in map: skip (duplicate, reuse existing rank)...
        -> Map now holds the rank of every distinct value...

      Phase 2 - Rebuild Answer Array:
        -> Allocate ans[] of same length as arr...
        -> For each index i: ans[i] = map.get(arr[i])...
        -> Original positions preserved, values replaced with ranks...
        -> Return ans[]...

    Step-by-Step Trace (arr = [5, 3, 5, 3, 1]):
      -> Sorted copy: [1, 3, 3, 5, 5]...
      -> Phase 1 (map building):
           1 → not in map → put(1, 1), rank=2...
           3 → not in map → put(3, 2), rank=3...
           3 → already in map → skip...
           5 → not in map → put(5, 3), rank=4...
           5 → already in map → skip...
      -> Map: {1→1, 3→2, 5→3}...
      -> Phase 2 (lookup):
           arr[0]=5 → map.get(5)=3...
           arr[1]=3 → map.get(3)=2...
           arr[2]=5 → map.get(5)=3...
           arr[3]=3 → map.get(3)=2...
           arr[4]=1 → map.get(1)=1...
      -> Result: [3, 2, 3, 2, 1]...

    Why Sort a Copy Instead of the Original?
      -> The original array positions must be preserved for the output...
      -> Sorting arr in-place would destroy the original index-to-value mapping...
      -> Arrays.copyOf creates an independent copy, leaving arr untouched...
      -> This is a standard pattern: sort-copy + map + lookup...

    Why HashMap Instead of TreeMap?
      -> TreeMap keeps keys sorted, but the copy is already sorted before map building...
      -> HashMap provides O(1) average put and get versus O(log n) for TreeMap...
      -> No need for sorted key traversal after the map is built...
      -> HashMap is the faster choice here...

    Rank Properties:
      -> Ranks are consecutive integers starting from 1 with no gaps...
      -> Two elements are equal if and only if they receive the same rank...
      -> The number of distinct ranks equals the number of distinct values...
      -> Maximum rank equals the number of distinct elements in the array...

    Edge Cases:
      -> Single element → sorted copy identical → map has one entry → result = [1]...
      -> All identical elements → only one rank (1) assigned → result all 1s...
      -> Already sorted array → ranks equal original indices + 1...
      -> Negative numbers → Arrays.sort handles them correctly → ranks assigned from most negative...
      -> Reverse sorted array → highest element gets rank n, lowest gets rank 1...

    Time and Space Complexity:
      -> Time:  O(n log n) for Arrays.sort on the copy + O(n) for map build + O(n) for lookup...
               Total: O(n log n) dominated by the sort...
      -> Space: O(n) for the sorted copy array + O(k) for HashMap where k = distinct elements...
               Total: O(n)...

    Applications:
      -> Competitive ranking systems in sports or scoring tables...
      -> Data normalization replacing raw values with relative ranks...
      -> Statistical rank-based analysis (Spearman correlation, etc.)...
      -> Coordinate compression in competitive programming problems...

*/

import java.util.Arrays;
import java.util.HashMap;

public class Rank_Transform_Of_An_Array {

    private static int[] arrayRankTransform(int[] arr) {

        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);

        int rank = 1;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : copy) {
            if (! map.containsKey(num)) {
                map.put(num, rank);
                rank++;
            }
        }

        int[] ans = new int[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = map.get(arr[i]);
        }

        return ans;
    }

    public static void main(String[] args) {
        
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              RANK TRANSFORM OF AN ARRAY                      ║");
        System.out.println("║  Replace each element with its rank when array is sorted     ║");
        System.out.println("║  (equal elements get the same rank)                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Basic Array ===");
        int[] arr1 = {40, 10, 20, 30};
        System.out.println("Input: " + Arrays.toString(arr1));
        System.out.println("\nSorted copy: [10, 20, 30, 40]");
        System.out.println("Assigning ranks:");
        System.out.println("  10 → rank 1");
        System.out.println("  20 → rank 2");
        System.out.println("  30 → rank 3");
        System.out.println("  40 → rank 4");
        System.out.println("Mapping back to original positions: [4, 1, 2, 3]\n");

        int[] result1 = arrayRankTransform(arr1);
        System.out.println("✓ Result: " + Arrays.toString(result1));
        System.out.println("  Expected: [4, 1, 2, 3]");
        System.out.println("  Status: " + (Arrays.toString(result1).equals("[4, 1, 2, 3]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: All Duplicates ===");
        int[] arr2 = {100, 100, 100};
        System.out.println("Input: " + Arrays.toString(arr2));
        System.out.println("\nSorted copy: [100, 100, 100]");
        System.out.println("Assigning ranks:");
        System.out.println("  100 → rank 1 (first occurrence, duplicates reuse it)");
        System.out.println("Mapping back to original positions: [1, 1, 1]\n");

        int[] result2 = arrayRankTransform(arr2);
        System.out.println("✓ Result: " + Arrays.toString(result2));
        System.out.println("  Expected: [1, 1, 1]");
        System.out.println("  Status: " + (Arrays.toString(result2).equals("[1, 1, 1]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Mixed Duplicates ===");
        int[] arr3 = {37, 12, 28, 9, 100, 56, 80, 5, 12};
        System.out.println("Input: " + Arrays.toString(arr3));
        System.out.println("\nSorted copy: [5, 9, 12, 12, 28, 37, 56, 80, 100]");
        System.out.println("Assigning ranks:");
        System.out.println("  5 → rank 1");
        System.out.println("  9 → rank 2");
        System.out.println("  12 → rank 3 (both occurrences share this rank)");
        System.out.println("  28 → rank 4");
        System.out.println("  37 → rank 5");
        System.out.println("  56 → rank 6");
        System.out.println("  80 → rank 7");
        System.out.println("  100 → rank 8");
        System.out.println("Mapping back to original positions: [5, 3, 4, 2, 8, 6, 7, 1, 3]\n");

        int[] result3 = arrayRankTransform(arr3);
        System.out.println("✓ Result: " + Arrays.toString(result3));
        System.out.println("  Expected: [5, 3, 4, 2, 8, 6, 7, 1, 3]");
        System.out.println("  Status: " + (Arrays.toString(result3).equals("[5, 3, 4, 2, 8, 6, 7, 1, 3]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Element ===");
        int[] arr4 = {5};
        System.out.println("Input: " + Arrays.toString(arr4));
        System.out.println("\nSorted copy: [5]");
        System.out.println("Assigning ranks:");
        System.out.println("  5 → rank 1");
        System.out.println("Mapping back to original positions: [1]\n");

        int[] result4 = arrayRankTransform(arr4);
        System.out.println("✓ Result: " + Arrays.toString(result4));
        System.out.println("  Expected: [1]");
        System.out.println("  Status: " + (Arrays.toString(result4).equals("[1]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Already Sorted ===");
        int[] arr5 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(arr5));
        System.out.println("\nSorted copy: [1, 2, 3, 4, 5]");
        System.out.println("Assigning ranks: each element maps to its own position");
        System.out.println("Mapping back to original positions: [1, 2, 3, 4, 5]\n");

        int[] result5 = arrayRankTransform(arr5);
        System.out.println("✓ Result: " + Arrays.toString(result5));
        System.out.println("  Expected: [1, 2, 3, 4, 5]");
        System.out.println("  Status: " + (Arrays.toString(result5).equals("[1, 2, 3, 4, 5]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Negative Numbers ===");
        int[] arr6 = {- 5, - 3, - 1, 0, 2};
        System.out.println("Input: " + Arrays.toString(arr6));
        System.out.println("\nSorted copy: [-5, -3, -1, 0, 2]");
        System.out.println("Assigning ranks:");
        System.out.println("  -5 → rank 1");
        System.out.println("  -3 → rank 2");
        System.out.println("  -1 → rank 3");
        System.out.println("  0  → rank 4");
        System.out.println("  2  → rank 5");
        System.out.println("Mapping back to original positions: [1, 2, 3, 4, 5]\n");

        int[] result6 = arrayRankTransform(arr6);
        System.out.println("✓ Result: " + Arrays.toString(result6));
        System.out.println("  Expected: [1, 2, 3, 4, 5]");
        System.out.println("  Status: " + (Arrays.toString(result6).equals("[1, 2, 3, 4, 5]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Repeated Pairs ===");
        int[] arr7 = {5, 3, 5, 3, 1};
        System.out.println("Input: " + Arrays.toString(arr7));
        System.out.println("\nSorted copy: [1, 3, 3, 5, 5]");
        System.out.println("Assigning ranks:");
        System.out.println("  1 → rank 1");
        System.out.println("  3 → rank 2 (both occurrences share this rank)");
        System.out.println("  5 → rank 3 (both occurrences share this rank)");
        System.out.println("Mapping back to original positions: [3, 2, 3, 2, 1]\n");

        int[] result7 = arrayRankTransform(arr7);
        System.out.println("✓ Result: " + Arrays.toString(result7));
        System.out.println("  Expected: [3, 2, 3, 2, 1]");
        System.out.println("  Status: " + (Arrays.toString(result7).equals("[3, 2, 3, 2, 1]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Replace each element with its rank in sorted order ║");
        System.out.println("║           (equal elements → equal ranks, ranks start at 1)   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Sort a Copy, Map Values → Ranks                ║");
        System.out.println("║    Sorting reveals relative order without losing original    ║");
        System.out.println("║    positions, since we operate on a separate copy array.     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Build Value → Rank Map                             ║");
        System.out.println("║    Copy and sort the array                                   ║");
        System.out.println("║    Walk through sorted copy, assign next rank                ║");
        System.out.println("║    only the FIRST time a value is seen                       ║");
        System.out.println("║    (duplicates reuse the already-assigned rank)              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Rebuild Answer From Original Array                 ║");
        System.out.println("║    For each element in the original array,                   ║");
        System.out.println("║    look up its rank in the map                               ║");
        System.out.println("║    Original order is preserved, values are replaced          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: arr = [37, 12, 28, 9, 100, 56, 80, 5, 12]          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Sorted copy:                                                ║");
        System.out.println("║    [5, 9, 12, 12, 28, 37, 56, 80, 100]                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Value → Rank Map:                                           ║");
        System.out.println("║    5→1  9→2  12→3  28→4  37→5  56→6  80→7  100→8             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Rebuild using original array order:                         ║");
        System.out.println("║    37→5, 12→3, 28→4, 9→2, 100→8, 56→6, 80→7, 5→1, 12→3       ║");
        System.out.println("║    Result: [5, 3, 4, 2, 8, 6, 7, 1, 3]                       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Ranks start at 1 and increase with no gaps              ║");
        System.out.println("║    • Equal elements always receive equal ranks               ║");
        System.out.println("║    • Original array order is preserved in the output         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n log n) for sorting + O(n) for mapping  ║");
        System.out.println("║  Space Complexity: O(n) for copy array + HashMap             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

}
