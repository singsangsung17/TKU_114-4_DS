import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Amy");
        names.add("Ben");
        names.add("  ");
        names.add("Amy");
        names.add(null);
        names.add("Cara");
        names.add("ben");
        names.add("");
        names.add("Cara");
        names.add(null);

        List<String> original = new ArrayList<>(names);
        System.out.println("清理前（" + original.size() + " 筆）：" + original);

        Iterator<String> iterator = names.iterator();
        int removed = 0;
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name == null || name.isBlank()) {
                iterator.remove();
                removed++;
            }
        }

        System.out.println("移除不合法資料：" + removed + " 筆");
        System.out.println("清理後（" + names.size() + " 筆）：" + names);

        Set<String> seen = new HashSet<>();
        Set<String> duplicated = new LinkedHashSet<>();
        for (String name : names) {
            if (!seen.add(name.toLowerCase())) {
                duplicated.add(name.toLowerCase());
            }
        }

        System.out.println("不重複姓名（" + seen.size() + " 位）：" + seen);
        System.out.println("重複報告：");
        if (duplicated.isEmpty()) {
            System.out.println("  無重複姓名");
        }
        for (String name : duplicated) {
            int count = 0;
            for (String candidate : names) {
                if (candidate.equalsIgnoreCase(name)) {
                    count++;
                }
            }
            System.out.println("  " + name + " 出現 " + count + " 次");
        }

        System.out.println("原始 list 不受影響：" + original);
    }
}
