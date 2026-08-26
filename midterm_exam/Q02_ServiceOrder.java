package midterm_exam;

import java.util.ArrayList;
import java.util.List;

public class Q02_ServiceOrder {
    public static class LineItem {
        private final String name;
        private final int unitPrice;
        private final int quantity;

        public LineItem(String name, int unitPrice, int quantity) {
            this.name = name;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public int subtotal() {
            return unitPrice * quantity;
        }
    }

    private final String orderId;
    private final List<LineItem> items;

    public Q02_ServiceOrder(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("orderId is invalid");
        }
        this.orderId = orderId;
        this.items = new ArrayList<LineItem>();
    }

    public boolean addItem(String name, int unitPrice, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (unitPrice < 0 || quantity <= 0) {
            return false;
        }
        items.add(new LineItem(name, unitPrice, quantity));
        return true;
    }

    public int itemCount() {
        return items.size();
    }

    public int totalAmount() {
        int total = 0;
        for (LineItem item : items) {
            total += item.subtotal();
        }
        return total;
    }

    public String largestItemName() {
        if (items.isEmpty()) {
            return "";
        }
        LineItem largest = items.get(0);
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i).subtotal() > largest.subtotal()) {
                largest = items.get(i);
            }
        }
        return largest.getName();
    }

    public java.util.List<String> itemSummaries() {
        List<String> result = new ArrayList<String>();
        for (LineItem item : items) {
            result.add(item.getName() + ":" + item.subtotal());
        }
        return result;
    }
}