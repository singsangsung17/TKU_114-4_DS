import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseTagReport {
    public static void main(String[] args) {
        String[] input = {"java", "tree", "java", "graph", "tree", "java", "  ", null};

        List<String> ordered = new ArrayList<>();
        Set<String> unique = new LinkedHashSet<>();
        Map<String, Integer> counts = new HashMap<>();

        for (String raw : input) {
            if (raw == null || raw.isBlank()) {
                continue;
            }
            String tag = raw.trim().toLowerCase();
            ordered.add(tag);
            unique.add(tag);
            counts.put(tag, counts.getOrDefault(tag, 0) + 1);
        }

        System.out.println("List 原始順序：" + ordered);
        System.out.println("  用途：保留輸入順序，允許重複，可用 index 取值");

        System.out.println("Set 不重複標籤：" + unique);
        System.out.println("  用途：自動去除重複，適合判斷某個標籤是否存在");

        System.out.println("Map 出現次數：" + counts);
        System.out.println("  用途：以標籤為 key 統計數量，查詢次數不需走訪整個 List");

        System.out.println("List 筆數=" + ordered.size()
                + " Set 筆數=" + unique.size()
                + " Map 筆數=" + counts.size());

        for (String tag : unique) {
            System.out.println(tag + " 次數：" + counts.get(tag));
        }

        System.out.println("查詢不存在的標籤：" + counts.getOrDefault("stack", 0));
        System.out.println("Set 是否包含 java：" + unique.contains("java"));
    }
}
