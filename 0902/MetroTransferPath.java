import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MetroTransferPath {
    static List<String> shortestPath(Map<String, List<String>> metro, String start, String target) {
        if (metro == null || start == null || target == null
                || !metro.containsKey(start) || !metro.containsKey(target)) return List.of();
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(target)) break;
            for (String next : metro.getOrDefault(current, List.of())) {
                if (metro.containsKey(next) && visited.add(next)) {
                    previous.put(next, current);
                    queue.offer(next);
                }
            }
        }
        if (!visited.contains(target)) return List.of();
        List<String> path = new ArrayList<>();
        for (String at = target; at != null; at = previous.get(at)) path.add(at);
        Collections.reverse(path);
        return path;
    }

    static int edgeCount(List<String> path) {
        return path == null || path.isEmpty() ? -1 : path.size() - 1;
    }

    static String report(Map<String, List<String>> metro, String start, String target) {
        List<String> path = shortestPath(metro, start, target);
        if (path.isEmpty()) return start + " -> " + target + " : no path";
        return String.join(" -> ", path) + " | edges = " + edgeCount(path);
    }

    public static void main(String[] args) {
        Map<String, List<String>> metro = new LinkedHashMap<>();
        metro.put("板橋", List.of("府中", "新埔"));
        metro.put("府中", List.of("板橋", "亞東醫院"));
        metro.put("新埔", List.of("板橋", "江子翠"));
        metro.put("江子翠", List.of("新埔", "龍山寺"));
        metro.put("龍山寺", List.of("江子翠", "西門"));
        metro.put("西門", List.of("龍山寺"));
        metro.put("亞東醫院", List.of("府中"));
        metro.put("孤立站", List.of());
        System.out.println(report(metro, "板橋", "西門"));
        System.out.println(report(metro, "板橋", "板橋"));
        System.out.println(report(metro, "板橋", "孤立站"));
        System.out.println(report(metro, "板橋", "不存在站"));
        System.out.println(report(new LinkedHashMap<>(), "板橋", "西門"));
        System.out.println(report(null, "板橋", "西門"));
    }
}
