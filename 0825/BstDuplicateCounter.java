class CounterNode {
    int key;
    int count;
    CounterNode left;
    CounterNode right;

    CounterNode(int key) {
        this.key = key;
        this.count = 1;
    }
}

class CounterBst {
    private CounterNode root;

    void add(int key) {
        if (root == null) {
            root = new CounterNode(key);
            return;
        }
        CounterNode current = root;
        while (true) {
            if (key == current.key) {
                current.count++;
                return;
            }
            if (key < current.key) {
                if (current.left == null) {
                    current.left = new CounterNode(key);
                    return;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new CounterNode(key);
                    return;
                }
                current = current.right;
            }
        }
    }

    int nodeCount() {
        return nodeCount(root);
    }

    private int nodeCount(CounterNode node) {
        return node == null ? 0 : 1 + nodeCount(node.left) + nodeCount(node.right);
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(CounterNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.key + "(" + node.count + ") ");
        inorder(node.right);
    }
}

public class BstDuplicateCounter {
    public static void main(String[] args) {
        CounterBst tree = new CounterBst();
        for (int key : new int[]{50, 30, 70, 30, 20, 50, 50, 80, 20}) {
            tree.add(key);
        }

        System.out.print("inorder=");
        tree.inorder();
        System.out.println("node 數=" + tree.nodeCount());
    }
}
