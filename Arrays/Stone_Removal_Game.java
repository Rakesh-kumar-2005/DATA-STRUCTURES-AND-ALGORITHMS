package Arrays;

/*

    Description:
      Following program determines whether Alice wins a deterministic stone removal game,
        where players alternately remove 10, 9, 8, ... stones, by directly simulating the game...

    Problem Statement:
      -> Given an integer totalStones representing the pile size...
      -> Alice and Bob alternately remove stones, Alice moves first...
      -> On the first turn exactly 10 stones are removed, then 9 on the next turn,
           then 8, and so on (one fewer each turn)...
      -> A player who cannot remove the required number of stones loses...
      -> Return true if Alice wins, false otherwise...

    Key Insight:
      -> No player ever makes a choice: the number of stones removed each turn is fixed...
      -> The game is fully deterministic, so no DP or game-theory search is needed...
      -> Simulating turn by turn and checking who gets stuck gives the answer directly...
      -> The game ends after at most 10 moves, so the simulation is effectively O(1)...

    Example:
      -> totalStones = 27:
           Alice removes 10 → 17 left...
           Bob removes 9 → 8 left...
           Alice removes 8 → 0 left...
           Bob needs 7 but 0 remain → Bob loses → Alice wins...
      -> totalStones = 19:
           Alice removes 10 → 9 left...
           Bob removes 9 → 0 left...
           Alice needs 8 but 0 remain → Alice loses → return false...
      -> totalStones = 9:
           Alice needs 10 but only 9 remain → Alice loses immediately...

    Algorithm Steps:
      -> If totalStones < 10: Alice cannot make her first move → return false...
      -> Initialize aliceTurn = true, stonesToRemove = 10...
      -> While totalStones >= stonesToRemove:
           totalStones -= stonesToRemove...
           stonesToRemove--...
           aliceTurn = !aliceTurn (pass the turn to the other player)...
      -> When the loop ends, aliceTurn indicates the player who cannot move (the loser)...
      -> Alice wins if it is NOT her turn → return !aliceTurn...

    Why return !aliceTurn:
      -> After each successful removal the turn flips to the opponent...
      -> When the loop exits, the player whose turn it is cannot remove the required stones...
      -> That player loses...
      -> If aliceTurn == true, Alice is stuck → Alice loses → return false...
      -> If aliceTurn == false, Bob is stuck → Alice wins → return true...

    Guard Clause (totalStones < 10):
      -> Without it, the while loop would never run and aliceTurn would stay true...
      -> Then !aliceTurn = false, which happens to be correct anyway...
      -> The guard is therefore redundant but makes the intent explicit and exits early...

    Step-by-Step Trace (totalStones = 54):
      -> Alice removes 10 → 44, stonesToRemove=9...
      -> Bob   removes 9  → 35, stonesToRemove=8...
      -> Alice removes 8  → 27, stonesToRemove=7...
      -> Bob   removes 7  → 20, stonesToRemove=6...
      -> Alice removes 6  → 14, stonesToRemove=5...
      -> Bob   removes 5  → 9,  stonesToRemove=4...
      -> Alice removes 4  → 5,  stonesToRemove=3...
      -> Bob   removes 3  → 2,  stonesToRemove=2...
      -> Alice removes 2  → 0,  stonesToRemove=1...
      -> Bob needs 1 but 0 remain → Bob stuck → Alice wins...

    Cumulative Removal Thresholds:
      -> Total removed after k moves: 10, 19, 27, 34, 40, 45, 49, 52, 54, 55...
      -> The game stops after k moves when totalStones lies in [cumulative(k), cumulative(k+1) - 1]...
      -> Alice wins if k is odd (she made the last move), Bob wins if k is even...
      -> Example: totalStones in [10, 18] → k=1 → Alice wins...
                  totalStones in [19, 26] → k=2 → Bob wins...
                  totalStones in [27, 33] → k=3 → Alice wins...

    Edge Cases:
      -> totalStones < 10 → Alice cannot move → false...
      -> totalStones == 10 → Alice removes all, Bob stuck → true...
      -> totalStones == 19 → Bob makes the last move → false...
      -> totalStones >= 55 → all 10 moves (10 down to 1) can be made, then Alice needs 0...
           the loop continues until stonesToRemove reaches 0, where totalStones >= 0 stays true...
           this leads to repeated "removals" of 0 and an infinite loop in the current code...

    Potential Bug For Large Inputs:
      -> When stonesToRemove reaches 0, the condition totalStones >= 0 is always true...
      -> The loop then keeps decrementing stonesToRemove into negatives, which increases totalStones...
      -> The loop never terminates for totalStones >= 55...
      -> A fix is to stop at stonesToRemove == 0 (e.g., add stonesToRemove > 0 to the loop condition)...

    Time and Space Complexity:
      -> Time:  O(1) — at most 10 iterations for valid inputs (totalStones < 55)...
      -> Space: O(1) — only three scalar variables used...

    Applications:
      -> Teaching deterministic game simulation versus minimax game search...
      -> Turn-based game rule validation...
      -> Competitive programming easy simulation problems with fixed move sequences...
      -> Parity analysis of alternating-turn processes...

*/

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
