import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IterativeDfsTrace {
    static List<String> traceDfs(Map<String, List<String>> graph, String start) {
        List<String> order = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            System.out.println("invalid start: " + start);
            return order;
        }
        ArrayDeque<String> stack = new ArrayDeque<>();
        Set<String> visited = new LinkedHashSet<>();
        stack.push(start);
        System.out.println("push " + start + " | stack=" + stack + " | visited=" + visited);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            System.out.println("pop  " + current + " | stack=" + stack + " | visited=" + visited);
            if (!visited.add(current)) {
                System.out.println("skip " + current + " already visited");
                continue;
            }
            order.add(current);
            List<String> neighbors = graph.getOrDefault(current, List.of());
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                String next = neighbors.get(i);
                if (graph.containsKey(next) && !visited.contains(next)) {
                    stack.push(next);
                    System.out.println("push " + next + " | stack=" + stack + " | visited=" + visited);
                }
            }
        }
        return order;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("D"));
        graph.put("D", List.of("A"));
        graph.put("E", List.of());
        System.out.println("order=" + traceDfs(graph, "A"));
        System.out.println("---");
        System.out.println("order=" + traceDfs(graph, "E"));
        System.out.println("---");
        System.out.println("order=" + traceDfs(graph, "X"));
        System.out.println("---");
        System.out.println("order=" + traceDfs(new LinkedHashMap<>(), "A"));
        System.out.println("---");
        System.out.println("order=" + traceDfs(null, "A"));
    }
}
