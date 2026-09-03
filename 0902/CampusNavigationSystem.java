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

public class CampusNavigationSystem {
    private final Map<String, String> places = new LinkedHashMap<>();
    private final Map<String, List<String>> roads = new LinkedHashMap<>();

    void addPlace(String id, String name) {
        if (id == null || name == null) return;
        places.put(id, name);
        roads.putIfAbsent(id, new ArrayList<>());
    }

    void addRoad(String from, String to) {
        if (!places.containsKey(from) || !places.containsKey(to) || from.equals(to)) return;
        if (!roads.get(from).contains(to)) roads.get(from).add(to);
        if (!roads.get(to).contains(from)) roads.get(to).add(from);
    }

    String nameOf(String id) {
        return places.getOrDefault(id, "未知地點");
    }

    List<String> shortestPath(String start, String target) {
        if (start == null || target == null
                || !places.containsKey(start) || !places.containsKey(target)) return List.of();
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(target)) break;
            for (String next : roads.getOrDefault(current, List.of())) {
                if (places.containsKey(next) && visited.add(next)) {
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

    String navigate(String start, String target) {
        List<String> path = shortestPath(start, target);
        if (path.isEmpty()) return nameOf(start) + " -> " + nameOf(target) + " : 查無路徑";
        List<String> names = new ArrayList<>();
        for (String id : path) names.add(nameOf(id));
        return String.join(" -> ", names) + " | 經過道路數 = " + (path.size() - 1);
    }

    public static void main(String[] args) {
        CampusNavigationSystem system = new CampusNavigationSystem();
        system.addPlace("P1", "校門");
        system.addPlace("P2", "圖書館");
        system.addPlace("P3", "資管系館");
        system.addPlace("P4", "體育館");
        system.addPlace("P5", "宿舍");
        system.addPlace("P6", "實驗農場");
        system.addRoad("P1", "P2");
        system.addRoad("P1", "P4");
        system.addRoad("P2", "P3");
        system.addRoad("P4", "P3");
        system.addRoad("P3", "P5");
        System.out.println(system.navigate("P1", "P5"));
        System.out.println(system.navigate("P1", "P1"));
        System.out.println(system.navigate("P1", "P6"));
        System.out.println(system.navigate("P1", "P9"));
        System.out.println(system.navigate(null, "P2"));
        System.out.println(new CampusNavigationSystem().navigate("P1", "P2"));
    }
}
