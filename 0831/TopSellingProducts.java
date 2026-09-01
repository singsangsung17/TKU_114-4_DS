import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopSellingProducts {
    record Product(String id, int sales) {
        Product {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("id");
            }
        }
    }

    static List<Product> topK(List<Product> input, int k) {
        List<Product> result = new ArrayList<>();
        if (input == null || k <= 0) {
            return result;
        }

        Map<String, Integer> merged = new LinkedHashMap<>();
        for (Product product : input) {
            if (product == null) {
                continue;
            }
            merged.put(product.id(),
                    merged.getOrDefault(product.id(), 0) + product.sales());
        }

        Comparator<Product> worstFirst = Comparator
                .comparingInt(Product::sales)
                .thenComparing(Product::id, Comparator.reverseOrder());
        PriorityQueue<Product> heap = new PriorityQueue<>(worstFirst);

        for (Map.Entry<String, Integer> entry : merged.entrySet()) {
            Product product = new Product(entry.getKey(), entry.getValue());
            if (heap.size() < k) {
                heap.offer(product);
            } else if (worstFirst.compare(product, heap.peek()) > 0) {
                heap.poll();
                heap.offer(product);
            }
        }

        result.addAll(heap);
        result.sort(Comparator.comparingInt(Product::sales)
                .reversed()
                .thenComparing(Product::id));
        return result;
    }

    public static void main(String[] args) {
        List<Product> input = List.of(
                new Product("P103", 120),
                new Product("P101", 300),
                new Product("P105", 250),
                new Product("P103", 80),
                new Product("P102", 250),
                new Product("P104", 90),
                new Product("P101", 50));

        System.out.println("top 3=" + topK(input, 3));
        System.out.println("top 1=" + topK(input, 1));
        System.out.println("top 99=" + topK(input, 99));
        System.out.println("top 0=" + topK(input, 0));
        System.out.println("top -1=" + topK(input, -1));
        System.out.println("null 輸入=" + topK(null, 3));
        System.out.println("空輸入=" + topK(List.of(), 3));
    }
}
