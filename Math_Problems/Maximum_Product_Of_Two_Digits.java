package Math_Problems;

/*

    Description:
      Following program finds the two largest digits in an integer and returns
        their product, using both an optimized single-pass and a brute force approach...

    Problem Statement:
      -> Given a positive integer n...
      -> Extract all individual digits from n...
      -> Find the two largest distinct-position digits (duplicates allowed by value)...
      -> Return their product...
      -> The number is guaranteed to have at least two digits...

    Key Insight:
      -> Only two variables (max1 and max2) are needed to track the top two digits...
      -> As each digit is extracted right-to-left via n % 10:
           If digit > max1: the current max1 becomes the new max2, digit becomes new max1...
           Else if digit > max2: digit directly updates max2...
      -> No sorting or full comparison table required → single pass suffices...

    Example:
      -> n = 124:
           Digits extracted (right-to-left): 4, 2, 1...
           digit=4: 4>max1(0) → max2=0, max1=4...
           digit=2: 2>max2(0) → max2=2...
           digit=1: 1>max2(2)? No → skip...
           Result: 4 × 2 = 8...
      -> n = 987654321:
           Largest two digits = 9 and 8...
           Result: 9 × 8 = 72...
      -> n = 500005:
           Digits: 5, 0, 0, 0, 0, 5...
           max1=5, max2=5 → Result: 5 × 5 = 25...

    Optimized Approach (Single Pass):
      -> Initialize max1 = 0, max2 = 0...
      -> While n != 0:
           digit = n % 10...
           If digit > max1: max2 = max1, max1 = digit...
           Else if digit > max2: max2 = digit...
           n /= 10...
      -> Return max1 * max2...

    Why max2 = max1 Before Updating max1:
      -> When a new digit exceeds max1, the old max1 must not be lost...
      -> The old max1 becomes the second largest (max2) before max1 is updated...
      -> If we updated max1 first, max2 = max1 would store the new value, not the old one...
      -> Order: max2 = max1 first, then max1 = digit...

    Step-by-Step Trace (n = 806):
      -> digit=6: 6>0(max1) → max2=0, max1=6...
      -> digit=0: 0>6? No, 0>0(max2)? No → skip...
      -> digit=8: 8>6(max1) → max2=6, max1=8...
      -> Result: 8 × 6 = 48...

    Brute Force Approach:
      -> Convert n to a char array of its digit characters...
      -> For every pair (i, j) where i < j:
           Parse both characters as integers...
           Update prod = Math.max(prod, v1 × v2)...
      -> Return the maximum product found...
      -> Compares every possible pair → guarantees correctness but slower...
      -> Time: O(d²), Space: O(d) where d = number of digits...

    Why Brute Force Is Used for Verification:
      -> Both approaches run independently and their results compared...
      -> Any mismatch signals a bug in the optimized logic...
      -> Brute force is simpler and obviously correct → serves as ground truth...

    Edge Cases:
      -> All same digits (e.g., 777) → max1=max2=7 → product = 49...
      -> Contains zeros (e.g., 806) → zeros never displace max1 or max2...
      -> Exactly two digits → only one pair → product = d1 × d2...
      -> Digits in ascending order → last two digits visited become max1 and max2 in order...
      -> Large numbers → digit count = log₁₀(n), no overflow since digits ≤ 9...

    Maximum Possible Product:
      -> Max digit is 9, so maximum product = 9 × 9 = 81...
      -> Result always fits in an int without risk of overflow...

    Time and Space Complexity:
      -> Optimized:
           Time:  O(log n) — one pass through all digits of n...
           Space: O(1) — only two integer variables...
      -> Brute Force:
           Time:  O(d²) where d = number of digits = O(log n)²...
           Space: O(d) — char array storing all digits...

    Applications:
      -> Digit selection and ranking in number theory problems...
      -> Feature extraction from numeric identifiers...
      -> Competitive programming digit maximization problems...
      -> Finding top-k elements in a fixed small universe (digits 0-9)...

*/

public class Maximum_Product_Of_Two_Digits {

    private static int optimized(int n) {

        int max1 = 0;
        int max2 = 0;

        while (n != 0) {
            int digit = n % 10;

            if (digit > max1) {
                max2 = max1;
                max1 = digit;
            } else if (digit > max2) {
                max2 = digit;
            }

            n /= 10;
        }

        return max1 * max2;
    }

    private static int bruteForce(int n) {

        int prod = 0;
        char[] arr = Integer.toString(n).toCharArray();

        for (int i = 0; i < arr.length - 1; i++) {
            int v1 = Integer.parseInt(arr[i] + "");
            for (int j = i + 1; j < arr.length; j++) {
                int v2 = Integer.parseInt(arr[j] + "");
                prod = Math.max(prod, v1 * v2);
            }
        }

        return prod;
    }

