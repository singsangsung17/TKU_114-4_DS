import java.util.Arrays;

class CircularQueue<T> {
    private final Object[] data;
    private int front;
    private int rear;
    private int size;

    CircularQueue(int capacity) {
        data = new Object[Math.max(1, capacity)];
    }

    boolean enqueue(T value) {
        if (value == null || isFull()) {
            return false;
        }
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T value = (T) data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) data[front];
    }

    int size() {
        return size;
    }

    int capacity() {
        return data.length;
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == data.length;
    }

    void printState(String action) {
        System.out.println(action);
        System.out.println("  " + Arrays.toString(data)
                + " front=" + front + " rear=" + rear + " size=" + size);
    }
}

public class CircularQueuePractice {
    public static void main(String[] args) {
        CircularQueue<String> queue = new CircularQueue<>(4);

        queue.printState("初始狀態：");

        queue.printState("enqueue A：" + queue.enqueue("A"));
        queue.printState("enqueue B：" + queue.enqueue("B"));
        queue.printState("enqueue C：" + queue.enqueue("C"));

        queue.printState("dequeue：" + queue.dequeue());
        queue.printState("dequeue：" + queue.dequeue());

        queue.printState("enqueue D：" + queue.enqueue("D"));
        queue.printState("enqueue E：" + queue.enqueue("E"));
        queue.printState("enqueue F：" + queue.enqueue("F"));

        queue.printState("dequeue：" + queue.dequeue());
        queue.printState("enqueue G：" + queue.enqueue("G"));

        System.out.println("目前 size=" + queue.size()
                + " capacity=" + queue.capacity()
                + " isFull=" + queue.isFull()
                + " peek=" + queue.peek());

        System.out.println("依 FIFO 取出全部：");
        while (!queue.isEmpty()) {
            System.out.println("  取出 " + queue.dequeue());
        }
        queue.printState("清空後：");
        System.out.println("空佇列 dequeue：" + queue.dequeue());
        System.out.println("空佇列 peek：" + queue.peek());
    }
}
