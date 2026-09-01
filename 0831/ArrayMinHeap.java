import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayMinHeap {
    private int[] data;
    private int size;

    public ArrayMinHeap(int initialCapacity) {
        data = new int[Math.max(1, initialCapacity)];
        size = 0;
    }

    public void add(int value) {
        ensureCapacity();
        data[size] = value;
        int index = size;
        size++;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data[parent] <= data[index]) break;
            swap(parent, index);
            index = parent;
        }
    }

    public int peek() {
        if (size == 0) throw new NoSuchElementException("heap is empty");
        return data[0];
    }

    public int remove() {
        if (size == 0) throw new NoSuchElementException("heap is empty");
        int result = data[0];
        size--;
        data[0] = data[size];
        data[size] = 0;
        bubbleDown(0);
        return result;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    public int[] snapshot() {
        return Arrays.copyOf(data, size);
    }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
            System.out.println("  resize -> " + data.length);
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            if (left >= size) return;

            int smaller = left;
            if (right < size && data[right] < data[left]) {
                smaller = right;
            }
            if (data[index] <= data[smaller]) return;
            swap(index, smaller);
            index = smaller;
        }
    }

    private void swap(int first, int second) {
        int temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {
        ArrayMinHeap heap = new ArrayMinHeap(2);

        try {
            heap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("空 heap peek 例外：" + e.getMessage());
        }
        try {
            heap.remove();
        } catch (NoSuchElementException e) {
            System.out.println("空 heap remove 例外：" + e.getMessage());
        }

        int[] values = {55, 12, 88, 3, 47, 21, 90, 15, 66, 8,
                31, 74, 5, 60, 39, 27, 82, 19, 44, 70, 12};
        System.out.println("加入 " + values.length + " 筆：");
        for (int value : values) {
            heap.add(value);
        }
        System.out.println("size=" + heap.size() + " capacity=" + heap.capacity());
        System.out.println("peek=" + heap.peek());
        System.out.println("snapshot=" + Arrays.toString(heap.snapshot()));

        System.out.print("依序移除=");
        while (heap.size() > 0) {
            System.out.print(heap.remove() + " ");
        }
        System.out.println();
        System.out.println("結束 size=" + heap.size()
                + " capacity=" + heap.capacity());
    }
}
