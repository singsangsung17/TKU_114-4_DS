import java.util.ArrayList;
import java.util.List;

class StatisticsNode {
    int value;
    StatisticsNode left;
    StatisticsNode right;

    StatisticsNode(int value) {
        this.value = value;
    }
}

class StatisticsBst {
    private StatisticsNode root;

    boolean add(int value) {
        if (root == null) {
            root = new StatisticsNode(value);
            return true;
        }
        StatisticsNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new StatisticsNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new StatisticsNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    List<Integer> valuesBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        valuesBetween(root, low, high, result);
        return result;
    }

    private void valuesBetween(StatisticsNode node, int low, int high,
                               List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.value > low) {
            valuesBetween(node.left, low, high, result);
        }
        if (node.value >= low && node.value <= high) {
            result.add(node.value);
        }
        if (node.value < high) {
            valuesBetween(node.right, low, high, result);
        }
    }

    int countBetween(int low, int high) {
        if (low > high) {
            return 0;
        }
        return countBetween(root, low, high);
    }

    private int countBetween(StatisticsNode node, int low, int high) {
        if (node == null) {
            return 0;
        }
        int total = 0;
        if (node.value > low) {
            total += countBetween(node.left, low, high);
        }
        if (node.value >= low && node.value <= high) {
            total++;
        }
        if (node.value < high) {
            total += countBetween(node.right, low, high);
        }
        return total;
    }

    int sumBetween(int low, int high) {
        if (low > high) {
            return 0;
        }
        return sumBetween(root, low, high);
    }

    private int sumBetween(StatisticsNode node, int low, int high) {
        if (node == null) {
            return 0;
        }
        int total = 0;
        if (node.value > low) {
            total += sumBetween(node.left, low, high);
        }
        if (node.value >= low && node.value <= high) {
            total += node.value;
        }
        if (node.value < high) {
            total += sumBetween(node.right, low, high);
        }
        return total;
    }
}

public class BstRangeStatistics {
    static void report(StatisticsBst tree, int low, int high) {
        System.out.println("[" + low + "," + high + "] values="
                + tree.valuesBetween(low, high)
                + " count=" + tree.countBetween(low, high)
                + " sum=" + tree.sumBetween(low, high));
    }

    public static void main(String[] args) {
        StatisticsBst tree = new StatisticsBst();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80}) {
            tree.add(value);
        }

        report(tree, 30, 60);
        report(tree, 20, 80);
        report(tree, 50, 50);
        report(tree, 90, 100);
        report(tree, 41, 49);
        report(tree, 80, 20);

        StatisticsBst empty = new StatisticsBst();
        report(empty, 10, 20);
    }
}
