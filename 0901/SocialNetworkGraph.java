import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SocialNetworkGraph {
    private final Map<String, Set<String>> friends = new LinkedHashMap<>();

    public boolean addUser(String user) {
        if (user == null || user.isBlank()) return false;
        return friends.putIfAbsent(user.trim(), new LinkedHashSet<>()) == null;
    }

    public boolean addFriendship(String first, String second) {
        if (!friends.containsKey(first) || !friends.containsKey(second)) return false;
        if (first.equals(second)) return false;
        boolean changed = friends.get(first).add(second);
        friends.get(second).add(first);
        return changed;
    }

    public boolean removeFriendship(String first, String second) {
        if (!friends.containsKey(first) || !friends.containsKey(second)) return false;
        boolean changed = friends.get(first).remove(second);
        friends.get(second).remove(first);
        return changed;
    }

    public List<String> friendsOf(String user) {
        Set<String> set = friends.get(user);
        return set == null ? List.of() : new ArrayList<>(set);
    }

    public List<String> mutualFriends(String first, String second) {
        if (!friends.containsKey(first) || !friends.containsKey(second)) return List.of();
        Set<String> mutual = new LinkedHashSet<>(friends.get(first));
        mutual.retainAll(friends.get(second));
        return new ArrayList<>(mutual);
    }

    public List<String> isolatedUsers() {
        List<String> isolated = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : friends.entrySet()) {
            if (entry.getValue().isEmpty()) isolated.add(entry.getKey());
        }
        return isolated;
    }

    public static void main(String[] args) {
        SocialNetworkGraph graph = new SocialNetworkGraph();
        for (String user : List.of("Amy", "Ben", "Cathy", "Dan", "Eva")) graph.addUser(user);
        graph.addFriendship("Amy", "Ben");
        graph.addFriendship("Amy", "Cathy");
        graph.addFriendship("Ben", "Cathy");
        graph.addFriendship("Cathy", "Dan");
        System.out.println("Amy friends=" + graph.friendsOf("Amy"));
        System.out.println("Amy-Ben mutual=" + graph.mutualFriends("Amy", "Ben"));
        System.out.println("isolated=" + graph.isolatedUsers());
        System.out.println("remove=" + graph.removeFriendship("Amy", "Ben"));
        System.out.println("Amy friends=" + graph.friendsOf("Amy"));
    }
}
