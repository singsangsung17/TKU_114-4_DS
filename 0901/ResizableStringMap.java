import java.util.ArrayList;
import java.util.List;

public class ResizableStringMap {
    private record Entry(String key, String value) {}

    private List<List<Entry>> buckets;
    private int size;

    public ResizableStringMap(int bucketCount) {
        if (bucketCount <= 0) throw new IllegalArgumentException("bucketCount");
        buckets = newBuckets(bucketCount);
    }

    private static List<List<Entry>> newBuckets(int count) {
        List<List<Entry>> list = new ArrayList<>();
        for (int i = 0; i < count; i++) list.add(new ArrayList<>());
        return list;
    }

    private int index(String key, int bucketCount) {
        return Math.floorMod(key.hashCode(), bucketCount);
    }

    public void put(String key, String value) {
        if (key == null) throw new IllegalArgumentException("key");
        List<Entry> chain = buckets.get(index(key, buckets.size()));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key().equals(key)) {
                chain.set(i, new Entry(key, value));
                return;
            }
        }
        chain.add(new Entry(key, value));
        size++;
        if (loadFactor() > 0.75) rehash();
    }

    public String get(String key) {
        if (key == null) return null;
        for (Entry entry : buckets.get(index(key, buckets.size()))) {
            if (entry.key().equals(key)) return entry.value();
        }
        return null;
    }

    public boolean remove(String key) {
        if (key == null) return false;
        List<Entry> chain = buckets.get(index(key, buckets.size()));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key().equals(key)) {
                chain.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    private void rehash() {
        int newCount = buckets.size() * 2 + 1;
        List<List<Entry>> old = buckets;
        buckets = newBuckets(newCount);
        for (List<Entry> chain : old) {
            for (Entry entry : chain) {
                buckets.get(index(entry.key(), newCount)).add(entry);
            }
        }
    }

    public int size() {
        return size;
    }

    public int bucketCount() {
        return buckets.size();
    }

    public double loadFactor() {
        return (double) size / buckets.size();
    }

    public void printBuckets() {
        for (int i = 0; i < buckets.size(); i++) {
            System.out.println(i + " -> " + buckets.get(i));
        }
    }

    public static void main(String[] args) {
        ResizableStringMap map = new ResizableStringMap(4);
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        map.put("a", "9");
        System.out.println("size=" + map.size());
        System.out.println("buckets=" + map.bucketCount());
        System.out.printf("load=%.2f%n", map.loadFactor());
        System.out.println("get a=" + map.get("a"));
        System.out.println("remove b=" + map.remove("b"));
        System.out.println("get b=" + map.get("b"));
        map.printBuckets();
    }
}
