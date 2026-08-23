class ArrayStack<T> {
    private final Object[] data;
    private int size;

    ArrayStack(int capacity) {
        data = new Object[Math.max(1, capacity)];
    }

    boolean push(T value) {
        if (value == null || isFull()) {
            return false;
        }
        data[size] = value;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    T pop() {
        if (isEmpty()) {
            return null;
        }
        size--;
        T value = (T) data[size];
        data[size] = null;
        return value;
    }

    @SuppressWarnings("unchecked")
    T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) data[size - 1];
    }

    int size() {
        return size;
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == data.length;
    }

    int capacity() {
        return data.length;
    }
}

public class GenericArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack<String> texts = new ArrayStack<>(3);

        System.out.println("push A：" + texts.push("A"));
        System.out.println("push B：" + texts.push("B"));
        System.out.println("push C：" + texts.push("C"));
        System.out.println("push D（已滿）：" + texts.push("D"));
        System.out.println("push null：" + texts.push(null));
        System.out.println("isFull：" + texts.isFull()
                + " size=" + texts.size() + " capacity=" + texts.capacity());

        System.out.println("peek：" + texts.peek());
        System.out.println("pop：" + texts.pop());
        System.out.println("取出後不需要 cast：" + texts.peek().toLowerCase());
        System.out.println("pop：" + texts.pop());
        System.out.println("pop：" + texts.pop());
        System.out.println("空 stack pop：" + texts.pop());
        System.out.println("空 stack peek：" + texts.peek());
        System.out.println("isEmpty：" + texts.isEmpty());

        ArrayStack<Integer> numbers = new ArrayStack<>(2);
        System.out.println("push 10：" + numbers.push(10));
        System.out.println("push 20：" + numbers.push(20));
        System.out.println("push 30（已滿）：" + numbers.push(30));
        System.out.println("pop 後加 5：" + (numbers.pop() + 5));
        System.out.println("size：" + numbers.size());
    }
}
