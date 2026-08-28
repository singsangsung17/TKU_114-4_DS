import java.util.ArrayList;
import java.util.List;

class TestBstNode {
    int value;
    TestBstNode left;
    TestBstNode right;

    TestBstNode(int value) {
        this.value = value;
    }
}

class TestBst {
    private TestBstNode root;

    boolean add(int value) {
        if (root == null) {
            root = new TestBstNode(value);
            return true;
        }
        TestBstNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new TestBstNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TestBstNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    boolean contains(int value) {
        TestBstNode current = root;
        while (current != null) {
            if (value == current.value) {
                return true;
            }
            current = value < current.value ? current.left : current.right;
        }
        return false;
    }

    boolean remove(int value) {
        if (!contains(value)) {
            return false;
        }
        root = remove(root, value);
        return true;
    }

    private TestBstNode remove(TestBstNode node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = remove(node.left, value);
        } else if (value > node.value) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            TestBstNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }
        return node;
    }

    List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(TestBstNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }

    List<Integer> rangeQuery(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        rangeQuery(root, low, high, result);
        return result;
    }

    private void rangeQuery(TestBstNode node, int low, int high,
                            List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.value > low) {
            rangeQuery(node.left, low, high, result);
        }
        if (node.value >= low && node.value <= high) {
            result.add(node.value);
        }
        if (node.value < high) {
            rangeQuery(node.right, low, high, result);
        }
    }

    int size() {
        return size(root);
    }

    private int size(TestBstNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValid(TestBstNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        if (node.value <= min || node.value >= max) {
            return false;
        }
        return isValid(node.left, min, node.value)
                && isValid(node.right, node.value, max);
    }
}

public class CompleteBstTestSuite {
    static int passed = 0;
    static int failed = 0;

    static void check(String description, boolean condition) {
        System.out.println((condition ? "PASS" : "FAIL") + " - " + description);
        if (condition) {
            passed++;
        } else {
            failed++;
        }
    }

    static TestBst buildTree() {
        TestBst tree = new TestBst();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80, 65}) {
            tree.add(value);
        }
        return tree;
    }

    public static void main(String[] args) {
        TestBst empty = new TestBst();
        check("empty size 為 0", empty.size() == 0);
        check("empty contains 回 false", !empty.contains(10));
        check("empty remove 回 false", !empty.remove(10));
        check("empty inorder 為空", empty.inorder().isEmpty());
        check("empty 是 valid", empty.isValid());
        check("empty range 為空", empty.rangeQuery(10, 20).isEmpty());

        TestBst tree = buildTree();
        check("建立後 size 為 8", tree.size() == 8);
        check("建立後是 valid", tree.isValid());
        check("inorder 為排序結果",
                tree.inorder().equals(List.of(20, 30, 40, 50, 60, 65, 70, 80)));

        check("duplicate add 回 false", !tree.add(30));
        check("duplicate 後 size 不變", tree.size() == 8);

        check("contains root", tree.contains(50));
        check("contains leaf", tree.contains(20));
        check("contains internal", tree.contains(70));
        check("missing contains 回 false", !tree.contains(999));
        check("missing remove 回 false", !tree.remove(999));

        check("remove leaf 20 回 true", tree.remove(20));
        check("remove leaf 後 size 為 7", tree.size() == 7);
        check("remove leaf 後仍 valid", tree.isValid());

        check("remove one child 70 回 true", tree.remove(70));
        check("remove one child 後 60 仍在", tree.contains(60));
        check("remove one child 後 65 仍在", tree.contains(65));
        check("remove one child 後仍 valid", tree.isValid());

        check("remove two children 50 回 true", tree.remove(50));
        check("remove two children 後 50 不在", !tree.contains(50));
        check("remove two children 後仍 valid", tree.isValid());
        check("remove two children 後 inorder 正確",
                tree.inorder().equals(List.of(30, 40, 60, 65, 80)));

        TestBst rangeTree = buildTree();
        check("range 一般情況",
                rangeTree.rangeQuery(30, 60).equals(List.of(30, 40, 50, 60)));
        check("range 單點", rangeTree.rangeQuery(65, 65).equals(List.of(65)));
        check("range 全部涵蓋", rangeTree.rangeQuery(0, 100).size() == 8);
        check("range 無資料", rangeTree.rangeQuery(90, 100).isEmpty());
        check("range low 大於 high", rangeTree.rangeQuery(80, 20).isEmpty());

        TestBst single = new TestBst();
        single.add(50);
        check("single root remove 回 true", single.remove(50));
        check("single root remove 後 size 為 0", single.size() == 0);

        System.out.println("PASS=" + passed + " FAIL=" + failed
                + " 總計=" + (passed + failed));
    }
}
