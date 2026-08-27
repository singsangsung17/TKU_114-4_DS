class Student {
    String studentId;
    String name;

    Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public String toString() {
        return studentId + " " + name;
    }
}

class StudentNode {
    Student student;
    StudentNode left;
    StudentNode right;

    StudentNode(Student student) {
        this.student = student;
    }
}

class StudentBst {
    private StudentNode root;

    boolean add(Student student) {
        if (student == null) {
            return false;
        }
        if (root == null) {
            root = new StudentNode(student);
            return true;
        }
        StudentNode current = root;
        while (true) {
            int compare = student.studentId.compareTo(current.student.studentId);
            if (compare == 0) {
                return false;
            }
            if (compare < 0) {
                if (current.left == null) {
                    current.left = new StudentNode(student);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new StudentNode(student);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Student search(String studentId) {
        StudentNode current = root;
        while (current != null) {
            int compare = studentId.compareTo(current.student.studentId);
            if (compare == 0) {
                return current.student;
            }
            current = compare < 0 ? current.left : current.right;
        }
        return null;
    }

    boolean remove(String studentId) {
        if (search(studentId) == null) {
            return false;
        }
        root = remove(root, studentId);
        return true;
    }

    private StudentNode remove(StudentNode node, String studentId) {
        if (node == null) {
            return null;
        }
        int compare = studentId.compareTo(node.student.studentId);
        if (compare < 0) {
            node.left = remove(node.left, studentId);
        } else if (compare > 0) {
            node.right = remove(node.right, studentId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            StudentNode successor = minimumNode(node.right);
            node.student = successor.student;
            node.right = remove(node.right, successor.student.studentId);
        }
        return node;
    }

    private StudentNode minimumNode(StudentNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    int size() {
        return size(root);
    }

    private int size(StudentNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(StudentNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.student + " | ");
        inorder(node.right);
    }
}

public class StudentBstIndex {
    public static void main(String[] args) {
        StudentBst index = new StudentBst();

        System.out.println("加入 S105=" + index.add(new Student("S105", "Eva")));
        System.out.println("加入 S101=" + index.add(new Student("S101", "Amy")));
        System.out.println("加入 S108=" + index.add(new Student("S108", "Finn")));
        System.out.println("加入 S103=" + index.add(new Student("S103", "Cara")));
        System.out.println("加入 S107=" + index.add(new Student("S107", "Dan")));
        System.out.println("重複 S103=" + index.add(new Student("S103", "Cara2")));
        System.out.println("加入 null=" + index.add(null));

        System.out.print("inorder=");
        index.inorder();
        System.out.println("size=" + index.size());

        System.out.println("查詢 S103=" + index.search("S103"));
        System.out.println("查詢 S999=" + index.search("S999"));

        System.out.println("刪除 leaf S101=" + index.remove("S101"));
        System.out.print("inorder=");
        index.inorder();

        System.out.println("刪除 two-child S105=" + index.remove("S105"));
        System.out.print("inorder=");
        index.inorder();

        System.out.println("刪除不存在 S999=" + index.remove("S999"));
        System.out.println("size=" + index.size());
    }
}
