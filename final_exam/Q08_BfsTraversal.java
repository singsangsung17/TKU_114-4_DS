import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Q08_BfsTraversal {

    public static java.util.List<String> bfs(
            java.util.Map<String, java.util.List<String>> graph, String start) {
        List<String> result = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            return result;
        }
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);
            List<String> neighbors = graph.get(current);
            if (neighbors == null) {
                continue;
            }
            for (String next : neighbors) {
                if (next == null || visited.contains(next)) {
                    continue;
                }
                visited.add(next);
                queue.add(next);
            }
        }
        return result;
    }

    public static java.util.Map<String, Integer> distanceFrom(
            java.util.Map<String, java.util.List<String>> graph, String start) {
        Map<String, Integer> distance = new HashMap<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            return distance;
        }
        Queue<String> queue = new ArrayDeque<>();
        distance.put(start, 0);
        queue.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            List<String> neighbors = graph.get(current);
            if (neighbors == null) {
                continue;
            }
            for (String next : neighbors) {
                if (next == null || distance.containsKey(next)) {
                    continue;
                }
                distance.put(next, distance.get(current) + 1);
                queue.add(next);
            }
        }
        return distance;
    }
}