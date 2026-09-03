import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q06_AdjacencyMatrixGraph {

    private final List<String> vertices = new ArrayList<>();
    private final Map<String, Integer> indexOf = new HashMap<>();
    private final boolean[][] matrix;

    public Q06_AdjacencyMatrixGraph(java.util.List<String> vertices) {
        if (vertices != null) {
            for (String v : vertices) {
                if (v != null && !indexOf.containsKey(v)) {
                    indexOf.put(v, this.vertices.size());
                    this.vertices.add(v);
                }
            }
        }
        matrix = new boolean[this.vertices.size()][this.vertices.size()];
    }

    public boolean addEdge(String first, String second) {
        Integer i = indexOf.get(first);
        Integer j = indexOf.get(second);
        if (i == null || j == null) {
            return false;
        }
        if (i.equals(j)) {
            return false;
        }
        if (matrix[i][j]) {
            return false;
        }
        matrix[i][j] = true;
        matrix[j][i] = true;
        return true;
    }

    public boolean removeEdge(String first, String second) {
        Integer i = indexOf.get(first);
        Integer j = indexOf.get(second);
        if (i == null || j == null) {
            return false;
        }
        if (!matrix[i][j]) {
            return false;
        }
        matrix[i][j] = false;
        matrix[j][i] = false;
        return true;
    }

    public boolean hasEdge(String first, String second) {
        Integer i = indexOf.get(first);
        Integer j = indexOf.get(second);
        if (i == null || j == null) {
            return false;
        }
        return matrix[i][j];
    }

    public int degree(String vertex) {
        Integer i = indexOf.get(vertex);
        if (i == null) {
            return 0;
        }
        int count = 0;
        for (int j = 0; j < vertices.size(); j++) {
            if (matrix[i][j]) {
                count++;
            }
        }
        return count;
    }

    public java.util.List<String> neighbors(String vertex) {
        List<String> result = new ArrayList<>();
        Integer i = indexOf.get(vertex);
        if (i == null) {
            return result;
        }
        for (int j = 0; j < vertices.size(); j++) {
            if (matrix[i][j]) {
                result.add(vertices.get(j));
            }
        }
        return result;
    }
}