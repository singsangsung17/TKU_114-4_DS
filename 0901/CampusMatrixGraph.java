import java.util.ArrayList;
import java.util.List;

public class CampusMatrixGraph {
    private final List<String> vertices;
    private final boolean[][] edges;

    public CampusMatrixGraph(List<String> vertices) {
        if (vertices == null || vertices.isEmpty()) throw new IllegalArgumentException("vertices");
        this.vertices = List.copyOf(vertices);
        this.edges = new boolean[vertices.size()][vertices.size()];
    }

    private int indexOf(String vertex) {
        int index = vertices.indexOf(vertex);
        if (index < 0) throw new IllegalArgumentException("unknown vertex: " + vertex);
        return index;
    }

    public boolean addEdge(String first, String second) {
        int a = indexOf(first);
        int b = indexOf(second);
        if (a == b || edges[a][b]) return false;
        edges[a][b] = true;
        edges[b][a] = true;
        return true;
    }

    public boolean removeEdge(String first, String second) {
        int a = indexOf(first);
        int b = indexOf(second);
        if (!edges[a][b]) return false;
        edges[a][b] = false;
        edges[b][a] = false;
        return true;
    }

    public int degree(String vertex) {
        int row = indexOf(vertex);
        int degree = 0;
        for (boolean connected : edges[row]) if (connected) degree++;
        return degree;
    }

    public List<String> neighbors(String vertex) {
        int row = indexOf(vertex);
        List<String> result = new ArrayList<>();
        for (int column = 0; column < vertices.size(); column++) {
            if (edges[row][column]) result.add(vertices.get(column));
        }
        return result;
    }

    public int edgeCount() {
        int count = 0;
        for (int row = 0; row < vertices.size(); row++) {
            for (int column = row + 1; column < vertices.size(); column++) {
                if (edges[row][column]) count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        CampusMatrixGraph graph = new CampusMatrixGraph(List.of("圖書館", "體育館", "教學樓", "宿舍"));
        System.out.println(graph.addEdge("圖書館", "體育館"));
        System.out.println(graph.addEdge("圖書館", "教學樓"));
        System.out.println(graph.addEdge("教學樓", "宿舍"));
        System.out.println(graph.addEdge("圖書館", "體育館"));
        System.out.println("圖書館 neighbors=" + graph.neighbors("圖書館"));
        System.out.println("圖書館 degree=" + graph.degree("圖書館"));
        System.out.println("edges=" + graph.edgeCount());
        System.out.println(graph.removeEdge("圖書館", "體育館"));
        System.out.println(graph.removeEdge("圖書館", "體育館"));
        System.out.println("edges=" + graph.edgeCount());
    }
}
