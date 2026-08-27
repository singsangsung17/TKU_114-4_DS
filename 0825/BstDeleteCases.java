class DeleteNode {
    int value;
    DeleteNode left;
    DeleteNode right;

    DeleteNode(int value) {
        this.value = value;
    }
}

class DeleteBst {
    private DeleteNode root;

    boolean add(int value) {
        if (root == null) {
            root = new DeleteNode(value);
            return true;
        }
        DeleteNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new DeleteNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new DeleteNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    boolean contains(int value) {
        DeleteNode current = root;
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

    private DeleteNode remove(DeleteNode node, int value) {
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
            DeleteNode successor = minimumNode(node.right);
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }
        return node;
    }

    private DeleteNode minimumNode(DeleteNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    int size() {
        return size(root);
    }

    private int size(DeleteNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValid(DeleteNode node, int min, int max) {
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

    private void inorder(DeleteNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.value + " ");
        inorder(node.right);
    }
}

public class BstDeleteCases {
    static void report(String title, DeleteBst tree) {
        System.out.println(title);
        System.out.print("  inorder=");
        tree.inorder();
        System.out.println("  size=" + tree.size() + " valid=" + tree.isValid());
    }

    public static void main(String[] args) {
        DeleteBst tree = new DeleteBst();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80, 65}) {
            tree.add(value);
        }

        report("初始：", tree);

        System.out.println("刪除 leaf 20：" + tree.remove(20));
        report("刪除 leaf 後：", tree);

        System.out.println("刪除 single-child 70：" + tree.remove(70));
        report("刪除 single-child 後：", tree);

        System.out.println("刪除 two-child 50：" + tree.remove(50));
        report("刪除 two-child 後：", tree);

        System.out.println("刪除不存在的 99：" + tree.remove(99));
    }
}
