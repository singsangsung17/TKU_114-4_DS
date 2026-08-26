package midterm_exam;

public class Q08_RecursiveAudit {
    public static int sumValid(int[] data, int index) {
        if (data == null) {
            return 0;
        }
        if (index < 0) {
            index = 0;
        }
        if (index >= data.length) {
            return 0;
        }
        int current = 0;
        if (data[index] >= 0 && data[index] <= 100) {
            current = data[index];
        }
        return current + sumValid(data, index + 1);
    }

    public static int countOccurrences(int[] data, int index, int target) {
        if (data == null) {
            return 0;
        }
        if (index < 0) {
            index = 0;
        }
        if (index >= data.length) {
            return 0;
        }
        int current = 0;
        if (data[index] == target) {
            current = 1;
        }
        return current + countOccurrences(data, index + 1, target);
    }

    public static boolean isPalindrome(String text, int left, int right) {
        if (text == null) {
            return false;
        }
        if (left >= right) {
            return true;
        }
        char a = Character.toLowerCase(text.charAt(left));
        char b = Character.toLowerCase(text.charAt(right));
        if (a != b) {
            return false;
        }
        return isPalindrome(text, left + 1, right - 1);
    }
}