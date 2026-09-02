import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LogisticsWeightedGraph {
    public record Edge(String to, int cost) {}

    private final Map<String, List<Edge>> outgoing = new LinkedHashMap<>();

    public boolean addNode(String node) {
        if (node == null || node.isBlank()) return false;
        return outgoing.putIfAbsent(node.trim(), new ArrayList<>()) == null;
    }

    public boolean addEdge(String from, String to, int cost) {
        if (cost < 0) return false;
        if (!outgoing.containsKey(from) || !outgoing.containsKey(to)) return false;
        if (from.equals(to)) return false;
        List<Edge> edges = outgoing.get(from);
        for (Edge edge : edges) {
            if (edge.to().equals(to)) return false;
        }
        edges.add(new Edge(to, cost));
        return true;
    }

    public boolean updateEdge(String from, String to, int cost) {
        if (cost < 0) return false;
        if (!outgoing.containsKey(from) || !outgoing.containsKey(to)) return false;
        List<Edge> edges = outgoing.get(from);
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).to().equals(to)) {
                edges.set(i, new Edge(to, cost));
                return true;
            }
        }
        return false;
    }

    public boolean removeEdge(String from, String to) {
        if (!outgoing.containsKey(from)) return false;
        List<Edge> edges = outgoing.get(from);
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).to().equals(to)) {
                edges.remove(i);
                return true;
            }
        }
        return false;
    }

    public Integer cost(String from, String to) {
        if (!outgoing.containsKey(from)) return null;
        for (Edge edge : outgoing.get(from)) {
            if (edge.to().equals(to)) return edge.cost();
        }
        return null;
    }

    public List<Edge> edgesFrom(String node) {
        return List.copyOf(outgoing.getOrDefault(node, List.of()));
    }

    public static void main(String[] args) {
        LogisticsWeightedGraph graph = new LogisticsWeightedGraph();
        for (String node : List.of("台北", "台中", "高雄")) graph.addNode(node);
        System.out.println(graph.addEdge("台北", "台中", 150));
        System.out.println(graph.addEdge("台中", "高雄", 200));
        System.out.println(graph.addEdge("台北", "台中", 180));
        System.out.println(graph.addEdge("台北", "花蓮", 100));
        System.out.println(graph.addEdge("台北", "高雄", -50));
        System.out.println(graph.updateEdge("台北", "台中", 170));
        System.out.println("台北->台中=" + graph.cost("台北", "台中"));
        System.out.println("台北 edges=" + graph.edgesFrom("台北"));
        System.out.println(graph.removeEdge("台中", "高雄"));
        System.out.println("台中->高雄=" + graph.cost("台中", "高雄"));
    }
}
