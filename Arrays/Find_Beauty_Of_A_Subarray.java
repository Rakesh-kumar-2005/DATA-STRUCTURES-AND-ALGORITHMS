package Arrays;

/*

    Description:
      Following program finds the "beauty" of every size-k sliding window of an array,
        defined as the x-th smallest negative number in that window, or 0 if fewer
        than x negatives exist...

    Problem Statement:
      -> Given an integer array arr, a window size k, and an integer x...
      -> For each contiguous subarray of size k (n-k+1 windows total):
           Count negative numbers from most negative (-50) to least negative (-1)...
           Return the value where the cumulative count first reaches x...
           Return 0 if the window contains fewer than x negative numbers...
      -> Array values are bounded: -50 ≤ arr[i] ≤ 50...
      -> Return an array of all window beauties...

    Key Insight:
      -> Values are bounded in [-50, 50], so a fixed-size frequency array of size 101
           (offset by +50) tracks exact counts of each value in the current window...
      -> Sliding the window involves only two O(1) frequency updates: remove outgoing,
           add incoming element...
      -> autoFind scans only the 50 possible negative values, which is O(1) per window
           since the range is fixed and independent of n...

    Example:
      -> arr = [1, -3, 1, -3, -3], k = 3, x = 2:
           Window 1 [1,-3,1]: only one -3, count never reaches x=2 → beauty = 0...
           Window 2 [-3,1,-3]: two -3's, cumulative at val=-3 reaches 2 → beauty = -3...
           Window 3 [1,-3,-3]: two -3's, same → beauty = -3...
           Result: [0, -3, -3]...
      -> arr = [-5,-4,-3,-2,-1], k = 5, x = 3:
           Only one window, all five negatives present...
           Count: val=-5→1, val=-4→2, val=-3→3 (reaches x=3) → beauty = -3...
           Result: [-3]...

    Frequency Array Design:
      -> Values range from -50 to 50 → shift by +50 to map to indices 0..100...
      -> freq[val + 50] = count of val in current window...
      -> This avoids dynamic structures (no HashMap, no TreeMap)...
      -> All operations (update, lookup) are O(1)...

    autoFind Logic:
      -> Scan val from -50 to -1 (most negative to least negative)...
      -> Accumulate freq[val + 50] into running count...
      -> Return val as soon as count >= x...
      -> If loop completes without reaching x, return 0...
      -> Scanning from most negative ensures we find the x-th smallest correctly...

    Two-Phase Algorithm:

      Phase 1 - Initialize First Window:
        -> Add freq for arr[0] through arr[k-1]...
        -> Compute and store beauty of first window using autoFind...

      Phase 2 - Slide Window (idx from k to n-1):
        -> Decrement freq of outgoing element: freq[arr[idx-k] + 50]--...
        -> Increment freq of incoming element: freq[arr[idx] + 50]++...
        -> Compute and store beauty of updated window using autoFind...

    Step-by-Step Trace (arr = [1,-3,1,-3,-3], k=3, x=2):
      -> Init: freq[-3+50]=freq[47]=1 (one -3 from index 0-2)...
               autoFind: val=-50 to -4 → count stays 0, val=-3 → count=1 < 2 → return 0...
               ans[0] = 0...
      -> Slide idx=3: remove arr[0]=1 (no effect on negatives), add arr[3]=-3 → freq[47]=2...
               autoFind: val=-3 → count=2 >= 2 → return -3...
               ans[1] = -3...
      -> Slide idx=4: remove arr[1]=-3 → freq[47]=1, add arr[4]=-3 → freq[47]=2...
               autoFind: val=-3 → count=2 >= 2 → return -3...
               ans[2] = -3...

    Why Scan From Most Negative to Least Negative:
      -> "x-th smallest negative" means we want the x-th smallest in sorted order...
      -> Most negative = smallest in value (e.g., -50 < -3)...
      -> Scanning from -50 upward counts in sorted ascending order...
      -> First time cumulative count reaches x gives exactly the x-th smallest...

    Edge Cases:
      -> No negatives in any window → autoFind always returns 0...
      -> x exceeds number of negatives in window → autoFind loop completes → return 0...
      -> All elements identical negatives → autoFind reaches x at that one value...
      -> k == n → single window → one beauty value returned...
      -> x = 1 → return the most negative value (smallest) in the window...

    Time and Space Complexity:
      -> Time:  O(n × 50) ≈ O(n) — each slide is O(1) update + O(50) autoFind scan...
      -> Space: O(1) — fixed-size 101-element frequency array (excludes output array)...

    Applications:
      -> Order statistics (k-th smallest) in a sliding window with bounded values...
      -> Negative number analysis in financial or sensor data streams...
      -> Competitive programming window-query problems with fixed value ranges...
      -> Real-time monitoring of threshold crossing counts in bounded data...

*/

