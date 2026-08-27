class ScoreRecord {
    int score;
    String studentId;

    ScoreRecord(int score, String studentId) {
        this.score = score;
        this.studentId = studentId;
    }

    int compareKey(ScoreRecord other) {
        if (score != other.score) {
            return Integer.compare(score, other.score);
        }
        return studentId.compareTo(other.studentId);
    }

    @Override
    public String toString() {
        return score + "/" + studentId;
    }
}

class ScoreNode {
    ScoreRecord record;
    ScoreNode left;
    ScoreNode right;

    ScoreNode(ScoreRecord record) {
        this.record = record;
    }
}

class ScoreBst {
    private ScoreNode root;

    boolean add(ScoreRecord record) {
        if (record == null) {
            return false;
        }
        if (root == null) {
            root = new ScoreNode(record);
            return true;
        }
        ScoreNode current = root;
        while (true) {
            int compare = record.compareKey(current.record);
            if (compare == 0) {
                return false;
            }
            if (compare < 0) {
                if (current.left == null) {
                    current.left = new ScoreNode(record);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ScoreNode(record);
                    return true;
                }
                current = current.right;
            }
        }
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(ScoreNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.record + " ");
        inorder(node.right);
    }

    void printScoreRange(int low, int high) {
        System.out.print("score[" + low + "," + high + "]=");
        if (low > high) {
            System.out.println("無效範圍");
            return;
        }
        printScoreRange(root, low, high);
        System.out.println();
    }

    private void printScoreRange(ScoreNode node, int low, int high) {
        if (node == null) {
            return;
        }
        if (node.record.score >= low) {
            printScoreRange(node.left, low, high);
        }
        if (node.record.score >= low && node.record.score <= high) {
            System.out.print(node.record + " ");
        }
        if (node.record.score <= high) {
            printScoreRange(node.right, low, high);
        }
    }
}

public class ScoreRangeBst {
    public static void main(String[] args) {
        ScoreBst tree = new ScoreBst();
        System.out.println("加入 88/S104=" + tree.add(new ScoreRecord(88, "S104")));
        System.out.println("加入 72/S101=" + tree.add(new ScoreRecord(72, "S101")));
        System.out.println("加入 95/S102=" + tree.add(new ScoreRecord(95, "S102")));
        System.out.println("加入 88/S101=" + tree.add(new ScoreRecord(88, "S101")));
        System.out.println("加入 60/S105=" + tree.add(new ScoreRecord(60, "S105")));
        System.out.println("加入 72/S103=" + tree.add(new ScoreRecord(72, "S103")));
        System.out.println("重複 88/S104=" + tree.add(new ScoreRecord(88, "S104")));

        System.out.print("inorder=");
        tree.inorder();

        tree.printScoreRange(70, 90);
        tree.printScoreRange(88, 88);
        tree.printScoreRange(0, 100);
        tree.printScoreRange(96, 99);
        tree.printScoreRange(90, 70);
    }
}
