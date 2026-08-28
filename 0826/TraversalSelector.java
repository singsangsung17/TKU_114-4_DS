class ExpressionNode {
    String value;
    ExpressionNode left;
    ExpressionNode right;

    ExpressionNode(String value) {
        this.value = value;
    }
}

public class TraversalSelector {
    static String prefix(ExpressionNode node) {
        if (node == null) {
            return "";
        }
        if (node.left == null && node.right == null) {
            return node.value;
        }
        return node.value + " " + prefix(node.left) + " " + prefix(node.right);
    }

    static String infix(ExpressionNode node) {
        if (node == null) {
            return "";
        }
        if (node.left == null && node.right == null) {
            return node.value;
        }
        return "(" + infix(node.left) + " " + node.value + " "
                + infix(node.right) + ")";
    }

    static String postfix(ExpressionNode node) {
        if (node == null) {
            return "";
        }
        if (node.left == null && node.right == null) {
            return node.value;
        }
        return postfix(node.left) + " " + postfix(node.right) + " " + node.value;
    }

    public static void main(String[] args) {
        ExpressionNode root = new ExpressionNode("*");
        root.left = new ExpressionNode("+");
        root.right = new ExpressionNode("-");
        root.left.left = new ExpressionNode("3");
        root.left.right = new ExpressionNode("4");
        root.right.left = new ExpressionNode("10");
        root.right.right = new ExpressionNode("6");

        System.out.println("prefix=" + prefix(root));
        System.out.println("infix=" + infix(root));
        System.out.println("postfix=" + postfix(root));

        ExpressionNode singleValue = new ExpressionNode("7");
        System.out.println("單一節點 prefix=" + prefix(singleValue));
        System.out.println("單一節點 infix=" + infix(singleValue));
        System.out.println("單一節點 postfix=" + postfix(singleValue));
    }
}
