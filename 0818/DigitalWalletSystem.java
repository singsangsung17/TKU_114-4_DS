class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private int depositCount;
    private int payCount;
    private int refundCount;

    DigitalWallet(String walletId, String owner, int openingBalance) {
        this.walletId = walletId == null || walletId.isBlank()
                ? "UNKNOWN" : walletId.trim();
        this.owner = owner == null || owner.isBlank() ? "Unknown" : owner.trim();
        this.balance = Math.max(0, openingBalance);
        this.depositCount = 0;
        this.payCount = 0;
        this.refundCount = 0;
    }

    boolean deposit(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        depositCount++;
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        payCount++;
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        refundCount++;
        return true;
    }

    int getBalance() {
        return balance;
    }

    int getTransactionCount() {
        return depositCount + payCount + refundCount;
    }

    void printSummary() {
        System.out.println(walletId + " owner=" + owner + " balance=" + balance);
        System.out.println("儲值次數=" + depositCount
                + " 付款次數=" + payCount
                + " 退款次數=" + refundCount
                + " 交易總次數=" + getTransactionCount());
    }

    @Override
    public String toString() {
        return walletId + " " + owner + " balance=" + balance;
    }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W001", "Amy", 0);

        System.out.println("儲值 1000：" + wallet.deposit(1000));
        System.out.println("付款 250：" + wallet.pay(250));
        System.out.println("付款 5000：" + wallet.pay(5000));
        System.out.println("餘額不足後餘額：" + wallet.getBalance());
        System.out.println("儲值 -100：" + wallet.deposit(-100));
        System.out.println("付款 -50：" + wallet.pay(-50));
        System.out.println("退款 -30：" + wallet.refund(-30));
        System.out.println("負數操作後餘額：" + wallet.getBalance());
        System.out.println("退款 200：" + wallet.refund(200));

        wallet.printSummary();
    }
}
