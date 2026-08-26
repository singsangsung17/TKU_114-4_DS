import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class TestNode {
    String value;
    TestNode left;
    TestNode right;

    TestNode(String value) {
        this.value = value;
    }
}

public class TraversalTestReport {
    static List<String> preorder(TestNode root) {
        List<String> result = new ArrayList<>();
        collectPreorder(root, result);
        return result;
    }

    private static void collectPreorder(TestNode node, List<String> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        collectPreorder(node.left, result);
        collectPreorder(node.right, result);
    }

    static List<String> inorder(TestNode root) {
        List<String> result = new ArrayList<>();
        collectInorder(root, result);
        return result;
    }

    private static void collectInorder(TestNode node, List<String> result) {
        if (node == null) {
            return;
        }
        collectInorder(node.left, result);
        result.add(node.value);
        collectInorder(node.right, result);
    }

    static List<String> postorder(TestNode root) {
        List<String> result = new ArrayList<>();
        collectPostorder(root, result);
        return result;
    }

    private static void collectPostorder(TestNode node, List<String> result) {
        if (node == null) {
            return;
        }
        collectPostorder(node.left, result);
        collectPostorder(node.right, result);
        result.add(node.value);
    }

    static List<String> levelOrder(TestNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TestNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TestNode current = queue.poll();
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

    static void check(String name, List<String> expected, List<String> actual) {
        System.out.println("  " + name);
        System.out.println("    預期=" + expected);
        System.out.println("    實際=" + actual);
        System.out.println("    相同=" + expected.equals(actual));
    }

    static void report(String title, TestNode root, List<String> expectedPre,
                       List<String> expectedIn, List<String> expectedPost,
                       List<String> expectedLevel) {
        System.out.println(title);
        check("preorder", expectedPre, preorder(root));
        check("inorder", expectedIn, inorder(root));
        check("postorder", expectedPost, postorder(root));
        check("levelOrder", expectedLevel, levelOrder(root));
    }

    public static void main(String[] args) {
        report("empty tree：", null,
                List.of(), List.of(), List.of(), List.of());

        report("single node：", new TestNode("X"),
                List.of("X"), List.of("X"), List.of("X"), List.of("X"));

        TestNode onlyLeft = new TestNode("A");
        onlyLeft.left = new TestNode("B");
        onlyLeft.left.left = new TestNode("C");
        report("only-left tree：", onlyLeft,
                List.of("A", "B", "C"), List.of("C", "B", "A"),
                List.of("C", "B", "A"), List.of("A", "B", "C"));

        TestNode onlyRight = new TestNode("A");
        onlyRight.right = new TestNode("B");
        onlyRight.right.right = new TestNode("C");
        report("only-right tree：", onlyRight,
                List.of("A", "B", "C"), List.of("A", "B", "C"),
                List.of("C", "B", "A"), List.of("A", "B", "C"));

        TestNode complete = new TestNode("A");
        complete.left = new TestNode("B");
        complete.right = new TestNode("C");
        complete.left.left = new TestNode("D");
        complete.left.right = new TestNode("E");
        complete.right.left = new TestNode("F");
        complete.right.right = new TestNode("G");
        report("complete tree：", complete,
                List.of("A", "B", "D", "E", "C", "F", "G"),
                List.of("D", "B", "E", "A", "F", "C", "G"),
                List.of("D", "E", "B", "F", "G", "C", "A"),
                List.of("A", "B", "C", "D", "E", "F", "G"));

        TestNode irregular = new TestNode("M");
        irregular.left = new TestNode("F");
        irregular.right = new TestNode("T");
        irregular.left.left = new TestNode("B");
        irregular.right.right = new TestNode("Z");
        irregular.left.left.right = new TestNode("D");
        report("irregular tree：", irregular,
                List.of("M", "F", "B", "D", "T", "Z"),
                List.of("B", "D", "F", "M", "T", "Z"),
                List.of("D", "B", "F", "Z", "T", "M"),
                List.of("M", "F", "T", "B", "Z", "D"));
    }
}
