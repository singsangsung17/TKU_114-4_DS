public class RecursiveTextTools {
    static String reverse(String text) {
        if (text == null || text.length() <= 1) {
            return text;
        }
        return reverse(text.substring(1)) + text.charAt(0);
    }

    static boolean isPalindrome(String text) {
        if (text == null) {
            return false;
        }
        return checkPalindrome(text.replace(" ", "").toLowerCase());
    }

    private static boolean checkPalindrome(String text) {
        if (text.length() <= 1) {
            return true;
        }
        if (text.charAt(0) != text.charAt(text.length() - 1)) {
            return false;
        }
        return checkPalindrome(text.substring(1, text.length() - 1));
    }

    static int countCharacter(String text, char target) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        int current = text.charAt(0) == target ? 1 : 0;
        return current + countCharacter(text.substring(1), target);
    }

    public static void main(String[] args) {
        System.out.println("reverse(\"\")=" + reverse(""));
        System.out.println("reverse(\"A\")=" + reverse("A"));
        System.out.println("reverse(\"Level\")=" + reverse("Level"));
        System.out.println("reverse(\"recursion\")=" + reverse("recursion"));

        System.out.println("isPalindrome(\"\")=" + isPalindrome(""));
        System.out.println("isPalindrome(\"A\")=" + isPalindrome("A"));
        System.out.println("isPalindrome(\"Level\")=" + isPalindrome("Level"));
        System.out.println("isPalindrome(\"never odd or even\")="
                + isPalindrome("never odd or even"));
        System.out.println("isPalindrome(\"recursion\")=" + isPalindrome("recursion"));

        System.out.println("countCharacter(\"recursion\", 'r')="
                + countCharacter("recursion", 'r'));
        System.out.println("countCharacter(\"recursion\", 'z')="
                + countCharacter("recursion", 'z'));
        System.out.println("countCharacter(\"\", 'a')=" + countCharacter("", 'a'));
    }
}
