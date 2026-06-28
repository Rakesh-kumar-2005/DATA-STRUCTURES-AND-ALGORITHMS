package Arrays;

import java.util.HashSet;

public class Balanced_Vowel_Consonant_Split {


    private static boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    private static void helper(char[] arr) {

        HashSet<Character> vowel = new HashSet<>();
        HashSet<Character> consonants = new HashSet<>();

        int n = arr.length;
        int[] leftVowel = new int[n];
        int[] rightVowel = new int[n];
        int[] leftConsonants = new int[n];
        int[] rightConsonants = new int[n];

        for (int i = 0; i < n; i++) {
            char temp = arr[i];
            if (isVowel(temp)) {
                vowel.add(temp);
            } else {
                consonants.add(temp);
            }

            leftVowel[i] = vowel.size();
            leftConsonants[i] = consonants.size();
        }

        vowel.clear();
        consonants.clear();

        for (int i = n - 1; i >= 0; i--) {
            char temp = arr[i];
            if (isVowel(temp)) {
                vowel.add(temp);
            } else {
                consonants.add(temp);
            }

            rightVowel[i] = vowel.size();
            rightConsonants[i] = consonants.size();
        }

        for (int i = 1; i < n - 1; i++) {
            if (leftVowel[i - 1] == rightVowel[i + 1] &&
                leftConsonants[i - 1] == rightConsonants[i + 1]) {
                System.out.println(i);
                return;
            }
        }

        System.out.println(- 1);

    }

