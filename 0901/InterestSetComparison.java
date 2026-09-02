import java.util.LinkedHashSet;
import java.util.Set;

public class InterestSetComparison {
    static Set<String> union(Set<String> first, Set<String> second) {
        Set<String> result = new LinkedHashSet<>(first);
        result.addAll(second);
        return result;
    }

    static Set<String> intersection(Set<String> first, Set<String> second) {
        Set<String> result = new LinkedHashSet<>(first);
        result.retainAll(second);
        return result;
    }

    static Set<String> firstOnly(Set<String> first, Set<String> second) {
        Set<String> result = new LinkedHashSet<>(first);
        result.removeAll(second);
        return result;
    }

    static Set<String> secondOnly(Set<String> first, Set<String> second) {
        Set<String> result = new LinkedHashSet<>(second);
        result.removeAll(first);
        return result;
    }

    public static void main(String[] args) {
        Set<String> amy = new LinkedHashSet<>(Set.of("reading", "volleyball", "coding"));
        Set<String> ben = new LinkedHashSet<>(Set.of("coding", "music", "volleyball"));

        System.out.println("union=" + union(amy, ben));
        System.out.println("intersection=" + intersection(amy, ben));
        System.out.println("firstOnly=" + firstOnly(amy, ben));
        System.out.println("secondOnly=" + secondOnly(amy, ben));
        System.out.println("amy=" + amy);
        System.out.println("ben=" + ben);
    }
}
