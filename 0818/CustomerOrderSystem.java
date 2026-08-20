class Customer {
    private final String id;
    private final String name;
    private final String phone;

    Customer(String id, String name, String phone) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        this.phone = phone == null || phone.isBlank() ? "none" : phone.trim();
    }

    String getName() {
        return name;
    }

    String label() {
        return id + " " + name + " (" + phone + ")";
    }
}

class OrderItem {
    private final String productName;
    private final int unitPrice;
    private final int quantity;

    OrderItem(String productName, int unitPrice, int quantity) {
        this.productName = productName == null || productName.isBlank()
                ? "Unknown" : productName.trim();
        this.unitPrice = Math.max(0, unitPrice);
        this.quantity = Math.max(0, quantity);
    }

    int getQuantity() {
        return quantity;
    }

    int subtotal() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return productName + " " + unitPrice + " x " + quantity
                + " = " + subtotal();
    }
}

class CustomerOrder {
    private final String orderId;
    private final Customer customer;
    private final OrderItem[] items;
    private int itemCount;

    CustomerOrder(String orderId, Customer customer, int capacity) {
        this.orderId = orderId == null || orderId.isBlank()
                ? "UNKNOWN" : orderId.trim();
        this.customer = customer;
        this.items = new OrderItem[Math.max(1, capacity)];
        this.itemCount = 0;
    }

    boolean addItem(OrderItem item) {
        if (item == null || itemCount >= items.length) {
            return false;
        }
        items[itemCount] = item;
        itemCount++;
        return true;
    }

    int totalAmount() {
        int total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].subtotal();
        }
        return total;
    }

    int totalQuantity() {
        int total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getQuantity();
        }
        return total;
    }

    int getItemCount() {
        return itemCount;
    }

    void printSummary() {
        String buyer = customer == null ? "未指定顧客" : customer.label();
        System.out.println(orderId + " | 顧客：" + buyer);
        for (int i = 0; i < itemCount; i++) {
            System.out.println("  " + items[i]);
        }
        System.out.println("  品項數=" + itemCount
                + " 總數量=" + totalQuantity()
                + " 訂單總額=" + totalAmount());
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer amy = new Customer("C101", "Amy", "0912-345-678");
        Customer ben = new Customer("C102", "Ben", "0922-111-222");

        CustomerOrder first = new CustomerOrder("O9001", amy, 3);
        System.out.println("加入品項：" + first.addItem(new OrderItem("Keyboard", 890, 2)));
        System.out.println("加入品項：" + first.addItem(new OrderItem("Mouse", 450, 1)));
        System.out.println("加入品項：" + first.addItem(new OrderItem("USB Hub", 620, 3)));
        System.out.println("超出容量：" + first.addItem(new OrderItem("Monitor", 5200, 1)));
        System.out.println("加入 null：" + first.addItem(null));

        CustomerOrder second = new CustomerOrder("O9002", ben, 2);
        second.addItem(new OrderItem("Notebook Stand", 750, 1));
        second.addItem(new OrderItem("   ", -100, -2));

        first.printSummary();
        second.printSummary();

        System.out.println("第一筆訂單品項數：" + first.getItemCount());
    }
}
