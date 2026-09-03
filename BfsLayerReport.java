import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BfsLayerReport {
    static Map<String, Integer> layers(Map<String, List<String>> graph, String start) {
        Map<String, Integer> distance = new LinkedHashMap<>();
        if (graph == null || start == null || !graph.containsKey(start)) return distance;
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);
        distance.put(start, 0);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            int next = distance.get(current) + 1;
            for (String neighbor : graph.getOrDefault(current, List.of())) {
                if (graph.containsKey(neighbor) && !distance.containsKey(neighbor)) {
                    distance.put(neighbor, next);
                    queue.offer(neighbor);
                }
            }
        }
        return distance;
    }

    static String report(Map<String, List<String>> graph, String start) {
        Map<String, Integer> distance = layers(graph, start);
        if (distance.isEmpty()) return "no reachable vertex from " + start;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : distance.entrySet()) {
            sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append('\n');
        }
        if (graph != null) {
            for (String vertex : graph.keySet()) {
                if (!distance.containsKey(vertex)) sb.append(vertex).append(" = unreachable\n");
            }
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("D", "E"));
        graph.put("D", List.of("F"));
        graph.put("E", List.of("F"));
        graph.put("F", List.of());
        graph.put("Z", List.of());
        System.out.println(report(graph, "A"));
        System.out.println("---");
        System.out.println(report(graph, "Z"));
        System.out.println("---");
        System.out.println(report(graph, "X"));
        System.out.println("---");
        System.out.println(report(new LinkedHashMap<>(), "A"));
        System.out.println("---");
        System.out.println(report(null, "A"));
    }
}
