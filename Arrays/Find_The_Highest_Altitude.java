package Arrays;

/*

    Description:
      Following program finds the highest altitude reached during a journey
        by computing a running cumulative sum of altitude gains and losses...

    Problem Statement:
      -> Given an integer array gain of size n representing net altitude changes...
      -> A biker starts at altitude 0 before the first point...
      -> gain[i] is the net altitude change between point i and point i+1...
      -> The altitude at each point is the cumulative sum of all gains up to that point...
      -> Return the highest altitude reached at any point during the journey...

    Key Insight:
      -> This is a prefix sum problem combined with maximum tracking...
      -> altitude[i] = sum of gain[0] through gain[i-1]...
      -> altitude[0] = 0 (starting point before any gain is applied)...
      -> Maximum altitude is initialized to 0 since start is always reachable...
      -> Update maximum after each cumulative addition...

    Example:
      -> gain = [-5, 1, 5, 0, -7]:
           altitude[0] = 0   (start)...
           altitude[1] = -5  (0 + (-5))...
           altitude[2] = -4  (-5 + 1)...
           altitude[3] = 1   (-4 + 5) ← max so far...
           altitude[4] = 1   (1 + 0)...
           altitude[5] = -6  (1 + (-7))...
           Maximum altitude = 1...
      -> gain = [1, 2, 3, 4]:
           Altitudes: 0, 1, 3, 6, 10...
           Maximum altitude = 10...

    Algorithm Steps:
      -> Create altitudes array of size n+1...
      -> Set altitudes[0] = 0 (implicit, Java initializes arrays to 0)...
      -> Initialize maxAltitude = 0 (starting altitude is always reachable)...
      -> For each index i from 0 to n-1:
           altitudes[i+1] = altitudes[i] + gain[i]...
           maxAltitude = Math.max(maxAltitude, altitudes[i+1])...
      -> Return maxAltitude...

    Step-by-Step Trace (gain = [2, -1, 3, -2, 4]):
      -> altitudes[0] = 0,  maxAltitude = 0...
      -> altitudes[1] = 2,  maxAltitude = 2...
      -> altitudes[2] = 1,  maxAltitude = 2...
      -> altitudes[3] = 4,  maxAltitude = 4...
      -> altitudes[4] = 2,  maxAltitude = 4...
      -> altitudes[5] = 6,  maxAltitude = 6...
      -> Return 6...

    Why maxAltitude Is Initialized to 0:
      -> The starting altitude is always 0 regardless of gains...
      -> If all gains are negative, the highest point is the starting point (0)...
      -> Initializing to 0 correctly handles the all-negative case...
      -> Example: gain = [-1, -2, -3] → altitudes: 0, -1, -3, -6 → max = 0...

    Space Optimization Possibility:
      -> The altitudes array is not strictly necessary...
      -> A single running variable tracks the current altitude...
      -> int current = 0, max = 0...
      -> For each gain g: current += g; max = Math.max(max, current)...
      -> Reduces space from O(n) to O(1) with identical time complexity...

    Edge Cases:
      -> All positive gains → altitude strictly increasing → last point is highest...
      -> All negative gains → starting altitude 0 is always the maximum...
      -> Single zero gain → altitude stays 0 → maximum = 0...
      -> Dip then rise → maximum may occur mid-journey or at end, not at start...
      -> Mixed gains → maximum tracked continuously captures the true peak...

    Prefix Sum Connection:
      -> altitude[i] is the prefix sum of gain[0..i-1]...
      -> This is a classic prefix sum pattern: build cumulative sum left to right...
      -> Maximum prefix sum = highest altitude...
      -> Starting value 0 acts as the prefix sum before any element is included...

    Time and Space Complexity:
      -> Time:  O(n) — single linear pass through the gain array...
      -> Space: O(n) — altitudes array of size n+1 (reducible to O(1))...

    Applications:
      -> GPS elevation tracking during cycling or hiking routes...
      -> Stock price maximum after a series of daily changes...
      -> Water level monitoring with incremental measurements...
      -> Cumulative score tracking in games with gain and loss events...

*/

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
