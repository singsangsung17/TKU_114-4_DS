class ReportNode {
    String value;
    ReportNode left;
    ReportNode right;

    ReportNode(String value) {
        this.value = value;
    }
}

public class BinaryTreeStructureReport {
    static int size(ReportNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

    static int leafCount(ReportNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return leafCount(node.left) + leafCount(node.right);
    }

    static int height(ReportNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static void printLeaves(ReportNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            System.out.print(node.value + " ");
            return;
        }
        printLeaves(node.left);
        printLeaves(node.right);
    }

    static void report(String title, ReportNode root) {
        System.out.println(title);
        System.out.println("  root=" + (root == null ? "null" : root.value));
        System.out.print("  leaves=");
        printLeaves(root);
        System.out.println();
        System.out.println("  size=" + size(root)
                + " leafCount=" + leafCount(root)
                + " height=" + height(root));
    }

    public static void main(String[] args) {
        ReportNode root = new ReportNode("A");
        root.left = new ReportNode("B");
        root.right = new ReportNode("C");
        root.left.left = new ReportNode("D");
        root.left.right = new ReportNode("E");
        root.right.right = new ReportNode("F");
        root.left.left.left = new ReportNode("G");

        report("七個節點的樹：", root);
        report("空樹：", null);
        report("單一節點的樹：", new ReportNode("X"));
    }
}
