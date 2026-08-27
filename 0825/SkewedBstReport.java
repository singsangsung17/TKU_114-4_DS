class SkewNode {
    int value;
    SkewNode left;
    SkewNode right;

    SkewNode(int value) {
        this.value = value;
    }
}

class SkewBst {
    private SkewNode root;

    boolean add(int value) {
        if (root == null) {
            root = new SkewNode(value);
            return true;
        }
        SkewNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new SkewNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new SkewNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    int size() {
        return size(root);
    }

    private int size(SkewNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    int height() {
        return height(root);
    }

    private int height(SkewNode node) {
        return node == null
                ? -1
                : 1 + Math.max(height(node.left), height(node.right));
    }

    int searchComparisons(int value) {
        SkewNode current = root;
        int comparisons = 0;
        while (current != null) {
            comparisons++;
            if (value == current.value) {
                return comparisons;
            }
            current = value < current.value ? current.left : current.right;
        }
        return comparisons;
    }
}

public class SkewedBstReport {
    static void report(String title, int[] order, int[] targets) {
        SkewBst tree = new SkewBst();
        for (int value : order) {
            tree.add(value);
        }
        System.out.println(title);
        System.out.println("  size=" + tree.size() + " height=" + tree.height());
        for (int target : targets) {
            System.out.println("  搜尋 " + target + " comparisons="
                    + tree.searchComparisons(target));
        }
    }

    public static void main(String[] args) {
        int[] sorted = {10, 20, 30, 40, 50, 60, 70};
        int[] balanced = {40, 20, 60, 10, 30, 50, 70};
        int[] targets = {70, 40, 65};

        report("排序資料建立（skewed）：", sorted, targets);
        report("平衡順序建立：", balanced, targets);
    }
}
