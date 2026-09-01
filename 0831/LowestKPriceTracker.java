import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LowestKPriceTracker {
    private final int capacity;
    private final PriorityQueue<Integer> heap =
            new PriorityQueue<>(Comparator.reverseOrder());

    public LowestKPriceTracker(int capacity) {
        this.capacity = capacity;
    }

    public void offer(Integer price) {
        if (capacity <= 0 || price == null || price < 0) {
            return;
        }
        if (heap.size() < capacity) {
            heap.offer(price);
            return;
        }
        if (price < heap.peek()) {
            heap.poll();
            heap.offer(price);
        }
    }

    public List<Integer> lowest() {
        List<Integer> result = new ArrayList<>(heap);
        Collections.sort(result);
        return result;
    }

    public static void main(String[] args) {
        LowestKPriceTracker tracker = new LowestKPriceTracker(3);
        Integer[] prices = {450, 120, null, 890, -50, 300, 75, 200};
        for (Integer price : prices) {
            tracker.offer(price);
            System.out.println("offer=" + price + " lowest=" + tracker.lowest());
        }
        System.out.println("結果=" + tracker.lowest());

        LowestKPriceTracker zero = new LowestKPriceTracker(0);
        zero.offer(100);
        zero.offer(50);
        System.out.println("K=0 結果=" + zero.lowest());

        LowestKPriceTracker negative = new LowestKPriceTracker(-2);
        negative.offer(100);
        System.out.println("K=-2 結果=" + negative.lowest());

        LowestKPriceTracker empty = new LowestKPriceTracker(3);
        empty.offer(null);
        empty.offer(-1);
        System.out.println("只有無效資料 結果=" + empty.lowest());
    }
}
