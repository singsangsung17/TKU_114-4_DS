import java.util.ArrayList;
import java.util.List;

public class Q04_ChainedHashTable {

    class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<List<Entry>> buckets = new ArrayList<>();
    private final int bucketCount;
    private int size = 0;

    public Q04_ChainedHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount must be positive");
        }
        this.bucketCount = bucketCount;
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    private int indexOf(int key) {
        int index = key % bucketCount;
        if (index < 0) {
            index += bucketCount;
        }
        return index;
    }

    public void put(int key, String value) {
        List<Entry> bucket = buckets.get(indexOf(key));
        for (Entry e : bucket) {
            if (e.key == key) {
                e.value = value;
                return;
            }
        }
        bucket.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        List<Entry> bucket = buckets.get(indexOf(key));
        for (Entry e : bucket) {
            if (e.key == key) {
                return e.value;
            }
        }
        return null;
    }

    public boolean remove(int key) {
        List<Entry> bucket = buckets.get(indexOf(key));
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).key == key) {
                bucket.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int longestChain() {
        int max = 0;
        for (List<Entry> bucket : buckets) {
            if (bucket.size() > max) {
                max = bucket.size();
            }
        }
        return max;
    }
}