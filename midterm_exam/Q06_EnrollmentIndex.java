package midterm_exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Q06_EnrollmentIndex {
    private final Map<String, Set<String>> index;

    public Q06_EnrollmentIndex() {
        this.index = new TreeMap<String, Set<String>>();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public boolean enroll(String courseCode, String studentId) {
        if (isBlank(courseCode) || isBlank(studentId)) {
            return false;
        }
        Set<String> students = index.get(courseCode);
        if (students == null) {
            students = new TreeSet<String>();
            index.put(courseCode, students);
        }
        return students.add(studentId);
    }

    public boolean drop(String courseCode, String studentId) {
        if (isBlank(courseCode) || isBlank(studentId)) {
            return false;
        }
        Set<String> students = index.get(courseCode);
        if (students == null || !students.remove(studentId)) {
            return false;
        }
        if (students.isEmpty()) {
            index.remove(courseCode);
        }
        return true;
    }

    public int courseSize(String courseCode) {
        if (isBlank(courseCode)) {
            return 0;
        }
        Set<String> students = index.get(courseCode);
        return students == null ? 0 : students.size();
    }

    public java.util.List<String> studentsOf(String courseCode) {
        List<String> result = new ArrayList<String>();
        if (isBlank(courseCode)) {
            return result;
        }
        Set<String> students = index.get(courseCode);
        if (students != null) {
            result.addAll(students);
        }
        return result;
    }

    public java.util.List<String> coursesOf(String studentId) {
        List<String> result = new ArrayList<String>();
        if (isBlank(studentId)) {
            return result;
        }
        for (Map.Entry<String, Set<String>> entry : index.entrySet()) {
            if (entry.getValue().contains(studentId)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public java.util.Map<String, Integer> summary() {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        for (Map.Entry<String, Set<String>> entry : index.entrySet()) {
            result.put(entry.getKey(), entry.getValue().size());
        }
        return result;
    }
}