    private static int maxProduct(int n) {

        int optimizedResult = optimized(n);
        int bruteForceResult = bruteForce(n);

        System.out.println("  [Verification] optimized: " + optimizedResult
            + " | bruteForce: " + bruteForceResult);

        if (optimizedResult != bruteForceResult) {
            System.out.println("  ⚠ MISMATCH DETECTED among approaches!");
        }

        return optimizedResult;
    }

    public static void main(String[] args) {
        
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           MAXIMUM PRODUCT OF TWO DIGITS                      ║");
        System.out.println("║  Find the two largest digits in a number and multiply them   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Basic Number ===");
        int n1 = 124;
        System.out.println("Input: " + n1);
        System.out.println("\nDigits: 1, 2, 4");
        System.out.println("Two largest: 4 and 2 → product = 8\n");

        int result1 = maxProduct(n1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 8");
        System.out.println("  Status: " + (result1 == 8 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: All Same Digits ===");
        int n2 = 777;
        System.out.println("Input: " + n2);
        System.out.println("\nDigits: 7, 7, 7");
        System.out.println("Two largest: 7 and 7 → product = 49\n");

        int result2 = maxProduct(n2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 49");
        System.out.println("  Status: " + (result2 == 49 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Two Digit Number ===");
        int n3 = 39;
        System.out.println("Input: " + n3);
        System.out.println("\nDigits: 3, 9");
        System.out.println("Only two digits, product = 3 × 9 = 27\n");

        int result3 = maxProduct(n3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 27");
        System.out.println("  Status: " + (result3 == 27 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Contains a Zero ===");
        int n4 = 806;
        System.out.println("Input: " + n4);
        System.out.println("\nDigits: 8, 0, 6");
        System.out.println("Two largest: 8 and 6 → product = 48\n");

        int result4 = maxProduct(n4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 48");
        System.out.println("  Status: " + (result4 == 48 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Large Number ===");
        int n5 = 987654321;
        System.out.println("Input: " + n5);
        System.out.println("\nDigits include 9 and 8 as the two largest");
        System.out.println("Product = 9 × 8 = 72\n");

        int result5 = maxProduct(n5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 72");
        System.out.println("  Status: " + (result5 == 72 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Single Nonzero Digit Twice ===");
        int n6 = 500005;
        System.out.println("Input: " + n6);
        System.out.println("\nDigits: 5, 0, 0, 0, 0, 5");
        System.out.println("Two largest: 5 and 5 → product = 25\n");

        int result6 = maxProduct(n6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 25");
        System.out.println("  Status: " + (result6 == 25 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Ascending Digits ===");
        int n7 = 123456;
        System.out.println("Input: " + n7);
        System.out.println("\nDigits: 1, 2, 3, 4, 5, 6");
        System.out.println("Two largest: 6 and 5 → product = 30\n");

        int result7 = maxProduct(n7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 30");
        System.out.println("  Status: " + (result7 == 30 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find the two largest digits in a number and        ║");
        System.out.println("║           return their product                               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Track Top Two Digits in a Single Pass          ║");
        System.out.println("║    No need to sort or compare every pair — just keep         ║");
        System.out.println("║    max1 (largest so far) and max2 (second largest so far)    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Two Approaches, Cross-Verified:                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  optimized: Single Pass Digit Extraction                     ║");
        System.out.println("║    Peel off digits with n % 10, n /= 10                      ║");
        System.out.println("║    If digit > max1: max2 = max1, max1 = digit                ║");
        System.out.println("║    Else if digit > max2: max2 = digit                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  bruteForce: Compare Every Pair                              ║");
        System.out.println("║    Convert to char array, try all (i, j) pairs with i < j    ║");
        System.out.println("║    Track the maximum product found                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: n = 124                                            ║");
        System.out.println("║    Digits extracted in order: 4, 2, 1                        ║");
        System.out.println("║    max1=4, max2=0 → max1=4, max2=2 → max1=4, max2=2 (1<2)    ║");
        System.out.println("║    Result: 4 × 2 = 8                                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Both approaches must always produce identical output    ║");
        System.out.println("║    • Works regardless of digit order in the number           ║");
        System.out.println("║    • Zero digits never affect the top-two selection          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(log n) optimized, O(d²) brute force      ║");
        System.out.println("║                    (d = number of digits)                    ║");
        System.out.println("║  Space Complexity: O(1) optimized, O(d) brute force          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}
