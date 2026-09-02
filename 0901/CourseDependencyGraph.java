import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseDependencyGraph {
    private final Map<String, Set<String>> nextCourses = new LinkedHashMap<>();

    public boolean addCourse(String course) {
        if (course == null || course.isBlank()) return false;
        return nextCourses.putIfAbsent(course.trim(), new LinkedHashSet<>()) == null;
    }

    public boolean addDependency(String prerequisite, String course) {
        if (!nextCourses.containsKey(prerequisite) || !nextCourses.containsKey(course)) return false;
        if (prerequisite.equals(course)) return false;
        return nextCourses.get(prerequisite).add(course);
    }

    public List<String> successors(String course) {
        Set<String> set = nextCourses.get(course);
        return set == null ? List.of() : new ArrayList<>(set);
    }

    public List<String> prerequisites(String course) {
        List<String> result = new ArrayList<>();
        if (!nextCourses.containsKey(course)) return result;
        for (Map.Entry<String, Set<String>> entry : nextCourses.entrySet()) {
            if (entry.getValue().contains(course)) result.add(entry.getKey());
        }
        return result;
    }

    public int outDegree(String course) {
        return successors(course).size();
    }

    public int inDegree(String course) {
        return prerequisites(course).size();
    }

    public void report() {
        for (String course : nextCourses.keySet()) {
            System.out.println(course
                    + " prerequisites=" + prerequisites(course)
                    + " next=" + successors(course)
                    + " in=" + inDegree(course)
                    + " out=" + outDegree(course));
        }
    }

    public static void main(String[] args) {
        CourseDependencyGraph graph = new CourseDependencyGraph();
        for (String course : List.of("程式設計", "資料結構", "演算法", "資料庫")) graph.addCourse(course);
        graph.addDependency("程式設計", "資料結構");
        graph.addDependency("資料結構", "演算法");
        graph.addDependency("程式設計", "資料庫");
        graph.report();
    }
}
