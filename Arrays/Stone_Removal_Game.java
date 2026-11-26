package Arrays;

public class Stone_Removal_Game {

    private static boolean canAliceWin(int totalStones) {
        if (totalStones < 10) {
            return false;
        }

        boolean aliceTurn = true;
        int stonesToRemove = 10;

        while (totalStones >= stonesToRemove) {
            totalStones -= stonesToRemove;
            stonesToRemove--;
            aliceTurn = ! aliceTurn;
        }

        return ! aliceTurn;

    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    STONE REMOVAL GAME                        ║");
        System.out.println("║  Alice and Bob alternately remove 10, 9, 8, ... stones. The  ║");
        System.out.println("║  player who cannot make a move loses. Alice moves first      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Alice Wins Easily ===");
        int n1 = 12;
        System.out.println("Total stones: " + n1);
        System.out.println("\nAlice removes 10 → 2 left, Bob needs 9 but can't → Alice wins\n");

        boolean result1 = canAliceWin(n1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result1 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Too Few Stones ===");
        int n2 = 1;
        System.out.println("Total stones: " + n2);
        System.out.println("\nAlice can't even remove 10 → Alice loses immediately\n");

        boolean result2 = canAliceWin(n2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result2 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: Just Below the Threshold ===");
        int n3 = 9;
        System.out.println("Total stones: " + n3);
        System.out.println("\n9 < 10, guard clause returns false\n");

        boolean result3 = canAliceWin(n3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result3 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Exactly Ten ===");
        int n4 = 10;
        System.out.println("Total stones: " + n4);
        System.out.println("\nAlice removes 10 → 0 left, Bob needs 9 but can't → Alice wins\n");

        boolean result4 = canAliceWin(n4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result4 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Bob Takes the Last Move ===");
        int n5 = 19;
        System.out.println("Total stones: " + n5);
        System.out.println("\nAlice removes 10 → 9 left, Bob removes 9 → 0 left");
        System.out.println("Alice needs 8 but can't → Alice loses\n");

        boolean result5 = canAliceWin(n5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: false");
        System.out.println("  Status: " + (result5 == false ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Alice Takes the Last Move ===");
        int n6 = 27;
        System.out.println("Total stones: " + n6);
        System.out.println("\nAlice 10, Bob 9, Alice 8 → 0 left, Bob needs 7 but can't → Alice wins\n");

        boolean result6 = canAliceWin(n6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result6 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Long Game ===");
        int n7 = 54;
        System.out.println("Total stones: " + n7);
        System.out.println("\nRemovals 10+9+8+7+6+5+4+3+2 = 54 (Alice makes the 9th move)");
        System.out.println("Bob needs 1 but 0 stones remain → Alice wins\n");

        boolean result7 = canAliceWin(n7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: true");
        System.out.println("  Status: " + (result7 == true ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Players alternately remove 10, 9, 8, ... stones.   ║");
        System.out.println("║           The player who can't move loses. Does Alice win?   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: The Game Is Fully Deterministic                ║");
        System.out.println("║    Nobody makes a choice: the amount removed per turn is     ║");
        System.out.println("║    fixed, so simulating the game gives the winner directly.  ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Simulation Logic:                                           ║");
        System.out.println("║    If totalStones < 10, Alice can't move → false             ║");
        System.out.println("║    While totalStones >= stonesToRemove:                      ║");
        System.out.println("║      totalStones -= stonesToRemove                           ║");
        System.out.println("║      stonesToRemove--                                        ║");
        System.out.println("║      aliceTurn = !aliceTurn                                  ║");
        System.out.println("║    When the loop ends, aliceTurn is the player who is stuck  ║");
        System.out.println("║    Alice wins if it's NOT her turn → return !aliceTurn       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: totalStones = 27                                   ║");
        System.out.println("║    Alice 10 → 17, Bob 9 → 8, Alice 8 → 0                     ║");
        System.out.println("║    Bob needs 7, has 0 → stuck → Alice wins                   ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • Cumulative removals: 10, 19, 27, 34, 40, 45, 49, 52, 54 ║");
        System.out.println("║    • Alice wins when the game stops after an odd number of   ║");
        System.out.println("║      moves, Bob wins after an even number                    ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(1) — at most 10 iterations               ║");
        System.out.println("║  Space Complexity: O(1) — a few variables                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}