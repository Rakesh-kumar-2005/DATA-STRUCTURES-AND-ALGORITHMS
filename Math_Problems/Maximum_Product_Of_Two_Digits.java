package Math_Problems;

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