import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Q05_StudentHashIndex {

    private final Map<String, Set<String>> studentToCourses = new HashMap<>();
    private final Map<String, Set<String>> courseToStudents = new HashMap<>();
    private int enrollmentCount = 0;

    private String normalize(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        if (t.isEmpty()) {
            return null;
        }
        return t.toUpperCase();
    }

    public boolean enroll(String studentId, String courseId) {
        String s = normalize(studentId);
        String c = normalize(courseId);
        if (s == null || c == null) {
            return false;
        }
        Set<String> courses = studentToCourses.get(s);
        if (courses != null && courses.contains(c)) {
            return false;
        }
        if (courses == null) {
            courses = new HashSet<>();
            studentToCourses.put(s, courses);
        }
        courses.add(c);
        Set<String> students = courseToStudents.get(c);
        if (students == null) {
            students = new HashSet<>();
            courseToStudents.put(c, students);
        }
        students.add(s);
        enrollmentCount++;
        return true;
    }

    public boolean drop(String studentId, String courseId) {
        String s = normalize(studentId);
        String c = normalize(courseId);
        if (s == null || c == null) {
            return false;
        }
        Set<String> courses = studentToCourses.get(s);
        if (courses == null || !courses.contains(c)) {
            return false;
        }
        courses.remove(c);
        if (courses.isEmpty()) {
            studentToCourses.remove(s);
        }
        Set<String> students = courseToStudents.get(c);
        if (students != null) {
            students.remove(s);
            if (students.isEmpty()) {
                courseToStudents.remove(c);
            }
        }
        enrollmentCount--;
        return true;
    }

    public java.util.Set<String> coursesOf(String studentId) {
        String s = normalize(studentId);
        if (s == null || !studentToCourses.containsKey(s)) {
            return Collections.unmodifiableSet(new HashSet<>());
        }
        return Collections.unmodifiableSet(new HashSet<>(studentToCourses.get(s)));
    }

    public java.util.Set<String> studentsIn(String courseId) {
        String c = normalize(courseId);
        if (c == null || !courseToStudents.containsKey(c)) {
            return Collections.unmodifiableSet(new HashSet<>());
        }
        return Collections.unmodifiableSet(new HashSet<>(courseToStudents.get(c)));
    }

    public int enrollmentCount() {
        return enrollmentCount;
    }
}