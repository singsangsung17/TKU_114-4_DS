import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class EnrollmentConflictSet {
    record EnrollmentKey(String studentId, String courseId) {
        EnrollmentKey {
            if (studentId == null || studentId.isBlank()) throw new IllegalArgumentException("studentId");
            if (courseId == null || courseId.isBlank()) throw new IllegalArgumentException("courseId");
            studentId = studentId.trim().toUpperCase();
            courseId = courseId.trim().toUpperCase();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof EnrollmentKey key)) return false;
            return studentId.equals(key.studentId) && courseId.equals(key.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseId);
        }
    }

    static List<EnrollmentKey> duplicates(List<EnrollmentKey> records) {
        Set<EnrollmentKey> seen = new HashSet<>();
        Set<EnrollmentKey> reported = new HashSet<>();
        List<EnrollmentKey> duplicates = new ArrayList<>();
        for (EnrollmentKey key : records) {
            if (!seen.add(key) && reported.add(key)) duplicates.add(key);
        }
        return duplicates;
    }

    static Map<String, Set<String>> coursesByStudent(List<EnrollmentKey> records) {
        Map<String, Set<String>> map = new HashMap<>();
        for (EnrollmentKey key : records) {
            map.computeIfAbsent(key.studentId(), id -> new HashSet<>()).add(key.courseId());
        }
        return map;
    }

    static Map<String, Integer> studentCountByCourse(List<EnrollmentKey> records) {
        Map<String, Set<String>> students = new HashMap<>();
        for (EnrollmentKey key : records) {
            students.computeIfAbsent(key.courseId(), id -> new HashSet<>()).add(key.studentId());
        }
        Map<String, Integer> counts = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : students.entrySet()) {
            counts.put(entry.getKey(), entry.getValue().size());
        }
        return counts;
    }

    public static void main(String[] args) {
        List<EnrollmentKey> records = List.of(
                new EnrollmentKey("412001", "IM101"),
                new EnrollmentKey("412001", "CS201"),
                new EnrollmentKey(" 412001 ", "im101"),
                new EnrollmentKey("412002", "IM101"),
                new EnrollmentKey("412002", "IM101"),
                new EnrollmentKey("412003", "CS201"));

        System.out.println("duplicates=" + duplicates(records));
        System.out.println("coursesByStudent=" + coursesByStudent(records));
        System.out.println("studentCountByCourse=" + studentCountByCourse(records));
    }
}
