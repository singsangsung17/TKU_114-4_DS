import java.util.ArrayList;
import java.util.List;

public class StudentIdHashAnalysis {
    static void analyze(String title, int[] studentIds, int bucketCount) {
        int count = Math.max(1, bucketCount);
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            buckets.add(new ArrayList<>());
        }
        for (int id : studentIds) {
            buckets.get(Math.floorMod(id, count)).add(id);
        }

        int collisions = 0;
        int longest = 0;
        int usedBuckets = 0;
        System.out.println(title + "（bucket count=" + count + "）");
        for (int i = 0; i < count; i++) {
            List<Integer> bucket = buckets.get(i);
            System.out.println("  bucket " + i + " 筆數=" + bucket.size()
                    + " " + bucket);
            if (bucket.size() > 1) {
                collisions += bucket.size() - 1;
            }
            if (!bucket.isEmpty()) {
                usedBuckets++;
            }
            longest = Math.max(longest, bucket.size());
        }

        double average = usedBuckets == 0
                ? 0.0 : (double) studentIds.length / usedBuckets;
        System.out.println("  總 collision=" + collisions
                + " 最大 chain=" + longest
                + " 使用中的 bucket=" + usedBuckets
                + " 平均 chain 長度=" + String.format("%.2f", average));
    }

    public static void main(String[] args) {
        int[] studentIds = {
            411630001, 411630002, 411630003, 411630004, 411630005,
            411630010, 411630015, 411630020, 411630025, 411630030,
            411640001, 411640002, 411640011, 411640022, 411640033
        };

        analyze("方案一", studentIds, 5);
        analyze("方案二", studentIds, 11);

        analyze("空輸入", new int[0], 5);
    }
}
