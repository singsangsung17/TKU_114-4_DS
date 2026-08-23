import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class Delivery {
    private final String id;
    private final String destination;
    private boolean completed;

    Delivery(String id, String destination) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.destination = destination == null || destination.isBlank()
                ? "Unknown" : destination.trim();
        this.completed = false;
    }

    String getId() {
        return id;
    }

    boolean isCompleted() {
        return completed;
    }

    void markCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return id + " -> " + destination + " completed=" + completed;
    }
}

class DeliveryWorkflow {
    private final Map<String, Delivery> byId = new HashMap<>();
    private final Deque<Delivery> waiting = new ArrayDeque<>();
    private final Deque<Delivery> history = new ArrayDeque<>();

    boolean add(Delivery delivery) {
        if (delivery == null || byId.containsKey(delivery.getId())) {
            return false;
        }
        byId.put(delivery.getId(), delivery);
        waiting.offerLast(delivery);
        return true;
    }

    Delivery process() {
        Delivery delivery = waiting.pollFirst();
        if (delivery == null) {
            return null;
        }
        delivery.markCompleted(true);
        history.push(delivery);
        return delivery;
    }

    Delivery undo() {
        Delivery delivery = history.poll();
        if (delivery == null) {
            return null;
        }
        delivery.markCompleted(false);
        waiting.offerFirst(delivery);
        return delivery;
    }

    Delivery find(String id) {
        return byId.get(id);
    }

    void printStatistics(String title) {
        System.out.println(title);
        System.out.println("  總筆數=" + byId.size()
                + " 等待中=" + waiting.size()
                + " 已完成=" + history.size());
        System.out.println("  等待佇列=" + waiting);
        System.out.println("  完成歷程=" + history);
    }
}

public class DeliveryWorkflowSystem {
    public static void main(String[] args) {
        DeliveryWorkflow workflow = new DeliveryWorkflow();

        System.out.println("空佇列處理：" + workflow.process());
        System.out.println("空歷程 undo：" + workflow.undo());

        System.out.println("新增 D101：" + workflow.add(new Delivery("D101", "Taipei")));
        System.out.println("新增 D102：" + workflow.add(new Delivery("D102", "Taichung")));
        System.out.println("新增 D103：" + workflow.add(new Delivery("D103", "Kaohsiung")));
        System.out.println("重複 D101：" + workflow.add(new Delivery("D101", "Hsinchu")));
        System.out.println("新增 null：" + workflow.add(null));

        workflow.printStatistics("初始狀態：");

        System.out.println("處理：" + workflow.process());
        System.out.println("處理：" + workflow.process());
        workflow.printStatistics("處理兩筆後：");

        System.out.println("查詢 D101：" + workflow.find("D101"));
        System.out.println("查詢 D999：" + workflow.find("D999"));

        System.out.println("undo：" + workflow.undo());
        workflow.printStatistics("undo 一次後：");

        System.out.println("undo：" + workflow.undo());
        System.out.println("再 undo：" + workflow.undo());
        workflow.printStatistics("全部復原後：");

        System.out.println("查詢 D102 狀態：" + workflow.find("D102"));
    }
}
