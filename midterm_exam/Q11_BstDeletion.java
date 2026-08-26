
import java.util.ArrayList;
import java.util.List;

public class Q11_BstDeletion {
    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;
    private int count;

    public boolean add(int value) {
        if (root == null) {
            root = new Node(value);
            count++;
            return true;
        }
        Node current = root;
        while (true) {
            if (value == current.value) {
                return false;
            }
            if (value < current.value) {
                if (current.left == null) {
                    current.left = new Node(value);
                    count++;
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node(value);
                    count++;
                    return true;
                }
                current = current.right;
            }
        }
    }

    public boolean remove(int value) {
        if (!contains(value)) {
            return false;
        }
        root = removeHelper(root, value);
        count--;
        return true;
    }

    private Node removeHelper(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = removeHelper(node.left, value);
            return node;
        }
        if (value > node.value) {
            node.right = removeHelper(node.right, value);
            return node;
        }
        if (node.left == null) {
            return node.right;
        }
        if (node.right == null) {
            return node.left;
        }
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        node.value = successor.value;
        node.right = removeHelper(node.right, successor.value);
        return node;
    }

    public boolean contains(int value) {
        Node current = root;
        while (current != null) {
            if (value == current.value) {
                return true;
            }
            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public int size() {
        return count;
    }

    public java.util.List<Integer> inorder() {
        List<Integer> result = new ArrayList<Integer>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left, result);
        result.add(node.value);
        inorderHelper(node.right, result);
    }

    public boolean isValid() {
        return isValidHelper(root, null, null);
    }

    private boolean isValidHelper(Node node, Integer low, Integer high) {
        if (node == null) {
            return true;
        }
        if (low != null && node.value <= low.intValue()) {
            return false;
        }
        if (high != null && node.value >= high.intValue()) {
            return false;
        }
        return isValidHelper(node.left, low, Integer.valueOf(node.value))
                && isValidHelper(node.right, Integer.valueOf(node.value), high);
    }
}
