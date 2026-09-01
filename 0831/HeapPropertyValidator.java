import java.util.List;

public class HeapPropertyValidator {
    public static boolean isMinHeap(List<Integer> data) {
        if (data == null) {
            return false;
        }
        for (int index = 0; index < data.size(); index++) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            if (left < data.size() && data.get(index) > data.get(left)) {
                return false;
            }
            if (right < data.size() && data.get(index) > data.get(right)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMaxHeap(List<Integer> data) {
        if (data == null) {
            return false;
        }
        for (int index = 0; index < data.size(); index++) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            if (left < data.size() && data.get(index) < data.get(left)) {
                return false;
            }
            if (right < data.size() && data.get(index) < data.get(right)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("null isMinHeap=" + isMinHeap(null));
        System.out.println("null isMaxHeap=" + isMaxHeap(null));

        System.out.println("empty isMinHeap=" + isMinHeap(List.of()));
        System.out.println("empty isMaxHeap=" + isMaxHeap(List.of()));

        System.out.println("單一元素 isMinHeap=" + isMinHeap(List.of(7)));
        System.out.println("單一元素 isMaxHeap=" + isMaxHeap(List.of(7)));

        List<Integer> minHeap = List.of(10, 30, 15, 50, 40, 20);
        System.out.println("minHeap isMinHeap=" + isMinHeap(minHeap));
        System.out.println("minHeap isMaxHeap=" + isMaxHeap(minHeap));

        List<Integer> maxHeap = List.of(50, 40, 50, 25, 30, 10);
        System.out.println("maxHeap isMinHeap=" + isMinHeap(maxHeap));
        System.out.println("maxHeap isMaxHeap=" + isMaxHeap(maxHeap));

        List<Integer> broken = List.of(10, 30, 15, 50, 5, 20);
        System.out.println("違規（index 4 小於 parent）isMinHeap=" + isMinHeap(broken));

        List<Integer> sorted = List.of(10, 20, 30, 40, 50);
        System.out.println("遞增序列 isMinHeap=" + isMinHeap(sorted));
        System.out.println("遞增序列 isMaxHeap=" + isMaxHeap(sorted));

        List<Integer> same = List.of(20, 20, 20, 20);
        System.out.println("全部相同 isMinHeap=" + isMinHeap(same));
        System.out.println("全部相同 isMaxHeap=" + isMaxHeap(same));
    }
}
