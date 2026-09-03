import java.util.ArrayList;
import java.util.List;

public class Q02_MinHeapInsert {

    private final ArrayList<Integer> heap = new ArrayList<>();

    public void add(int value) {
        heap.add(value);
        int i = heap.size() - 1;
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(i) < heap.get(parent)) {
                int tmp = heap.get(i);
                heap.set(i, heap.get(parent));
                heap.set(parent, tmp);
                i = parent;
            } else {
                break;
            }
        }
    }

    public Integer peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public java.util.List<Integer> snapshot() {
        return new ArrayList<>(heap);
    }

    public boolean isValidMinHeap() {
        for (int i = 0; i < heap.size(); i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < heap.size() && heap.get(i) > heap.get(left)) {
                return false;
            }
            if (right < heap.size() && heap.get(i) > heap.get(right)) {
                return false;
            }
        }
        return true;
    }
}