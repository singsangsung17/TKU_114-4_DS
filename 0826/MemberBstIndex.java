class Member {
    String memberId;
    String name;
    String email;

    Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return memberId + " " + name + " " + email;
    }
}

class MemberNode {
    Member member;
    MemberNode left;
    MemberNode right;

    MemberNode(Member member) {
        this.member = member;
    }
}

class MemberBst {
    private MemberNode root;

    boolean add(Member member) {
        if (member == null || member.email == null || member.email.isBlank()) {
            return false;
        }
        if (root == null) {
            root = new MemberNode(member);
            return true;
        }
        MemberNode current = root;
        while (true) {
            int compare = member.memberId.compareTo(current.member.memberId);
            if (compare == 0) {
                return false;
            }
            if (compare < 0) {
                if (current.left == null) {
                    current.left = new MemberNode(member);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new MemberNode(member);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Member find(String memberId) {
        MemberNode current = root;
        while (current != null) {
            int compare = memberId.compareTo(current.member.memberId);
            if (compare == 0) {
                return current.member;
            }
            current = compare < 0 ? current.left : current.right;
        }
        return null;
    }

    boolean updateEmail(String memberId, String email) {
        Member member = find(memberId);
        if (member == null || email == null || email.isBlank()) {
            return false;
        }
        member.email = email.trim();
        return true;
    }

    boolean remove(String memberId) {
        if (find(memberId) == null) {
            return false;
        }
        root = remove(root, memberId);
        return true;
    }

    private MemberNode remove(MemberNode node, String memberId) {
        if (node == null) {
            return null;
        }
        int compare = memberId.compareTo(node.member.memberId);
        if (compare < 0) {
            node.left = remove(node.left, memberId);
        } else if (compare > 0) {
            node.right = remove(node.right, memberId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            MemberNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.member = successor.member;
            node.right = remove(node.right, successor.member.memberId);
        }
        return node;
    }

    void report() {
        report(root);
    }

    private void report(MemberNode node) {
        if (node == null) {
            return;
        }
        report(node.left);
        System.out.println("  " + node.member);
        report(node.right);
    }
}

public class MemberBstIndex {
    public static void main(String[] args) {
        MemberBst index = new MemberBst();

        System.out.println("加入 M300=" + index.add(new Member("M300", "Amy", "amy@mail.com")));
        System.out.println("加入 M100=" + index.add(new Member("M100", "Ben", "ben@mail.com")));
        System.out.println("加入 M500=" + index.add(new Member("M500", "Cara", "cara@mail.com")));
        System.out.println("加入 M200=" + index.add(new Member("M200", "Dan", "dan@mail.com")));
        System.out.println("重複 M300=" + index.add(new Member("M300", "Amy2", "amy2@mail.com")));
        System.out.println("email 空白=" + index.add(new Member("M400", "Eva", "   ")));
        System.out.println("email 為 null=" + index.add(new Member("M600", "Finn", null)));

        System.out.println("report：");
        index.report();

        System.out.println("查詢 M200=" + index.find("M200"));
        System.out.println("查詢 M999=" + index.find("M999"));

        System.out.println("更新 M200 email=" + index.updateEmail("M200", "dan.new@mail.com"));
        System.out.println("更新為空白=" + index.updateEmail("M200", "   "));
        System.out.println("更新不存在=" + index.updateEmail("M999", "x@mail.com"));
        System.out.println("查詢 M200=" + index.find("M200"));

        System.out.println("刪除 M100=" + index.remove("M100"));
        System.out.println("刪除 M300=" + index.remove("M300"));
        System.out.println("刪除 M999=" + index.remove("M999"));

        System.out.println("report：");
        index.report();
    }
}
