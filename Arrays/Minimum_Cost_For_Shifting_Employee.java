package Arrays;

/*

    Description:
      Following program finds the minimum total cost when exactly N employees must be
        shifted to city B, using a baseline-plus-delta greedy strategy...

    Problem Statement:
      -> Given arrays A and B of size n, where A[i] and B[i] are costs for employee i
           in city A and city B respectively...
      -> Given an integer N representing exactly how many employees must go to city B...
      -> The cost of shifting employee i to city B is: min(A[i], B[i]) + B[i]...
           (min(A[i], B[i]) accounts for a sub-decision related to routing or partial cost)...
      -> Choose exactly N employees to shift such that total cost is minimized...
      -> Return the minimum total cost...

    Key Insight:
      -> Assume everyone starts in city A → baseline cost = sum of all A[i]...
      -> For each employee i, compute the extra cost of shifting them to city B:
           extraCost[i] = cityBCost[i] - A[i]...
      -> Negative extra cost means shifting that employee reduces total cost...
      -> To minimize total cost, pick the N employees with the smallest extraCost values...
      -> Sort extraCost and greedily add the N smallest (most negative first)...

    Extra Cost Formula:
      -> cityBCost[i] = Math.min(A[i], B[i]) + B[i]...
      -> This captures the effective cost of assigning employee i to city B...
      -> extraCost[i] = cityBCost[i] - A[i] = cost change from keeping them in A to shifting...
      -> If extraCost[i] < 0: shifting saves money relative to city A...
      -> If extraCost[i] > 0: shifting costs more but may still be required if N demands it...

    Example:
      -> N=2, A=[7, 3, 9], B=[2, 10, 1]:
           Baseline = 7 + 3 + 9 = 19...
           cityBCost[0] = min(7,2)+2 = 4, extraCost[0] = 4-7 = -3...
           cityBCost[1] = min(3,10)+10 = 13, extraCost[1] = 13-3 = 10...
           cityBCost[2] = min(9,1)+1 = 2, extraCost[2] = 2-9 = -7...
           Sorted extraCost: [-7, -3, 10]...
           Pick N=2 smallest: -7 + (-3) = -10...
           Total: 19 + (-10) = 9...
      -> N=1, A=[10, 20], B=[5, 8]:
           Baseline = 30...
           extraCost: [0, -4], sorted: [-4, 0]...
           Pick 1 smallest: -4 → Total: 30 + (-4) = 26...

    Algorithm Steps:
      -> Initialize ans = 0...
      -> For each employee i from 0 to n-1:
           Add A[i] to ans (build baseline)...
           Compute cityBCost[i] = Math.min(A[i], B[i]) + B[i]...
           Compute extraCost[i] = cityBCost[i] - A[i]...
      -> Sort extraCost in ascending order...
      -> For i from 0 to N-1: add extraCost[i] to ans...
      -> Return ans...

    Why Sort and Pick N Smallest:
      -> Sorting extraCost in ascending order puts the most beneficial (or least harmful) shifts first...
      -> Negative values reduce total cost → greedily take all negatives first...
      -> If more than N negatives exist, we only take N (as required)...
      -> If fewer than N negatives exist, we take some positive values too (forced by the N constraint)...
      -> This greedy selection is optimal because all shifts are independent...

    Step-by-Step Trace (N=2, A=[10,20,30,40], B=[5,15,25,50]):
      -> Baseline = 10+20+30+40 = 100...
      -> cityBCost[0] = min(10,5)+5 = 10, extra = 0...
      -> cityBCost[1] = min(20,15)+15 = 30, extra = 10...
      -> cityBCost[2] = min(30,25)+25 = 50, extra = 20...
      -> cityBCost[3] = min(40,50)+50 = 90, extra = 50...
      -> Sorted extraCost: [0, 10, 20, 50]...
      -> Pick 2 smallest: 0 + 10 = 10...
      -> Total: 100 + 10 = 110...

    Edge Cases:
      -> N = 0 → no shifts, return sum of all A[i]...
      -> N = n → all employees shift → pick all extra costs regardless of sign...
      -> All B[i] < A[i] → all extraCosts negative → always beneficial to shift...
      -> All B[i] > A[i] → all extraCosts positive → forced shifts increase total cost...
      -> Single employee, N = 1 → only one shift possible → add its extraCost...

    Time and Space Complexity:
      -> Time:  O(n log n) — dominated by sorting the extraCost array...
      -> Space: O(n) — extraCost array of size n...

    Applications:
      -> Employee assignment and relocation optimization in HR planning...
      -> Resource reallocation problems with fixed transfer quotas...
      -> Cost minimization in logistics when a subset must be rerouted...
      -> Competitive programming greedy selection with fixed selection count...

*/

