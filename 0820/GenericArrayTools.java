import java.util.Arrays;
import java.util.Objects;

public class GenericArrayTools {
    static <T> int countMatches(T[] data, T target) {
        if (data == null) {
            return 0;
        }
        int count = 0;
        for (T value : data) {
            if (Objects.equals(value, target)) {
                count++;
            }
        }
        return count;
    }

    static <T> T last(T[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return data[data.length - 1];
    }

    static <T> void swap(T[] data, int first, int second) {
        if (data == null) {
            return;
        }
        if (first < 0 || second < 0
                || first >= data.length || second >= data.length) {
            return;
        }
        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {
        String[] names = {"Amy", "Ben", "Amy", null, "Cara"};
        Integer[] scores = {82, 75, 91, 75};
        String[] empty = {};

        System.out.println("Amy 出現次數：" + countMatches(names, "Amy"));
        System.out.println("null 出現次數：" + countMatches(names, null));
        System.out.println("75 出現次數：" + countMatches(scores, 75));
        System.out.println("空陣列查詢：" + countMatches(empty, "Amy"));
        System.out.println("null 陣列查詢：" + countMatches(null, "Amy"));

        System.out.println("最後一個名字：" + last(names));
        System.out.println("最後一個分數：" + last(scores));
        System.out.println("空陣列最後一個：" + last(empty));
        System.out.println("null 陣列最後一個：" + last(null));

        swap(scores, 0, 3);
        System.out.println("交換 0 與 3：" + Arrays.toString(scores));
        swap(scores, 0, 99);
        System.out.println("index 超出範圍：" + Arrays.toString(scores));
        swap(scores, -1, 2);
        System.out.println("index 為負數：" + Arrays.toString(scores));
        swap(null, 0, 1);
        System.out.println("null 陣列交換不會發生例外");
    }
}
