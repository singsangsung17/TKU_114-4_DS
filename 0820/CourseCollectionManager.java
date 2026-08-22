import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class StudentRecord {
    private final String studentId;
    private final String name;
    private int score;
    private final Set<String> tags = new LinkedHashSet<>();

    StudentRecord(String studentId, String name, int score) {
        this.studentId = studentId == null || studentId.isBlank()
                ? "UNKNOWN" : studentId.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        this.score = clamp(score);
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }

    String getStudentId() {
        return studentId;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = clamp(score);
    }

    void addTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            tags.add(tag.trim().toLowerCase());
        }
    }

    boolean hasTag(String tag) {
        return tag != null && !tag.isBlank()
                && tags.contains(tag.trim().toLowerCase());
    }

    String getLevel() {
        if (score >= 90) {
            return "A";
        }
        if (score >= 80) {
            return "B";
        }
        if (score >= 70) {
            return "C";
        }
        if (score >= 60) {
            return "D";
        }
        return "F";
    }

    @Override
    public String toString() {
        return studentId + " " + name + " score=" + score
                + " level=" + getLevel() + " tags=" + tags;
    }
}

class CourseBook {
    private final List<StudentRecord> order = new ArrayList<>();
    private final Set<String> registeredIds = new HashSet<>();
    private final Map<String, StudentRecord> byId = new HashMap<>();

    boolean enroll(StudentRecord record) {
        if (record == null || !registeredIds.add(record.getStudentId())) {
            return false;
        }
        order.add(record);
        byId.put(record.getStudentId(), record);
        return true;
    }

    StudentRecord find(String studentId) {
        return byId.get(studentId);
    }

    boolean updateScore(String studentId, int score) {
        StudentRecord record = byId.get(studentId);
        if (record == null) {
            return false;
        }
        record.setScore(score);
        return true;
    }

    List<StudentRecord> findByTag(String tag) {
        List<StudentRecord> result = new ArrayList<>();
        for (StudentRecord record : order) {
            if (record.hasTag(tag)) {
                result.add(record);
            }
        }
        return result;
    }

    Map<String, Integer> scoreDistribution() {
        Map<String, Integer> distribution = new LinkedHashMap<>();
        distribution.put("A", 0);
        distribution.put("B", 0);
        distribution.put("C", 0);
        distribution.put("D", 0);
        distribution.put("F", 0);
        for (StudentRecord record : order) {
            String level = record.getLevel();
            distribution.put(level, distribution.get(level) + 1);
        }
        return distribution;
    }

    List<StudentRecord> top(int count) {
        List<StudentRecord> ranking = new ArrayList<>(order);
        ranking.sort(Comparator.comparingInt(StudentRecord::getScore)
                .reversed()
                .thenComparing(StudentRecord::getStudentId));
        if (count <= 0) {
            return new ArrayList<>();
        }
        if (count >= ranking.size()) {
            return ranking;
        }
        return new ArrayList<>(ranking.subList(0, count));
    }

    void removeBelow(int minimum) {
        order.removeIf(record -> record.getScore() < minimum);
        registeredIds.clear();
        byId.clear();
        for (StudentRecord record : order) {
            registeredIds.add(record.getStudentId());
            byId.put(record.getStudentId(), record);
        }
    }

    int size() {
        return order.size();
    }

    boolean isConsistent() {
        return order.size() == registeredIds.size()
                && order.size() == byId.size();
    }

    void printState(String title) {
        System.out.println(title);
        System.out.println("  List=" + order.size()
                + " Set=" + registeredIds.size()
                + " Map=" + byId.size());
        for (StudentRecord record : order) {
            System.out.println("  " + record);
        }
    }
}

public class CourseCollectionManager {
    public static void main(String[] args) {
        CourseBook book = new CourseBook();

        StudentRecord amy = new StudentRecord("S101", "Amy", 88);
        StudentRecord ben = new StudentRecord("S102", "Ben", 55);
        StudentRecord cara = new StudentRecord("S103", "Cara", 92);
        StudentRecord dan = new StudentRecord("S104", "Dan", 88);
        StudentRecord eva = new StudentRecord("S105", "Eva", 47);
        StudentRecord finn = new StudentRecord("S106", "Finn", 73);

        amy.addTag("Java");
        amy.addTag("java");
        ben.addTag("   ");
        cara.addTag("Tree");
        dan.addTag("java");
        eva.addTag(null);
        finn.addTag("Graph");

        System.out.println("報名 Amy：" + book.enroll(amy));
        System.out.println("報名 Ben：" + book.enroll(ben));
        System.out.println("報名 Cara：" + book.enroll(cara));
        System.out.println("報名 Dan：" + book.enroll(dan));
        System.out.println("報名 Eva：" + book.enroll(eva));
        System.out.println("報名 Finn：" + book.enroll(finn));
        System.out.println("重複學號 S101：" + book.enroll(
                new StudentRecord("S101", "Amy2", 100)));
        System.out.println("報名 null：" + book.enroll(null));

        book.printState("初始狀態：");

        System.out.println("更新 S102 成績：" + book.updateScore("S102", 66));
        System.out.println("更新 S999 成績：" + book.updateScore("S999", 80));
        System.out.println("成績超出範圍：" + book.updateScore("S103", 150));
        System.out.println("S102 更新後：" + book.find("S102"));
        System.out.println("S103 更新後：" + book.find("S103"));

        System.out.println("tag=java：" + book.findByTag("java"));
        System.out.println("tag=JAVA：" + book.findByTag("JAVA"));
        System.out.println("tag=stack：" + book.findByTag("stack"));
        System.out.println("tag 空白：" + book.findByTag("   "));

        System.out.println("等第分布：" + book.scoreDistribution());

        System.out.println("前 3 名：" + book.top(3));
        System.out.println("前 99 名（超過人數）：" + book.top(99));
        System.out.println("前 0 名：" + book.top(0));

        book.removeBelow(60);
        book.printState("removeBelow(60) 之後：");
        System.out.println("被刪除的 S105 是否還查得到："
                + (book.find("S105") != null));
        System.out.println("三種容器筆數是否一致：" + book.isConsistent());
    }
}
