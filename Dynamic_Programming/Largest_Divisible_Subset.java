package Dynamic_Programming;

/*

    Description:
      Following program finds the largest subset of an integer array where every pair
        of elements satisfies a divisibility relation, using an LIS-style DP approach...

    Problem Statement:
      -> Given an array of distinct positive integers nums...
      -> A divisible subset is a set where for every pair (a, b): a % b == 0 OR b % a == 0...
      -> Find and return the largest such subset...
      -> If multiple largest subsets exist, return any one of them...

    Key Insight:
      -> After sorting in ascending order, divisibility becomes transitive and one-directional:
           If nums[j] divides nums[i] (j < i after sort), then nums[j] also divides everything
           smaller in the chain → checking nums[i] % nums[j] == 0 is sufficient...
      -> This mirrors the LIS problem exactly, replacing the "less than" relation with "divides"...
      -> Sort → apply LIS-style dp[] with divisibility check → reconstruct with parent[] pointers...

    Example:
      -> nums = [1, 2, 4, 8, 9, 72]:
           Sorted: [1, 2, 4, 8, 9, 72]...
           dp = [1, 2, 3, 4, 2, 5]...
           Chains: 1→2→4→8→72 (length 5)...
           Result: [1, 2, 4, 8, 72]...
      -> nums = [1, 2, 4, 8]:
           Entire array is a divisibility chain: 1→2→4→8...
           Result: [1, 2, 4, 8]...
      -> nums = [3, 5, 7]:
           No element divides another → each element alone → length 1...

    Algorithm Steps:
      -> Sort nums ascending...
      -> Initialize dp[i] = 1 and parent[i] = i for all i...
      -> For each i from 0 to n-1:
           For each j from 0 to i-1:
             If nums[i] % nums[j] == 0 AND dp[j] + 1 > dp[i]:
               dp[i] = dp[j] + 1...
               parent[i] = j...
           Track lastIdx = index with max dp[i]...
      -> Reconstruct via parent[] backtracking from lastIdx...
      -> Reverse the collected list → return...

    Why Sorting Enables One-Directional Check:
      -> Without sorting, checking a % b == 0 OR b % a == 0 requires both directions...
      -> After sorting, nums[j] < nums[i] for j < i (assuming distinct values)...
      -> If nums[i] % nums[j] == 0: nums[j] divides nums[i] → divisibility relation holds...
      -> The reverse (nums[j] % nums[i] == 0) is impossible when nums[j] < nums[i] (distinct)...
      -> Sorting reduces the check to a single modulo operation per pair...

    Step-by-Step Trace (nums = [1, 2, 3]):
      -> After sort: [1, 2, 3]...
      -> i=0: dp[0]=1, parent[0]=0...
      -> i=1: j=0: 2%1==0, dp[1]=2, parent[1]=0...
      -> i=2: j=0: 3%1==0, dp[2]=2, parent[2]=0...
               j=1: 3%2!=0, skip...
      -> maxLength=2, lastIdx=1 (dp[1]=2, or lastIdx=2 if 3 had equal dp)...
      -> Chain: 1→0→0(self) → collected [2,1] → reversed [1,2]...
      -> Result: [1, 2]...

    Parent Array and Backtracking:
      -> parent[i] initialized to i (self-loop → marks chain start)...
      -> When dp[i] improves via j: parent[i] = j (predecessor in best chain)...
      -> Backtrack: start at lastIdx, collect nums[lastIdx], follow parent[] until self-loop...
      -> List built in reverse (tail to head) → Collections.reverse() restores correct order...

    Comparison With LIS:
      -> LIS: pick arr[curr] if arr[curr] > arr[prev] (strictly increasing relation)...
      -> LDS: pick nums[i] if nums[i] % nums[j] == 0 (divisibility relation)...
      -> Both use the same dp[i] semantics: longest valid chain ending at index i...
      -> Both reconstruct via parent[] pointer backtracking...
      -> Both require sorting before applying the DP (LIS on original, LDS on sorted)...

    Edge Cases:
      -> Single element → dp[0]=1, parent[0]=0 (self-loop) → returns [nums[0]]...
      -> All same values → a % a == 0 always → entire array qualifies → full chain...
      -> No divisibility pairs → all dp[i]=1 → single element returned...
      -> Powers of 2 → perfect chain 1→2→4→8→... → entire sorted array returned...

    Time and Space Complexity:
      -> Time:  O(n²) — sorting O(n log n) + double loop O(n²) dominates...
      -> Space: O(n) — dp[], parent[], and result ArrayList...

    Applications:
      -> Finding longest factor chains in number theory problems...
      -> Building hierarchical divisor structures in mathematical datasets...
      -> Competitive programming problems involving divisibility constraints...
      -> Database query optimization with divisibility-based grouping relationships...

*/

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
