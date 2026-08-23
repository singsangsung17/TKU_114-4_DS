import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

class Patient {
    private final String recordId;
    private final String name;

    Patient(String recordId, String name) {
        this.recordId = recordId == null || recordId.isBlank()
                ? "UNKNOWN" : recordId.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
    }

    String getRecordId() {
        return recordId;
    }

    @Override
    public String toString() {
        return recordId + " " + name;
    }
}

class ClinicQueue {
    private final Deque<Patient> waiting = new ArrayDeque<>();
    private final List<Patient> finished = new ArrayList<>();

    boolean register(Patient patient) {
        if (patient == null || findWaiting(patient.getRecordId()) != null) {
            return false;
        }
        return waiting.offerLast(patient);
    }

    boolean cancel(String recordId) {
        if (recordId == null || recordId.isBlank()) {
            return false;
        }
        Iterator<Patient> iterator = waiting.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getRecordId().equals(recordId.trim())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    Patient callNext() {
        Patient patient = waiting.pollFirst();
        if (patient != null) {
            finished.add(patient);
        }
        return patient;
    }

    Patient peekNext() {
        return waiting.peekFirst();
    }

    Patient findWaiting(String recordId) {
        for (Patient patient : waiting) {
            if (patient.getRecordId().equals(recordId)) {
                return patient;
            }
        }
        return null;
    }

    int waitingCount() {
        return waiting.size();
    }

    List<Patient> finishedToday() {
        return new ArrayList<>(finished);
    }

    @Override
    public String toString() {
        return waiting.toString();
    }
}

public class ClinicQueueSystem {
    public static void main(String[] args) {
        ClinicQueue clinic = new ClinicQueue();

        System.out.println("空隊列叫號：" + clinic.callNext());
        System.out.println("空隊列下一位：" + clinic.peekNext());

        System.out.println("掛號 M001：" + clinic.register(new Patient("M001", "Amy")));
        System.out.println("掛號 M002：" + clinic.register(new Patient("M002", "Ben")));
        System.out.println("掛號 M003：" + clinic.register(new Patient("M003", "Cara")));
        System.out.println("掛號 M004：" + clinic.register(new Patient("M004", "Dan")));
        System.out.println("重複掛號 M002：" + clinic.register(new Patient("M002", "Ben2")));
        System.out.println("掛號 null：" + clinic.register(null));

        System.out.println("等候名單：" + clinic + " 人數=" + clinic.waitingCount());

        System.out.println("取消 M003：" + clinic.cancel("M003"));
        System.out.println("取消不存在的 M999：" + clinic.cancel("M999"));
        System.out.println("取消空字串：" + clinic.cancel("   "));
        System.out.println("取消後名單：" + clinic + " 人數=" + clinic.waitingCount());

        System.out.println("下一位：" + clinic.peekNext());
        System.out.println("叫號：" + clinic.callNext());
        System.out.println("叫號：" + clinic.callNext());
        System.out.println("剩餘名單：" + clinic);

        System.out.println("查詢等候中的 M004：" + clinic.findWaiting("M004"));
        System.out.println("查詢已看診的 M001：" + clinic.findWaiting("M001"));

        System.out.println("叫號：" + clinic.callNext());
        System.out.println("再叫號：" + clinic.callNext());

        System.out.println("當日完成清單：" + clinic.finishedToday());
        System.out.println("完成人數：" + clinic.finishedToday().size()
                + " 等候人數：" + clinic.waitingCount());
    }
}
