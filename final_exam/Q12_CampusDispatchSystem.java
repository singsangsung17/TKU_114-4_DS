import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Q12_CampusDispatchSystem {

    public record Request(String id, String location, int priority, long sequence) {}

    private final Map<String, Set<String>> roads = new HashMap<>();
    private final Map<String, Request> requestById = new HashMap<>();
    private final PriorityQueue<Request> pending;

    public Q12_CampusDispatchSystem() {
        this.pending = new PriorityQueue<>(
                Comparator.comparingInt(Request::priority)
                        .thenComparingLong(Request::sequence));
    }

    public boolean addLocation(String location) {
        if (location == null || roads.containsKey(location)) {
            return false;
        }
        roads.put(location, new LinkedHashSet<>());
        return true;
    }

    public boolean addRoad(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        if (!roads.containsKey(first) || !roads.containsKey(second)) {
            return false;
        }
        if (first.equals(second)) {
            return false;
        }
        if (roads.get(first).contains(second)) {
            return false;
        }
        roads.get(first).add(second);
        roads.get(second).add(first);
        return true;
    }

    public boolean submit(Request request) {
        if (request == null || request.id() == null || request.location() == null) {
            return false;
        }
        if (requestById.containsKey(request.id())) {
            return false;
        }
        requestById.put(request.id(), request);
        pending.offer(request);
        return true;
    }

    public Request nextReachable(String serviceCenter) {
        if (serviceCenter == null || !roads.containsKey(serviceCenter)) {
            return null;
        }
        List<Request> skipped = new ArrayList<>();
        Request found = null;
        while (!pending.isEmpty()) {
            Request candidate = pending.poll();
            if (!route(serviceCenter, candidate.location()).isEmpty()) {
                found = candidate;
                break;
            }
            skipped.add(candidate);
        }
        pending.addAll(skipped);
        if (found != null) {
            requestById.remove(found.id());
        }
        return found;
    }

    public java.util.List<String> route(String start, String target) {
        List<String> path = new ArrayList<>();
        if (start == null || target == null) {
            return path;
        }
        if (!roads.containsKey(start) || !roads.containsKey(target)) {
            return path;
        }
        if (start.equals(target)) {
            path.add(start);
            return path;
        }
        Map<String, String> predecessor = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        predecessor.put(start, null);
        queue.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String next : roads.get(current)) {
                if (predecessor.containsKey(next)) {
                    continue;
                }
                predecessor.put(next, current);
                queue.add(next);
            }
        }
        if (!predecessor.containsKey(target)) {
            return path;
        }
        String node = target;
        while (node != null) {
            path.add(node);
            node = predecessor.get(node);
        }
        Collections.reverse(path);
        return path;
    }

    public int pendingCount() {
        return pending.size();
    }
}