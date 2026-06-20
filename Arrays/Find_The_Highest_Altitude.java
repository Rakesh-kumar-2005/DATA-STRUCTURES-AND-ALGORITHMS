package Arrays;

public class Find_The_Highest_Altitude {

    private static int largestAltitude(int[] gain) {

        int n = gain.length;
        int[] altitudes = new int[n + 1];
        int maxAltitude = 0;

        for (int i = 0; i < n; i++) {
            altitudes[i + 1] = altitudes[i] + gain[i];
            maxAltitude = Math.max(altitudes[i + 1], maxAltitude);
        }

        return maxAltitude;
    }


    private static String arrayToString(int[] arr) {

        if (arr.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);

            if (i < arr.length - 1) {
                sb.append(", ");
            }

        }

        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              FIND THE HIGHEST ALTITUDE                       ║");
        System.out.println("║  Track altitude starting from 0, find maximum altitude       ║");
        System.out.println("║  gain[i] = altitude change at step i                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Basic Gains ===");
        int[] gain1 = {- 5, 1, 5, 0, - 7};
        System.out.println("Gains: " + arrayToString(gain1));
        System.out.println("\nAltitude tracking:");
        System.out.println("  Start: altitude = 0");
        System.out.println("  -5: altitude = 0 + (-5) = -5");
        System.out.println("  +1: altitude = -5 + 1 = -4");
        System.out.println("  +5: altitude = -4 + 5 = 1 ← max");
        System.out.println("  +0: altitude = 1 + 0 = 1");
        System.out.println("  -7: altitude = 1 + (-7) = -6");
        System.out.println("Maximum altitude: 1\n");

        int result1 = largestAltitude(gain1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result1 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: All Positive ===");
        int[] gain2 = {1, 2, 3, 4};
        System.out.println("Gains: " + arrayToString(gain2));
        System.out.println("\nAltitude tracking:");
        System.out.println("  Start: altitude = 0");
        System.out.println("  +1: altitude = 1");
        System.out.println("  +2: altitude = 3");
        System.out.println("  +3: altitude = 6 ← max");
        System.out.println("  +4: altitude = 10 ← max");
        System.out.println("Maximum altitude: 10\n");

        int result2 = largestAltitude(gain2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 10");
        System.out.println("  Status: " + (result2 == 10 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: All Negative ===");
        int[] gain3 = {- 1, - 2, - 3, - 4};
        System.out.println("Gains: " + arrayToString(gain3));
        System.out.println("\nAltitude tracking:");
        System.out.println("  Start: altitude = 0 ← max");
        System.out.println("  -1: altitude = -1");
        System.out.println("  -2: altitude = -3");
        System.out.println("  -3: altitude = -6");
        System.out.println("  -4: altitude = -10");
        System.out.println("Maximum altitude: 0\n");

        int result3 = largestAltitude(gain3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Gain ===");
        int[] gain4 = {5};
        System.out.println("Gains: " + arrayToString(gain4));
        System.out.println("\nAltitude tracking:");
        System.out.println("  Start: altitude = 0");
        System.out.println("  +5: altitude = 5 ← max");
        System.out.println("Maximum altitude: 5\n");

        int result4 = largestAltitude(gain4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result4 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Mixed Pattern ===");
        int[] gain5 = {2, - 1, 3, - 2, 4};
        System.out.println("Gains: " + arrayToString(gain5));
        System.out.println("\nAltitude tracking:");
        System.out.println("  Start: altitude = 0");
        System.out.println("  +2: altitude = 2");
        System.out.println("  -1: altitude = 1");
        System.out.println("  +3: altitude = 4 ← max");
        System.out.println("  -2: altitude = 2");
        System.out.println("  +4: altitude = 6 ← max");
        System.out.println("Maximum altitude: 6\n");

        int result5 = largestAltitude(gain5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 6");
        System.out.println("  Status: " + (result5 == 6 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Zero Gain ===");
        int[] gain6 = {0};
        System.out.println("Gains: " + arrayToString(gain6));
        System.out.println("\nAltitude tracking:");
        System.out.println("  Start: altitude = 0 ← max");
        System.out.println("  +0: altitude = 0");
        System.out.println("Maximum altitude: 0\n");

        int result6 = largestAltitude(gain6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result6 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Dip Then Rise ===");
        int[] gain7 = {- 10, 5, 8, 2};
        System.out.println("Gains: " + arrayToString(gain7));
        System.out.println("\nAltitude tracking:");
        System.out.println("  Start: altitude = 0 ← max (initially)");
        System.out.println("  -10: altitude = -10");
        System.out.println("  +5: altitude = -5");
        System.out.println("  +8: altitude = 3 ← max");
        System.out.println("  +2: altitude = 5 ← max");
        System.out.println("Maximum altitude: 5\n");

        int result7 = largestAltitude(gain7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result7 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find highest altitude during a journey             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Given:                                                      ║");
        System.out.println("║    • Array of altitude gains/losses                          ║");
        System.out.println("║    • Start at altitude 0                                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Goal: Find maximum altitude reached                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Algorithm:                                                  ║");
        System.out.println("║    1. Create altitudes array: altitudes[n+1]                 ║");
        System.out.println("║       altitudes[0] = 0 (starting altitude)                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║    2. For each gain:                                         ║");
        System.out.println("║       altitudes[i+1] = altitudes[i] + gain[i]                ║");
        System.out.println("║       (cumulative sum: running altitude)                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║    3. Track maximum:                                         ║");
        System.out.println("║       maxAltitude = max(maxAltitude, altitudes[i+1])         ║");
        System.out.println("║                                                              ║");
        System.out.println("║    4. Return maxAltitude                                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight:                                                ║");
        System.out.println("║    This is a prefix sum problem with max tracking            ║");
        System.out.println("║    altitude[i] = sum of gain[0] to gain[i-1]                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: gain = [-5, 1, 5, 0, -7]                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║    Altitude array:                                           ║");
        System.out.println("║    Index:   0   1   2   3   4   5                            ║");
        System.out.println("║    Altitude: 0  -5  -4   1   1  -6                           ║");
        System.out.println("║              ↑               ↑                               ║");
        System.out.println("║            start           max                               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Complexity:                                                 ║");
        System.out.println("║    Time:  O(n) - single pass through gains                   ║");
        System.out.println("║    Space: O(n) - altitudes array                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Space Optimization:                                         ║");
        System.out.println("║    Could use O(1) space:                                     ║");
        System.out.println("║    int current = 0, max = 0;                                 ║");
        System.out.println("║    for (int g : gain) {                                      ║");
        System.out.println("║      current += g;                                           ║");
        System.out.println("║      max = Math.max(max, current);                           ║");
        System.out.println("║    }                                                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

}