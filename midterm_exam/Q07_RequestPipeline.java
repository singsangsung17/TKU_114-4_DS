package midterm_exam;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {
    public static boolean isBalanced(String text) {
        if (text == null) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<Character>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char open = stack.pop();
                if (c == ')' && open != '(') {
                    return false;
                }
                if (c == ']' && open != '[') {
                    return false;
                }
                if (c == '}' && open != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static java.util.List<String> process(String[] commands) {
        List<String> result = new ArrayList<String>();
        if (commands == null) {
            return result;
        }
        Deque<String> normal = new ArrayDeque<String>();
        Deque<String> urgent = new ArrayDeque<String>();
        for (String command : commands) {
            if (command == null) {
                continue;
            }
            String trimmed = command.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            String[] parts = trimmed.split("\\s+");
            if (parts[0].equals("PROCESS")) {
                if (parts.length != 1) {
                    continue;
                }
                if (!urgent.isEmpty()) {
                    result.add(urgent.poll());
                } else if (!normal.isEmpty()) {
                    result.add(normal.poll());
                } else {
                    result.add("EMPTY");
                }
            } else if (parts[0].equals("NORMAL")) {
                if (parts.length != 2) {
                    continue;
                }
                normal.offer(parts[1]);
            } else if (parts[0].equals("URGENT")) {
                if (parts.length != 2) {
                    continue;
                }
                urgent.offer(parts[1]);
            }
        }
        return result;
    }
}
