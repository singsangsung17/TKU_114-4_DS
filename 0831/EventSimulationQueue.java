import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class EventSimulationQueue {
    record Event(String id, int time, String type, int sequence) {
        Event {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("id");
            }
        }
    }

    private final PriorityQueue<Event> queue = new PriorityQueue<>(
            Comparator.comparingInt(Event::time)
                    .thenComparingInt(Event::sequence));
    private final Set<String> cancelled = new HashSet<>();
    private final List<String> log = new ArrayList<>();

    public boolean schedule(Event event) {
        if (event == null) {
            return false;
        }
        return queue.offer(event);
    }

    public boolean cancel(String eventId) {
        if (eventId == null || eventId.isBlank()) {
            return false;
        }
        for (Event event : queue) {
            if (event.id().equals(eventId)) {
                return cancelled.add(eventId);
            }
        }
        return false;
    }

    public void run() {
        while (!queue.isEmpty()) {
            Event event = queue.poll();
            if (cancelled.contains(event.id())) {
                log.add("SKIP " + event.id() + " time=" + event.time());
                continue;
            }
            log.add("RUN " + event.id() + " time=" + event.time()
                    + " type=" + event.type()
                    + " sequence=" + event.sequence());
        }
    }

    public List<String> executionLog() {
        return log;
    }

    public static void main(String[] args) {
        EventSimulationQueue simulator = new EventSimulationQueue();

        System.out.println("排程 E1=" + simulator.schedule(new Event("E1", 30, "OPEN", 1)));
        System.out.println("排程 E2=" + simulator.schedule(new Event("E2", 10, "SETUP", 2)));
        System.out.println("排程 E3=" + simulator.schedule(new Event("E3", 30, "CHECK", 0)));
        System.out.println("排程 E4=" + simulator.schedule(new Event("E4", 50, "CLOSE", 3)));
        System.out.println("排程 E5=" + simulator.schedule(new Event("E5", 10, "GREET", 1)));
        System.out.println("排程 null=" + simulator.schedule(null));

        System.out.println("取消 E4=" + simulator.cancel("E4"));
        System.out.println("重複取消 E4=" + simulator.cancel("E4"));
        System.out.println("取消不存在 E9=" + simulator.cancel("E9"));
        System.out.println("取消空字串=" + simulator.cancel("   "));

        simulator.run();

        System.out.println("執行紀錄：");
        for (String line : simulator.executionLog()) {
            System.out.println("  " + line);
        }
    }
}
