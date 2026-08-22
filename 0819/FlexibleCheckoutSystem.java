interface PricingPolicy {
    String name();

    int finalPrice(int originalPrice);
}

class StandardPricing implements PricingPolicy {
    @Override
    public String name() {
        return "standard";
    }

    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }
}

class VipPricing implements PricingPolicy {
    @Override
    public String name() {
        return "vip85";
    }

    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice) * 85 / 100;
    }
}

class ThresholdPricing implements PricingPolicy {
    @Override
    public String name() {
        return "over2000-300";
    }

    @Override
    public int finalPrice(int originalPrice) {
        int amount = Math.max(0, originalPrice);
        if (amount >= 2000) {
            return amount - 300;
        }
        return amount;
    }
}

interface NotificationChannel {
    String name();

    boolean send(String receiver, String message);
}

class EmailChannel implements NotificationChannel {
    @Override
    public String name() {
        return "email";
    }

    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL " + receiver + " -> " + message);
        return true;
    }
}

class SmsChannel implements NotificationChannel {
    @Override
    public String name() {
        return "sms";
    }

    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.length() != 10) {
            return false;
        }
        for (int i = 0; i < receiver.length(); i++) {
            if (!Character.isDigit(receiver.charAt(i))) {
                return false;
            }
        }
        System.out.println("SMS " + receiver + " -> " + message);
        return true;
    }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public String name() {
        return "console";
    }

    @Override
    public boolean send(String receiver, String message) {
        System.out.println("CONSOLE " + receiver + " -> " + message);
        return true;
    }
}

final class CheckoutResult {
    private final String orderId;
    private final int originalPrice;
    private final int finalPrice;
    private final String notificationStatus;

    CheckoutResult(String orderId, int originalPrice, int finalPrice,
                   String notificationStatus) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationStatus = notificationStatus;
    }

    String getNotificationStatus() {
        return notificationStatus;
    }

    int getFinalPrice() {
        return finalPrice;
    }

    @Override
    public String toString() {
        return orderId + " 原價=" + originalPrice + " 實付=" + finalPrice
                + " 通知=" + notificationStatus;
    }
}

class CheckoutService {
    private final PricingPolicy pricing;
    private final NotificationChannel channel;

    CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        String id = orderId == null || orderId.isBlank()
                ? "UNKNOWN" : orderId.trim();
        if (orderId == null || orderId.isBlank() || originalPrice < 0) {
            return new CheckoutResult(id, Math.max(0, originalPrice), 0, "INVALID");
        }
        if (pricing == null || channel == null) {
            return new CheckoutResult(id, originalPrice, originalPrice, "NO_POLICY");
        }
        int amount = pricing.finalPrice(originalPrice);
        boolean sent = channel.send(receiver,
                "order=" + id + ", amount=" + amount);
        return new CheckoutResult(id, originalPrice, amount,
                sent ? "SENT" : "FAILED");
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        CheckoutService[] services = {
            new CheckoutService(new StandardPricing(), new EmailChannel()),
            new CheckoutService(new StandardPricing(), new SmsChannel()),
            new CheckoutService(new VipPricing(), new EmailChannel()),
            new CheckoutService(new VipPricing(), new ConsoleChannel()),
            new CheckoutService(new ThresholdPricing(), new SmsChannel()),
            new CheckoutService(new ThresholdPricing(), new ConsoleChannel())
        };

        String[] receivers = {
            "amy@example.com",
            "0912345678",
            "ben@example.com",
            "counter",
            "0987654321",
            "counter"
        };

        for (int i = 0; i < services.length; i++) {
            CheckoutResult result = services[i].checkout(
                    "O" + (3001 + i), 2400, receivers[i]);
            System.out.println(result);
        }

        CheckoutService vipSms = new CheckoutService(
                new VipPricing(), new SmsChannel());
        System.out.println(vipSms.checkout("O3007", 1000, "invalid-number"));
        System.out.println(vipSms.checkout("   ", 1000, "0912345678"));
        System.out.println(vipSms.checkout("O3008", -500, "0912345678"));

        CheckoutService broken = new CheckoutService(null, null);
        System.out.println(broken.checkout("O3009", 800, "0912345678"));
    }
}
