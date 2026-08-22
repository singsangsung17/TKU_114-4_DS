import java.util.ArrayList;
import java.util.List;

public class WildcardNumberTools {
    static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Number value : values) {
            total += value.doubleValue();
        }
        return total / values.size();
    }

    static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return Double.NaN;
        }
        double max = values.get(0).doubleValue();
        for (Number value : values) {
            if (value.doubleValue() > max) {
                max = value.doubleValue();
            }
        }
        return max;
    }

    static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null || start > end) {
            return;
        }
        for (int i = start; i <= end; i++) {
            target.add(i);
        }
    }

    public static void main(String[] args) {
        List<Integer> scores = new ArrayList<>(List.of(80, 90, 60, 70));
        List<Double> weights = new ArrayList<>(List.of(1.5, 2.5, 3.0));
        List<Integer> emptyScores = new ArrayList<>();

        System.out.println("整數平均：" + average(scores));
        System.out.println("小數平均：" + average(weights));
        System.out.println("整數最大：" + maximum(scores));
        System.out.println("小數最大：" + maximum(weights));

        System.out.println("空 list 平均：" + average(emptyScores));
        System.out.println("空 list 最大：" + maximum(emptyScores));

        List<Number> numbers = new ArrayList<>();
        addRange(numbers, 1, 5);
        System.out.println("加入 1 到 5：" + numbers);

        addRange(numbers, 8, 3);
        System.out.println("start 大於 end：" + numbers);

        addRange(scores, 100, 100);
        System.out.println("加入單一數值：" + scores);

        List<Object> objects = new ArrayList<>();
        addRange(objects, 1, 3);
        System.out.println("List<Object> 也能接收：" + objects);
    }
}
