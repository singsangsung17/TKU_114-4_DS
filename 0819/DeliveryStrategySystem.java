interface DeliveryMethod {
    int calculateFee(int weightGram);

    String description();
}

class HomeDelivery implements DeliveryMethod {
    private final int baseFee;

    HomeDelivery(int baseFee) {
        this.baseFee = Math.max(0, baseFee);
    }

    @Override
    public int calculateFee(int weightGram) {
        int weight = Math.max(0, weightGram);
        return baseFee + (weight / 1000) * 20;
    }

    @Override
    public String description() {
        return "宅配到府，預計 2 個工作天";
    }
}

class StorePickup implements DeliveryMethod {
    @Override
    public int calculateFee(int weightGram) {
        int weight = Math.max(0, weightGram);
        if (weight > 5000) {
            return 120;
        }
        return 60;
    }

    @Override
    public String description() {
        return "超商取貨，預計 3 個工作天，超過 5 公斤加收費用";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public int calculateFee(int weightGram) {
        return 0;
    }

    @Override
    public String description() {
        return "門市自取，當日備貨完成後即可領取";
    }
}

class OrderService {
    private final String orderId;
    private final DeliveryMethod deliveryMethod;

    OrderService(String orderId, DeliveryMethod deliveryMethod) {
        this.orderId = orderId == null || orderId.isBlank()
                ? "UNKNOWN" : orderId.trim();
        this.deliveryMethod = deliveryMethod;
    }

    int shippingFee(int weightGram) {
        if (deliveryMethod == null) {
            return 0;
        }
        return deliveryMethod.calculateFee(weightGram);
    }

    int totalAmount(int productPrice, int weightGram) {
        return Math.max(0, productPrice) + shippingFee(weightGram);
    }

    void printSummary(int productPrice, int weightGram) {
        String info = deliveryMethod == null
                ? "未選擇配送方式" : deliveryMethod.description();
        System.out.println(orderId + " | " + info);
        System.out.println("  商品金額=" + Math.max(0, productPrice)
                + " 重量=" + Math.max(0, weightGram) + "g"
                + " 運費=" + shippingFee(weightGram)
                + " 應付總額=" + totalAmount(productPrice, weightGram));
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        OrderService home = new OrderService("O2001", new HomeDelivery(80));
        OrderService store = new OrderService("O2002", new StorePickup());
        OrderService self = new OrderService("O2003", new SelfPickup());
        OrderService heavy = new OrderService("O2004", new StorePickup());
        OrderService missing = new OrderService("O2005", null);

        home.printSummary(1200, 2500);
        store.printSummary(890, 800);
        self.printSummary(450, 3000);
        heavy.printSummary(2600, 6200);
        missing.printSummary(300, -500);
    }
}
