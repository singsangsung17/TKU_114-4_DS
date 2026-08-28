class FileSystemNode {
    String name;
    boolean directory;
    int ownSize;
    FileSystemNode left;
    FileSystemNode right;

    FileSystemNode(String name, boolean directory, int ownSize) {
        this.name = name;
        this.directory = directory;
        this.ownSize = ownSize;
    }
}

public class DirectoryTreeReport {
    static int totalSize(FileSystemNode node) {
        if (node == null) {
            return 0;
        }
        int leftSize = totalSize(node.left);
        int rightSize = totalSize(node.right);
        return leftSize + rightSize + node.ownSize;
    }

    static int totalNodes(FileSystemNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + totalNodes(node.left) + totalNodes(node.right);
    }

    static int fileCount(FileSystemNode node) {
        if (node == null) {
            return 0;
        }
        int current = node.directory ? 0 : 1;
        return current + fileCount(node.left) + fileCount(node.right);
    }

    static int directoryCount(FileSystemNode node) {
        if (node == null) {
            return 0;
        }
        int current = node.directory ? 1 : 0;
        return current + directoryCount(node.left) + directoryCount(node.right);
    }

    static int height(FileSystemNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static FileSystemNode largestFile(FileSystemNode node) {
        if (node == null) {
            return null;
        }
        FileSystemNode largest = node.directory ? null : node;
        FileSystemNode leftLargest = largestFile(node.left);
        FileSystemNode rightLargest = largestFile(node.right);
        if (leftLargest != null
                && (largest == null || leftLargest.ownSize > largest.ownSize)) {
            largest = leftLargest;
        }
        if (rightLargest != null
                && (largest == null || rightLargest.ownSize > largest.ownSize)) {
            largest = rightLargest;
        }
        return largest;
    }

    static void printDirectorySizes(FileSystemNode node) {
        if (node == null) {
            return;
        }
        printDirectorySizes(node.left);
        printDirectorySizes(node.right);
        if (node.directory) {
            System.out.println("  " + node.name + " 總容量=" + totalSize(node));
        }
    }

    public static void main(String[] args) {
        FileSystemNode root = new FileSystemNode("root", true, 0);
        root.left = new FileSystemNode("documents", true, 0);
        root.right = new FileSystemNode("media", true, 0);
        root.left.left = new FileSystemNode("report.pdf", false, 120);
        root.left.right = new FileSystemNode("notes.txt", false, 15);
        root.right.left = new FileSystemNode("photos", true, 0);
        root.right.right = new FileSystemNode("movie.mp4", false, 900);
        root.right.left.left = new FileSystemNode("cover.png", false, 240);

        System.out.println("每個 directory 的總容量（postorder）：");
        printDirectorySizes(root);

        System.out.println("total node=" + totalNodes(root));
        System.out.println("file count=" + fileCount(root));
        System.out.println("directory count=" + directoryCount(root));
        System.out.println("height=" + height(root));

        FileSystemNode largest = largestFile(root);
        System.out.println("最大檔案=" + largest.name + " " + largest.ownSize);

        System.out.println("空樹 total node=" + totalNodes(null));
        System.out.println("空樹 height=" + height(null));
        System.out.println("空樹最大檔案=" + largestFile(null));
    }
}
