import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q11_BstHashDirectory {

    class Node {
        int id;
        Node left;
        Node right;

        Node(int id) {
            this.id = id;
        }
    }

    private Node root;
    private final Map<Integer, String> nameById = new HashMap<>();

    public boolean add(int id, String name) {
        if (id <= 0 || name == null) {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        if (nameById.containsKey(id)) {
            return false;
        }
        root = insert(root, id);
        nameById.put(id, trimmed);
        return true;
    }

    private Node insert(Node node, int id) {
        if (node == null) {
            return new Node(id);
        }
        if (id < node.id) {
            node.left = insert(node.left, id);
        } else if (id > node.id) {
            node.right = insert(node.right, id);
        }
        return node;
    }

    public String findName(int id) {
        return nameById.get(id);
    }

    public boolean remove(int id) {
        if (!nameById.containsKey(id)) {
            return false;
        }
        root = delete(root, id);
        nameById.remove(id);
        return true;
    }

    private Node delete(Node node, int id) {
        if (node == null) {
            return null;
        }
        if (id < node.id) {
            node.left = delete(node.left, id);
        } else if (id > node.id) {
            node.right = delete(node.right, id);
        } else {
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
            node.id = successor.id;
            node.right = delete(node.right, successor.id);
        }
        return node;
    }

    public java.util.List<Integer> idsBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        collect(root, low, high, result);
        return result;
    }

    private void collect(Node node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.id > low) {
            collect(node.left, low, high, result);
        }
        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }
        if (node.id < high) {
            collect(node.right, low, high, result);
        }
    }

    public int size() {
        return nameById.size();
    }
}