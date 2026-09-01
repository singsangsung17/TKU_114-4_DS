import java.util.Comparator;
import java.util.PriorityQueue;

public class SupportTicketQueue {
    record Ticket(String id, int severity, int createdOrder) {
        Ticket {
            if (id == null || id.isBlank()) throw new IllegalArgumentException("id");
        }
    }

    public static void main(String[] args) {
        Comparator<Ticket> order = Comparator
                .comparingInt(Ticket::severity)
                .reversed()
                .thenComparingInt(Ticket::createdOrder);

        PriorityQueue<Ticket> tickets = new PriorityQueue<>(order);
        tickets.offer(new Ticket("T-101", 2, 1));
        tickets.offer(new Ticket("T-102", 5, 2));
        tickets.offer(new Ticket("T-103", 5, 3));
        tickets.offer(new Ticket("T-104", 1, 4));
        tickets.offer(new Ticket("T-105", 3, 5));
        tickets.offer(new Ticket("T-106", 5, 1));

        while (!tickets.isEmpty()) {
            Ticket ticket = tickets.poll();
            System.out.println(ticket.id() + "|" + ticket.severity()
                    + "|" + ticket.createdOrder());
        }
    }
}
