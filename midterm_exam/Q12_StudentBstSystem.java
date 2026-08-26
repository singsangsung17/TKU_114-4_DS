package midterm_exam;

import java.util.ArrayList;
import java.util.List;

public class Q12_StudentBstSystem {
    public static class Student {
        private final int id;
        private final String name;
        private int score;

        public Student(int id, String name, int score) {
            if (id <= 0) {
                throw new IllegalArgumentException("id must be positive");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("name is invalid");
            }
            this.id = id;
            this.name = name;
            if (score < 0) {
                this.score = 0;
            } else if (score > 100) {
                this.score = 100;
            } else {
                this.score = score;
            }
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public String toString() {
            return id + "|" + name + "|" + score;
        }
    }

    private static class Node {
        Student student;
        Node left;
        Node right;

        Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    public boolean add(Student student) {
        if (student == null) {
            return false;
        }
        if (root == null) {
            root = new Node(student);
            return true;
        }
        Node current = root;
        while (true) {
            if (student.getId() == current.student.getId()) {
                return false;
            }
            if (student.getId() < current.student.getId()) {
                if (current.left == null) {
                    current.left = new Node(student);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node(student);
                    return true;
                }
                current = current.right;
            }
        }
    }

    public Student find(int id) {
        Node current = root;
        while (current != null) {
            if (id == current.student.getId()) {
                return current.student;
            }
            if (id < current.student.getId()) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public boolean updateScore(int id, int score) {
        Student student = find(id);
        if (student == null) {
            return false;
        }
        if (score < 0) {
            student.score = 0;
        } else if (score > 100) {
            student.score = 100;
        } else {
            student.score = score;
        }
        return true;
    }

    public boolean remove(int id) {
        if (find(id) == null) {
            return false;
        }
        root = removeHelper(root, id);
        return true;
    }

    private Node removeHelper(Node node, int id) {
        if (node == null) {
            return null;
        }
        if (id < node.student.getId()) {
            node.left = removeHelper(node.left, id);
            return node;
        }
        if (id > node.student.getId()) {
            node.right = removeHelper(node.right, id);
            return node;
        }
        if (node.left == null) {
            return node.right;
        }
        if (node.right == null) {
            return node.left;
        }
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        node.student = successor.student;
        node.right = removeHelper(node.right, successor.student.getId());
        return node;
    }

    public java.util.List<Student> studentsBetween(int lowId, int highId) {
        List<Student> result = new ArrayList<Student>();
        if (lowId > highId) {
            return result;
        }
        betweenHelper(root, lowId, highId, result);
        return result;
    }

    private void betweenHelper(Node node, int lowId, int highId, List<Student> result) {
        if (node == null) {
            return;
        }
        if (node.student.getId() > lowId) {
            betweenHelper(node.left, lowId, highId, result);
        }
        if (node.student.getId() >= lowId && node.student.getId() <= highId) {
            result.add(node.student);
        }
        if (node.student.getId() < highId) {
            betweenHelper(node.right, lowId, highId, result);
        }
    }

    public java.util.List<Student> inorder() {
        List<Student> result = new ArrayList<Student>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Student> result) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left, result);
        result.add(node.student);
        inorderHelper(node.right, result);
    }
}
