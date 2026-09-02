import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseGradeMap {
    private final Map<String, List<Integer>> grades = new HashMap<>();

    public void add(String courseId, int score) {
        if (courseId == null || courseId.isBlank()) throw new IllegalArgumentException("courseId");
        grades.computeIfAbsent(courseId.trim().toUpperCase(), key -> new ArrayList<>()).add(score);
    }

    public double average(String courseId) {
        List<Integer> scores = grades.get(courseId);
        if (scores == null || scores.isEmpty()) return 0.0;
        int sum = 0;
        for (int score : scores) sum += score;
        return (double) sum / scores.size();
    }

    public int max(String courseId) {
        List<Integer> scores = grades.get(courseId);
        if (scores == null || scores.isEmpty()) return 0;
        int max = scores.get(0);
        for (int score : scores) if (score > max) max = score;
        return max;
    }

    public List<String> report() {
        List<String> courses = new ArrayList<>(grades.keySet());
        courses.sort(null);
        List<String> lines = new ArrayList<>();
        for (String course : courses) {
            lines.add(String.format("%s count=%d avg=%.2f max=%d",
                    course, grades.get(course).size(), average(course), max(course)));
        }
        return lines;
    }

    public static void main(String[] args) {
        CourseGradeMap map = new CourseGradeMap();
        map.add("IM101", 80);
        map.add("IM101", 90);
        map.add("CS201", 70);
        map.add("cs201", 95);
        map.add("AI300", 60);
        for (String line : map.report()) System.out.println(line);
        System.out.println("IM101 avg=" + map.average("IM101"));
        System.out.println("CS201 max=" + map.max("CS201"));
    }
}
