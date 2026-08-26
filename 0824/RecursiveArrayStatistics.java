public class RecursiveArrayStatistics {
    static int maximum(int[] values) {
        checkArray(values);
        return maximumFrom(values, 0);
    }

    static int minimum(int[] values) {
        checkArray(values);
        return minimumFrom(values, 0);
    }

    static int countAbove(int[] values, int threshold) {
        checkArray(values);
        return countAboveFrom(values, 0, threshold);
    }

    private static void checkArray(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("values is null or empty");
        }
    }

    private static int maximumFrom(int[] values, int index) {
        if (index == values.length - 1) {
            return values[index];
        }
        return Math.max(values[index], maximumFrom(values, index + 1));
    }

    private static int minimumFrom(int[] values, int index) {
        if (index == values.length - 1) {
            return values[index];
        }
        return Math.min(values[index], minimumFrom(values, index + 1));
    }

    private static int countAboveFrom(int[] values, int index, int threshold) {
        if (index >= values.length) {
            return 0;
        }
        int current = values[index] > threshold ? 1 : 0;
        return current + countAboveFrom(values, index + 1, threshold);
    }

    public static void main(String[] args) {
        int[] values = {4, 17, -3, 9, 17};

        System.out.println("maximum=" + maximum(values));
        System.out.println("minimum=" + minimum(values));
        System.out.println("countAbove(8)=" + countAbove(values, 8));
        System.out.println("countAbove(20)=" + countAbove(values, 20));

        int[] single = {6};
        System.out.println("single maximum=" + maximum(single));
        System.out.println("single minimum=" + minimum(single));

        try {
            maximum(null);
        } catch (IllegalArgumentException e) {
            System.out.println("null 例外：" + e.getMessage());
        }

        try {
            minimum(new int[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("empty 例外：" + e.getMessage());
        }
    }
}
