package Arrays;

/*

    Description:
      Following program finds the index of a split point in a character array where
        the number of distinct vowels on the left equals those on the right, and
        the number of distinct consonants on the left equals those on the right...

    Problem Statement:
      -> Given a character array arr of lowercase English letters...
      -> Find an index i (1 <= i <= n-2) such that:
           Distinct vowels in arr[0..i-1] == Distinct vowels in arr[i+1..n-1]...
           Distinct consonants in arr[0..i-1] == Distinct consonants in arr[i+1..n-1]...
      -> The character at index i is excluded from both sides (it is the split point)...
      -> Print the first valid index i, or -1 if none exists...

    Key Insight:
      -> Count of distinct characters (not total occurrences) must match...
      -> HashSet naturally tracks distinct characters without duplicates...
      -> Precompute left prefix counts and right suffix counts for O(1) lookup at each split...
      -> Check condition at every candidate index i from 1 to n-2...

    Example:
      -> arr = ['a', 'b', 'c', 'd', 'e']:
           At i=2 (character 'c'):
             Left arr[0..1] = ['a','b']: distinct vowels = {a} = 1, consonants = {b} = 1...
             Right arr[3..4] = ['d','e']: distinct vowels = {e} = 1, consonants = {d} = 1...
             Both match → return 2...
      -> arr = ['a', 'a', 'c', 'd', 'e', 'e', 'f']:
           No valid split found → return -1...

    Algorithm Steps:

      Left-to-Right Pass (build prefix counts):
        -> Maintain two HashSets: vowel and consonants...
        -> For each index i from 0 to n-1:
             Add arr[i] to appropriate set...
             leftVowel[i] = vowel.size()...
             leftConsonants[i] = consonants.size()...
        -> leftVowel[i] = distinct vowels in arr[0..i]...
        -> leftConsonants[i] = distinct consonants in arr[0..i]...

      Right-to-Left Pass (build suffix counts):
        -> Clear both HashSets and reuse them...
        -> For each index i from n-1 to 0:
             Add arr[i] to appropriate set...
             rightVowel[i] = vowel.size()...
             rightConsonants[i] = consonants.size()...
        -> rightVowel[i] = distinct vowels in arr[i..n-1]...
        -> rightConsonants[i] = distinct consonants in arr[i..n-1]...

      Split Check Pass:
        -> For each candidate index i from 1 to n-2:
             Left side = arr[0..i-1] → use leftVowel[i-1] and leftConsonants[i-1]...
             Right side = arr[i+1..n-1] → use rightVowel[i+1] and rightConsonants[i+1]...
             If both pairs match → print i and return...
        -> If no valid index found → print -1...

    Step-by-Step Trace (arr = ['a', 'b', 'c', 'd', 'e']):

      Left Pass:
        -> i=0: 'a' vowel → leftVowel[0]=1, leftConsonants[0]=0...
        -> i=1: 'b' consonant → leftVowel[1]=1, leftConsonants[1]=1...
        -> i=2: 'c' consonant → leftVowel[2]=1, leftConsonants[2]=2...
        -> i=3: 'd' consonant → leftVowel[3]=1, leftConsonants[3]=3...
        -> i=4: 'e' vowel → leftVowel[4]=2, leftConsonants[4]=3...

      Right Pass:
        -> i=4: 'e' vowel → rightVowel[4]=1, rightConsonants[4]=0...
        -> i=3: 'd' consonant → rightVowel[3]=1, rightConsonants[3]=1...
        -> i=2: 'c' consonant → rightVowel[2]=1, rightConsonants[2]=2...
        -> i=1: 'b' consonant → rightVowel[1]=1, rightConsonants[1]=3...
        -> i=0: 'a' vowel → rightVowel[0]=2, rightConsonants[0]=3...

      Split Check:
        -> i=1: left[0]=(1,0), right[2]=(1,2) → consonants differ → skip...
        -> i=2: left[1]=(1,1), right[3]=(1,1) → both match → print 2, return...

    Why HashSet for Distinct Count?
      -> HashSet ignores duplicate insertions automatically...
      -> size() always returns count of unique elements seen so far...
      -> Example: arr=['a','a','e'] → vowel set = {a, e} → distinct count = 2, not 3...
      -> Array-based counting would require checking for first occurrence, which is complex...

    Why Index Range is 1 to n-2?
      -> i=0 has no elements on the left → left side is always empty...
      -> i=n-1 has no elements on the right → right side is always empty...
      -> Only indices 1 through n-2 have non-trivial left and right partitions...

    Edge Cases:
      -> All vowels → consonant sets always empty on both sides → never matches unless
           distinct vowel counts also match, but consonants vacuously equal (0==0)...
           Actually could match if distinct vowel counts match on both sides...
      -> All consonants → vowel sets always empty → only consonant counts checked...
      -> No valid split exists → loop completes without returning → print -1...
      -> Repeated characters → HashSet handles deduplication transparently...

    Time and Space Complexity:
      -> Time:  O(n) — three separate linear passes through the array...
      -> Space: O(n) — four prefix arrays of size n each...
               O(1) — HashSets bounded by at most 26 English letters total...

    Applications:
      -> Text balancing and partitioning in natural language processing...
      -> Symmetric string analysis problems in competitive programming...
      -> Data partitioning where distinct category counts must be equal...
      -> Balanced split problems generalizable to any categorical classification...

*/

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
