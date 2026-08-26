import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class UnitNode {
    String name;
    UnitNode left;
    UnitNode right;

    UnitNode(String name) {
        this.name = name;
    }
}

public class OrganizationTreeReport {
    static String findParent(UnitNode node, String target) {
        if (node == null || target == null) {
            return null;
        }
        if (node.left != null && node.left.name.equals(target)) {
            return node.name;
        }
        if (node.right != null && node.right.name.equals(target)) {
            return node.name;
        }
        String fromLeft = findParent(node.left, target);
        if (fromLeft != null) {
            return fromLeft;
        }
        return findParent(node.right, target);
    }

    static int findDepth(UnitNode node, String target) {
        if (node == null || target == null) {
            return -1;
        }
        if (node.name.equals(target)) {
            return 0;
        }
        int left = findDepth(node.left, target);
        if (left >= 0) {
            return left + 1;
        }
        int right = findDepth(node.right, target);
        if (right >= 0) {
            return right + 1;
        }
        return -1;
    }

    static List<String> pathFromRoot(UnitNode root, String target) {
        List<String> path = new ArrayList<>();
        collectPath(root, target, path);
        return path;
    }

    private static boolean collectPath(UnitNode node, String target,
                                       List<String> path) {
        if (node == null || target == null) {
            return false;
        }
        path.add(node.name);
        if (node.name.equals(target)) {
            return true;
        }
        if (collectPath(node.left, target, path)
                || collectPath(node.right, target, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    static void printByLevel(UnitNode root) {
        if (root == null) {
            System.out.println("  空組織");
            return;
        }
        Queue<UnitNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();
            System.out.print("  level " + level + ": ");
            for (int i = 0; i < count; i++) {
                UnitNode current = queue.poll();
                System.out.print(current.name + " ");
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
        UnitNode root = new UnitNode("HeadOffice");
        root.left = new UnitNode("Sales");
        root.right = new UnitNode("Technology");
        root.left.left = new UnitNode("Domestic");
        root.left.right = new UnitNode("Export");
        root.right.left = new UnitNode("Platform");
        root.right.right = new UnitNode("Support");

        System.out.println("組織層級：");
        printByLevel(root);

        System.out.println("findParent(Export)=" + findParent(root, "Export"));
        System.out.println("findParent(Sales)=" + findParent(root, "Sales"));
        System.out.println("findParent(HeadOffice)=" + findParent(root, "HeadOffice"));
        System.out.println("findParent(HR)=" + findParent(root, "HR"));

        System.out.println("findDepth(HeadOffice)=" + findDepth(root, "HeadOffice"));
        System.out.println("findDepth(Support)=" + findDepth(root, "Support"));
        System.out.println("findDepth(HR)=" + findDepth(root, "HR"));

        System.out.println("pathFromRoot(Support)=" + pathFromRoot(root, "Support"));
        System.out.println("pathFromRoot(Domestic)=" + pathFromRoot(root, "Domestic"));
        System.out.println("pathFromRoot(HR)=" + pathFromRoot(root, "HR"));

        System.out.println("空組織：");
        printByLevel(null);
        System.out.println("空組織 findParent=" + findParent(null, "Sales"));
        System.out.println("空組織 findDepth=" + findDepth(null, "Sales"));
        System.out.println("空組織 pathFromRoot=" + pathFromRoot(null, "Sales"));
    }
}
