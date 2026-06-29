package String;

public class Number_Of_Strings_That_Appear_As_Substrings_In_Word {

    private static int numOfStrings(String[] patterns, String word) {
        int count = 0;

        for (String currWord : patterns) {
            if (word.contains(currWord)) {
                count++;
            }
        }

        return count;
    }

    private static String arrayToString(String[] arr) {

        if (arr.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append("\"").append(arr[i]).append("\"");
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   NUMBER OF STRINGS THAT APPEAR AS SUBSTRINGS IN WORD        ║");
        System.out.println("║  Count how many patterns exist as substrings in word         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Multiple Patterns Found ===");
        String[] patterns1 = {"ab", "ba", "aaab", "abab", "baa"};
        String word1 = "aaab";
        System.out.println("Patterns: " + arrayToString(patterns1));
        System.out.println("Word: \"" + word1 + "\"");
        System.out.println("\nChecking each pattern:");
        System.out.println("  \"ab\" in \"aaab\"? YES (positions 2-3)");
        System.out.println("  \"ba\" in \"aaab\"? NO");
        System.out.println("  \"aaab\" in \"aaab\"? YES (entire word)");
        System.out.println("  \"abab\" in \"aaab\"? NO");
        System.out.println("  \"baa\" in \"aaab\"? NO");
        System.out.println("Count: 2\n");

        int result1 = numOfStrings(patterns1, word1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result1 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: All Patterns Found ===");
        String[] patterns2 = {"a", "b", "c"};
        String word2 = "abc";
        System.out.println("Patterns: " + arrayToString(patterns2));
        System.out.println("Word: \"" + word2 + "\"");
        System.out.println("\nChecking each pattern:");
        System.out.println("  \"a\" in \"abc\"? YES");
        System.out.println("  \"b\" in \"abc\"? YES");
        System.out.println("  \"c\" in \"abc\"? YES");
        System.out.println("Count: 3\n");

        int result2 = numOfStrings(patterns2, word2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result2 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: No Patterns Found ===");
        String[] patterns3 = {"x", "y", "z"};
        String word3 = "abc";
        System.out.println("Patterns: " + arrayToString(patterns3));
        System.out.println("Word: \"" + word3 + "\"");
        System.out.println("\nChecking each pattern:");
        System.out.println("  \"x\" in \"abc\"? NO");
        System.out.println("  \"y\" in \"abc\"? NO");
        System.out.println("  \"z\" in \"abc\"? NO");
        System.out.println("Count: 0\n");

        int result3 = numOfStrings(patterns3, word3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result3 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Single Character Patterns ===");
        String[] patterns4 = {"h", "e", "l", "o"};
        String word4 = "hello";
        System.out.println("Patterns: " + arrayToString(patterns4));
        System.out.println("Word: \"" + word4 + "\"");
        System.out.println("\nChecking each pattern:");
        System.out.println("  \"h\" in \"hello\"? YES");
        System.out.println("  \"e\" in \"hello\"? YES");
        System.out.println("  \"l\" in \"hello\"? YES");
        System.out.println("  \"o\" in \"hello\"? YES");
        System.out.println("Count: 4\n");

        int result4 = numOfStrings(patterns4, word4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 4");
        System.out.println("  Status: " + (result4 == 4 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Overlapping Patterns ===");
        String[] patterns5 = {"ab", "ba", "abc", "cab"};
        String word5 = "cab";
        System.out.println("Patterns: " + arrayToString(patterns5));
        System.out.println("Word: \"" + word5 + "\"");
        System.out.println("\nChecking each pattern:");
        System.out.println("  \"ab\" in \"cab\"? YES (positions 1-2)");
        System.out.println("  \"ba\" in \"cab\"? NO");
        System.out.println("  \"abc\" in \"cab\"? NO");
        System.out.println("  \"cab\" in \"cab\"? YES (entire word)");
        System.out.println("Count: 2\n");

        int result5 = numOfStrings(patterns5, word5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result5 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Pattern Longer Than Word ===");
        String[] patterns6 = {"hello", "hi", "h"};
        String word6 = "hi";
        System.out.println("Patterns: " + arrayToString(patterns6));
        System.out.println("Word: \"" + word6 + "\"");
        System.out.println("\nChecking each pattern:");
        System.out.println("  \"hello\" in \"hi\"? NO (pattern too long)");
        System.out.println("  \"hi\" in \"hi\"? YES (entire word)");
        System.out.println("  \"h\" in \"hi\"? YES");
        System.out.println("Count: 2\n");

        int result6 = numOfStrings(patterns6, word6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result6 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Duplicate Patterns ===");
        String[] patterns7 = {"a", "a", "b", "b", "b"};
        String word7 = "ab";
        System.out.println("Patterns: " + arrayToString(patterns7));
        System.out.println("Word: \"" + word7 + "\"");
        System.out.println("\nChecking each pattern (duplicates count separately):");
        System.out.println("  \"a\" in \"ab\"? YES (count 1)");
        System.out.println("  \"a\" in \"ab\"? YES (count 2)");
        System.out.println("  \"b\" in \"ab\"? YES (count 3)");
        System.out.println("  \"b\" in \"ab\"? YES (count 4)");
        System.out.println("  \"b\" in \"ab\"? YES (count 5)");
        System.out.println("Count: 5\n");

        int result7 = numOfStrings(patterns7, word7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result7 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 8: Empty Patterns Array ===");
        String[] patterns8 = {};
        String word8 = "hello";
        System.out.println("Patterns: " + arrayToString(patterns8));
        System.out.println("Word: \"" + word8 + "\"");
        System.out.println("\nNo patterns to check");
        System.out.println("Count: 0\n");

        int result8 = numOfStrings(patterns8, word8);
        System.out.println("✓ Result: " + result8);
        System.out.println("  Expected: 0");
        System.out.println("  Status: " + (result8 == 0 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Count patterns that appear as substrings in word   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Algorithm:                                                  ║");
        System.out.println("║    count = 0                                                 ║");
        System.out.println("║    for each pattern in patterns:                             ║");
        System.out.println("║      if word.contains(pattern):                              ║");
        System.out.println("║        count++                                               ║");
        System.out.println("║    return count                                              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Points:                                                 ║");
        System.out.println("║    • contains() method checks if substring exists            ║");
        System.out.println("║    • Checks for any occurrence in word                       ║");
        System.out.println("║    • Each duplicate pattern counted separately               ║");
        System.out.println("║    • Case-sensitive by default                               ║");
        System.out.println("║    • Substring can be at any position                        ║");
        System.out.println("║    • Substring can overlap others                            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  String.contains() Method:                                   ║");
        System.out.println("║    • Returns true if pattern appears anywhere in word        ║");
        System.out.println("║    • Time complexity: O(n*m) worst case                      ║");
        System.out.println("║      n = length of word                                      ║");
        System.out.println("║      m = length of pattern                                   ║");
        System.out.println("║    • KMP algorithm internally optimized                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: patterns=[\"ab\",\"ba\",\"aaab\",\"abab\",\"baa\"]           ║");
        System.out.println("║           word=\"aaab\"                                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║    Iteration 1: \"ab\".contains(\"aaab\")? true → count=1        ║");
        System.out.println("║    Iteration 2: \"ba\".contains(\"aaab\")? false → count=1       ║");
        System.out.println("║    Iteration 3: \"aaab\".contains(\"aaab\")? true → count=2      ║");
        System.out.println("║    Iteration 4: \"abab\".contains(\"aaab\")? false → count=2     ║");
        System.out.println("║    Iteration 5: \"baa\".contains(\"aaab\")? false → count=2      ║");
        System.out.println("║    Return 2                                                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity:                                            ║");
        System.out.println("║    O(p * (w * m)) where:                                     ║");
        System.out.println("║      p = number of patterns                                  ║");
        System.out.println("║      w = length of word                                      ║");
        System.out.println("║      m = average length of pattern                           ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Space Complexity: O(1) - only count variable                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Alternative Approaches:                                     ║");
        System.out.println("║    1. indexOf() - find position instead of boolean           ║");
        System.out.println("║    2. Regular expressions - pattern matching                 ║");
        System.out.println("║    3. KMP algorithm - optimized string matching              ║");
        System.out.println("║    4. Trie - preprocess patterns for efficiency              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }


}