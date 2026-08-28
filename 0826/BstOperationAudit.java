import java.util.ArrayList;
import java.util.List;

class AuditNode {
    int value;
    AuditNode left;
    AuditNode right;

    AuditNode(int value) {
        this.value = value;
    }
}

class AuditBst {
    private AuditNode root;

    boolean add(int value) {
        if (root == null) {
            root = new AuditNode(value);
            return true;
        }
        AuditNode current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new AuditNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new AuditNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    boolean contains(int value) {
        AuditNode current = root;
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

    private AuditNode remove(AuditNode node, int value) {
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
            AuditNode successor = minimumNode(node.right);
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }
        return node;
    }

    private AuditNode minimumNode(AuditNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(AuditNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }

    int size() {
        return size(root);
    }

    private int size(AuditNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    int height() {
        return height(root);
    }

    private int height(AuditNode node) {
        return node == null
                ? -1
                : 1 + Math.max(height(node.left), height(node.right));
    }

    boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValid(AuditNode node, int min, int max) {
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

public class BstOperationAudit {
    static AuditBst tree = new AuditBst();

    static void audit(String operation, boolean result) {
        System.out.println(operation + " result=" + result);
        System.out.println("  inorder=" + tree.inorder()
                + " size=" + tree.size()
                + " height=" + tree.height()
                + " valid=" + tree.isValid());
    }

    public static void main(String[] args) {
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80, 65}) {
            audit("add(" + value + ")", tree.add(value));
        }

        audit("add(30) duplicate", tree.add(30));
        audit("remove(999) missing", tree.remove(999));
        audit("remove(20) leaf", tree.remove(20));
        audit("remove(70) one child", tree.remove(70));
        audit("remove(50) two children", tree.remove(50));
    }
}
