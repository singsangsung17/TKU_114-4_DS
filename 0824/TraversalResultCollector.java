import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class CollectNode {
    String value;
    CollectNode left;
    CollectNode right;

    CollectNode(String value) {
        this.value = value;
    }
}

public class TraversalResultCollector {
    static List<String> preorder(CollectNode root) {
        List<String> result = new ArrayList<>();
        collectPreorder(root, result);
        return result;
    }

    private static void collectPreorder(CollectNode node, List<String> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        collectPreorder(node.left, result);
        collectPreorder(node.right, result);
    }

    static List<String> inorder(CollectNode root) {
        List<String> result = new ArrayList<>();
        collectInorder(root, result);
        return result;
    }

    private static void collectInorder(CollectNode node, List<String> result) {
        if (node == null) {
            return;
        }
        collectInorder(node.left, result);
        result.add(node.value);
        collectInorder(node.right, result);
    }

    static List<String> postorder(CollectNode root) {
        List<String> result = new ArrayList<>();
        collectPostorder(root, result);
        return result;
    }

    private static void collectPostorder(CollectNode node, List<String> result) {
        if (node == null) {
            return;
        }
        collectPostorder(node.left, result);
        collectPostorder(node.right, result);
        result.add(node.value);
    }

    static List<String> levelOrder(CollectNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<CollectNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            CollectNode current = queue.poll();
            result.add(current.value);
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        return result;
    }

    static void report(String title, CollectNode root) {
        System.out.println(title);
        System.out.println("  preorder=" + preorder(root));
        System.out.println("  inorder=" + inorder(root));
        System.out.println("  postorder=" + postorder(root));
        System.out.println("  levelOrder=" + levelOrder(root));
    }

    public static void main(String[] args) {
        report("empty tree：", null);

        report("single node：", new CollectNode("X"));

        CollectNode skewed = new CollectNode("A");
        skewed.left = new CollectNode("B");
        skewed.left.left = new CollectNode("C");
        skewed.left.left.left = new CollectNode("D");
        report("left-skewed tree：", skewed);

        CollectNode complete = new CollectNode("A");
        complete.left = new CollectNode("B");
        complete.right = new CollectNode("C");
        complete.left.left = new CollectNode("D");
        complete.left.right = new CollectNode("E");
        complete.right.left = new CollectNode("F");
        complete.right.right = new CollectNode("G");
        report("complete tree：", complete);
    }
}
