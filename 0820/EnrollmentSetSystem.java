import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Enrollment {
    private final String studentId;
    private final String courseCode;
    private final String studentName;

    Enrollment(String studentId, String courseCode, String studentName) {
        this.studentId = studentId == null || studentId.isBlank()
                ? "UNKNOWN" : studentId.trim();
        this.courseCode = courseCode == null || courseCode.isBlank()
                ? "UNKNOWN" : courseCode.trim();
        this.studentName = studentName == null || studentName.isBlank()
                ? "Unknown" : studentName.trim();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Enrollment enrollment)) {
            return false;
        }
        return Objects.equals(studentId, enrollment.studentId)
                && Objects.equals(courseCode, enrollment.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return studentId + " " + studentName + " @" + courseCode;
    }
}

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        Set<Enrollment> enrollments = new HashSet<>();

        System.out.println("S101 加選 CS201："
                + enrollments.add(new Enrollment("S101", "CS201", "Amy")));
        System.out.println("S101 加選 CS202："
                + enrollments.add(new Enrollment("S101", "CS202", "Amy")));
        System.out.println("S102 加選 CS201："
                + enrollments.add(new Enrollment("S102", "CS201", "Ben")));
        System.out.println("S101 重複加選 CS201："
                + enrollments.add(new Enrollment("S101", "CS201", "Amy Chen")));
        System.out.println("目前筆數：" + enrollments.size());

        Enrollment probe = new Enrollment("S101", "CS201", "完全不同的名字");
        System.out.println("以新物件查詢是否已報名："
                + enrollments.contains(probe));
        System.out.println("查詢未報名的組合："
                + enrollments.contains(new Enrollment("S102", "CS202", "Ben")));

        System.out.println("以新物件退選 S101 CS201："
                + enrollments.remove(probe));
        System.out.println("再次退選同一筆："
                + enrollments.remove(probe));
        System.out.println("退選後筆數：" + enrollments.size());

        System.out.println("兩個身分相同的物件 equals："
                + new Enrollment("S102", "CS201", "Ben").equals(
                        new Enrollment("S102", "CS201", "Ben2")));
        System.out.println("兩個身分相同的物件 hashCode 一致："
                + (new Enrollment("S102", "CS201", "Ben").hashCode()
                        == new Enrollment("S102", "CS201", "Ben2").hashCode()));

        System.out.println("剩餘報名資料：");
        for (Enrollment enrollment : enrollments) {
            System.out.println("  " + enrollment);
        }
    }
}
