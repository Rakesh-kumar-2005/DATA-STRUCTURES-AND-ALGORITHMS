package Tree;

public class Count_Nodes_Equal_To_Average_Of_Subtree {

    static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

    }

    private static int[] helper(TreeNode root, int[] ans) {

        if (root.left == null && root.right == null) {
            ans[0]++;
            return new int[]{root.val, 1};
        }

        int[] left = (root.left != null) ? helper(root.left, ans) : new int[2];
        int[] right = (root.right != null) ? helper(root.right, ans) : new int[2];

        int sum = left[0] + right[0] + root.val;
        int size = left[1] + right[1] + 1;

        int average = sum / size;
        if (average == root.val) {
            ans[0]++;
        }

        return new int[]{sum, size};

    }

    private static int averageOfSubtree(TreeNode root) {

        int[] ans = {0};
        helper(root, ans);
        return ans[0];
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║      COUNT NODES EQUAL TO AVERAGE OF SUBTREE                 ║");
        System.out.println("║  Count nodes where node.val equals the integer average of    ║");
        System.out.println("║  all values in its subtree                                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("=== Test Case 1: Classic Case ===");
        TreeNode root1 = new TreeNode(4);
        root1.left = new TreeNode(8);
        root1.right = new TreeNode(5);
        root1.left.left = new TreeNode(0);
        root1.left.right = new TreeNode(1);
        root1.right.right = new TreeNode(6);
        System.out.println("Tree:");
        System.out.println("           4");
        System.out.println("          / \\");
        System.out.println("         8   5");
        System.out.println("        / \\   \\");
        System.out.println("       0   1   6");
        System.out.println("\nLeaves (0,1,6) always count. Node 8's subtree avg = (8+0+1)/3=3 (no).");
        System.out.println("Node 5's subtree avg = (5+6)/2=5 (yes). Root avg = (4+8+0+1+5+6)/6=4 (yes)\n");

        int result1 = averageOfSubtree(root1);
        System.out.println("✓ Result: " + result1);
        System.out.println("  Expected: 5");
        System.out.println("  Status: " + (result1 == 5 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 2: Single Node ===");
        TreeNode root2 = new TreeNode(1);
        System.out.println("Tree: single node with value 1");
        System.out.println("\nA lone node is trivially its own average\n");

        int result2 = averageOfSubtree(root2);
        System.out.println("✓ Result: " + result2);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result2 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 3: All Same Values ===");
        TreeNode root3 = new TreeNode(5);
        root3.left = new TreeNode(5);
        root3.right = new TreeNode(5);
        System.out.println("Tree: root=5, left=5, right=5");
        System.out.println("\nEvery subtree average equals 5, so every node qualifies\n");

        int result3 = averageOfSubtree(root3);
        System.out.println("✓ Result: " + result3);
        System.out.println("  Expected: 3");
        System.out.println("  Status: " + (result3 == 3 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 4: Left-Skewed Tree ===");
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.left.left = new TreeNode(3);
        System.out.println("Tree: 1 -> left 2 -> left 3 (a straight left chain)");
        System.out.println("\nLeaf 3 counts. Subtree at 2: avg=(2+3)/2=2 (yes)");
        System.out.println("Root subtree: avg=(1+2+3)/3=2 (no, root val is 1)\n");

        int result4 = averageOfSubtree(root4);
        System.out.println("✓ Result: " + result4);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result4 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 5: Right-Skewed Tree ===");
        TreeNode root5 = new TreeNode(10);
        root5.right = new TreeNode(20);
        root5.right.right = new TreeNode(30);
        System.out.println("Tree: 10 -> right 20 -> right 30 (a straight right chain)");
        System.out.println("\nLeaf 30 counts. Subtree at 20: avg=(20+30)/2=25 (no)");
        System.out.println("Root subtree: avg=(10+20+30)/3=20 (no, root val is 10)\n");

        int result5 = averageOfSubtree(root5);
        System.out.println("✓ Result: " + result5);
        System.out.println("  Expected: 1");
        System.out.println("  Status: " + (result5 == 1 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 6: Balanced Tree, Only Leaves Match ===");
        TreeNode root6 = new TreeNode(100);
        root6.left = new TreeNode(1);
        root6.right = new TreeNode(1);
        System.out.println("Tree: root=100, left=1, right=1");
        System.out.println("\nBoth leaves count. Root avg=(100+1+1)/3=34 (no, root val is 100)\n");

        int result6 = averageOfSubtree(root6);
        System.out.println("✓ Result: " + result6);
        System.out.println("  Expected: 2");
        System.out.println("  Status: " + (result6 == 2 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("=== Test Case 7: Larger Balanced Tree ===");
        TreeNode root7 = new TreeNode(7);
        root7.left = new TreeNode(3);
        root7.right = new TreeNode(11);
        root7.left.left = new TreeNode(3);
        root7.left.right = new TreeNode(3);
        root7.right.left = new TreeNode(11);
        root7.right.right = new TreeNode(11);
        System.out.println("Tree: root=7, left subtree all 3's, right subtree all 11's");
        System.out.println("\nAll 4 leaves count. Left subtree avg=(3+3+3)/3=3 (yes)");
        System.out.println("Right subtree avg=(11+11+11)/3=11 (yes). Root avg=(7+9+33)/7=7 (yes)\n");

        int result7 = averageOfSubtree(root7);
        System.out.println("✓ Result: " + result7);
        System.out.println("  Expected: 7");
        System.out.println("  Status: " + (result7 == 7 ? "PASS ✓" : "FAIL ✗") + "\n");

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITHM INSIGHTS                                          ║");
        System.out.println("║  ────────────────────────────────────────────────────────────║");
        System.out.println("║  Problem: Count nodes whose value equals the integer         ║");
        System.out.println("║           (floor-division) average of all values in its      ║");
        System.out.println("║           own subtree                                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Key Insight: Post-Order Traversal Returns (sum, count)      ║");
        System.out.println("║    Each recursive call returns the subtree's total sum and   ║");
        System.out.println("║    node count, letting the parent compute its own average    ║");
        System.out.println("║    without re-traversing the subtree.                        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Logic:                                                      ║");
        System.out.println("║    Leaf node: increment ans[0] immediately, return {val, 1}  ║");
        System.out.println("║    Internal node: recurse left and right (treating a missing ║");
        System.out.println("║      child as {0, 0} via `new int[2]`)                       ║");
        System.out.println("║    sum = left.sum + right.sum + root.val                     ║");
        System.out.println("║    size = left.size + right.size + 1                         ║");
        System.out.println("║    If sum/size == root.val, increment ans[0]                 ║");
        System.out.println("║    Return {sum, size} to the parent call                     ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Example: root=4, left=8(children 0,1), right=5(child 6)     ║");
        System.out.println("║    Leaves 0,1,6 each count → ans=3                           ║");
        System.out.println("║    Node 8: avg=(8+0+1)/3=3, root.val=8 → no match            ║");
        System.out.println("║    Node 5: avg=(5+6)/2=5 → match, ans=4                      ║");
        System.out.println("║    Root 4: avg=(4+8+0+1+5+6)/6=4 → match, ans=5              ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Properties:                                                 ║");
        System.out.println("║    • ans[0] uses an array as a mutable counter across        ║");
        System.out.println("║      recursive calls (Java has no pass-by-reference ints)    ║");
        System.out.println("║    • Integer division truncates, matching LeetCode's spec    ║");
        System.out.println("║    • Every leaf automatically satisfies the condition        ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Time Complexity: O(n) — each node visited exactly once      ║");
        System.out.println("║  Space Complexity: O(h) — recursion stack, h = tree height   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

    }

}