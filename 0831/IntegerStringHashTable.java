import java.util.ArrayList;
import java.util.List;

public class IntegerStringHashTable {
    static class Entry {
        final int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private final List<List<Entry>> buckets = new ArrayList<>();
    private int size;

    public IntegerStringHashTable(int bucketCount) {
        int count = Math.max(1, bucketCount);
        for (int i = 0; i < count; i++) {
            buckets.add(new ArrayList<>());
        }
        size = 0;
    }

    public void put(int key, String value) {
        List<Entry> bucket = buckets.get(indexOf(key));
        for (Entry entry : bucket) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }
        bucket.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        for (Entry entry : buckets.get(indexOf(key))) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean containsKey(int key) {
        for (Entry entry : buckets.get(indexOf(key))) {
            if (entry.key == key) {
                return true;
            }
        }
        return false;
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

    public void bucketReport() {
        for (int i = 0; i < buckets.size(); i++) {
            System.out.println("  bucket " + i + " -> " + buckets.get(i));
        }
    }

    private int indexOf(int key) {
        return Math.floorMod(key, buckets.size());
    }

    public static void main(String[] args) {
        IntegerStringHashTable table = new IntegerStringHashTable(5);

        table.put(12, "A");
        table.put(7, "B");
        table.put(22, "C");
        table.put(-3, "D");
        table.put(40, "E");
        System.out.println("size=" + table.size());

        table.put(7, "B2");
        System.out.println("更新 key 7 後 size=" + table.size());
        System.out.println("get(7)=" + table.get(7));

        System.out.println("get(12)=" + table.get(12));
        System.out.println("get(-3)=" + table.get(-3));
        System.out.println("get(999)=" + table.get(999));

        System.out.println("containsKey(40)=" + table.containsKey(40));
        System.out.println("containsKey(999)=" + table.containsKey(999));

        System.out.println("bucket 分布：");
        table.bucketReport();

        System.out.println("remove(12)=" + table.remove(12));
        System.out.println("remove(12) 再一次=" + table.remove(12));
        System.out.println("remove(999)=" + table.remove(999));
        System.out.println("刪除後 size=" + table.size());

        System.out.println("bucket 分布：");
        table.bucketReport();
    }
}
