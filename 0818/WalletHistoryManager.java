final class WalletTransaction {
    private final int sequence;
    private final String type;
    private final int amount;
    private final int balanceAfter;

    WalletTransaction(int sequence, String type, int amount, int balanceAfter) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    int getSequence() {
        return sequence;
    }

    String getType() {
        return type;
    }

    int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return sequence + " " + type + " " + amount
                + " balance=" + balanceAfter;
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] transactions;
    private int transactionCount;

    DigitalWallet(String walletId, String owner, int historyCapacity) {
        this.walletId = walletId == null || walletId.isBlank()
                ? "UNKNOWN" : walletId.trim();
        this.owner = owner == null || owner.isBlank() ? "Unknown" : owner.trim();
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, historyCapacity)];
        this.transactionCount = 0;
    }

    boolean deposit(int amount) {
        if (amount <= 0 || isFull()) {
            return false;
        }
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance || isFull()) {
            return false;
        }
        balance -= amount;
        record("PAY", amount);
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0 || isFull()) {
            return false;
        }
        balance += amount;
        record("REFUND", amount);
        return true;
    }

    boolean transferTo(DigitalWallet target, int amount) {
        if (target == null || target == this) {
            return false;
        }
        if (amount <= 0 || amount > balance) {
            return false;
        }
        if (isFull() || target.isFull()) {
            return false;
        }
        balance -= amount;
        record("TRANSFER_OUT", amount);
        target.balance += amount;
        target.record("TRANSFER_IN", amount);
        return true;
    }

    WalletTransaction findTransaction(int sequence) {
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getSequence() == sequence) {
                return transactions[i];
            }
        }
        return null;
    }

    int totalByType(String type) {
        if (type == null) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getType().equals(type)) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }

    int getBalance() {
        return balance;
    }

    int getTransactionCount() {
        return transactionCount;
    }

    private boolean isFull() {
        return transactionCount >= transactions.length;
    }

    private void record(String type, int amount) {
        transactions[transactionCount] = new WalletTransaction(
                transactionCount + 1, type, amount, balance);
        transactionCount++;
    }

    void printStatement() {
        System.out.println(walletId + " owner=" + owner
                + " balance=" + balance
                + " records=" + transactionCount + "/" + transactions.length);
        for (int i = 0; i < transactionCount; i++) {
            System.out.println("  " + transactions[i]);
        }
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        DigitalWallet source = new DigitalWallet("W001", "Amy", 6);
        DigitalWallet target = new DigitalWallet("W002", "Ben", 2);

        System.out.println("儲值 1000：" + source.deposit(1000));
        System.out.println("付款 250：" + source.pay(250));
        System.out.println("付款 900：" + source.pay(900));
        System.out.println("退款 50：" + source.refund(50));

        System.out.println("轉帳 300：" + source.transferTo(target, 300));
        System.out.println("轉帳 -100：" + source.transferTo(target, -100));
        System.out.println("轉帳給自己：" + source.transferTo(source, 100));
        System.out.println("轉帳給 null：" + source.transferTo(null, 100));

        System.out.println("目標儲值 500：" + target.deposit(500));
        System.out.println("目標紀錄已滿後轉帳 100：" + source.transferTo(target, 100));
        System.out.println("目標餘額：" + target.getBalance());
        System.out.println("來源餘額：" + source.getBalance());

        System.out.println("查詢第 2 筆：" + source.findTransaction(2));
        System.out.println("查詢第 99 筆：" + source.findTransaction(99));
        System.out.println("DEPOSIT 總額：" + source.totalByType("DEPOSIT"));
        System.out.println("TRANSFER_OUT 總額：" + source.totalByType("TRANSFER_OUT"));
        System.out.println("TRANSFER_IN 總額：" + target.totalByType("TRANSFER_IN"));

        source.printStatement();
        target.printStatement();
    }
}
