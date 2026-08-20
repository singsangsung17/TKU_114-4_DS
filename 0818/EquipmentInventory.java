class Equipment {
    private String id;
    private String name;
    private int availableCount;

    Equipment(String id, String name, int availableCount) {
        this.id = id == null || id.isBlank() ? "Unknown" : id.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        this.availableCount = Math.max(0, availableCount);
    }

    boolean borrowOne() {
        if (availableCount <= 0) {
            return false;
        }
        availableCount--;
        return true;
    }

    void returnItems(int quantity) {
        if (quantity > 0) {
            availableCount += quantity;
        }
    }

    int getAvailableCount() {
        return availableCount;
    }

    @Override
    public String toString() {
        return id + " " + name + " available=" + availableCount;
    }
}

public class EquipmentInventory {
    public static void main(String[] args) {
        Equipment projector = new Equipment("E101", "Projector", 2);
        Equipment cable = new Equipment("   ", "   ", -5);

        System.out.println("借用投影機：" + projector.borrowOne());
        System.out.println("借用投影機：" + projector.borrowOne());
        System.out.println("借用投影機：" + projector.borrowOne());

        projector.returnItems(3);
        projector.returnItems(-2);

        System.out.println("借用第二項設備：" + cable.borrowOne());
        cable.returnItems(4);

        System.out.println(projector);
        System.out.println(cable);
        System.out.println("投影機庫存：" + projector.getAvailableCount());
    }
}
