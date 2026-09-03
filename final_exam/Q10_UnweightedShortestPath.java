import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Q10_UnweightedShortestPath {

    public static java.util.List<String> shortestPath(
            java.util.Map<String, java.util.List<String>> graph,
            String start, String target) {
        List<String> path = new ArrayList<>();
        if (graph == null || start == null || target == null) {
            return path;
        }
        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return path;
        }
        if (start.equals(target)) {
            path.add(start);
            return path;
        }

        Map<String, String> predecessor = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        predecessor.put(start, null);
        queue.add(start);
        boolean found = false;

        while (!queue.isEmpty() && !found) {
            String current = queue.poll();
            List<String> neighbors = graph.get(current);
            if (neighbors == null) {
                continue;
            }
            for (String next : neighbors) {
                if (next == null || predecessor.containsKey(next)) {
                    continue;
                }
                predecessor.put(next, current);
                if (next.equals(target)) {
                    found = true;
                    break;
                }
                queue.add(next);
            }
        }

        if (!predecessor.containsKey(target)) {
            return path;
        }

        String node = target;
        while (node != null) {
            path.add(node);
            node = predecessor.get(node);
        }
        Collections.reverse(path);
        return path;
    }
}