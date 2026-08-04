package Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Find_Missing_Elements {


    // HASHSET
    private static ArrayList<Integer> findMissingElements1(int[] nums) {

        ArrayList<Integer> ans = new ArrayList<>();
        HashSet<Integer> st = new HashSet<>();

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int num : nums) {
            st.add(num);
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        st.add(min);
        min++;

        while (min < max) {
            if (! st.contains(min)) {
                ans.add(min);
            }
            min++;
        }

        return ans;
    }

    // SORTING...
    private static ArrayList<Integer> findMissingElements2(int[] nums) {

        ArrayList<Integer> ans = new ArrayList<>();
        Arrays.sort(nums);

        int curr = nums[0];
        for (int i = 0; i < nums.length; i++, curr++) {
            if (nums[i] > curr) {
                ans.add(curr);
                i--;
            }
        }

        return ans;
    }

    // OPTIMIZED
    private static ArrayList<Integer> findMissingElements3(int[] nums) {

        ArrayList<Integer> ans = new ArrayList<>();

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        int[] temp = new int[max + 1];
        for (int i : nums) {
            temp[i]++;
        }

        for (int i = min; i < max; i++) {
            if (temp[i] == 0) {
                ans.add(i);
            }
        }

        return ans;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 FIND MISSING ELEMENTS                        ║");
        System.out.println("║  Find all integers missing between the min and max of the    ║");
        System.out.println("║  array (exclusive of max)                                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");


        System.out.println("=== Test Case 1: Basic Gaps ===");
        int[] nums1 = {5, 10, 6};
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("\nmin=5, max=10");
        System.out.println("Missing between 5 and 10 (exclusive of 10): 7, 8, 9\n");

        ArrayList<Integer> result1a = findMissingElements1(nums1.clone());
        ArrayList<Integer> result1b = findMissingElements2(nums1.clone());
        ArrayList<Integer> result1c = findMissingElements3(nums1.clone());
        System.out.println("✓ HashSet Result: " + result1a);
        System.out.println("✓ Sorting Result: " + result1b);
        System.out.println("✓ Optimized Result: " + result1c);
        System.out.println("  Expected: [7, 8, 9]");
        System.out.println("  Status: " + (result1a.equals(result1b) && result1b.equals(result1c) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 2: No Missing Elements ===");
        int[] nums2 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("\nmin=1, max=5, every number in between is present → no gaps\n");

        ArrayList<Integer> result2a = findMissingElements1(nums2.clone());
        ArrayList<Integer> result2b = findMissingElements2(nums2.clone());
        ArrayList<Integer> result2c = findMissingElements3(nums2.clone());
        System.out.println("✓ HashSet Result: " + result2a);
        System.out.println("✓ Sorting Result: " + result2b);
        System.out.println("✓ Optimized Result: " + result2c);
        System.out.println("  Expected: []");
        System.out.println("  Status: " + (result2a.equals(result2b) && result2b.equals(result2c) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 3: Single Large Gap ===");
        int[] nums3 = {1, 10};
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("\nmin=1, max=10, all of 2-9 are missing\n");

        ArrayList<Integer> result3a = findMissingElements1(nums3.clone());
        ArrayList<Integer> result3b = findMissingElements2(nums3.clone());
        ArrayList<Integer> result3c = findMissingElements3(nums3.clone());
        System.out.println("✓ HashSet Result: " + result3a);
        System.out.println("✓ Sorting Result: " + result3b);
        System.out.println("✓ Optimized Result: " + result3c);
        System.out.println("  Expected: [2, 3, 4, 5, 6, 7, 8, 9]");
        System.out.println("  Status: " + (result3a.equals(result3b) && result3b.equals(result3c) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 4: Two Elements, No Gap ===");
        int[] nums4 = {3, 4};
        System.out.println("Input: " + Arrays.toString(nums4));
        System.out.println("\nmin=3, max=4, consecutive, no missing elements\n");

        ArrayList<Integer> result4a = findMissingElements1(nums4.clone());
        ArrayList<Integer> result4b = findMissingElements2(nums4.clone());
        ArrayList<Integer> result4c = findMissingElements3(nums4.clone());
        System.out.println("✓ HashSet Result: " + result4a);
        System.out.println("✓ Sorting Result: " + result4b);
        System.out.println("✓ Optimized Result: " + result4c);
        System.out.println("  Expected: []");
        System.out.println("  Status: " + (result4a.equals(result4b) && result4b.equals(result4c) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 5: Duplicates Present ===");
        int[] nums5 = {2, 2, 2, 8};
        System.out.println("Input: " + Arrays.toString(nums5));
        System.out.println("\nmin=2, max=8, duplicates don't affect the missing range");
        System.out.println("Missing: 3, 4, 5, 6, 7\n");

        ArrayList<Integer> result5a = findMissingElements1(nums5.clone());
        ArrayList<Integer> result5b = findMissingElements2(nums5.clone());
        ArrayList<Integer> result5c = findMissingElements3(nums5.clone());
        System.out.println("✓ HashSet Result: " + result5a);
        System.out.println("✓ Sorting Result: " + result5b);
        System.out.println("✓ Optimized Result: " + result5c);
        System.out.println("  Expected: [3, 4, 5, 6, 7]");
        System.out.println("  Status: " + (result5a.equals(result5b) && result5b.equals(result5c) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 6: Single Element ===");
        int[] nums6 = {7};
        System.out.println("Input: " + Arrays.toString(nums6));
        System.out.println("\nmin=7, max=7, no range to check → no missing elements\n");

        ArrayList<Integer> result6a = findMissingElements1(nums6.clone());
        ArrayList<Integer> result6b = findMissingElements2(nums6.clone());
        ArrayList<Integer> result6c = findMissingElements3(nums6.clone());
        System.out.println("✓ HashSet Result: " + result6a);
        System.out.println("✓ Sorting Result: " + result6b);
        System.out.println("✓ Optimized Result: " + result6c);
        System.out.println("  Expected: []");
        System.out.println("  Status: " + (result6a.equals(result6b) && result6b.equals(result6c) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("=== Test Case 7: Multiple Gaps Scattered ===");
        int[] nums7 = {4, 7, 9, 12};
        System.out.println("Input: " + Arrays.toString(nums7));
        System.out.println("\nmin=4, max=12");
        System.out.println("Missing: 5, 6, 8, 10, 11\n");

        ArrayList<Integer> result7a = findMissingElements1(nums7.clone());
        ArrayList<Integer> result7b = findMissingElements2(nums7.clone());
        ArrayList<Integer> result7c = findMissingElements3(nums7.clone());
        System.out.println("✓ HashSet Result: " + result7a);
        System.out.println("✓ Sorting Result: " + result7b);
        System.out.println("✓ Optimized Result: " + result7c);
        System.out.println("  Expected: [5, 6, 8, 10, 11]");
        System.out.println("  Status: " + (result7a.equals(result7b) && result7b.equals(result7c) ? "MATCH ✓" : "MISMATCH ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find every integer strictly between the array's    ║");
        System.out.println("║           min and max that does NOT appear in the array      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Three Approaches, Cross-Verified:                           ║");
        System.out.println("║    1) HashSet — store all values, walk min+1 to max-1,       ║");
        System.out.println("║       checking set membership                                ║");
        System.out.println("║    2) Sorting — sort array, walk expected consecutive value  ║");
        System.out.println("║       against actual sorted value, adding gaps as found      ║");
        System.out.println("║    3) Optimized — bucket/counting array sized to max+1,      ║");
        System.out.println("║       mark presence, then scan min to max-1                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: nums = [5, 10, 6]                                  ║");
        System.out.println("║    min=5, max=10 → check 6,7,8,9 against presence            ║");
        System.out.println("║    6 present, 7/8/9 missing → [7, 8, 9]                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Sorting approach mutates the input array in place       ║");
        System.out.println("║    • All three approaches must always agree on output        ║");
        System.out.println("║    • Range is exclusive of max, inclusive of min+1           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n) hashset/optimized, O(n log n) sorting ║");
        System.out.println("║  Space Complexity: O(n) hashset, O(max) optimized, O(1) sort ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}