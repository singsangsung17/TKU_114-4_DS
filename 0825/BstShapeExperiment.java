class ShapeNode {
    int value;
    ShapeNode left;
    ShapeNode right;

    ShapeNode(int value) {
        this.value = value;
    }
}

class ShapeBst {
    private ShapeNode root;

    boolean add(int value) {
        if (root == null) {
            root = new ShapeNode(value);
            return true;
        }
        ShapeNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new ShapeNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ShapeNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    int height() {
        return height(root);
    }

    private int height(ShapeNode node) {
        return node == null
                ? -1
                : 1 + Math.max(height(node.left), height(node.right));
    }

    int searchComparisons(int value) {
        ShapeNode current = root;
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

public class BstShapeExperiment {
    static void experiment(String title, int[] order, int[] values) {
        ShapeBst tree = new ShapeBst();
        for (int value : order) {
            tree.add(value);
        }
        int total = 0;
        int worst = 0;
        for (int value : values) {
            int comparisons = tree.searchComparisons(value);
            total += comparisons;
            worst = Math.max(worst, comparisons);
        }
        System.out.println(title);
        System.out.println("  height=" + tree.height()
                + " 全部搜尋比較次數=" + total
                + " 最差=" + worst
                + " 平均=" + String.format("%.2f", (double) total / values.length));
    }

    public static void main(String[] args) {
        int[] values = {10, 20, 30, 40, 50, 60, 70, 80, 90,
                100, 110, 120, 130, 140, 150};

        int[] ascending = {10, 20, 30, 40, 50, 60, 70, 80, 90,
                100, 110, 120, 130, 140, 150};
        int[] descending = {150, 140, 130, 120, 110, 100, 90, 80, 70,
                60, 50, 40, 30, 20, 10};
        int[] balanced = {80, 40, 120, 20, 60, 100, 140, 10, 30, 50, 70,
                90, 110, 130, 150};

        experiment("遞增順序插入：", ascending, values);
        experiment("遞減順序插入：", descending, values);
        experiment("平衡順序插入：", balanced, values);
    }
}
