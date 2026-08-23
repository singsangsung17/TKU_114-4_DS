import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionChoiceReport {
    static void printChoice(int number, String requirement,
                            String type, String implementation, String reason) {
        System.out.println("需求 " + number + "：" + requirement);
        System.out.println("  interface=" + type
                + " implementation=" + implementation);
        System.out.println("  理由：" + reason);
    }

    public static void main(String[] args) {
        printChoice(1, "保留搜尋紀錄且允許重複",
                "List<String>", "ArrayList<>",
                "需要保留輸入順序且同一個關鍵字可以重複出現");
        List<String> searchLog = new ArrayList<>();
        searchLog.add("java");
        searchLog.add("stack");
        searchLog.add("java");
        System.out.println("  搜尋紀錄=" + searchLog
                + " 筆數=" + searchLog.size()
                + " 最近一筆=" + searchLog.get(searchLog.size() - 1));

        printChoice(2, "保存不重複會員編號",
                "Set<String>", "HashSet<>",
                "重複加入會自動被拒絕，判斷是否存在的成本低");
        Set<String> memberIds = new HashSet<>();
        System.out.println("  加入 M001=" + memberIds.add("M001"));
        System.out.println("  加入 M002=" + memberIds.add("M002"));
        System.out.println("  重複加入 M001=" + memberIds.add("M001"));
        System.out.println("  會員數=" + memberIds.size()
                + " 是否含 M002=" + memberIds.contains("M002"));

        printChoice(3, "以學號查詢成績",
                "Map<String, Integer>", "HashMap<>",
                "以學號為 key 直接取值，不必走訪整份名單");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("S101", 88);
        scores.put("S102", 92);
        scores.put("S101", 95);
        System.out.println("  S101 成績=" + scores.get("S101"));
        System.out.println("  S999 成績=" + scores.getOrDefault("S999", -1));
        System.out.println("  重複 key 會覆蓋，筆數=" + scores.size());

        printChoice(4, "依到達順序處理列印工作",
                "Deque<String>", "ArrayDeque<>",
                "FIFO：offerLast 進、pollFirst 出，先送出的先印");
        Deque<String> printJobs = new ArrayDeque<>();
        printJobs.offerLast("report.pdf");
        printJobs.offerLast("slides.pptx");
        printJobs.offerLast("photo.png");
        System.out.println("  待印=" + printJobs);
        System.out.println("  列印=" + printJobs.pollFirst());
        System.out.println("  列印=" + printJobs.pollFirst());
        System.out.println("  剩餘=" + printJobs);

        printChoice(5, "復原最近操作",
                "Deque<String>", "ArrayDeque<>",
                "LIFO：push 與 pop 都在同一端，最後做的最先復原");
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("開啟檔案");
        undoStack.push("輸入標題");
        undoStack.push("刪除段落");
        System.out.println("  操作歷程=" + undoStack);
        System.out.println("  復原=" + undoStack.poll());
        System.out.println("  復原=" + undoStack.poll());
        System.out.println("  剩餘=" + undoStack);
        System.out.println("  清空後再復原=" + undoStack.poll());
        System.out.println("  完全清空後復原=" + undoStack.poll());
    }
}
