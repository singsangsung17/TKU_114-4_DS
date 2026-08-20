import java.util.Objects;

class LibraryMember {
    private final String memberId;
    private String name;
    private String email;

    LibraryMember(String memberId, String name, String email) {
        this.memberId = memberId == null || memberId.isBlank()
                ? "UNKNOWN" : memberId.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        this.email = email == null || email.isBlank() ? "none" : email.trim();
    }

    @Override
    public String toString() {
        return "LibraryMember{memberId='" + memberId + "', name='" + name
                + "', email='" + email + "'}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LibraryMember member)) {
            return false;
        }
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}

public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember first = new LibraryMember("M001", "Amy", "amy@mail.com");
        LibraryMember second = new LibraryMember("M001", "Amy Chen", "chen@mail.com");
        LibraryMember third = new LibraryMember("M002", "Ben", "ben@mail.com");
        LibraryMember alias = first;

        System.out.println(first);
        System.out.println(second);

        System.out.println("first == second：" + (first == second));
        System.out.println("first.equals(second)：" + first.equals(second));
        System.out.println("first == alias：" + (first == alias));
        System.out.println("first.equals(third)：" + first.equals(third));
        System.out.println("first.equals(null)：" + first.equals(null));
        System.out.println("hashCode 相同："
                + (first.hashCode() == second.hashCode()));
    }
}
