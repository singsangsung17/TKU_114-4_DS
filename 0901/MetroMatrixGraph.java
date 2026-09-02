import java.util.ArrayList;
import java.util.List;

public class MetroMatrixGraph {
    private final List<String> stations;
    private final boolean[][] edges;

    public MetroMatrixGraph(List<String> stations) {
        if (stations == null || stations.isEmpty()) throw new IllegalArgumentException("stations");
        this.stations = List.copyOf(stations);
        this.edges = new boolean[stations.size()][stations.size()];
    }

    private int indexOf(String station) {
        int index = stations.indexOf(station);
        if (index < 0) throw new IllegalArgumentException("unknown station: " + station);
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

    public List<String> neighbors(String station) {
        int row = indexOf(station);
        List<String> result = new ArrayList<>();
        for (int column = 0; column < stations.size(); column++) {
            if (edges[row][column]) result.add(stations.get(column));
        }
        return result;
    }

    public int degree(String station) {
        int row = indexOf(station);
        int degree = 0;
        for (boolean connected : edges[row]) if (connected) degree++;
        return degree;
    }

    public int edgeCount() {
        int count = 0;
        for (int row = 0; row < stations.size(); row++) {
            for (int column = row + 1; column < stations.size(); column++) {
                if (edges[row][column]) count++;
            }
        }
        return count;
    }

    public void matrixReport() {
        System.out.print("      ");
        for (String station : stations) System.out.print(station + " ");
        System.out.println();
        for (int row = 0; row < stations.size(); row++) {
            System.out.print(stations.get(row) + " ");
            for (int column = 0; column < stations.size(); column++) {
                System.out.print((edges[row][column] ? 1 : 0) + "   ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MetroMatrixGraph metro = new MetroMatrixGraph(List.of("板橋", "府中", "亞東", "新埔"));
        metro.addEdge("板橋", "府中");
        metro.addEdge("板橋", "新埔");
        metro.addEdge("府中", "亞東");
        metro.addEdge("板橋", "府中");
        System.out.println("板橋 neighbors=" + metro.neighbors("板橋"));
        System.out.println("板橋 degree=" + metro.degree("板橋"));
        System.out.println("edges=" + metro.edgeCount());
        metro.matrixReport();
    }
}
