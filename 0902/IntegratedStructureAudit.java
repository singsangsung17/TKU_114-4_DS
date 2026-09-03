import java.util.ArrayList;
import java.util.List;

public class IntegratedStructureAudit {
    record Scenario(String name, String operation, String usedStructure) {}

    record Diagnosis(String scenario, String used, String suggested, boolean reasonable, String note) {}

    static String suggest(String operation) {
        if (operation == null) return "UNKNOWN";
        return switch (operation) {
            case "INDEX_ACCESS" -> "ArrayList";
            case "FIFO" -> "ArrayDeque as Queue";
            case "LIFO" -> "ArrayDeque as Stack";
            case "SORTED_RANGE" -> "TreeMap";
            case "NEXT_PRIORITY" -> "PriorityQueue";
            case "KEY_LOOKUP" -> "HashMap";
            case "EXISTENCE_CHECK" -> "HashSet";
            case "RELATION_TRAVERSAL" -> "Graph adjacency list";
            default -> "UNKNOWN";
        };
    }

    static Diagnosis audit(Scenario scenario) {
        if (scenario == null) {
            return new Diagnosis("null", "null", "UNKNOWN", false, "情境未提供");
        }
        String suggested = suggest(scenario.operation());
        if (suggested.equals("UNKNOWN")) {
            return new Diagnosis(scenario.name(), String.valueOf(scenario.usedStructure()),
                    suggested, false, "無法辨識的操作需求");
        }
        boolean ok = suggested.equals(scenario.usedStructure());
        String note = ok ? "選擇與操作需求相符"
                : "操作需求為 " + scenario.operation() + "，" + scenario.usedStructure()
                  + " 在此需求下成本較高，應改用 " + suggested;
        return new Diagnosis(scenario.name(), String.valueOf(scenario.usedStructure()),
                suggested, ok, note);
    }

    static List<Diagnosis> auditAll(List<Scenario> scenarios) {
        List<Diagnosis> result = new ArrayList<>();
        if (scenarios == null) return result;
        for (Scenario scenario : scenarios) result.add(audit(scenario));
        return result;
    }

    static String report(List<Scenario> scenarios) {
        List<Diagnosis> diagnoses = auditAll(scenarios);
        if (diagnoses.isEmpty()) return "no scenario";
        StringBuilder sb = new StringBuilder();
        int pass = 0;
        for (Diagnosis diagnosis : diagnoses) {
            if (diagnosis.reasonable()) pass++;
            sb.append(diagnosis.scenario()).append('\n')
              .append("  使用：").append(diagnosis.used()).append('\n')
              .append("  建議：").append(diagnosis.suggested()).append('\n')
              .append("  合理：").append(diagnosis.reasonable()).append('\n')
              .append("  診斷：").append(diagnosis.note()).append('\n');
        }
        sb.append("合理 ").append(pass).append(" / ").append(diagnoses.size());
        return sb.toString();
    }

    public static void main(String[] args) {
        List<Scenario> scenarios = new ArrayList<>(List.of(
                new Scenario("成績表依名次取值", "INDEX_ACCESS", "ArrayList"),
                new Scenario("掛號依序叫號", "FIFO", "ArrayList"),
                new Scenario("編輯器復原", "LIFO", "ArrayDeque as Stack"),
                new Scenario("查詢分數區間", "SORTED_RANGE", "HashMap"),
                new Scenario("取出最高優先工單", "NEXT_PRIORITY", "PriorityQueue"),
                new Scenario("依學號查詢", "KEY_LOOKUP", "ArrayList"),
                new Scenario("檢查帳號是否存在", "EXISTENCE_CHECK", "HashSet"),
                new Scenario("計算最少轉乘", "RELATION_TRAVERSAL", "Graph adjacency list"),
                new Scenario("未知需求", "SOMETHING", "ArrayList")));
        scenarios.add(null);
        System.out.println(report(scenarios));
        System.out.println("---");
        System.out.println(report(List.of()));
        System.out.println(report(null));
    }
}
