class CheckNode {
    int value;
    CheckNode left;
    CheckNode right;

    CheckNode(int value) {
        this.value = value;
    }
}

public class BstInvariantChecker {
    static boolean isValid(CheckNode node) {
        return isValid(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean isValid(CheckNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        if (node.value <= min || node.value >= max) {
            return false;
        }
        return isValid(node.left, min, node.value)
                && isValid(node.right, node.value, max);
    }

    public static void main(String[] args) {
        CheckNode valid = new CheckNode(50);
        valid.left = new CheckNode(30);
        valid.right = new CheckNode(70);
        valid.left.left = new CheckNode(20);
        valid.left.right = new CheckNode(40);
        valid.right.left = new CheckNode(60);
        valid.right.right = new CheckNode(80);
        System.out.println("valid tree=" + isValid(valid));

        CheckNode broken1 = new CheckNode(50);
        broken1.left = new CheckNode(30);
        broken1.right = new CheckNode(70);
        broken1.left.right = new CheckNode(55);
        System.out.println("違規一（左子樹深處大於 root）=" + isValid(broken1));

        CheckNode broken2 = new CheckNode(50);
        broken2.left = new CheckNode(30);
        broken2.right = new CheckNode(70);
        broken2.right.left = new CheckNode(45);
        System.out.println("違規二（右子樹深處小於 root）=" + isValid(broken2));

        CheckNode broken3 = new CheckNode(50);
        broken3.left = new CheckNode(30);
        broken3.left.left = new CheckNode(20);
        broken3.left.left.right = new CheckNode(35);
        System.out.println("違規三（第三層違反祖先邊界）=" + isValid(broken3));

        System.out.println("空樹=" + isValid(null));
    }
}
