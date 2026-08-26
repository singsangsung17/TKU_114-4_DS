public class RecursiveDigitReport {
    static int digitSum(int number) {
        int value = Math.abs(number);
        if (value < 10) {
            return value;
        }
        return value % 10 + digitSum(value / 10);
    }

    static int digitCount(int number) {
        int value = Math.abs(number);
        if (value < 10) {
            return 1;
        }
        return 1 + digitCount(value / 10);
    }

    static int countDigit(int number, int digit) {
        int value = Math.abs(number);
        int current = value % 10 == digit ? 1 : 0;
        if (value < 10) {
            return current;
        }
        return current + countDigit(value / 10, digit);
    }

    public static void main(String[] args) {
        System.out.println("50205 digitSum=" + digitSum(50205));
        System.out.println("50205 digitCount=" + digitCount(50205));
        System.out.println("50205 countDigit(0)=" + countDigit(50205, 0));
        System.out.println("50205 countDigit(5)=" + countDigit(50205, 5));

        System.out.println("0 digitSum=" + digitSum(0));
        System.out.println("0 digitCount=" + digitCount(0));
        System.out.println("0 countDigit(0)=" + countDigit(0, 0));

        System.out.println("-731 digitSum=" + digitSum(-731));
        System.out.println("-731 digitCount=" + digitCount(-731));
        System.out.println("-731 countDigit(7)=" + countDigit(-731, 7));
    }
}
