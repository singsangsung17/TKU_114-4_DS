import java.util.ArrayList;
import java.util.List;

public class CollisionBucketReport {
    private final List<List<Integer>> buckets = new ArrayList<>();

    public CollisionBucketReport(int bucketCount) {
        int count = Math.max(1, bucketCount);
        for (int i = 0; i < count; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    public void put(int key) {
        buckets.get(indexOf(key)).add(key);
    }

    private int indexOf(int key) {
        return Math.floorMod(key, buckets.size());
    }

    public void report() {
        int collisions = 0;
        int longest = 0;
        for (int i = 0; i < buckets.size(); i++) {
            List<Integer> bucket = buckets.get(i);
            System.out.println("  bucket " + i + " -> " + bucket
                    + " size=" + bucket.size());
            if (bucket.size() > 1) {
                collisions += bucket.size() - 1;
            }
            longest = Math.max(longest, bucket.size());
        }
        System.out.println("  collision 數量=" + collisions
                + " 最長 chain=" + longest);
    }

    public static void main(String[] args) {
        CollisionBucketReport table = new CollisionBucketReport(5);
        for (int key : new int[]{12, 7, 22, -3, 7, 40, 15, -8}) {
            table.put(key);
        }
        System.out.println("一般輸入：");
        table.report();

        CollisionBucketReport empty = new CollisionBucketReport(5);
        System.out.println("空輸入：");
        empty.report();

        CollisionBucketReport single = new CollisionBucketReport(1);
        for (int key : new int[]{10, 20, 30}) {
            single.put(key);
        }
        System.out.println("只有一個 bucket：");
        single.report();
    }
}
