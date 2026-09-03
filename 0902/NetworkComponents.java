import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class NetworkComponents {
    static List<List<String>> components(Map<String, List<String>> graph) {
        List<List<String>> result = new ArrayList<>();
        if (graph == null) return result;
        Set<String> visited = new HashSet<>();
        for (String start : graph.keySet()) {
            if (visited.contains(start)) continue;
            List<String> component = new ArrayList<>();
            Queue<String> queue = new ArrayDeque<>();
            queue.offer(start);
            visited.add(start);
            while (!queue.isEmpty()) {
                String current = queue.poll();
                component.add(current);
                for (String next : graph.getOrDefault(current, List.of())) {
                    if (graph.containsKey(next) && visited.add(next)) queue.offer(next);
                }
            }
            result.add(component);
        }
        return result;
    }

    static int componentCount(Map<String, List<String>> graph) {
        return components(graph).size();
    }

    static List<String> largestComponent(Map<String, List<String>> graph) {
        List<String> largest = new ArrayList<>();
        for (List<String> component : components(graph)) {
            if (component.size() > largest.size()) largest = component;
        }
        return largest;
    }

    static String report(Map<String, List<String>> graph) {
        List<List<String>> all = components(graph);
        if (all.isEmpty()) return "no component";
        StringBuilder sb = new StringBuilder();
        for (List<String> component : all) sb.append(component).append('\n');
        sb.append("count = ").append(all.size()).append('\n');
        sb.append("largest = ").append(largestComponent(graph));
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("A"));
        graph.put("C", List.of("A"));
        graph.put("D", List.of("E"));
        graph.put("E", List.of("D"));
        graph.put("F", List.of());
        System.out.println(report(graph));
        System.out.println("---");
        System.out.println(report(new LinkedHashMap<>()));
        System.out.println("---");
        System.out.println(report(null));
    }
}
