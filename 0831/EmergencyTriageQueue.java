import java.util.Comparator;
import java.util.PriorityQueue;

public class EmergencyTriageQueue {
    record Patient(String recordId, int severity, int arrivalOrder) {
        Patient {
            if (recordId == null || recordId.isBlank()) {
                throw new IllegalArgumentException("recordId");
            }
        }
    }

    private final PriorityQueue<Patient> queue = new PriorityQueue<>(
            Comparator.comparingInt(Patient::severity)
                    .reversed()
                    .thenComparingInt(Patient::arrivalOrder)
                    .thenComparing(Patient::recordId));

    public boolean checkIn(Patient patient) {
        if (patient == null) {
            return false;
        }
        return queue.offer(patient);
    }

    public Patient peekNext() {
        return queue.peek();
    }

    public Patient callNext() {
        return queue.poll();
    }

    public int waitingCount() {
        return queue.size();
    }

    public static void main(String[] args) {
        EmergencyTriageQueue triage = new EmergencyTriageQueue();

        System.out.println("空佇列下一位=" + triage.peekNext());
        System.out.println("空佇列叫號=" + triage.callNext());
        System.out.println("空佇列人數=" + triage.waitingCount());

        System.out.println("報到 M001=" + triage.checkIn(new Patient("M001", 2, 1)));
        System.out.println("報到 M002=" + triage.checkIn(new Patient("M002", 5, 2)));
        System.out.println("報到 M003=" + triage.checkIn(new Patient("M003", 5, 3)));
        System.out.println("報到 M004=" + triage.checkIn(new Patient("M004", 1, 4)));
        System.out.println("報到 M005=" + triage.checkIn(new Patient("M005", 3, 5)));
        System.out.println("報到 M006=" + triage.checkIn(new Patient("M006", 5, 1)));
        System.out.println("報到 null=" + triage.checkIn(null));

        System.out.println("目前人數=" + triage.waitingCount());
        System.out.println("下一位=" + triage.peekNext());

        while (triage.waitingCount() > 0) {
            Patient patient = triage.callNext();
            System.out.println("叫號=" + patient.recordId()
                    + "|" + patient.severity()
                    + "|" + patient.arrivalOrder()
                    + " 剩餘=" + triage.waitingCount());
        }

        System.out.println("清空後叫號=" + triage.callNext());
        System.out.println("清空後人數=" + triage.waitingCount());
    }
}
