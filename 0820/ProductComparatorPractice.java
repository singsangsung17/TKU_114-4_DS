import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class StoreProduct implements Comparable<StoreProduct> {
    private final String id;
    private final String name;
    private final int price;
    private final int stock;

    StoreProduct(String id, String name, int price, int stock) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    int getStock() {
        return stock;
    }

    @Override
    public int compareTo(StoreProduct other) {
        return id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return id + " " + name + " price=" + price + " stock=" + stock;
    }
}

public class ProductComparatorPractice {
    static void printAll(String title, List<StoreProduct> products) {
        System.out.println(title);
        for (StoreProduct product : products) {
            System.out.println("  " + product);
        }
    }

    public static void main(String[] args) {
        List<StoreProduct> products = new ArrayList<>();
        products.add(new StoreProduct("P103", "Mouse", 450, 12));
        products.add(new StoreProduct("P101", "Keyboard", 890, 4));
        products.add(new StoreProduct("P105", "Headset", 890, 12));
        products.add(new StoreProduct("P102", "Cable", 450, 30));
        products.add(new StoreProduct("P104", "Monitor", 5200, 4));

        printAll("原始順序：", products);

        List<StoreProduct> byId = new ArrayList<>(products);
        byId.sort(null);
        printAll("依 id 升冪（natural order）：", byId);

        Comparator<StoreProduct> byPrice =
                Comparator.comparingInt(StoreProduct::getPrice)
                        .thenComparing(StoreProduct::getName);
        List<StoreProduct> byPriceList = new ArrayList<>(products);
        byPriceList.sort(byPrice);
        printAll("依 price 升冪，同價依 name：", byPriceList);

        Comparator<StoreProduct> byStock =
                Comparator.comparingInt(StoreProduct::getStock)
                        .reversed()
                        .thenComparing(StoreProduct::getId);
        List<StoreProduct> byStockList = new ArrayList<>(products);
        byStockList.sort(byStock);
        printAll("依 stock 降冪，同庫存依 id：", byStockList);

        printAll("排序後原始 list 未變：", products);
    }
}
