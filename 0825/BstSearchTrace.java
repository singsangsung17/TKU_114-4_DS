class TraceNode {
    int value;
    TraceNode left;
    TraceNode right;

    TraceNode(int value) {
        this.value = value;
    }
}

class TraceBst {
    private TraceNode root;

    boolean add(int value) {
        if (root == null) {
            root = new TraceNode(value);
            return true;
        }
        TraceNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new TraceNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TraceNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    boolean searchWithTrace(int value) {
        System.out.println("搜尋 " + value + "：");
        TraceNode current = root;
        int comparisons = 0;
        while (current != null) {
            comparisons++;
            if (value == current.value) {
                System.out.println("  current=" + current.value + " 方向=found");
                System.out.println("  結果=true comparisons=" + comparisons);
                return true;
            }
            if (value < current.value) {
                System.out.println("  current=" + current.value + " 方向=left");
                current = current.left;
            } else {
                System.out.println("  current=" + current.value + " 方向=right");
                current = current.right;
            }
        }
        System.out.println("  結果=false comparisons=" + comparisons);
        return false;
    }
}

public class BstSearchTrace {
    public static void main(String[] args) {
        TraceBst tree = new TraceBst();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80}) {
            tree.add(value);
        }

        tree.searchWithTrace(50);
        tree.searchWithTrace(20);
        tree.searchWithTrace(70);
        tree.searchWithTrace(65);
    }
}
