import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DirectedReachability {
    record Query(String from, String to) {}

    static boolean reachable(Map<String, List<String>> graph, String from, String to) {
        if (graph == null || from == null || to == null
                || !graph.containsKey(from) || !graph.containsKey(to)) return false;
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.offer(from);
        visited.add(from);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(to)) return true;
            for (String next : graph.getOrDefault(current, List.of())) {
                if (graph.containsKey(next) && visited.add(next)) queue.offer(next);
            }
        }
        return false;
    }

    static List<String> answerAll(Map<String, List<String>> graph, List<Query> queries) {
        List<String> answers = new ArrayList<>();
        if (queries == null) return answers;
        for (Query query : queries) {
            if (query == null) {
                answers.add("null query -> false");
                continue;
            }
            answers.add(query.from() + " -> " + query.to() + " : "
                    + reachable(graph, query.from(), query.to()));
        }
        return answers;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B"));
        graph.put("B", List.of("C"));
        graph.put("C", List.of());
        graph.put("D", List.of("A"));
        graph.put("E", List.of());
        List<Query> queries = new ArrayList<>(List.of(
                new Query("A", "C"),
                new Query("C", "A"),
                new Query("D", "C"),
                new Query("E", "E"),
                new Query("A", "X"),
                new Query("X", "A")));
        queries.add(null);
        for (String line : answerAll(graph, queries)) System.out.println(line);
        System.out.println("---");
        System.out.println(answerAll(new LinkedHashMap<>(), List.of(new Query("A", "B"))));
        System.out.println(answerAll(null, List.of(new Query("A", "B"))));
        System.out.println(answerAll(graph, null));
    }
}
