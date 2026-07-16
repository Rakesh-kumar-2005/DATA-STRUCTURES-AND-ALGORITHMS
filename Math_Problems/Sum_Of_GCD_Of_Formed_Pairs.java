package Math_Problems;

import java.util.Arrays;

public class Sum_Of_GCD_Of_Formed_Pairs {

    private static long gcdSum(int[] nums) {

        int n = nums.length;
        long[] prefixGCD = new long[n];

        long max = nums[0];
        for (int i = 0; i < n; i++) {
            max = Math.max(nums[i], max);
            prefixGCD[i] = gcd(max, nums[i]);
        }

        Arrays.sort(prefixGCD);
        int low = 0;
        int high = n - 1;
        long sum = 0;

        while (low < high) {
            sum += gcd(prefixGCD[low], prefixGCD[high]);
            low++;
            high--;
        }

        return sum;
    }

    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           SUM OF GCD OF FORMED PAIRS                         ║");
        System.out.println("║  Build prefix-max GCDs, sort them, pair from outside-in,     ║");
        System.out.println("║  and sum the GCD of each paired value                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Increasing Array ===");
        int[] nums1 = {1, 2, 3};
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("\nBuilding prefixGCD (running max vs current):");
        System.out.println("  i=0: max=1, gcd(1,1) → 1");
        System.out.println("  i=1: max=2, gcd(2,2) → 2");
        System.out.println("  i=2: max=3, gcd(3,3) → 3");
        System.out.println("prefixGCD = [1, 2, 3], sorted = [1, 2, 3]");
        System.out.println("Pairing outside-in:");
        System.out.println("  (low=0,high=2): gcd(1,3)=1 → sum=1");
        System.out.println("  low=1,high=1 → stop\n");

