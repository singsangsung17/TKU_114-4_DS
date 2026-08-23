import java.util.ArrayDeque;
import java.util.Deque;

class TextEditor {
    private final Deque<String> undoStack = new ArrayDeque<>();
    private final Deque<String> redoStack = new ArrayDeque<>();

    boolean apply(String action) {
        if (action == null || action.isBlank()) {
            return false;
        }
        undoStack.push(action.trim());
        redoStack.clear();
        return true;
    }

    String undo() {
        if (undoStack.isEmpty()) {
            return "NOTHING_TO_UNDO";
        }
        String action = undoStack.pop();
        redoStack.push(action);
        return action;
    }

    String redo() {
        if (redoStack.isEmpty()) {
            return "NOTHING_TO_REDO";
        }
        String action = redoStack.pop();
        undoStack.push(action);
        return action;
    }

    String current() {
        String action = undoStack.peek();
        return action == null ? "EMPTY" : action;
    }

    void printState(String title) {
        System.out.println(title);
        System.out.println("  undo=" + undoStack + " redo=" + redoStack
                + " current=" + current());
    }
}

public class TextEditorHistory {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();

        editor.printState("初始狀態：");
        System.out.println("空狀態 undo：" + editor.undo());
        System.out.println("空狀態 redo：" + editor.redo());

        System.out.println("操作 Open file：" + editor.apply("Open file"));
        System.out.println("操作 Type title：" + editor.apply("Type title"));
        System.out.println("操作 Delete line：" + editor.apply("Delete line"));
        System.out.println("操作 空白：" + editor.apply("   "));
        editor.printState("三次操作後：");

        System.out.println("undo：" + editor.undo());
        editor.printState("第一次 undo 後：");
        System.out.println("undo：" + editor.undo());
        editor.printState("第二次 undo 後：");

        System.out.println("redo：" + editor.redo());
        editor.printState("redo 後：");

        System.out.println("新操作 Insert image：" + editor.apply("Insert image"));
        editor.printState("新操作後（redo 應被清空）：");
        System.out.println("再按 redo：" + editor.redo());

        System.out.println("undo：" + editor.undo());
        System.out.println("undo：" + editor.undo());
        System.out.println("undo：" + editor.undo());
        System.out.println("undo：" + editor.undo());
        editor.printState("全部復原後：");
    }
}
