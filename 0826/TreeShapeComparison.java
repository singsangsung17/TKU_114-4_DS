class ShapeCompareNode {
    int value;
    ShapeCompareNode left;
    ShapeCompareNode right;

    ShapeCompareNode(int value) {
        this.value = value;
    }
}

class ShapeCompareBst {
    private ShapeCompareNode root;

    boolean add(int value) {
        if (root == null) {
            root = new ShapeCompareNode(value);
            return true;
        }
        ShapeCompareNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new ShapeCompareNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ShapeCompareNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    int height() {
        return height(root);
    }

    private int height(ShapeCompareNode node) {
        return node == null
                ? -1
                : 1 + Math.max(height(node.left), height(node.right));
    }

    int searchComparisons(int value) {
        ShapeCompareNode current = root;
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

public class TreeShapeComparison {
    static void compare(String title, int[] order, int[] keys, int[] missing) {
        ShapeCompareBst tree = new ShapeCompareBst();
        for (int value : order) {
            tree.add(value);
        }

        int total = 0;
        int worst = 0;
        for (int key : keys) {
            int comparisons = tree.searchComparisons(key);
            total += comparisons;
            worst = Math.max(worst, comparisons);
        }

        System.out.println(title);
        System.out.println("  height=" + tree.height()
                + " 全部 key 比較總數=" + total
                + " 最差=" + worst
                + " 平均=" + String.format("%.2f", (double) total / keys.length));
        for (int key : missing) {
            System.out.println("  missing key " + key + " 比較次數="
                    + tree.searchComparisons(key));
        }
    }

    public static void main(String[] args) {
        int[] keys = {10, 20, 30, 40, 50, 60, 70, 80, 90,
                100, 110, 120, 130, 140, 150};
        int[] missing = {5, 75, 155};

        int[] ascending = {10, 20, 30, 40, 50, 60, 70, 80, 90,
                100, 110, 120, 130, 140, 150};
        int[] descending = {150, 140, 130, 120, 110, 100, 90, 80, 70,
                60, 50, 40, 30, 20, 10};
        int[] balanced = {80, 40, 120, 20, 60, 100, 140, 10, 30, 50, 70,
                90, 110, 130, 150};

        compare("升冪插入：", ascending, keys, missing);
        compare("降冪插入：", descending, keys, missing);
        compare("接近平衡插入：", balanced, keys, missing);
    }
}
