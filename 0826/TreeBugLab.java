import java.util.ArrayList;
import java.util.List;

class BugNode {
    int value;
    BugNode left;
    BugNode right;

    BugNode(int value) {
        this.value = value;
    }
}

public class TreeBugLab {
    static BugNode buildTree() {
        BugNode root = new BugNode(50);
        root.left = new BugNode(30);
        root.right = new BugNode(70);
        root.left.left = new BugNode(20);
        root.left.right = new BugNode(40);
        root.right.left = new BugNode(60);
        return root;
    }

    static boolean brokenSearch(BugNode node, int value) {
        if (node == null) {
            return false;
        }
        if (node.value == value) {
            return true;
        }
        if (value < node.value) {
            return brokenSearch(node.right, value);
        }
        return brokenSearch(node.left, value);
    }

    static boolean fixedSearch(BugNode node, int value) {
        if (node == null) {
            return false;
        }
        if (node.value == value) {
            return true;
        }
        if (value < node.value) {
            return fixedSearch(node.left, value);
        }
        return fixedSearch(node.right, value);
    }

    static void brokenInorder(BugNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        brokenInorder(node.left, result);
        brokenInorder(node.right, result);
    }

    static void fixedInorder(BugNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        fixedInorder(node.left, result);
        result.add(node.value);
        fixedInorder(node.right, result);
    }

    static BugNode brokenRemove(BugNode node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = brokenRemove(node.left, value);
        } else if (value > node.value) {
            node.right = brokenRemove(node.right, value);
        } else {
            return null;
        }
        return node;
    }

    static BugNode fixedRemove(BugNode node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = fixedRemove(node.left, value);
        } else if (value > node.value) {
            node.right = fixedRemove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            BugNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.value = successor.value;
            node.right = fixedRemove(node.right, successor.value);
        }
        return node;
    }

    static boolean brokenValidate(BugNode node) {
        if (node == null) {
            return true;
        }
        if (node.left != null && node.left.value >= node.value) {
            return false;
        }
        if (node.right != null && node.right.value <= node.value) {
            return false;
        }
        return brokenValidate(node.left) && brokenValidate(node.right);
    }

    static boolean fixedValidate(BugNode node) {
        return fixedValidate(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean fixedValidate(BugNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        if (node.value <= min || node.value >= max) {
            return false;
        }
        return fixedValidate(node.left, min, node.value)
                && fixedValidate(node.right, node.value, max);
    }

    static List<Integer> toList(BugNode node) {
        List<Integer> result = new ArrayList<>();
        fixedInorder(node, result);
        return result;
    }

    public static void main(String[] args) {
        BugNode tree = buildTree();

        System.out.println("錯誤一：search 方向相反");
        System.out.println("  broken search(20)=" + brokenSearch(tree, 20));
        System.out.println("  fixed search(20)=" + fixedSearch(tree, 20));

        System.out.println("錯誤二：inorder 順序錯誤");
        List<Integer> brokenOrder = new ArrayList<>();
        brokenInorder(tree, brokenOrder);
        List<Integer> fixedOrder = new ArrayList<>();
        fixedInorder(tree, fixedOrder);
        System.out.println("  broken inorder=" + brokenOrder);
        System.out.println("  fixed inorder=" + fixedOrder);

        System.out.println("錯誤三：delete 遺失 child");
        BugNode brokenTree = buildTree();
        brokenTree = brokenRemove(brokenTree, 70);
        System.out.println("  broken remove(70)=" + toList(brokenTree));
        BugNode fixedTree = buildTree();
        fixedTree = fixedRemove(fixedTree, 70);
        System.out.println("  fixed remove(70)=" + toList(fixedTree));

        System.out.println("錯誤四：validation 只檢查直接 child");
        BugNode invalid = new BugNode(50);
        invalid.left = new BugNode(30);
        invalid.right = new BugNode(70);
        invalid.left.right = new BugNode(55);
        System.out.println("  broken validate=" + brokenValidate(invalid));
        System.out.println("  fixed validate=" + fixedValidate(invalid));
    }
}
