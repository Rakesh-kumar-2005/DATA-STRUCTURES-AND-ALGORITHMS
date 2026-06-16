package String;

public class Process_String_With_Special_Operations {

    private static String processStr(String st) {

        StringBuilder sb = new StringBuilder();

        for (char ch : st.toCharArray()) {
            if (ch == '#') {
                sb.append(sb);
            } else if (ch == '%') {
                sb.reverse();
            } else if (ch == '*') {
                if (! sb.isEmpty()) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        PROCESS STRING WITH SPECIAL OPERATIONS                ║");
        System.out.println("║  Process string with special characters:                     ║");
        System.out.println("║    '#' → Duplicate current string (append to itself)         ║");
        System.out.println("║    '%' → Reverse current string                              ║");
        System.out.println("║    '*' → Delete last character                               ║");
        System.out.println("║    Other → Append character to string                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Simple Append ===");
        String input1 = "abc";
        System.out.println("Input: \"" + input1 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  'b': append → \"ab\"");
        System.out.println("  'c': append → \"abc\"\n");

        String result1 = processStr(input1);
        System.out.println("✓ Result: \"" + result1 + "\"");
        System.out.println("  Expected: \"abc\"");
        System.out.println("  Status: " + (result1.equals("abc") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Duplication (#) ===");
        String input2 = "a#";
        System.out.println("Input: \"" + input2 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  '#': duplicate (append to itself) → \"a\" + \"a\" = \"aa\"\n");

        String result2 = processStr(input2);
        System.out.println("✓ Result: \"" + result2 + "\"");
        System.out.println("  Expected: \"aa\"");
        System.out.println("  Status: " + (result2.equals("aa") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Reverse (%) ===");
        String input3 = "ab%";
        System.out.println("Input: \"" + input3 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  'b': append → \"ab\"");
        System.out.println("  '%': reverse → \"ba\"\n");

        String result3 = processStr(input3);
        System.out.println("✓ Result: \"" + result3 + "\"");
        System.out.println("  Expected: \"ba\"");
        System.out.println("  Status: " + (result3.equals("ba") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Delete Last (*) ===");
        String input4 = "abc*";
        System.out.println("Input: \"" + input4 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  'b': append → \"ab\"");
        System.out.println("  'c': append → \"abc\"");
        System.out.println("  '*': delete last char → \"ab\"\n");

        String result4 = processStr(input4);
        System.out.println("✓ Result: \"" + result4 + "\"");
        System.out.println("  Expected: \"ab\"");
        System.out.println("  Status: " + (result4.equals("ab") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: All Operations Combined ===");
        String input5 = "ab#*%c";
        System.out.println("Input: \"" + input5 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  'b': append → \"ab\"");
        System.out.println("  '#': duplicate → \"ab\" + \"ab\" = \"abab\"");
        System.out.println("  '*': delete last → \"aba\"");
        System.out.println("  '%': reverse → \"aba\" (palindrome!)");
        System.out.println("  'c': append → \"abac\"\n");

        String result5 = processStr(input5);
        System.out.println("✓ Result: \"" + result5 + "\"");
        System.out.println("  Expected: \"abac\"");
        System.out.println("  Status: " + (result5.equals("abac") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Delete from Empty (Safe) ===");
        String input6 = "*ab";
        System.out.println("Input: \"" + input6 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  '*': delete (string empty, skip) → \"\"");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  'b': append → \"ab\"\n");

        String result6 = processStr(input6);
        System.out.println("✓ Result: \"" + result6 + "\"");
        System.out.println("  Expected: \"ab\"");
        System.out.println("  Status: " + (result6.equals("ab") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Multiple Duplications ===");
        String input7 = "a##";
        System.out.println("Input: \"" + input7 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  '#': duplicate → \"a\" + \"a\" = \"aa\"");
        System.out.println("  '#': duplicate → \"aa\" + \"aa\" = \"aaaa\"\n");

        String result7 = processStr(input7);
        System.out.println("✓ Result: \"" + result7 + "\"");
        System.out.println("  Expected: \"aaaa\"");
        System.out.println("  Status: " + (result7.equals("aaaa") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 8: Reverse Palindrome ===");
        String input8 = "a%";
        System.out.println("Input: \"" + input8 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  '%': reverse → \"a\" (single char is palindrome)\n");

        String result8 = processStr(input8);
        System.out.println("✓ Result: \"" + result8 + "\"");
        System.out.println("  Expected: \"a\"");
        System.out.println("  Status: " + (result8.equals("a") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 9: Complex Sequence ===");
        String input9 = "abc*#%";
        System.out.println("Input: \"" + input9 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  'b': append → \"ab\"");
        System.out.println("  'c': append → \"abc\"");
        System.out.println("  '*': delete last → \"ab\"");
        System.out.println("  '#': duplicate → \"ab\" + \"ab\" = \"abab\"");
        System.out.println("  '%': reverse → \"baba\"\n");

        String result9 = processStr(input9);
        System.out.println("✓ Result: \"" + result9 + "\"");
        System.out.println("  Expected: \"baba\"");
        System.out.println("  Status: " + (result9.equals("baba") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 10: Empty Result ===");
        String input10 = "a*";
        System.out.println("Input: \"" + input10 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  '*': delete last → \"\"\n");

        String result10 = processStr(input10);
        System.out.println("✓ Result: \"" + result10 + "\"");
        System.out.println("  Expected: \"\"");
        System.out.println("  Status: " + (result10.equals("") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 11: Reverse Then Append ===");
        String input11 = "ab%cd";
        System.out.println("Input: \"" + input11 + "\"");
        System.out.println("\nStep by step:");
        System.out.println("  'a': append → \"a\"");
        System.out.println("  'b': append → \"ab\"");
        System.out.println("  '%': reverse → \"ba\"");
        System.out.println("  'c': append → \"bac\"");
        System.out.println("  'd': append → \"bacd\"\n");

        String result11 = processStr(input11);
        System.out.println("✓ Result: \"" + result11 + "\"");
        System.out.println("  Expected: \"bacd\"");
        System.out.println("  Status: " + (result11.equals("bacd") ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Process string with special operations             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Operations:                                                 ║");
        System.out.println("║    '#' - Duplicate: sb.append(sb)                            ║");
        System.out.println("║    '%' - Reverse: sb.reverse()                               ║");
        System.out.println("║    '*' - Delete: sb.deleteCharAt(length-1)                   ║");
        System.out.println("║    Other - Append: sb.append(character)                      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Data Structure Choice:                                      ║");
        System.out.println("║    StringBuilder chosen because:                             ║");
        System.out.println("║    • Efficient append: O(1) amortized                        ║");
        System.out.println("║    • Efficient delete: O(1) if removing last char            ║");
        System.out.println("║    • Efficient reverse: O(n) - acceptable for this task      ║");
        System.out.println("║    • Mutable: allows in-place modifications                  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Algorithm:                                                  ║");
        System.out.println("║    For each character in input string:                       ║");
        System.out.println("║      if (ch == '#'):                                         ║");
        System.out.println("║        sb.append(sb)  // Duplicate contents                  ║");
        System.out.println("║      else if (ch == '%'):                                    ║");
        System.out.println("║        sb.reverse()   // Reverse in-place                    ║");
        System.out.println("║      else if (ch == '*'):                                    ║");
        System.out.println("║        if (!sb.isEmpty())                                    ║");
        System.out.println("║          sb.deleteCharAt(sb.length() - 1)  // Remove last    ║");
        System.out.println("║      else:                                                   ║");
        System.out.println("║        sb.append(ch)  // Add regular character               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Duplication Detail (#):                                     ║");
        System.out.println("║    Current content: \"ab\"                                     ║");
        System.out.println("║    sb.append(sb) creates: \"ab\" + \"ab\" = \"abab\"               ║");
        System.out.println("║    Effectively doubles the string                            ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Reverse Detail (%):                                         ║");
        System.out.println("║    Reverses entire current content                           ║");
        System.out.println("║    \"abc\" → \"cba\"                                             ║");
        System.out.println("║    Palindromes remain unchanged: \"aba\" → \"aba\"               ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Delete Detail (*):                                          ║");
        System.out.println("║    Removes last character from current string                ║");
        System.out.println("║    \"abc\" → \"ab\"                                              ║");
        System.out.println("║    If empty, skip (safety check important)                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Edge Cases:                                                 ║");
        System.out.println("║    • Delete from empty: Skip safely with isEmpty() check     ║");
        System.out.println("║    • Operations on empty: All operations work correctly      ║");
        System.out.println("║      - # on \"\" → \"\"                                          ║");
        System.out.println("║      - % on \"\" → \"\"                                          ║");
        System.out.println("║    • Duplicate growth: Can create large strings quickly      ║");
        System.out.println("║      - \"a###\" → \"a\" → \"aa\" → \"aaaa\" → \"aaaaaaaa\"             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example Walkthrough: \"ab#*%c\"                               ║");
        System.out.println("║    Initial: sb = \"\"                                          ║");
        System.out.println("║    'a': append 'a' → \"a\"                                     ║");
        System.out.println("║    'b': append 'b' → \"ab\"                                    ║");
        System.out.println("║    '#': duplicate → append(sb) → \"ab\" + \"ab\" = \"abab\"        ║");
        System.out.println("║    '*': delete last → \"aba\"                                  ║");
        System.out.println("║    '%': reverse → \"aba\" (palindrome stays same)              ║");
        System.out.println("║    'c': append 'c' → \"abac\"                                  ║");
        System.out.println("║    Return \"abac\"                                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Method Complexity:                                          ║");
        System.out.println("║    Let n = length of input string                            ║");
        System.out.println("║    Let m = max length of StringBuilder during process        ║");
        System.out.println("║    Time:  O(n × m) worst case due to reverse + duplicate     ║");
        System.out.println("║    Space: O(m) for StringBuilder content                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  StringBuilder Methods Used:                                 ║");
        System.out.println("║    append(String) - O(n) where n is length of string         ║");
        System.out.println("║    reverse()      - O(n) where n is length of builder        ║");
        System.out.println("║    deleteCharAt() - O(n) worst case, O(1) if last char       ║");
        System.out.println("║    length()       - O(1)                                     ║");
        System.out.println("║    isEmpty()      - O(1)                                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}