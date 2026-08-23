import java.util.Arrays;

class DynamicArray<T> {
    private Object[] data;
    private int size;

    DynamicArray(int initialCapacity) {
        data = new Object[Math.max(1, initialCapacity)];
    }

    void add(T value) {
        ensureCapacity();
        data[size] = value;
        size++;
    }

    void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index);
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    @SuppressWarnings("unchecked")
    T set(int index, T value) {
        checkIndex(index);
        T old = (T) data[index];
        data[index] = value;
        return old;
    }

    @SuppressWarnings("unchecked")
    T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        data[size] = null;
        return removed;
    }

    int size() {
        return size;
    }

    int capacity() {
        return data.length;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
            System.out.println("  resize -> " + data.length);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index);
        }
    }

    String rawState() {
        return Arrays.toString(data);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(data, size));
    }
}

public class DynamicArrayPractice {
    public static void main(String[] args) {
        DynamicArray<String> names = new DynamicArray<>(2);
        names.add("Amy");
        names.add("Ben");
        names.add("Cara");
        System.out.println("尾端新增：" + names
                + " size=" + names.size() + " capacity=" + names.capacity());

        names.add(1, "Zoe");
        System.out.println("index 1 插入：" + names);
        names.add(names.size(), "Finn");
        System.out.println("尾端插入：" + names
                + " size=" + names.size() + " capacity=" + names.capacity());

        System.out.println("get(2)：" + names.get(2));
        System.out.println("set(0, Ivy) 回傳舊值：" + names.set(0, "Ivy"));
        System.out.println("修改後：" + names);

        System.out.println("remove(1)：" + names.remove(1));
        System.out.println("刪除後：" + names);
        System.out.println("底層陣列：" + names.rawState());
        System.out.println("最後無效格已設為 null");

        try {
            names.get(-1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("get(-1) 例外：" + e.getMessage());
        }
        try {
            names.get(names.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("get(size) 例外：" + e.getMessage());
        }
        try {
            names.add(99, "Out");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("add(99) 例外：" + e.getMessage());
        }

        DynamicArray<Integer> empty = new DynamicArray<>(2);
        try {
            empty.remove(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("空結構 remove(0) 例外：" + e.getMessage());
        }

        DynamicArray<Integer> scores = new DynamicArray<>(1);
        scores.add(80);
        scores.add(90);
        scores.add(70);
        System.out.println("整數版：" + scores
                + " size=" + scores.size() + " capacity=" + scores.capacity());
        System.out.println("取出不需要 cast：" + (scores.get(0) + 5));
        System.out.println("remove(0)：" + scores.remove(0));
        System.out.println("刪除後：" + scores + " 底層=" + scores.rawState());
    }
}
