package String;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Evaluate_The_Bracket_Pairs_Of_A_String {

    private static String evaluate(String sentence, ArrayList<ArrayList<String>> knowledge) {

        HashMap<String, String> map = new HashMap<>();
        for (ArrayList<String> temp : knowledge) {
            map.put(temp.get(0), temp.get(1));
        }

        StringBuilder sb = new StringBuilder();
        int n = sentence.length();

        for (int currIdx = 0; currIdx < n; currIdx++) {
            char currChar = sentence.charAt(currIdx);

            if (currChar == '(') {

                int lastIdx = currIdx + 1;
                while (sentence.charAt(lastIdx) != ')') {
                    lastIdx++;
                }

                String var = sentence.substring(currIdx + 1, lastIdx);
                String val = map.getOrDefault(var, "?");
                sb.append(val);

                currIdx = lastIdx;
            } else {
                sb.append(currChar);
            }

        }

        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║       EVALUATE THE BRACKET PAIRS OF A STRING                 ║");
        System.out.println("║  Replace every (key) in a sentence with its mapped value,    ║");
        System.out.println("║  or '?' if the key is unknown                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        String sentence1 = "(name)is(age)yearsold";
        ArrayList<ArrayList<String>> knowledge1 = new ArrayList<>();
        knowledge1.add(new ArrayList<>(Arrays.asList("name", "bob")));
        knowledge1.add(new ArrayList<>(Arrays.asList("age", "two")));
        System.out.println("Sentence: \"" + sentence1 + "\"");
        System.out.println("Knowledge: [[name,bob], [age,two]]");
        System.out.println("\n(name) → bob, (age) → two\n");

        String result1 = evaluate(sentence1, knowledge1);
        System.out.println("✓ Result: \"" + result1 + "\"");
        System.out.println("  Expected: \"bobistwoyearsold\"");
        System.out.println("  Status: " + (result1.equals("bobistwoyearsold") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Unknown Key ===");
        String sentence2 = "hi(name)";
        ArrayList<ArrayList<String>> knowledge2 = new ArrayList<>();
        knowledge2.add(new ArrayList<>(Arrays.asList("a", "b")));
        System.out.println("Sentence: \"" + sentence2 + "\"");
        System.out.println("Knowledge: [[a,b]]");
        System.out.println("\n'name' isn't in knowledge → replaced with '?'\n");

        String result2 = evaluate(sentence2, knowledge2);
        System.out.println("✓ Result: \"" + result2 + "\"");
        System.out.println("  Expected: \"hi?\"");
        System.out.println("  Status: " + (result2.equals("hi?") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: No Brackets at All ===");
        String sentence3 = "Hello world";
        ArrayList<ArrayList<String>> knowledge3 = new ArrayList<>();
        knowledge3.add(new ArrayList<>(Arrays.asList("x", "y")));
        System.out.println("Sentence: \"" + sentence3 + "\"");
        System.out.println("Knowledge: [[x,y]]");
        System.out.println("\nNo parentheses present, sentence returned unchanged\n");

        String result3 = evaluate(sentence3, knowledge3);
        System.out.println("✓ Result: \"" + result3 + "\"");
        System.out.println("  Expected: \"Hello world\"");
        System.out.println("  Status: " + (result3.equals("Hello world") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Empty Knowledge List ===");
        String sentence4 = "(a)is(b)";
        ArrayList<ArrayList<String>> knowledge4 = new ArrayList<>();
        System.out.println("Sentence: \"" + sentence4 + "\"");
        System.out.println("Knowledge: [] (empty)");
        System.out.println("\nBoth keys unknown, both replaced with '?'\n");

        String result4 = evaluate(sentence4, knowledge4);
        System.out.println("✓ Result: \"" + result4 + "\"");
        System.out.println("  Expected: \"?is?\"");
        System.out.println("  Status: " + (result4.equals("?is?") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Single Bracket Only ===");
        String sentence5 = "(foo)";
        ArrayList<ArrayList<String>> knowledge5 = new ArrayList<>();
        knowledge5.add(new ArrayList<>(Arrays.asList("foo", "bar")));
        System.out.println("Sentence: \"" + sentence5 + "\"");
        System.out.println("Knowledge: [[foo,bar]]");
        System.out.println("\nEntire sentence is one bracket, replaced fully\n");

        String result5 = evaluate(sentence5, knowledge5);
        System.out.println("✓ Result: \"" + result5 + "\"");
        System.out.println("  Expected: \"bar\"");
        System.out.println("  Status: " + (result5.equals("bar") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Multiple Brackets Same Key ===");
        String sentence6 = "(a)(a)(a)";
        ArrayList<ArrayList<String>> knowledge6 = new ArrayList<>();
        knowledge6.add(new ArrayList<>(Arrays.asList("a", "x")));
        System.out.println("Sentence: \"" + sentence6 + "\"");
        System.out.println("Knowledge: [[a,x]]");
        System.out.println("\nEvery occurrence of (a) is independently replaced with x\n");

        String result6 = evaluate(sentence6, knowledge6);
        System.out.println("✓ Result: \"" + result6 + "\"");
        System.out.println("  Expected: \"xxx\"");
        System.out.println("  Status: " + (result6.equals("xxx") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Mixed Known and Unknown Keys ===");
        String sentence7 = "(x)plus(y)equals(z)";
        ArrayList<ArrayList<String>> knowledge7 = new ArrayList<>();
        knowledge7.add(new ArrayList<>(Arrays.asList("x", "1")));
        knowledge7.add(new ArrayList<>(Arrays.asList("z", "3")));
        System.out.println("Sentence: \"" + sentence7 + "\"");
        System.out.println("Knowledge: [[x,1], [z,3]]");
        System.out.println("\n(x)→1, (y) unknown→'?', (z)→3\n");

        String result7 = evaluate(sentence7, knowledge7);
        System.out.println("✓ Result: \"" + result7 + "\"");
        System.out.println("  Expected: \"1plus?equals3\"");
        System.out.println("  Status: " + (result7.equals("1plus?equals3") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Replace every (key) substring in a sentence with   ║");
        System.out.println("║           its mapped value from a knowledge base, or '?' if  ║");
        System.out.println("║           the key has no known value                         ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Build a Map First, Then Single-Pass Scan       ║");
        System.out.println("║    Converting the knowledge list into a HashMap gives O(1)   ║");
        System.out.println("║    lookups, so the sentence only needs one linear scan       ║");
        System.out.println("║    instead of searching the knowledge list repeatedly.       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Scan Logic:                                                 ║");
        System.out.println("║    Walk through the sentence character by character          ║");
        System.out.println("║    On '(': find the matching ')', extract the key substring, ║");
        System.out.println("║      look it up in the map (default '?' if missing),         ║");
        System.out.println("║      append the result, and jump currIdx past the ')'        ║");
        System.out.println("║    On any other character: append it directly                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: sentence = \"(name)is(age)yearsold\"                 ║");
        System.out.println("║    knowledge = [[name,bob],[age,two]]                        ║");
        System.out.println("║    (name) → bob, (age) → two                                 ║");
        System.out.println("║    Result: \"bobistwoyearsold\"                                ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Brackets are never nested, so no stack is needed        ║");
        System.out.println("║    • Same key can appear in multiple brackets independently  ║");
        System.out.println("║    • Missing keys always resolve to the literal '?' character║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n + m) — n = sentence length,            ║");
        System.out.println("║                    m = knowledge list size (for map building)║");
        System.out.println("║  Space Complexity: O(m) for the map, O(n) for the result     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}