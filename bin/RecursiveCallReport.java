public class RecursiveCallReport {
    static int sum(int[] data, int index) {
        if (index >= data.length) {
            System.out.println("index=" + index + " 超出範圍 return 0");
            return 0;
        }
        System.out.println("index=" + index + " current=" + data[index]);
        int recursiveResult = sum(data, index + 1);
        int returnValue = data[index] + recursiveResult;
        System.out.println("index=" + index
                + " current=" + data[index]
                + " recursiveResult=" + recursiveResult
                + " return=" + returnValue);
        return returnValue;
    }

    public static void main(String[] args) {
        int[] normal = {4, 7, 2};
        System.out.println("一般陣列：");
        System.out.println("答案=" + sum(normal, 0));

        int[] single = {9};
        System.out.println("單一元素：");
        System.out.println("答案=" + sum(single, 0));

        int[] empty = {};
        System.out.println("空陣列：");
        System.out.println("答案=" + sum(empty, 0));
    }
}
