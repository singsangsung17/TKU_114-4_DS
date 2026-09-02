import java.util.ArrayList;
import java.util.List;

public class BookIsbnHashTable {
    private record Entry(String isbn, String title) {}

    private final List<List<Entry>> buckets;
    private int size;

    public BookIsbnHashTable(int bucketCount) {
        if (bucketCount <= 0) throw new IllegalArgumentException("bucketCount");
        buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) buckets.add(new ArrayList<>());
    }

    private int index(String isbn) {
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("isbn");
        return Math.floorMod(isbn.hashCode(), buckets.size());
    }

    public void put(String isbn, String title) {
        List<Entry> chain = buckets.get(index(isbn));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).isbn().equals(isbn)) {
                chain.set(i, new Entry(isbn, title));
                return;
            }
        }
        chain.add(new Entry(isbn, title));
        size++;
    }

    public String get(String isbn) {
        for (Entry entry : buckets.get(index(isbn))) {
            if (entry.isbn().equals(isbn)) return entry.title();
        }
        return null;
    }

    public boolean remove(String isbn) {
        List<Entry> chain = buckets.get(index(isbn));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).isbn().equals(isbn)) {
                chain.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public double loadFactor() {
        return (double) size / buckets.size();
    }

    public void bucketReport() {
        for (int i = 0; i < buckets.size(); i++) {
            List<String> isbns = new ArrayList<>();
            for (Entry entry : buckets.get(i)) isbns.add(entry.isbn());
            System.out.println(i + " -> " + isbns);
        }
    }

    public static void main(String[] args) {
        BookIsbnHashTable table = new BookIsbnHashTable(5);
        table.put("978001", "Java 入門");
        table.put("978002", "資料結構");
        table.put("978003", "演算法");
        table.put("978001", "Java 入門 第二版");
        System.out.println("size=" + table.size());
        System.out.println("get 978001=" + table.get("978001"));
        System.out.println("get 999999=" + table.get("999999"));
        System.out.println("remove 978002=" + table.remove("978002"));
        System.out.println("remove 978002=" + table.remove("978002"));
        System.out.println("size=" + table.size());
        System.out.printf("load=%.2f%n", table.loadFactor());
        table.bucketReport();
    }
}
