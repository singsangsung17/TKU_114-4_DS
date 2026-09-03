import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q09_DfsPathSearch {

    public static java.util.List<String> dfs(
            java.util.Map<String, java.util.List<String>> graph, String start) {
        List<String> result = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            return result;
        }
        visit(graph, start, new HashSet<>(), result);
        return result;
    }

    private static void visit(java.util.Map<String, java.util.List<String>> graph,
            String current, Set<String> visited, List<String> result) {
        visited.add(current);
        result.add(current);
        List<String> neighbors = graph.get(current);
        if (neighbors == null) {
            return;
        }
        for (String next : neighbors) {
            if (next == null || visited.contains(next)) {
                continue;
            }
            visit(graph, next, visited, result);
        }
    }

    public static boolean reachable(
            java.util.Map<String, java.util.List<String>> graph,
            String start, String target) {
        if (graph == null || start == null || target == null) {
            return false;
        }
        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return false;
        }
        if (start.equals(target)) {
            return true;
        }
        return dfs(graph, start).contains(target);
    }
}