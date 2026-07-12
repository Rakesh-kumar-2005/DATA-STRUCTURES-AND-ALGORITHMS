package Math_Problems;

public class Concatenate_Non_Zero_Digits_And_Multiply_By_Sum {

    private static long sumAndMultiply(int n) {

        long sum = 0;
        long number = 0;
        int multiplier = 1;

        while (n != 0) {
            int remainder = n % 10;

            if (remainder != 0) {
                sum += remainder;
                number = (long) remainder * multiplier + number;
                multiplier *= 10;
            }

            n /= 10;
        }

        return number * sum;
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   CONCATENATE NON-ZERO DIGITS AND MULTIPLY BY SUM            ║");
        System.out.println("║  Extract non-zero digits, concatenate, sum, then multiply    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        testCase(1234, "1234");
        testCase(1020, "12");
        testCase(100, "1");
        testCase(505, "55");
        testCase(9999, "9999");
        testCase(2468, "2468");
        testCase(1000, "1");
        testCase(102030, "123");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM: Extract non-zeros, concatenate, multiply by sum  ║");
        System.out.println("║  Example: 1020                                               ║");
        System.out.println("║    Non-zero digits: 1, 2                                     ║");
        System.out.println("║    Concatenated: 12                                          ║");
        System.out.println("║    Sum: 1 + 2 = 3                                            ║");
        System.out.println("║    Result: 12 × 3 = 36                                       ║");
        System.out.println("║  Time: O(log n), Space: O(1)                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    private static void testCase(int n, String digits) {

        long result = sumAndMultiply(n);
        String[] parts = digits.split("");

        long sum = 0;
        for (String d : parts) {
            sum += Long.parseLong(d);
        }

        long concatenated = Long.parseLong(digits);
        long expected = concatenated * sum;

        System.out.println("n = " + n + "\ndigits : " + digits + "\nsum : " + sum +
            "\nconcat : " + concatenated + "\nresult : " + result +
            (result == expected ? " ✓" : " ✗"));

        System.out.println("\n");

    }

}