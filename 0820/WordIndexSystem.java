import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordIndexSystem {
    static String normalize(String word) {
        String cleaned = word.replace(".", "").replace(",", "")
                .replace("!", "").replace("?", "").trim();
        return cleaned.toLowerCase();
    }

    public static void main(String[] args) {
        String[] sentences = {
            "Java collections store objects.",
            "A List keeps order, a Set removes duplicates.",
            "Java generics catch type errors early.",
            "A Map counts words, and a Map stores pairs."
        };

        Map<String, Integer> counts = new HashMap<>();
        Set<String> unique = new LinkedHashSet<>();

        for (String sentence : sentences) {
            for (String raw : sentence.split("\\s+")) {
                String word = normalize(raw);
                if (word.isEmpty()) {
                    continue;
                }
                unique.add(word);
                counts.put(word, counts.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("不重複單字（" + unique.size() + " 個）：" + unique);
        System.out.println("總出現次數：" + counts.values().stream()
                .mapToInt(Integer::intValue).sum());

        List<String> repeated = new ArrayList<>();
        for (String word : unique) {
            if (counts.get(word) >= 2) {
                repeated.add(word);
            }
        }
        repeated.sort(Comparator.comparingInt((String word) -> counts.get(word))
                .reversed()
                .thenComparing(Comparator.naturalOrder()));

        System.out.println("出現至少兩次的單字：");
        for (String word : repeated) {
            System.out.println("  " + word + " -> " + counts.get(word));
        }

        System.out.println("查詢 java：" + counts.getOrDefault("java", 0));
        System.out.println("查詢 python：" + counts.getOrDefault("python", 0));
        System.out.println("Java 與 java 視為同一個字："
                + (counts.get("java") != null && counts.get("java") == 2));
    }
}
