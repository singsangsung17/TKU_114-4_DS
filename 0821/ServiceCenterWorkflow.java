import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class ServiceTicket {
    private final String id;
    private final String customer;
    private String status;

    ServiceTicket(String id, String customer) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.customer = customer == null || customer.isBlank()
                ? "Unknown" : customer.trim();
        this.status = "WAITING";
    }

    String getId() {
        return id;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + " " + customer + " [" + status + "]";
    }
}

class ServiceCenter {
    private final Map<String, ServiceTicket> byId = new HashMap<>();
    private final Deque<ServiceTicket> waiting = new ArrayDeque<>();
    private final Deque<ServiceTicket> completed = new ArrayDeque<>();
    private final Set<String> usedIds = new HashSet<>();

    boolean createTicket(String id, String customer) {
        if (id == null || id.isBlank() || !usedIds.add(id.trim())) {
            return false;
        }
        ServiceTicket ticket = new ServiceTicket(id, customer);
        byId.put(ticket.getId(), ticket);
        waiting.offerLast(ticket);
        return true;
    }

    ServiceTicket processNext() {
        ServiceTicket ticket = waiting.pollFirst();
        if (ticket == null) {
            return null;
        }
        ticket.setStatus("COMPLETED");
        completed.push(ticket);
        return ticket;
    }

    boolean cancelWaiting(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }
        String target = id.trim();
        Iterator<ServiceTicket> iterator = waiting.iterator();
        while (iterator.hasNext()) {
            ServiceTicket ticket = iterator.next();
            if (ticket.getId().equals(target)) {
                iterator.remove();
                ticket.setStatus("CANCELLED");
                return true;
            }
        }
        return false;
    }

    ServiceTicket undoLastCompletion() {
        ServiceTicket ticket = completed.poll();
        if (ticket == null) {
            return null;
        }
        ticket.setStatus("WAITING");
        waiting.offerFirst(ticket);
        return ticket;
    }

    ServiceTicket findById(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        return byId.get(id.trim());
    }

    void printSummary(String title) {
        System.out.println(title);
        System.out.println("  總開立=" + byId.size()
                + " 等待中=" + waiting.size()
                + " 已完成=" + completed.size());
        System.out.println("  等待佇列=" + waiting);
        System.out.println("  完成歷程=" + completed);
    }
}

public class ServiceCenterWorkflow {
    public static void main(String[] args) {
        ServiceCenter center = new ServiceCenter();

        System.out.println("空佇列處理：" + center.processNext());
        System.out.println("空歷程 undo：" + center.undoLastCompletion());
        System.out.println("取消不存在的 T999：" + center.cancelWaiting("T999"));

        System.out.println("開立 T101：" + center.createTicket("T101", "Amy"));
        System.out.println("開立 T102：" + center.createTicket("T102", "Ben"));
        System.out.println("開立 T103：" + center.createTicket("T103", "Cara"));
        System.out.println("開立 T104：" + center.createTicket("T104", "Dan"));
        System.out.println("重複 id T102：" + center.createTicket("T102", "Ben2"));
        System.out.println("空白 id：" + center.createTicket("   ", "Eva"));
        center.printSummary("開立後：");

        System.out.println("取消等待中的 T103：" + center.cancelWaiting("T103"));
        System.out.println("T103 狀態：" + center.findById("T103"));
        center.printSummary("取消後：");

        System.out.println("處理：" + center.processNext());
        System.out.println("處理：" + center.processNext());
        center.printSummary("處理兩筆後：");

        System.out.println("取消已完成的 T101：" + center.cancelWaiting("T101"));
        System.out.println("查詢 T101：" + center.findById("T101"));
        System.out.println("查詢 T999：" + center.findById("T999"));

        System.out.println("undo：" + center.undoLastCompletion());
        System.out.println("undo：" + center.undoLastCompletion());
        center.printSummary("連續兩次 undo 後：");
        System.out.println("再 undo：" + center.undoLastCompletion());

        System.out.println("重新處理：" + center.processNext());
        System.out.println("下一位：" + center.findById("T102"));
        center.printSummary("最終狀態：");
    }
}