    private static String charArrayToString(char[] arr) {

        if (arr.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append("'").append(arr[i]).append("'");
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║         BALANCED VOWEL-CONSONANT SPLIT                       ║");
        System.out.println("║  Find split point where left and right have equal            ║");
        System.out.println("║  distinct vowels AND distinct consonants                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Given Example ===");
        char[] arr1 = {'a', 'a', 'c', 'd', 'e', 'e', 'f'};
        System.out.println("Array: " + charArrayToString(arr1));
        System.out.println("Indices: 0    1    2    3    4    5    6");
        System.out.println("\nAnalyzing each split position:");
        System.out.println("Position 1: Left=['a'], Right=['c','d','e','e','f']");
        System.out.println("  Left vowels={a}(1), Right vowels={e}(1) ✓");
        System.out.println("  Left consonants={} (0), Right consonants={c,d,f}(3) ✗");
        System.out.println("Position 2: Left=['a','a','c'], Right=['d','e','e','f']");
        System.out.println("  Left vowels={a}(1), Right vowels={e}(1) ✓");
        System.out.println("  Left consonants={c}(1), Right consonants={d,f}(2) ✗");
        System.out.println("Position 3: Left=['a','a','c','d'], Right=['e','e','f']");
        System.out.println("  Left vowels={a}(1), Right vowels={e}(1) ✓");
        System.out.println("  Left consonants={c,d}(2), Right consonants={f}(1) ✗");
        System.out.println("Position 4: Left=['a','a','c','d','e'], Right=['e','f']");
        System.out.println("  Left vowels={a,e}(2), Right vowels={e}(1) ✗");
        System.out.println("Position 5: Left=['a','a','c','d','e','e'], Right=['f']");
        System.out.println("  Left vowels={a,e}(2), Right vowels={}(0) ✗");
        System.out.println("\nNo valid split found\n");
        System.out.println("✓ Result: ");
        helper(arr1);
        System.out.println();

        System.out.println("=== Test Case 2: Valid Split at Middle ===");
        char[] arr2 = {'a', 'b', 'e', 'c'};
        System.out.println("Array: " + charArrayToString(arr2));
        System.out.println("Indices: 0    1    2    3");
        System.out.println("\nAnalyzing split positions:");
        System.out.println("Position 1: Left=['a'], Right=['e','c']");
        System.out.println("  Left vowels={a}(1), Right vowels={e}(1) ✓");
        System.out.println("  Left consonants={}(0), Right consonants={c}(1) ✗");
        System.out.println("Position 2: Left=['a','b'], Right=['c']");
        System.out.println("  Left vowels={a}(1), Right vowels={}(0) ✗");
        System.out.println("\nNo valid split found\n");
        System.out.println("✓ Result: ");
        helper(arr2);
        System.out.println();

        System.out.println("=== Test Case 3: Simple Two Vowels, Two Consonants ===");
        char[] arr3 = {'a', 'b', 'c', 'e'};
        System.out.println("Array: " + charArrayToString(arr3));
        System.out.println("Indices: 0    1    2    3");
        System.out.println("\nAnalyzing split positions:");
        System.out.println("Position 1: Left=['a'], Right=['c','e']");
        System.out.println("  Left vowels={a}(1), Right vowels={e}(1) ✓");
        System.out.println("  Left consonants={}(0), Right consonants={c}(1) ✗");
        System.out.println("Position 2: Left=['a','b'], Right=['e']");
        System.out.println("  Left vowels={a}(1), Right vowels={e}(1) ✓");
        System.out.println("  Left consonants={b}(1), Right consonants={}(0) ✗");
        System.out.println("\nNo valid split found\n");
        System.out.println("✓ Result: ");
        helper(arr3);
        System.out.println();

        System.out.println("=== Test Case 4: Single Vowel on Each Side ===");
        char[] arr4 = {'a', 'b', 'c', 'e'};
        System.out.println("Array: " + charArrayToString(arr4));
        System.out.println("Same as Test Case 3, no valid split\n");
        System.out.println("✓ Result: ");
        helper(arr4);
        System.out.println();

        System.out.println("=== Test Case 5: Matching Pattern ===");
        char[] arr5 = {'a', 'b', 'c', 'd', 'e'};
        System.out.println("Array: " + charArrayToString(arr5));
        System.out.println("Indices: 0    1    2    3    4");
        System.out.println("\nAnalyzing split positions:");
        System.out.println("Position 1: Left=['a'], Right=['c','d','e']");
        System.out.println("  Left vowels={a}(1), Right vowels={e}(1) ✓");
        System.out.println("  Left consonants={}(0), Right consonants={c,d}(2) ✗");
        System.out.println("Position 2: Left=['a','b'], Right=['d','e']");
        System.out.println("  Left vowels={a}(1), Right vowels={e}(1) ✓");
        System.out.println("  Left consonants={b}(1), Right consonants={d}(1) ✓");
        System.out.println("  FOUND VALID SPLIT AT POSITION 2!\n");
        System.out.println("✓ Result: ");
        helper(arr5);
        System.out.println();

        System.out.println("=== Test Case 6: All Vowels ===");
        char[] arr6 = {'a', 'e', 'i', 'o', 'u'};
        System.out.println("Array: " + charArrayToString(arr6));
        System.out.println("All vowels, no consonants");
        System.out.println("Can't have balanced split (consonants always 0 on both sides)\n");
        System.out.println("✓ Result: ");
        helper(arr6);
        System.out.println();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Find split point i where:                          ║");
        System.out.println("║    Distinct vowels on left = Distinct vowels on right        ║");
        System.out.println("║    Distinct consonants on left = Distinct consonants on right║");
        System.out.println("║    Character at position i is excluded (it's the split)      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Distinct Count, Not Total Count                ║");
        System.out.println("║    Use HashSet to track distinct vowels and consonants       ║");
        System.out.println("║    Example: \"aaa\" has 3 a's but only 1 distinct vowel        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Algorithm:                                                  ║");
        System.out.println("║    1. For position i: Compute distinct vowels/consonants     ║");
        System.out.println("║       on left (0 to i-1) and right (i+1 to n-1)              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Preprocessing: Build Prefix Arrays                          ║");
        System.out.println("║                                                              ║");
        System.out.println("║   Left to Right Pass:                                        ║");
        System.out.println("║    leftVowel[i] = count of distinct vowels in [0..i]         ║");
        System.out.println("║    leftConsonants[i] = count of distinct consonants in [0..i]║");
        System.out.println("║                                                              ║");
        System.out.println("║   Right to Left Pass:                                        ║");
        System.out.println("║    rightVowel[i] = count of distinct vowels in [i..n-1]      ║");
        System.out.println("║    rightConsonants[i] = count of distinct consonants [i..n-1]║");
        System.out.println("║                                                              ║");
        System.out.println("║  Checking Split Point i:                                     ║");
        System.out.println("║    Left side has: leftVowel[i-1] distinct vowels             ║");
        System.out.println("║    Right side has: rightVowel[i+1] distinct vowels           ║");
        System.out.println("║    Check: leftVowel[i-1] == rightVowel[i+1] AND              ║");
        System.out.println("║           leftConsonants[i-1] == rightConsonants[i+1]        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: arr=['a','b','c','d','e']                          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Left Pass:                                                  ║");
        System.out.println("║    i=0: a → vowel={a}(1), consonant={}(0)                    ║");
        System.out.println("║    i=1: b → vowel={a}(1), consonant={b}(1)                   ║");
        System.out.println("║    i=2: c → vowel={a}(1), consonant={b,c}(2)                 ║");
        System.out.println("║    i=3: d → vowel={a}(1), consonant={b,c,d}(3)               ║");
        System.out.println("║    i=4: e → vowel={a,e}(2), consonant={b,c,d}(3)             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Right Pass:                                                 ║");
        System.out.println("║    i=4: e → vowel={e}(1), consonant={}(0)                    ║");
        System.out.println("║    i=3: d → vowel={e}(1), consonant={d}(1)                   ║");
        System.out.println("║    i=2: c → vowel={e}(1), consonant={c,d}(2)                 ║");
        System.out.println("║    i=1: b → vowel={e}(1), consonant={b,c,d}(3)               ║");
        System.out.println("║    i=0: a → vowel={a,e}(2), consonant={b,c,d}(3)             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Check Splits:                                               ║");
        System.out.println("║    i=1: Left[0]=(1,0), Right[2]=(1,2) → Consonants differ    ║");
        System.out.println("║    i=2: Left[1]=(1,1), Right[3]=(1,1) → MATCH! ✓             ║");
        System.out.println("║    Return 2                                                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity:                                            ║");
        System.out.println("║    O(n) for left pass + O(n) for right pass + O(n) for check ║");
        System.out.println("║    Total: O(n)                                               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Space Complexity:                                           ║");
        System.out.println("║    O(n) for four prefix arrays                               ║");
        System.out.println("║    O(10) for HashSets (max 5 vowels + 21 consonants)         ║");
        System.out.println("║    Total: O(n)                                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}