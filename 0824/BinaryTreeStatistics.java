class StatNode {
    int value;
    StatNode left;
    StatNode right;

    StatNode(int value) {
        this.value = value;
    }
}

public class BinaryTreeStatistics {
    static int size(StatNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

    static int sum(StatNode node) {
        if (node == null) {
            return 0;
        }
        return node.value + sum(node.left) + sum(node.right);
    }

    static int maximum(StatNode node) {
        if (node == null) {
            throw new IllegalArgumentException("empty tree has no maximum");
        }
        int max = node.value;
        if (node.left != null) {
            max = Math.max(max, maximum(node.left));
        }
        if (node.right != null) {
            max = Math.max(max, maximum(node.right));
        }
        return max;
    }

    static int leafCount(StatNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return leafCount(node.left) + leafCount(node.right);
    }

    static int height(StatNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static boolean contains(StatNode node, int target) {
        if (node == null) {
            return false;
        }
        if (node.value == target) {
            return true;
        }
        return contains(node.left, target) || contains(node.right, target);
    }

    public static void main(String[] args) {
        StatNode root = new StatNode(10);
        root.left = new StatNode(-5);
        root.right = new StatNode(20);
        root.left.left = new StatNode(3);
        root.left.right = new StatNode(-8);
        root.right.right = new StatNode(7);

        System.out.println("size=" + size(root));
        System.out.println("sum=" + sum(root));
        System.out.println("maximum=" + maximum(root));
        System.out.println("leafCount=" + leafCount(root));
        System.out.println("height=" + height(root));
        System.out.println("contains(-8)=" + contains(root, -8));
        System.out.println("contains(99)=" + contains(root, 99));

        System.out.println("empty size=" + size(null));
        System.out.println("empty sum=" + sum(null));
        System.out.println("empty leafCount=" + leafCount(null));
        System.out.println("empty height=" + height(null));
        System.out.println("empty contains=" + contains(null, 10));

        try {
            maximum(null);
        } catch (IllegalArgumentException e) {
            System.out.println("empty maximum 例外：" + e.getMessage());
        }

        StatNode negativeOnly = new StatNode(-3);
        negativeOnly.left = new StatNode(-9);
        System.out.println("全負數樹 maximum=" + maximum(negativeOnly));
    }
}
