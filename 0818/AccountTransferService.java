class Account {
    private final String id;
    private int balance;

    Account(String id, int openingBalance) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.balance = Math.max(0, openingBalance);
    }

    boolean withdraw(int amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return id + " balance=" + balance;
    }
}

class TransferService {
    boolean transfer(Account source, Account target, int amount) {
        if (source == null || target == null || source == target) {
            return false;
        }
        if (amount <= 0) {
            return false;
        }
        if (!source.withdraw(amount)) {
            return false;
        }
        target.deposit(amount);
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account a = new Account("A", 1000);
        Account b = new Account("B", 200);
        TransferService service = new TransferService();

        System.out.println("轉帳 300：" + service.transfer(a, b, 300));
        System.out.println(a);
        System.out.println(b);

        System.out.println("轉帳 5000（餘額不足）：" + service.transfer(a, b, 5000));
        System.out.println("轉帳 -100（金額不合法）：" + service.transfer(a, b, -100));
        System.out.println("同帳戶轉帳：" + service.transfer(a, a, 100));
        System.out.println("目標為 null：" + service.transfer(a, null, 100));
        System.out.println("來源為 null：" + service.transfer(null, b, 100));

        System.out.println("失敗操作後：");
        System.out.println(a);
        System.out.println(b);
        System.out.println("兩帳戶總額：" + (a.getBalance() + b.getBalance()));
    }
}