import java.util.Arrays;

public class Find_Beauty_Of_A_Subarray {

    private static int autoFind(int[] freq, int x) {
        int count = 0;

        for (int val = - 50; val <= - 1; val++) {
            count += freq[val + 50];
            if (count >= x) {
                return val;
            }
        }

        return 0;
    }

    private static int[] findBeauty(int[] arr, int k, int x) {

        int n = arr.length;
        int[] ans = new int[n - k + 1];
        int[] freq = new int[101];

        for (int i = 0; i < k; i++) {
            freq[arr[i] + 50]++;
        }

        int t = 0;
        ans[t++] = autoFind(freq, x);

        for (int idx = k; idx < n; idx++) {
            freq[arr[idx - k] + 50]--;
            freq[arr[idx] + 50]++;
            ans[t++] = autoFind(freq, x);
        }

        return ans;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              FIND BEAUTY OF A SUBARRAY                       ║");
        System.out.println("║  For each sliding window, find the x-th smallest negative    ║");
        System.out.println("║  number (beauty), or 0 if fewer than x negatives exist       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Mixed Positives and Negatives ===");
        int[] arr1 = {1, - 3, 1, - 3, - 3};
        int k1 = 3, x1 = 2;
        System.out.println("Input: " + Arrays.toString(arr1) + ", k=" + k1 + ", x=" + x1);
        System.out.println("\nWindow [1,-3,1]: only one negative (-3), x=2 not reached → 0");
        System.out.println("Window [-3,1,-3]: two -3's, cumulative count hits 2 at val=-3 → -3");
        System.out.println("Window [1,-3,-3]: two -3's, same logic → -3\n");

        int[] result1 = findBeauty(arr1, k1, x1);
        System.out.println("✓ Result: " + Arrays.toString(result1));
        System.out.println("  Expected: [0, -3, -3]");
        System.out.println("  Status: " + (Arrays.toString(result1).equals("[0, -3, -3]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: All Positive Numbers ===");
        int[] arr2 = {1, 2, 3, 4, 5};
        int k2 = 3, x2 = 1;
        System.out.println("Input: " + Arrays.toString(arr2) + ", k=" + k2 + ", x=" + x2);
        System.out.println("\nNo negatives exist in any window → beauty is always 0\n");

        int[] result2 = findBeauty(arr2, k2, x2);
        System.out.println("✓ Result: " + Arrays.toString(result2));
        System.out.println("  Expected: [0, 0, 0]");
        System.out.println("  Status: " + (Arrays.toString(result2).equals("[0, 0, 0]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Single Negative Per Window ===");
        int[] arr3 = {- 5, 2, 3};
        int k3 = 2, x3 = 1;
        System.out.println("Input: " + Arrays.toString(arr3) + ", k=" + k3 + ", x=" + x3);
        System.out.println("\nWindow [-5,2]: one negative, x=1 reached at -5 → -5");
        System.out.println("Window [2,3]: no negatives → 0\n");

        int[] result3 = findBeauty(arr3, k3, x3);
        System.out.println("✓ Result: " + Arrays.toString(result3));
        System.out.println("  Expected: [-5, 0]");
        System.out.println("  Status: " + (Arrays.toString(result3).equals("[-5, 0]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Window Equals Entire Array ===");
        int[] arr4 = {- 1, - 2, - 3};
        int k4 = 3, x4 = 2;
        System.out.println("Input: " + Arrays.toString(arr4) + ", k=" + k4 + ", x=" + x4);
        System.out.println("\nOnly window: [-1,-2,-3]");
        System.out.println("Cumulative from most negative: -3(1), -2(2) → x=2 reached at -2\n");

        int[] result4 = findBeauty(arr4, k4, x4);
        System.out.println("✓ Result: " + Arrays.toString(result4));
        System.out.println("  Expected: [-2]");
        System.out.println("  Status: " + (Arrays.toString(result4).equals("[-2]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Not Enough Negatives ===");
        int[] arr5 = {- 1, - 2, 3};
        int k5 = 3, x5 = 5;
        System.out.println("Input: " + Arrays.toString(arr5) + ", k=" + k5 + ", x=" + x5);
        System.out.println("\nOnly 2 negatives exist, x=5 never reached → 0\n");

        int[] result5 = findBeauty(arr5, k5, x5);
        System.out.println("✓ Result: " + Arrays.toString(result5));
        System.out.println("  Expected: [0]");
        System.out.println("  Status: " + (Arrays.toString(result5).equals("[0]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: All Identical Negatives ===");
        int[] arr6 = {- 3, - 3, - 3, - 3};
        int k6 = 2, x6 = 1;
        System.out.println("Input: " + Arrays.toString(arr6) + ", k=" + k6 + ", x=" + x6);
        System.out.println("\nEvery window is [-3,-3], count reaches x=1 immediately at -3\n");

        int[] result6 = findBeauty(arr6, k6, x6);
        System.out.println("✓ Result: " + Arrays.toString(result6));
        System.out.println("  Expected: [-3, -3, -3]");
        System.out.println("  Status: " + (Arrays.toString(result6).equals("[-3, -3, -3]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Full Descending Negatives ===");
        int[] arr7 = {- 5, - 4, - 3, - 2, - 1};
        int k7 = 5, x7 = 3;
        System.out.println("Input: " + Arrays.toString(arr7) + ", k=" + k7 + ", x=" + x7);
        System.out.println("\nOnly window: all five negatives");
        System.out.println("Cumulative: -5(1), -4(2), -3(3) → x=3 reached at -3\n");

        int[] result7 = findBeauty(arr7, k7, x7);
        System.out.println("✓ Result: " + Arrays.toString(result7));
        System.out.println("  Expected: [-3]");
        System.out.println("  Status: " + (Arrays.toString(result7).equals("[-3]") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: For every size-k window, find the x-th smallest    ║");
        System.out.println("║           negative number (or 0 if fewer than x negatives    ║");
        System.out.println("║           exist in that window)                              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Frequency Array + Sliding Window               ║");
        System.out.println("║    Values range -50 to 50, so a fixed 101-size frequency     ║");
        System.out.println("║    array (offset by +50) tracks counts without resorting     ║");
        System.out.println("║    the window on every slide.                                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Build Initial Window Frequency                     ║");
        System.out.println("║    freq[arr[i]+50]++ for the first k elements                ║");
        System.out.println("║    Call autoFind to get the first window's beauty            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Slide the Window                                   ║");
        System.out.println("║    Remove outgoing element's frequency, add incoming one     ║");
        System.out.println("║    Call autoFind again for the updated window                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  autoFind Logic:                                             ║");
        System.out.println("║    Walk val from -50 to -1 (most negative to least)          ║");
        System.out.println("║    Accumulate freq[val+50] into a running count              ║");
        System.out.println("║    Return val as soon as count >= x                          ║");
        System.out.println("║    If loop finishes without reaching x, return 0             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: window = [-5,-4,-3,-2,-1], x = 3                   ║");
        System.out.println("║    val=-5: count=1                                           ║");
        System.out.println("║    val=-4: count=2                                           ║");
        System.out.println("║    val=-3: count=3 → reached x=3 → return -3                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Only negative values ever contribute to beauty          ║");
        System.out.println("║    • Beauty is 0 whenever fewer than x negatives are present ║");
        System.out.println("║    • Frequency array avoids re-scanning the whole window     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n × 50) ≈ O(n) since range is fixed      ║");
        System.out.println("║  Space Complexity: O(1) — fixed-size 101-element freq array  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
