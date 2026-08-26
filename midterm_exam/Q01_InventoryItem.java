package midterm_exam;

public class Q01_InventoryItem {
    private final String id;
    private final String name;
    private int stock;

    public Q01_InventoryItem(String id, String name, int stock) {
        if (id == null || name == null) {
            throw new IllegalArgumentException("id or name is null");
        }
        String trimmedId = id.trim();
        String trimmedName = name.trim();
        if (trimmedId.isEmpty() || trimmedName.isEmpty()) {
            throw new IllegalArgumentException("id or name is empty");
        }
        this.id = trimmedId;
        this.name = trimmedName;
        this.stock = stock < 0 ? 0 : stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public boolean restock(int amount) {
        if (amount <= 0) {
            return false;
        }
        stock += amount;
        return true;
    }

    public boolean sell(int amount) {
        if (amount <= 0 || amount > stock) {
            return false;
        }
        stock -= amount;
        return true;
    }

    public String status() {
        return id + "|" + name + "|" + stock;
    }
}