package Dynamic_Programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Largest_Divisible_Subset {

    private static ArrayList<Integer> largestDivisibleSubset(int[] nums) {

        int n = nums.length;
        Arrays.sort(nums);

        int[] dp = new int[n];
        int[] parent = new int[n];

        int maxLength = 0;
        int lastIdx = 1;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if (maxLength < dp[i]) {
                maxLength = dp[i];
                lastIdx = i;
            }
        }

        ArrayList<Integer> list = new ArrayList<>();
        list.add(nums[lastIdx]);

        while (parent[lastIdx] != lastIdx) {
            lastIdx = parent[lastIdx];
            list.add(nums[lastIdx]);
        }

        Collections.reverse(list);
        return list;

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              LARGEST DIVISIBLE SUBSET                        ║");
        System.out.println("║  Find the largest subset where every pair (a, b) satisfies   ║");
        System.out.println("║  a % b == 0 or b % a == 0                                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        int[] nums1 = {1, 2, 3};
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("\nSorted: [1, 2, 3]");
        System.out.println("1 divides 2, so [1, 2] is a valid chain (length 2)\n");

        ArrayList<Integer> result1 = largestDivisibleSubset(nums1.clone());
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: [1, 2] (or [1, 3])");
        System.out.println("  Status: " + (result1.size() == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Powers Chain ===");
        int[] nums2 = {1, 2, 4, 8};
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("\nEntire array forms a divisibility chain: 1→2→4→8\n");

        ArrayList<Integer> result2 = largestDivisibleSubset(nums2.clone());
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: [1, 2, 4, 8]");
        System.out.println("  Status: " + (result2.equals(Arrays.asList(1, 2, 4, 8)) ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Larger Mixed Case ===");
        int[] nums3 = {1, 2, 4, 8, 9, 72};
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("\nBest chain: 1→2→4→8→72 (length 5)\n");

        ArrayList<Integer> result3 = largestDivisibleSubset(nums3.clone());
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected length: 5");
        System.out.println("  Status: " + (result3.size() == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Element ===");
        int[] nums4 = {5};
        System.out.println("Input: " + Arrays.toString(nums4));
        System.out.println("\nOnly one element, chain is just itself\n");

        ArrayList<Integer> result4 = largestDivisibleSubset(nums4.clone());
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: [5]");
        System.out.println("  Status: " + (result4.equals(Arrays.asList(5)) ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: No Divisibility Relations ===");
        int[] nums5 = {3, 5, 7};
        System.out.println("Input: " + Arrays.toString(nums5));
        System.out.println("\nNo element divides another, best chain length is 1\n");

        ArrayList<Integer> result5 = largestDivisibleSubset(nums5.clone());
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected length: 1");
        System.out.println("  Status: " + (result5.size() == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Duplicate-Free With Common Factor ===");
        int[] nums6 = {4, 8, 10, 240};
        System.out.println("Input: " + Arrays.toString(nums6));
        System.out.println("\nBest chain: 4→8→240 or 4→10→240 or similar (length 3)\n");

        ArrayList<Integer> result6 = largestDivisibleSubset(nums6.clone());
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected length: 3");
        System.out.println("  Status: " + (result6.size() == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: All Same Value ===");
        int[] nums7 = {6, 6, 6};
        System.out.println("Input: " + Arrays.toString(nums7));
        System.out.println("\nEvery pair divides evenly (6%6==0), full chain length 3\n");

        ArrayList<Integer> result7 = largestDivisibleSubset(nums7.clone());
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: [6, 6, 6]");
        System.out.println("  Status: " + (result7.equals(Arrays.asList(6, 6, 6)) ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the largest subset where every pair of        ║");
        System.out.println("║           elements satisfies a % b == 0 or b % a == 0        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Sorting Reduces the Problem to LIS-Style DP    ║");
        System.out.println("║    After sorting ascending, if nums[i] % nums[j] == 0 for    ║");
        System.out.println("║    some j < i, then nums[j] automatically divides everything ║");
        System.out.println("║    smaller in the chain too — this mirrors LIS's 'increasing'║");
        System.out.println("║    relation but with divisibility instead of '<'.            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Steps (same structure as LIS reconstruction):               ║");
        System.out.println("║    1) Sort nums ascending                                    ║");
        System.out.println("║    2) dp[i] = length of best divisible chain ending at i     ║");
        System.out.println("║       (default 1), parent[i] = i initially                   ║");
        System.out.println("║    3) For each i, check all j < i: if nums[i] % nums[j] == 0 ║");
        System.out.println("║       and dp[j]+1 > dp[i], update dp[i] and parent[i] = j    ║");
        System.out.println("║    4) Track lastIdx = index with the largest dp value        ║");
        System.out.println("║    5) Walk backwards via parent[] from lastIdx until it      ║");
        System.out.println("║       points to itself, collecting each nums value           ║");
        System.out.println("║    6) Reverse the collected list for ascending chain order   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: nums = [1, 2, 4, 8, 9, 72]                         ║");
        System.out.println("║    dp = [1, 2, 3, 4, 2, 5], lastIdx = 5 (nums[5]=72, dp=5)   ║");
        System.out.println("║    parent chain: 5→3→2→1→0→0                                 ║");
        System.out.println("║    Collected backwards: [72,8,4,2,1] → reversed: [1,2,4,8,72]║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Sorting guarantees the divisor always comes before      ║");
        System.out.println("║      the multiple, enabling the LIS-style DP transition      ║");
        System.out.println("║    • Multiple valid largest subsets may exist                ║");
        System.out.println("║    • Input array is mutated in place by Arrays.sort          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n²) for the double loop over pairs       ║");
        System.out.println("║  Space Complexity: O(n) for dp[], parent[], and result list  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}