import java.util.ArrayList;
import java.util.List;

public class DataStructureDecisionReport {
    record Decision(String requirement, String structure, String reason, String bigO) {}

    static Decision decide(String requirement) {
        if (requirement == null) {
            return new Decision("null", "UNKNOWN", "需求未指定", "N/A");
        }
        return switch (requirement) {
            case "依 index 取得第 k 筆資料" ->
                    new Decision(requirement, "ArrayList", "連續記憶體可直接定位 index", "get O(1)，中間插入 O(n)");
            case "依到達順序處理排隊資料" ->
                    new Decision(requirement, "ArrayDeque as Queue", "先進先出且兩端操作成本固定", "offer/poll O(1)");
            case "還原上一步操作" ->
                    new Decision(requirement, "ArrayDeque as Stack", "後進先出符合回溯需求", "push/pop O(1)");
            case "查詢分數介於 60 到 80 的學生" ->
                    new Decision(requirement, "TreeMap / Balanced BST", "資料有序可做 range 查詢", "平衡時 O(log n)，最差 O(n)");
            case "每次取出優先度最高的工單" ->
                    new Decision(requirement, "PriorityQueue / Heap", "只需維持極值而非全排序", "peek O(1)，add/remove O(log n)");
            case "依會員 id 查出資料" ->
                    new Decision(requirement, "HashMap", "key 直接雜湊定位", "平均 O(1)，最差 O(n)");
            case "找出兩個地點間最少轉乘路線" ->
                    new Decision(requirement, "Graph adjacency list + BFS", "多對多關係需展開走訪", "BFS O(V+E)");
            case "判斷帳號是否已註冊" ->
                    new Decision(requirement, "HashSet", "只需存在性判斷不需順序", "add/contains 平均 O(1)");
            case "取出目前最小值並持續更新" ->
                    new Decision(requirement, "PriorityQueue / Min-Heap", "反覆取極值且資料會變動", "peek O(1)，poll O(log n)");
            case "維持資料永遠依 key 排序輸出" ->
                    new Decision(requirement, "TreeMap", "走訪時自然依 key 排序", "put/get O(log n)");
            case "在清單頭尾都要頻繁新增刪除" ->
                    new Decision(requirement, "ArrayDeque", "雙端操作不需搬移元素", "頭尾操作 O(1)");
            case "分析課程先修關係是否可達" ->
                    new Decision(requirement, "Directed Graph + DFS", "有方向的相依關係需深度走訪", "DFS O(V+E)");
            default -> new Decision(requirement, "UNKNOWN", "無對應規則", "N/A");
        };
    }

    static List<Decision> decideAll(List<String> requirements) {
        List<Decision> result = new ArrayList<>();
        if (requirements == null) return result;
        for (String requirement : requirements) result.add(decide(requirement));
        return result;
    }

    static String report(List<String> requirements) {
        List<Decision> decisions = decideAll(requirements);
        if (decisions.isEmpty()) return "no requirement";
        StringBuilder sb = new StringBuilder();
        for (Decision decision : decisions) {
            sb.append(decision.requirement()).append('\n')
              .append("  結構：").append(decision.structure()).append('\n')
              .append("  理由：").append(decision.reason()).append('\n')
              .append("  Big-O：").append(decision.bigO()).append('\n');
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        List<String> requirements = new ArrayList<>(List.of(
                "依 index 取得第 k 筆資料",
                "依到達順序處理排隊資料",
                "還原上一步操作",
                "查詢分數介於 60 到 80 的學生",
                "每次取出優先度最高的工單",
                "依會員 id 查出資料",
                "找出兩個地點間最少轉乘路線",
                "判斷帳號是否已註冊",
                "取出目前最小值並持續更新",
                "維持資料永遠依 key 排序輸出",
                "在清單頭尾都要頻繁新增刪除",
                "分析課程先修關係是否可達"));
        System.out.println(report(requirements));
        System.out.println("---");
        System.out.println(report(List.of("未知需求")));
        System.out.println("---");
        List<String> withNull = new ArrayList<>();
        withNull.add(null);
        System.out.println(report(withNull));
        System.out.println("---");
        System.out.println(report(List.of()));
        System.out.println(report(null));
    }
}
