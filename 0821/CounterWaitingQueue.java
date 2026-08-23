import java.util.ArrayDeque;
import java.util.Deque;

class Customer {
    private final String ticket;
    private final String name;

    Customer(String ticket, String name) {
        this.ticket = ticket == null || ticket.isBlank() ? "UNKNOWN" : ticket.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
    }

    @Override
    public String toString() {
        return ticket + " " + name;
    }
}

class CounterQueue {
    private final Deque<Customer> waiting = new ArrayDeque<>();

    boolean join(Customer customer) {
        if (customer == null) {
            return false;
        }
        return waiting.offerLast(customer);
    }

    Customer peekNext() {
        return waiting.peekFirst();
    }

    Customer serveNext() {
        return waiting.pollFirst();
    }

    int waitingCount() {
        return waiting.size();
    }

    boolean isEmpty() {
        return waiting.isEmpty();
    }

    @Override
    public String toString() {
        return waiting.toString();
    }
}

public class CounterWaitingQueue {
    static void serve(CounterQueue counter) {
        Customer customer = counter.serveNext();
        if (customer == null) {
            System.out.println("服務：目前沒有等候的顧客");
            return;
        }
        System.out.println("服務：" + customer
                + "（剩餘等候 " + counter.waitingCount() + " 位）");
    }

    public static void main(String[] args) {
        CounterQueue counter = new CounterQueue();

        System.out.println("空隊列下一位：" + counter.peekNext());
        serve(counter);

        System.out.println("加入 A101：" + counter.join(new Customer("A101", "Amy")));
        System.out.println("加入 A102：" + counter.join(new Customer("A102", "Ben")));
        System.out.println("加入 A103：" + counter.join(new Customer("A103", "Cara")));
        System.out.println("加入 null：" + counter.join(null));

        System.out.println("等候名單：" + counter);
        System.out.println("等候人數：" + counter.waitingCount());
        System.out.println("下一位：" + counter.peekNext());

        serve(counter);
        serve(counter);
        System.out.println("剩餘名單：" + counter);

        serve(counter);
        serve(counter);
        System.out.println("是否已清空：" + counter.isEmpty());
        System.out.println("等候人數：" + counter.waitingCount());
    }
}
