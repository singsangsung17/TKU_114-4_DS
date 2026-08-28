class Course {
    String courseCode;
    String title;
    int credit;

    Course(String courseCode, String title, int credit) {
        this.courseCode = courseCode;
        this.title = title;
        this.credit = credit;
    }

    @Override
    public String toString() {
        return courseCode + " " + title + " credit=" + credit;
    }
}

class CourseNode {
    Course course;
    CourseNode left;
    CourseNode right;

    CourseNode(Course course) {
        this.course = course;
    }
}

class CourseBst {
    private CourseNode root;

    boolean add(Course course) {
        if (course == null || course.credit < 1 || course.credit > 6) {
            return false;
        }
        if (root == null) {
            root = new CourseNode(course);
            return true;
        }
        CourseNode current = root;
        while (true) {
            int compare = course.courseCode.compareTo(current.course.courseCode);
            if (compare == 0) {
                return false;
            }
            if (compare < 0) {
                if (current.left == null) {
                    current.left = new CourseNode(course);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new CourseNode(course);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Course find(String courseCode) {
        CourseNode current = root;
        while (current != null) {
            int compare = courseCode.compareTo(current.course.courseCode);
            if (compare == 0) {
                return current.course;
            }
            current = compare < 0 ? current.left : current.right;
        }
        return null;
    }

    boolean updateCredit(String courseCode, int credit) {
        Course course = find(courseCode);
        if (course == null || credit < 1 || credit > 6) {
            return false;
        }
        course.credit = credit;
        return true;
    }

    boolean remove(String courseCode) {
        if (find(courseCode) == null) {
            return false;
        }
        root = remove(root, courseCode);
        return true;
    }

    private CourseNode remove(CourseNode node, String courseCode) {
        if (node == null) {
            return null;
        }
        int compare = courseCode.compareTo(node.course.courseCode);
        if (compare < 0) {
            node.left = remove(node.left, courseCode);
        } else if (compare > 0) {
            node.right = remove(node.right, courseCode);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            CourseNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.course = successor.course;
            node.right = remove(node.right, successor.course.courseCode);
        }
        return node;
    }

    void printRange(String low, String high) {
        System.out.print("range[" + low + "," + high + "]=");
        if (low.compareTo(high) > 0) {
            System.out.println("無效範圍");
            return;
        }
        printRange(root, low, high);
        System.out.println();
    }

    private void printRange(CourseNode node, String low, String high) {
        if (node == null) {
            return;
        }
        if (node.course.courseCode.compareTo(low) > 0) {
            printRange(node.left, low, high);
        }
        if (node.course.courseCode.compareTo(low) >= 0
                && node.course.courseCode.compareTo(high) <= 0) {
            System.out.print(node.course.courseCode + " ");
        }
        if (node.course.courseCode.compareTo(high) < 0) {
            printRange(node.right, low, high);
        }
    }

    void report() {
        report(root);
    }

    private void report(CourseNode node) {
        if (node == null) {
            return;
        }
        report(node.left);
        System.out.println("  " + node.course);
        report(node.right);
    }
}

public class CourseBstIndex {
    public static void main(String[] args) {
        CourseBst index = new CourseBst();

        System.out.println("加入 CS301=" + index.add(new Course("CS301", "Data Structure", 3)));
        System.out.println("加入 CS101=" + index.add(new Course("CS101", "Programming", 3)));
        System.out.println("加入 IM501=" + index.add(new Course("IM501", "Database", 3)));
        System.out.println("加入 CS201=" + index.add(new Course("CS201", "Java Lab", 2)));
        System.out.println("加入 IM301=" + index.add(new Course("IM301", "Networks", 3)));
        System.out.println("重複 CS301=" + index.add(new Course("CS301", "DS2", 3)));
        System.out.println("credit 為 0=" + index.add(new Course("CS999", "Seminar", 0)));
        System.out.println("credit 為 7=" + index.add(new Course("CS998", "Workshop", 7)));

        System.out.println("排序報表：");
        index.report();

        System.out.println("查詢 IM301=" + index.find("IM301"));
        System.out.println("查詢 XX999=" + index.find("XX999"));

        System.out.println("更新 CS201 credit=" + index.updateCredit("CS201", 4));
        System.out.println("更新為 0=" + index.updateCredit("CS201", 0));
        System.out.println("更新不存在=" + index.updateCredit("XX999", 3));
        System.out.println("查詢 CS201=" + index.find("CS201"));

        index.printRange("CS101", "CS301");
        index.printRange("CS000", "ZZ999");
        index.printRange("IM999", "IM100");

        System.out.println("刪除 CS101=" + index.remove("CS101"));
        System.out.println("刪除 CS301=" + index.remove("CS301"));
        System.out.println("刪除 XX999=" + index.remove("XX999"));

        System.out.println("排序報表：");
        index.report();
    }
}
