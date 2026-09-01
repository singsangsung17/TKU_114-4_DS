import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class IntegerMinHeap {
    private final List<Integer> data = new ArrayList<>();

    public void add(int value) {
        data.add(value);
        int index = data.size() - 1;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data.get(parent) <= data.get(index)) break;
            swap(parent, index);
            index = parent;
        }
    }

    public int peek() {
        if (data.isEmpty()) throw new NoSuchElementException("heap is empty");
        return data.get(0);
    }

    public int removeMin() {
        if (data.isEmpty()) throw new NoSuchElementException("heap is empty");
        int result = data.get(0);
        int last = data.remove(data.size() - 1);
        if (!data.isEmpty()) {
            data.set(0, last);
            bubbleDown(0);
        }
        return result;
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            if (left >= data.size()) return;

            int smaller = left;
            if (right < data.size() && data.get(right) < data.get(left)) {
                smaller = right;
            }
            if (data.get(index) <= data.get(smaller)) return;
            swap(index, smaller);
            index = smaller;
        }
    }

    private void swap(int first, int second) {
        int temp = data.get(first);
        data.set(first, data.get(second));
        data.set(second, temp);
    }

    public static void main(String[] args) {
        IntegerMinHeap heap = new IntegerMinHeap();

        System.out.println("空 heap isEmpty=" + heap.isEmpty()
                + " size=" + heap.size());
        try {
            heap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("空 heap peek 例外：" + e.getMessage());
        }
        try {
            heap.removeMin();
        } catch (NoSuchElementException e) {
            System.out.println("空 heap removeMin 例外：" + e.getMessage());
        }

        for (int value : new int[]{30, 10, 20, 50, 40, 15, 10}) {
            heap.add(value);
        }
        System.out.println("size=" + heap.size() + " peek=" + heap.peek());

        List<Integer> removed = new ArrayList<>();
        while (!heap.isEmpty()) {
            removed.add(heap.removeMin());
        }
        System.out.println("移除順序=" + removed);

        boolean sorted = true;
        for (int i = 1; i < removed.size(); i++) {
            if (removed.get(i - 1) > removed.get(i)) {
                sorted = false;
            }
        }
        System.out.println("是否為非遞減順序=" + sorted);
        System.out.println("結束 size=" + heap.size()
                + " isEmpty=" + heap.isEmpty());
    }
}
