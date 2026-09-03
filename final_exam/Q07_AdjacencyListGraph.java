import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Q07_AdjacencyListGraph {

    private final Map<String, Set<String>> adjacency = new HashMap<>();
    private int edgeCount = 0;

    public boolean addVertex(String vertex) {
        if (vertex == null || adjacency.containsKey(vertex)) {
            return false;
        }
        adjacency.put(vertex, new LinkedHashSet<>());
        return true;
    }

    public boolean addEdge(String from, String to) {
        if (from == null || to == null) {
            return false;
        }
        if (!adjacency.containsKey(from) || !adjacency.containsKey(to)) {
            return false;
        }
        if (from.equals(to)) {
            return false;
        }
        Set<String> targets = adjacency.get(from);
        if (targets.contains(to)) {
            return false;
        }
        targets.add(to);
        edgeCount++;
        return true;
    }

    public boolean removeEdge(String from, String to) {
        if (from == null || to == null) {
            return false;
        }
        if (!adjacency.containsKey(from) || !adjacency.containsKey(to)) {
            return false;
        }
        Set<String> targets = adjacency.get(from);
        if (!targets.contains(to)) {
            return false;
        }
        targets.remove(to);
        edgeCount--;
        return true;
    }

    public java.util.List<String> outgoing(String vertex) {
        List<String> result = new ArrayList<>();
        if (vertex == null || !adjacency.containsKey(vertex)) {
            return result;
        }
        result.addAll(adjacency.get(vertex));
        return result;
    }

    public int inDegree(String vertex) {
        if (vertex == null || !adjacency.containsKey(vertex)) {
            return 0;
        }
        int count = 0;
        for (Set<String> targets : adjacency.values()) {
            if (targets.contains(vertex)) {
                count++;
            }
        }
        return count;
    }

    public int edgeCount() {
        return edgeCount;
    }
}