import java.util.ArrayList;
import java.util.List;

class Repository<T> {
    private final List<T> items = new ArrayList<>();

    boolean add(T item) {
        if (item == null) {
            return false;
        }
        return items.add(item);
    }

    T get(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    boolean remove(T item) {
        return items.remove(item);
    }

    T removeAt(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    int size() {
        return items.size();
    }

    boolean isEmpty() {
        return items.isEmpty();
    }

    void printAll(String title) {
        System.out.println(title + "（共 " + items.size() + " 筆）");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  [" + i + "] " + items.get(i));
        }
    }
}

class Product {
    private final String id;
    private final String name;
    private final int price;

    Product(String id, String name, int price) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        this.price = Math.max(0, price);
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + " " + name + " price=" + price;
    }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        Repository<String> tags = new Repository<>();
        System.out.println("加入 java：" + tags.add("java"));
        System.out.println("加入 tree：" + tags.add("tree"));
        System.out.println("加入 graph：" + tags.add("graph"));
        System.out.println("加入 null：" + tags.add(null));
        tags.printAll("標籤倉庫：");

        System.out.println("index 1：" + tags.get(1));
        System.out.println("index 99：" + tags.get(99));
        System.out.println("移除 tree：" + tags.remove("tree"));
        System.out.println("移除不存在的 stack：" + tags.remove("stack"));
        System.out.println("移除 index 0：" + tags.removeAt(0));
        System.out.println("移除 index -1：" + tags.removeAt(-1));
        tags.printAll("清理後標籤：");

        Repository<Product> products = new Repository<>();
        Product keyboard = new Product("P101", "Keyboard", 890);
        products.add(keyboard);
        products.add(new Product("P102", "Mouse", 450));
        products.add(new Product("P103", "Monitor", 5200));
        products.printAll("商品倉庫：");

        Product first = products.get(0);
        System.out.println("取出不需要 cast：" + first.getName()
                + " 加價後=" + (first.getPrice() + 100));
        System.out.println("移除 keyboard：" + products.remove(keyboard));
        System.out.println("剩餘筆數：" + products.size()
                + " 是否為空：" + products.isEmpty());
        products.printAll("最終商品倉庫：");
    }
}
