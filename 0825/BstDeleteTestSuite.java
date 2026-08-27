class SuiteNode {
    int value;
    SuiteNode left;
    SuiteNode right;

    SuiteNode(int value) {
        this.value = value;
    }
}

class SuiteBst {
    private SuiteNode root;

    boolean add(int value) {
        if (root == null) {
            root = new SuiteNode(value);
            return true;
        }
        SuiteNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new SuiteNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new SuiteNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    boolean contains(int value) {
        SuiteNode current = root;
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

    private SuiteNode remove(SuiteNode node, int value) {
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
            SuiteNode successor = minimumNode(node.right);
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }
        return node;
    }

    private SuiteNode minimumNode(SuiteNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    int size() {
        return size(root);
    }

    private int size(SuiteNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValid(SuiteNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        if (node.value <= min || node.value >= max) {
            return false;
        }
        return isValid(node.left, min, node.value)
                && isValid(node.right, node.value, max);
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(SuiteNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.value + " ");
        inorder(node.right);
    }
}

public class BstDeleteTestSuite {
    static void report(SuiteBst tree) {
        System.out.print("  inorder=");
        tree.inorder();
        System.out.println("  size=" + tree.size() + " valid=" + tree.isValid());
    }

    public static void main(String[] args) {
        System.out.println("測試一：empty tree 刪除");
        SuiteBst empty = new SuiteBst();
        System.out.println("  remove(10)=" + empty.remove(10));
        report(empty);

        System.out.println("測試二：missing value");
        SuiteBst missing = new SuiteBst();
        for (int value : new int[]{50, 30, 70}) {
            missing.add(value);
        }
        System.out.println("  remove(99)=" + missing.remove(99));
        report(missing);

        System.out.println("測試三：single root");
        SuiteBst single = new SuiteBst();
        single.add(50);
        System.out.println("  remove(50)=" + single.remove(50));
        report(single);

        System.out.println("測試四：root with one child");
        SuiteBst oneChild = new SuiteBst();
        oneChild.add(50);
        oneChild.add(70);
        System.out.println("  remove(50)=" + oneChild.remove(50));
        report(oneChild);

        System.out.println("測試五：root with two children");
        SuiteBst twoChildren = new SuiteBst();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80}) {
            twoChildren.add(value);
        }
        System.out.println("  remove(50)=" + twoChildren.remove(50));
        report(twoChildren);

        System.out.println("測試六：連續刪除到 empty");
        SuiteBst all = new SuiteBst();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80}) {
            all.add(value);
        }
        for (int value : new int[]{20, 30, 50, 80, 70, 60, 40}) {
            System.out.println("  remove(" + value + ")=" + all.remove(value));
            report(all);
        }
        System.out.println("  最後 remove(50)=" + all.remove(50));
    }
}
