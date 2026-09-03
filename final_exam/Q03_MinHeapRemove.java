import java.util.ArrayList;
import java.util.List;

public class Q03_MinHeapRemove {

    private final ArrayList<Integer> heap = new ArrayList<>();

    public Q03_MinHeapRemove(java.util.List<Integer> values) {
        if (values != null) {
            for (Integer v : values) {
                if (v != null) {
                    heap.add(v);
                }
            }
        }
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    public Integer removeMin() {
        if (heap.isEmpty()) {
            return null;
        }
        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            bubbleDown(0);
        }
        return min;
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

    private void bubbleDown(int i) {
        int n = heap.size();
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;
            if (left < n && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }
            if (right < n && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }
            if (smallest == i) {
                break;
            }
            int tmp = heap.get(i);
            heap.set(i, heap.get(smallest));
            heap.set(smallest, tmp);
            i = smallest;
        }
    }
}