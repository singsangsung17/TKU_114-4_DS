class FolderNode {
    String name;
    int ownSize;
    FolderNode left;
    FolderNode right;

    FolderNode(String name, int ownSize) {
        this.name = name;
        this.ownSize = ownSize;
    }
}

public class FolderSizeTree {
    static int subtreeSize(FolderNode node) {
        if (node == null) {
            return 0;
        }
        int leftSize = subtreeSize(node.left);
        int rightSize = subtreeSize(node.right);
        return leftSize + rightSize + node.ownSize;
    }

    static FolderNode largestSubtree(FolderNode node) {
        if (node == null) {
            return null;
        }
        FolderNode largest = node;
        FolderNode leftLargest = largestSubtree(node.left);
        FolderNode rightLargest = largestSubtree(node.right);
        if (leftLargest != null
                && subtreeSize(leftLargest) > subtreeSize(largest)) {
            largest = leftLargest;
        }
        if (rightLargest != null
                && subtreeSize(rightLargest) > subtreeSize(largest)) {
            largest = rightLargest;
        }
        return largest;
    }

    static void printLeafFolders(FolderNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            System.out.println("  " + node.name + " " + node.ownSize);
            return;
        }
        printLeafFolders(node.left);
        printLeafFolders(node.right);
    }

    public static void main(String[] args) {
        FolderNode root = new FolderNode("root", 10);
        root.left = new FolderNode("documents", 20);
        root.right = new FolderNode("media", 5);
        root.left.left = new FolderNode("reports", 100);
        root.left.right = new FolderNode("notes", 30);
        root.right.right = new FolderNode("videos", 500);
        root.left.left.left = new FolderNode("2024", 60);

        System.out.println("總大小=" + subtreeSize(root));

        FolderNode largestChild = largestSubtree(root.left);
        FolderNode rightLargest = largestSubtree(root.right);
        if (subtreeSize(rightLargest) > subtreeSize(largestChild)) {
            largestChild = rightLargest;
        }
        System.out.println("最大 subtree（不含 root）=" + largestChild.name
                + " " + subtreeSize(largestChild));

        System.out.println("leaf folder：");
        printLeafFolders(root);

        System.out.println("documents subtree=" + subtreeSize(root.left));
        System.out.println("media subtree=" + subtreeSize(root.right));
        System.out.println("空目錄 subtree=" + subtreeSize(null));
    }
}