import java.util.Arrays;

public class Minimum_Cost_For_Shifting_Employee {

    private static int minimumCost(int N, int[] A, int[] B) {

        int n = A.length;
        int[] extraCost = new int[n];
        int ans = 0;

        for (int i = 0; i < n; i++) {
            ans += A[i];

            int cityBCost = Math.min(A[i], B[i]) + B[i];
            extraCost[i] = cityBCost - A[i];
        }

        Arrays.sort(extraCost);

        for (int i = 0; i < N; i++) {
            ans += extraCost[i];
        }

        return ans;
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        MINIMUM COST FOR SHIFTING EMPLOYEE                    ║");
        System.out.println("║  Choose N employees to shift to city B, minimizing total     ║");
        System.out.println("║  cost across both cities                                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Basic Two Employees ===");
        int N1 = 1;
        int[] A1 = {10, 20};
        int[] B1 = {5, 8};
        System.out.println("N = " + N1);
        System.out.println("A (city A cost): " + Arrays.toString(A1));
        System.out.println("B (city B cost): " + Arrays.toString(B1));
        System.out.println("\nBase cost (everyone stays in A): 10 + 20 = 30");
        System.out.println("extraCost[0] = min(10,5)+5-10 = 0");
        System.out.println("extraCost[1] = min(20,8)+8-20 = -4");
        System.out.println("Sorted extraCost = [-4, 0], pick smallest 1 → -4");
        System.out.println("Total: 30 + (-4) = 26\n");

        int result1 = minimumCost(N1, A1, B1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 26");
        System.out.println("  Status: " + (result1 == 26 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: N = 0 (No Shifting) ===");
        int N2 = 0;
        int[] A2 = {5, 5};
        int[] B2 = {3, 3};
        System.out.println("N = " + N2);
        System.out.println("A: " + Arrays.toString(A2));
        System.out.println("B: " + Arrays.toString(B2));
        System.out.println("\nNo shifts allowed, everyone stays in city A");
        System.out.println("Total: 5 + 5 = 10\n");

        int result2 = minimumCost(N2, A2, B2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 10");
        System.out.println("  Status: " + (result2 == 10 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: N = 2, Three Employees ===");
        int N3 = 2;
        int[] A3 = {7, 3, 9};
        int[] B3 = {2, 10, 1};
        System.out.println("N = " + N3);
        System.out.println("A: " + Arrays.toString(A3));
        System.out.println("B: " + Arrays.toString(B3));
        System.out.println("\nBase cost: 7 + 3 + 9 = 19");
        System.out.println("extraCost = [-3, 10, -7], sorted = [-7, -3, 10]");
        System.out.println("Pick smallest 2: -7 + -3 = -10");
        System.out.println("Total: 19 + (-10) = 9\n");

        int result3 = minimumCost(N3, A3, B3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 9");
        System.out.println("  Status: " + (result3 == 9 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: N Equals All Employees ===");
        int N4 = 3;
        int[] A4 = {4, 6, 8};
        int[] B4 = {1, 2, 3};
        System.out.println("N = " + N4);
        System.out.println("A: " + Arrays.toString(A4));
        System.out.println("B: " + Arrays.toString(B4));
        System.out.println("\nBase cost: 4 + 6 + 8 = 18");
        System.out.println("extraCost = [-2, -2, -2]");
        System.out.println("Pick all 3: -2 + -2 + -2 = -6");
        System.out.println("Total: 18 + (-6) = 12\n");

        int result4 = minimumCost(N4, A4, B4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 12");
        System.out.println("  Status: " + (result4 == 12 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Employee, No Extra Cost ===");
        int N5 = 1;
        int[] A5 = {100};
        int[] B5 = {50};
        System.out.println("N = " + N5);
        System.out.println("A: " + Arrays.toString(A5));
        System.out.println("B: " + Arrays.toString(B5));
        System.out.println("\nBase cost: 100");
        System.out.println("extraCost[0] = min(100,50)+50-100 = 0");
        System.out.println("Total: 100 + 0 = 100\n");

        int result5 = minimumCost(N5, A5, B5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 100");
        System.out.println("  Status: " + (result5 == 100 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: City B More Expensive ===");
        int N6 = 1;
        int[] A6 = {5};
        int[] B6 = {20};
        System.out.println("N = " + N6);
        System.out.println("A: " + Arrays.toString(A6));
        System.out.println("B: " + Arrays.toString(B6));
        System.out.println("\nBase cost: 5");
        System.out.println("extraCost[0] = min(5,20)+20-5 = 20");
        System.out.println("Total: 5 + 20 = 25 (shifting is forced despite being costlier)\n");

        int result6 = minimumCost(N6, A6, B6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 25");
        System.out.println("  Status: " + (result6 == 25 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Four Employees, N = 2 ===");
        int N7 = 2;
        int[] A7 = {10, 20, 30, 40};
        int[] B7 = {5, 15, 25, 50};
        System.out.println("N = " + N7);
        System.out.println("A: " + Arrays.toString(A7));
        System.out.println("B: " + Arrays.toString(B7));
        System.out.println("\nBase cost: 10 + 20 + 30 + 40 = 100");
        System.out.println("extraCost = [0, 10, 20, 50]");
        System.out.println("Pick smallest 2: 0 + 10 = 10");
        System.out.println("Total: 100 + 10 = 110\n");

        int result7 = minimumCost(N7, A7, B7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 110");
        System.out.println("  Status: " + (result7 == 110 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Choose exactly N employees to shift to city B,     ║");
        System.out.println("║           minimizing the combined cost of both cities        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Start From 'Everyone Stays', Then Adjust       ║");
        System.out.println("║    Assume baseline where every employee remains in city A.   ║");
        System.out.println("║    Shifting employee i instead costs an 'extra' delta        ║");
        System.out.println("║    relative to that baseline — pick the N cheapest deltas.   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Phases:                                                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 1: Compute Baseline and Extra Costs                   ║");
        System.out.println("║    ans = sum of all A[i] (baseline: nobody shifts)           ║");
        System.out.println("║    cityBCost[i] = min(A[i], B[i]) + B[i]                     ║");
        System.out.println("║    extraCost[i] = cityBCost[i] - A[i]                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Phase 2: Apply the N Cheapest Shifts                        ║");
        System.out.println("║    Sort extraCost ascending                                  ║");
        System.out.println("║    Add the N smallest extraCost values to ans                ║");
        System.out.println("║    (most negative deltas reduce total cost the most)         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: A = [7, 3, 9], B = [2, 10, 1], N = 2               ║");
        System.out.println("║    Baseline: 7 + 3 + 9 = 19                                  ║");
        System.out.println("║    extraCost = [-3, 10, -7] → sorted = [-7, -3, 10]          ║");
        System.out.println("║    Pick smallest 2: -7 + -3 = -10                            ║");
        System.out.println("║    Result: 19 + (-10) = 9                                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Sorting extraCost isolates the most beneficial shifts   ║");
        System.out.println("║    • N shifts are always applied, even if extraCost > 0      ║");
        System.out.println("║    • cityBCost formula accounts for a min-cost sub-decision  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n log n) for sorting extraCost           ║");
        System.out.println("║  Space Complexity: O(n) for the extraCost array              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
