import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CoursePlanningGraph {
    private final Map<String, List<String>> unlocks = new LinkedHashMap<>();

    void addCourse(String course) {
        if (course == null) return;
        unlocks.putIfAbsent(course, new ArrayList<>());
    }

    void addPrerequisite(String prerequisite, String course) {
        if (prerequisite == null || course == null) return;
        if (!unlocks.containsKey(prerequisite) || !unlocks.containsKey(course)) return;
        if (prerequisite.equals(course)) return;
        if (!unlocks.get(prerequisite).contains(course)) unlocks.get(prerequisite).add(course);
    }

    boolean reachable(String from, String to) {
        if (from == null || to == null
                || !unlocks.containsKey(from) || !unlocks.containsKey(to)) return false;
        ArrayDeque<String> stack = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        stack.push(from);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.add(current)) continue;
            if (current.equals(to)) return true;
            for (String next : unlocks.getOrDefault(current, List.of())) {
                if (unlocks.containsKey(next) && !visited.contains(next)) stack.push(next);
            }
        }
        return false;
    }

    List<String> affectedCourses(String course) {
        List<String> affected = new ArrayList<>();
        if (course == null || !unlocks.containsKey(course)) return affected;
        Set<String> visited = new LinkedHashSet<>();
        collect(course, visited);
        visited.remove(course);
        affected.addAll(visited);
        return affected;
    }

    private void collect(String current, Set<String> visited) {
        if (!visited.add(current)) return;
        for (String next : unlocks.getOrDefault(current, List.of())) {
            if (unlocks.containsKey(next)) collect(next, visited);
        }
    }

    String report(String course) {
        if (course == null || !unlocks.containsKey(course)) return "查無課程：" + course;
        List<String> affected = affectedCourses(course);
        if (affected.isEmpty()) return course + " 沒有後續受影響課程";
        return course + " 受影響課程 = " + affected;
    }

    public static void main(String[] args) {
        CoursePlanningGraph graph = new CoursePlanningGraph();
        graph.addCourse("程式設計");
        graph.addCourse("資料結構");
        graph.addCourse("演算法");
        graph.addCourse("資料庫");
        graph.addCourse("系統分析");
        graph.addCourse("體育");
        graph.addPrerequisite("程式設計", "資料結構");
        graph.addPrerequisite("資料結構", "演算法");
        graph.addPrerequisite("資料結構", "資料庫");
        graph.addPrerequisite("資料庫", "系統分析");
        graph.addPrerequisite("程式設計", "程式設計");
        graph.addPrerequisite("程式設計", "不存在課程");
        System.out.println(graph.reachable("程式設計", "系統分析"));
        System.out.println(graph.reachable("系統分析", "程式設計"));
        System.out.println(graph.reachable("體育", "體育"));
        System.out.println(graph.reachable("程式設計", "不存在課程"));
        System.out.println(graph.reachable(null, "演算法"));
        System.out.println(graph.report("程式設計"));
        System.out.println(graph.report("資料庫"));
        System.out.println(graph.report("體育"));
        System.out.println(graph.report("不存在課程"));
        System.out.println(new CoursePlanningGraph().report("程式設計"));
    }
}