        long result1 = gcdSum(nums1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result1 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Powers of Two ===");
        int[] nums2 = {2, 4, 8};
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("\nBuilding prefixGCD:");
        System.out.println("  i=0: max=2, gcd(2,2) → 2");
        System.out.println("  i=1: max=4, gcd(4,4) → 4");
        System.out.println("  i=2: max=8, gcd(8,8) → 8");
        System.out.println("prefixGCD = [2, 4, 8], sorted = [2, 4, 8]");
        System.out.println("Pairing outside-in:");
        System.out.println("  (low=0,high=2): gcd(2,8)=2 → sum=2");
        System.out.println("  low=1,high=1 → stop\n");

        long result2 = gcdSum(nums2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result2 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: All Identical Elements ===");
        int[] nums3 = {5, 5, 5, 5};
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("\nBuilding prefixGCD:");
        System.out.println("  Every max stays 5, gcd(5,5) → 5 each time");
        System.out.println("prefixGCD = [5, 5, 5, 5], sorted = [5, 5, 5, 5]");
        System.out.println("Pairing outside-in:");
        System.out.println("  (low=0,high=3): gcd(5,5)=5 → sum=5");
        System.out.println("  (low=1,high=2): gcd(5,5)=5 → sum=10");
        System.out.println("  low=2,high=1 → stop\n");

        long result3 = gcdSum(nums3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 10");
        System.out.println("  Status: " + (result3 == 10 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Element ===");
        int[] nums4 = {1};
        System.out.println("Input: " + Arrays.toString(nums4));
        System.out.println("\nBuilding prefixGCD:");
        System.out.println("  i=0: max=1, gcd(1,1) → 1");
        System.out.println("prefixGCD = [1]");
        System.out.println("low=0, high=0 → loop never runs, sum stays 0\n");

        long result4 = gcdSum(nums4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result4 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Mixed Values ===");
        int[] nums5 = {7, 3, 14, 6};
        System.out.println("Input: " + Arrays.toString(nums5));
        System.out.println("\nBuilding prefixGCD:");
        System.out.println("  i=0: max=7, gcd(7,7) → 7");
        System.out.println("  i=1: max=7, gcd(7,3) → 1");
        System.out.println("  i=2: max=14, gcd(14,14) → 14");
        System.out.println("  i=3: max=14, gcd(14,6) → 2");
        System.out.println("prefixGCD = [7, 1, 14, 2], sorted = [1, 2, 7, 14]");
        System.out.println("Pairing outside-in:");
        System.out.println("  (low=0,high=3): gcd(1,14)=1 → sum=1");
        System.out.println("  (low=1,high=2): gcd(2,7)=1 → sum=2");
        System.out.println("  low=2,high=1 → stop\n");

        long result5 = gcdSum(nums5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result5 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Multiples of Five ===");
        int[] nums6 = {10, 15, 20, 25, 30};
        System.out.println("Input: " + Arrays.toString(nums6));
        System.out.println("\nBuilding prefixGCD:");
        System.out.println("  i=0: max=10, gcd(10,10) → 10");
        System.out.println("  i=1: max=15, gcd(15,15) → 15");
        System.out.println("  i=2: max=20, gcd(20,20) → 20");
        System.out.println("  i=3: max=25, gcd(25,25) → 25");
        System.out.println("  i=4: max=30, gcd(30,30) → 30");
        System.out.println("prefixGCD = [10, 15, 20, 25, 30], sorted = same");
        System.out.println("Pairing outside-in:");
        System.out.println("  (low=0,high=4): gcd(10,30)=10 → sum=10");
        System.out.println("  (low=1,high=3): gcd(15,25)=5 → sum=15");
        System.out.println("  low=2,high=2 → stop\n");

        long result6 = gcdSum(nums6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 15");
        System.out.println("  Status: " + (result6 == 15 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Five Elements, Varied Factors ===");
        int[] nums7 = {9, 3, 6, 2, 12};
        System.out.println("Input: " + Arrays.toString(nums7));
        System.out.println("\nBuilding prefixGCD:");
        System.out.println("  i=0: max=9, gcd(9,9) → 9");
        System.out.println("  i=1: max=9, gcd(9,3) → 3");
        System.out.println("  i=2: max=9, gcd(9,6) → 3");
        System.out.println("  i=3: max=9, gcd(9,2) → 1");
        System.out.println("  i=4: max=12, gcd(12,12) → 12");
        System.out.println("prefixGCD = [9, 3, 3, 1, 12], sorted = [1, 3, 3, 9, 12]");
        System.out.println("Pairing outside-in:");
        System.out.println("  (low=0,high=4): gcd(1,12)=1 → sum=1");
        System.out.println("  (low=1,high=3): gcd(3,9)=3 → sum=4");
        System.out.println("  low=2,high=2 → stop\n");

        long result7 = gcdSum(nums7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result7 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Build a prefix-max-based GCD array, sort it,       ║");
        System.out.println("║           then sum GCDs of values paired from outside in     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Two Separate Reductions                        ║");
        System.out.println("║    1) Compress each index into gcd(runningMax, nums[i])      ║");
        System.out.println("║    2) Sort that compressed array, then pair smallest         ║");
        System.out.println("║       with largest, shrinking inward                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Build prefixGCD Array                              ║");
        System.out.println("║    Track running max as array is scanned left to right       ║");
        System.out.println("║    prefixGCD[i] = gcd(runningMax, nums[i])                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Sort and Pair From the Ends                        ║");
        System.out.println("║    Sort prefixGCD ascending                                  ║");
        System.out.println("║    low starts at 0, high starts at n-1                       ║");
        System.out.println("║    While low < high:                                         ║");
        System.out.println("║      sum += gcd(prefixGCD[low], prefixGCD[high])             ║");
        System.out.println("║      low++, high--                                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: nums = [9, 3, 6, 2, 12]                            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  prefixGCD build:                                            ║");
        System.out.println("║    9→9, 3→3, 6→3, 2→1, 12→12                                 ║");
        System.out.println("║    prefixGCD = [9, 3, 3, 1, 12]                              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Sorted: [1, 3, 3, 9, 12]                                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Pairing:                                                    ║");
        System.out.println("║    gcd(1,12)=1, gcd(3,9)=3                                   ║");
        System.out.println("║    Total sum = 4                                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Recursive gcd() uses the Euclidean algorithm            ║");
        System.out.println("║    • Sorting reorders prefixGCD, losing original index ties  ║");
        System.out.println("║    • Middle element (if n is odd) is left unpaired           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n log n) for sorting + O(n log(max))     ║");
        System.out.println("║                    for the gcd calls                         ║");
        System.out.println("║  Space Complexity: O(n) for the prefixGCD array              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

}
