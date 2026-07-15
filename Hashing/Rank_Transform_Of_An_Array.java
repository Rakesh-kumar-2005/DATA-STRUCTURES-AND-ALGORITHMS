package Hashing;

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
