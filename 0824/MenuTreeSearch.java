class MenuNode {
    String value;
    MenuNode left;
    MenuNode right;

    MenuNode(String value) {
        this.value = value;
    }
}

public class MenuTreeSearch {
    static boolean contains(MenuNode node, String target) {
        if (node == null) {
            return false;
        }
        if (node.value.equals(target)) {
            return true;
        }
        return contains(node.left, target) || contains(node.right, target);
    }

    static int findDepth(MenuNode node, String target) {
        if (node == null) {
            return -1;
        }
        if (node.value.equals(target)) {
            return 0;
        }
        int left = findDepth(node.left, target);
        if (left >= 0) {
            return left + 1;
        }
        int right = findDepth(node.right, target);
        if (right >= 0) {
            return right + 1;
        }
        return -1;
    }

    static int countLeaves(MenuNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeaves(node.left) + countLeaves(node.right);
    }

    static void display(MenuNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.value + " ");
        display(node.left);
        display(node.right);
    }

    public static void main(String[] args) {
        MenuNode root = new MenuNode("Main");
        root.left = new MenuNode("File");
        root.right = new MenuNode("Help");
        root.left.left = new MenuNode("Open");
        root.left.right = new MenuNode("Save");
        root.right.left = new MenuNode("About");
        root.left.left.left = new MenuNode("Recent");

        System.out.print("preorder: ");
        display(root);
        System.out.println();

        System.out.println("contains(Save)=" + contains(root, "Save"));
        System.out.println("contains(Print)=" + contains(root, "Print"));

        System.out.println("findDepth(Main)=" + findDepth(root, "Main"));
        System.out.println("findDepth(About)=" + findDepth(root, "About"));
        System.out.println("findDepth(Recent)=" + findDepth(root, "Recent"));
        System.out.println("findDepth(Print)=" + findDepth(root, "Print"));

        System.out.println("countLeaves=" + countLeaves(root));

        System.out.println("empty contains=" + contains(null, "Main"));
        System.out.println("empty findDepth=" + findDepth(null, "Main"));
        System.out.println("empty countLeaves=" + countLeaves(null));
    }
}
