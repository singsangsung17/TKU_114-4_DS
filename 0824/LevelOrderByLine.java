import java.util.ArrayDeque;
import java.util.Queue;

class LineNode {
    String value;
    LineNode left;
    LineNode right;

    LineNode(String value) {
        this.value = value;
    }
}

public class LevelOrderByLine {
    static void printByLine(LineNode root) {
        if (root == null) {
            System.out.println("empty tree");
            return;
        }
        Queue<LineNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int count = queue.size();
            System.out.print("level " + level + " (" + count + "): ");
            for (int i = 0; i < count; i++) {
                LineNode current = queue.poll();
                System.out.print(current.value + " ");
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        LineNode root = new LineNode("A");
        root.left = new LineNode("B");
        root.right = new LineNode("C");
        root.left.left = new LineNode("D");
        root.left.right = new LineNode("E");
        root.right.right = new LineNode("F");
        root.left.left.left = new LineNode("G");

        printByLine(root);

        System.out.println("--- empty tree ---");
        printByLine(null);

        System.out.println("--- single node ---");
        printByLine(new LineNode("X"));
    }
}
