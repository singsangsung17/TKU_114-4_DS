import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebsiteLinkGraph {
    private final Map<String, Set<String>> outgoing = new LinkedHashMap<>();

    public boolean addPage(String page) {
        if (page == null || page.isBlank()) return false;
        return outgoing.putIfAbsent(page.trim(), new LinkedHashSet<>()) == null;
    }

    public boolean addLink(String from, String to) {
        if (!outgoing.containsKey(from) || !outgoing.containsKey(to)) return false;
        if (from.equals(to)) return false;
        return outgoing.get(from).add(to);
    }

    public List<String> outgoingLinks(String page) {
        Set<String> links = outgoing.get(page);
        return links == null ? List.of() : new ArrayList<>(links);
    }

    public int incomingCount(String page) {
        if (!outgoing.containsKey(page)) return 0;
        int count = 0;
        for (Set<String> links : outgoing.values()) {
            if (links.contains(page)) count++;
        }
        return count;
    }

    public List<String> pagesWithoutIncoming() {
        List<String> result = new ArrayList<>();
        for (String page : outgoing.keySet()) {
            if (incomingCount(page) == 0) result.add(page);
        }
        return result;
    }

    public List<String> pagesWithoutOutgoing() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : outgoing.entrySet()) {
            if (entry.getValue().isEmpty()) result.add(entry.getKey());
        }
        return result;
    }

    public static void main(String[] args) {
        WebsiteLinkGraph graph = new WebsiteLinkGraph();
        for (String page : List.of("home", "about", "product", "contact")) graph.addPage(page);
        graph.addLink("home", "about");
        graph.addLink("home", "product");
        graph.addLink("about", "contact");
        graph.addLink("product", "contact");
        for (String page : List.of("home", "about", "product", "contact")) {
            System.out.println(page + " out=" + graph.outgoingLinks(page)
                    + " in=" + graph.incomingCount(page));
        }
        System.out.println("noIncoming=" + graph.pagesWithoutIncoming());
        System.out.println("noOutgoing=" + graph.pagesWithoutOutgoing());
    }
}
