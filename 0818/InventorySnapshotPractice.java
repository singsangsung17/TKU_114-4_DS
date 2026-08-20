import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;

    InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = warehouseId == null || warehouseId.isBlank()
                ? "UNKNOWN" : warehouseId.trim();
        this.quantities = quantities == null
                ? new int[0]
                : Arrays.copyOf(quantities, quantities.length);
    }

    int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length);
    }

    int totalQuantity() {
        int total = 0;
        for (int quantity : quantities) {
            total += quantity;
        }
        return total;
    }

    int outOfStockCount() {
        int count = 0;
        for (int quantity : quantities) {
            if (quantity == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return warehouseId + " " + Arrays.toString(quantities);
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] source = {5, 0, 3, 0};
        InventorySnapshot snapshot = new InventorySnapshot("W001", source);

        source[0] = 99;
        int[] received = snapshot.getQuantities();
        received[2] = 99;

        System.out.println("source=" + Arrays.toString(source));
        System.out.println("received=" + Arrays.toString(received));
        System.out.println("snapshot=" + snapshot);
        System.out.println("總數量：" + snapshot.totalQuantity());
        System.out.println("缺貨品項：" + snapshot.outOfStockCount());

        InventorySnapshot empty = new InventorySnapshot(null, null);
        System.out.println("empty=" + empty);
        System.out.println("總數量：" + empty.totalQuantity());
        System.out.println("缺貨品項：" + empty.outOfStockCount());
    }
}
