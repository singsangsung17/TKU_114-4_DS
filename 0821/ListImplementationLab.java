import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListImplementationLab {
    static void appendAll(List<Integer> values, int[] source) {
        for (int value : source) {
            values.add(value);
        }
    }

    static boolean insertAt(List<Integer> values, int index, int value) {
        if (index < 0 || index > values.size()) {
            return false;
        }
        values.add(index, value);
        return true;
    }

    static int search(List<Integer> values, int target) {
        return values.indexOf(target);
    }

    static Integer removeAt(List<Integer> values, int index) {
        if (index < 0 || index >= values.size()) {
            return null;
        }
        return values.remove(index);
    }

    static int sum(List<Integer> values) {
        int total = 0;
        for (int value : values) {
            total += value;
        }
        return total;
    }

    static void runAll(String title, List<Integer> values) {
        System.out.println(title);
        appendAll(values, new int[]{10, 20, 30, 40});
        System.out.println("  尾端新增：" + values);
        System.out.println("  index 1 插入 99：" + insertAt(values, 1, 99));
        System.out.println("  插入後：" + values);
        System.out.println("  index 99 插入：" + insertAt(values, 99, 55));
        System.out.println("  搜尋 30：" + search(values, 30));
        System.out.println("  搜尋 77：" + search(values, 77));
        System.out.println("  刪除 index 2：" + removeAt(values, 2));
        System.out.println("  刪除 index -1：" + removeAt(values, -1));
        System.out.println("  刪除後：" + values);
        System.out.println("  總和：" + sum(values));
    }

    public static void main(String[] args) {
        runAll("ArrayList", new ArrayList<>());
        runAll("LinkedList", new LinkedList<>());

        System.out.println("兩者功能結果完全相同，差別在內部成本：");
        System.out.println("  ArrayList 底層是陣列，get(index) 直接算位址，成本固定；");
        System.out.println("  但中間插入或刪除要搬移後面所有元素，資料越多越慢。");
        System.out.println("  LinkedList 底層是節點串接，插入刪除只改前後指標；");
        System.out.println("  但 get(index) 必須從頭一個一個走，index 越後面越慢。");
        System.out.println("  以本題為例，隨機讀取多選 ArrayList，頻繁在頭尾增刪選 LinkedList。");
    }
}
