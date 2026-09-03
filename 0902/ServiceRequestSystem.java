import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ServiceRequestSystem {
    static class Request {
        final String id;
        final String title;
        final int priority;
        final long sequence;
        boolean cancelled;

        Request(String id, String title, int priority, long sequence) {
            this.id = id;
            this.title = title;
            this.priority = priority;
            this.sequence = sequence;
            this.cancelled = false;
        }

        @Override
        public String toString() {
            return id + "(" + title + ", p=" + priority + ")";
        }
    }

    private final Map<String, Request> index = new HashMap<>();
    private final PriorityQueue<Request> queue = new PriorityQueue<>(
            Comparator.comparingInt((Request r) -> -r.priority).thenComparingLong(r -> r.sequence));
    private long counter = 0;

    boolean submit(String id, String title, int priority) {
        if (id == null || title == null || index.containsKey(id)) return false;
        Request request = new Request(id, title, priority, counter++);
        index.put(id, request);
        queue.offer(request);
        return true;
    }

    Request find(String id) {
        if (id == null) return null;
        return index.get(id);
    }

    boolean cancel(String id) {
        Request request = find(id);
        if (request == null || request.cancelled) return false;
        request.cancelled = true;
        index.remove(id);
        queue.remove(request);
        return true;
    }

    Request next() {
        while (!queue.isEmpty()) {
            Request request = queue.poll();
            if (request.cancelled || !index.containsKey(request.id)) continue;
            index.remove(request.id);
            return request;
        }
        return null;
    }

    boolean consistent() {
        if (index.size() != queue.size()) return false;
        for (Request request : queue) {
            if (request.cancelled || index.get(request.id) != request) return false;
        }
        return true;
    }

    List<String> pendingIds() {
        List<String> ids = new ArrayList<>(index.keySet());
        ids.sort(null);
        return ids;
    }

    public static void main(String[] args) {
        ServiceRequestSystem system = new ServiceRequestSystem();
        System.out.println(system.submit("R1", "網路斷線", 5));
        System.out.println(system.submit("R2", "印表機卡紙", 2));
        System.out.println(system.submit("R3", "帳號鎖定", 5));
        System.out.println(system.submit("R4", "更新軟體", 1));
        System.out.println(system.submit("R1", "重複單號", 9));
        System.out.println(system.submit(null, "空值", 3));
        System.out.println(system.find("R2"));
        System.out.println(system.find("R9"));
        System.out.println(system.find(null));
        System.out.println(system.cancel("R3"));
        System.out.println(system.cancel("R3"));
        System.out.println(system.cancel("R9"));
        System.out.println(system.pendingIds());
        System.out.println(system.consistent());
        System.out.println(system.next());
        System.out.println(system.next());
        System.out.println(system.consistent());
        System.out.println(system.next());
        System.out.println(system.next());
        System.out.println(system.pendingIds());
        System.out.println(system.consistent());
    }
}
