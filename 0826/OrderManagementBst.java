class ManagedOrder {
    int orderId;
    String customer;
    int amount;
    String status;

    ManagedOrder(int orderId, String customer, int amount) {
        this.orderId = orderId;
        this.customer = customer;
        this.amount = amount;
        this.status = "NEW";
    }

    @Override
    public String toString() {
        return orderId + " " + customer + " amount=" + amount
                + " status=" + status;
    }
}

class ManagedOrderNode {
    ManagedOrder order;
    ManagedOrderNode left;
    ManagedOrderNode right;

    ManagedOrderNode(ManagedOrder order) {
        this.order = order;
    }
}

class ManagedOrderBst {
    private ManagedOrderNode root;

    boolean add(ManagedOrder order) {
        if (order == null || order.amount < 0) {
            return false;
        }
        if (root == null) {
            root = new ManagedOrderNode(order);
            return true;
        }
        ManagedOrderNode current = root;
        while (true) {
            if (order.orderId == current.order.orderId) {
                return false;
            }
            if (order.orderId < current.order.orderId) {
                if (current.left == null) {
                    current.left = new ManagedOrderNode(order);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ManagedOrderNode(order);
                    return true;
                }
                current = current.right;
            }
        }
    }

    ManagedOrder find(int orderId) {
        ManagedOrderNode current = root;
        while (current != null) {
            if (orderId == current.order.orderId) {
                return current.order;
            }
            current = orderId < current.order.orderId
                    ? current.left : current.right;
        }
        return null;
    }

    boolean updateStatus(int orderId, String status) {
        ManagedOrder order = find(orderId);
        if (order == null || status == null || status.isBlank()) {
            return false;
        }
        if (order.status.equals("CANCELLED")) {
            return false;
        }
        order.status = status.trim();
        return true;
    }

    boolean cancel(int orderId) {
        ManagedOrder order = find(orderId);
        if (order == null || order.status.equals("CANCELLED")) {
            return false;
        }
        order.status = "CANCELLED";
        return true;
    }

    boolean remove(int orderId) {
        ManagedOrder order = find(orderId);
        if (order == null || !order.status.equals("CANCELLED")) {
            return false;
        }
        root = remove(root, orderId);
        return true;
    }

    private ManagedOrderNode remove(ManagedOrderNode node, int orderId) {
        if (node == null) {
            return null;
        }
        if (orderId < node.order.orderId) {
            node.left = remove(node.left, orderId);
        } else if (orderId > node.order.orderId) {
            node.right = remove(node.right, orderId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            ManagedOrderNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.order = successor.order;
            node.right = remove(node.right, successor.order.orderId);
        }
        return node;
    }

    void printRange(int low, int high) {
        System.out.print("range[" + low + "," + high + "]=");
        if (low > high) {
            System.out.println("無效範圍");
            return;
        }
        printRange(root, low, high);
        System.out.println();
    }

    private void printRange(ManagedOrderNode node, int low, int high) {
        if (node == null) {
            return;
        }
        if (node.order.orderId > low) {
            printRange(node.left, low, high);
        }
        if (node.order.orderId >= low && node.order.orderId <= high) {
            System.out.print(node.order.orderId + " ");
        }
        if (node.order.orderId < high) {
            printRange(node.right, low, high);
        }
    }

    int totalAmount() {
        return totalAmount(root);
    }

    private int totalAmount(ManagedOrderNode node) {
        if (node == null) {
            return 0;
        }
        int current = node.order.status.equals("CANCELLED")
                ? 0 : node.order.amount;
        return current + totalAmount(node.left) + totalAmount(node.right);
    }

    void report() {
        report(root);
    }

    private void report(ManagedOrderNode node) {
        if (node == null) {
            return;
        }
        report(node.left);
        System.out.println("  " + node.order);
        report(node.right);
    }
}

public class OrderManagementBst {
    public static void main(String[] args) {
        ManagedOrderBst system = new ManagedOrderBst();

        System.out.println("新增 5005=" + system.add(new ManagedOrder(5005, "Amy", 1200)));
        System.out.println("新增 5001=" + system.add(new ManagedOrder(5001, "Ben", 800)));
        System.out.println("新增 5009=" + system.add(new ManagedOrder(5009, "Cara", 2500)));
        System.out.println("新增 5003=" + system.add(new ManagedOrder(5003, "Dan", 450)));
        System.out.println("重複 5005=" + system.add(new ManagedOrder(5005, "Amy2", 999)));
        System.out.println("amount 為負=" + system.add(new ManagedOrder(5007, "Eva", -100)));

        System.out.println("報表：");
        system.report();

        System.out.println("查詢 5003=" + system.find(5003));
        System.out.println("查詢 9999=" + system.find(9999));

        System.out.println("更新 5003 狀態=" + system.updateStatus(5003, "SHIPPED"));
        System.out.println("更新為空白=" + system.updateStatus(5003, "   "));
        System.out.println("更新不存在=" + system.updateStatus(9999, "SHIPPED"));

        System.out.println("未取消不得刪除 5001=" + system.remove(5001));
        System.out.println("取消 5001=" + system.cancel(5001));
        System.out.println("重複取消 5001=" + system.cancel(5001));
        System.out.println("取消後更新狀態=" + system.updateStatus(5001, "SHIPPED"));
        System.out.println("取消後刪除 5001=" + system.remove(5001));
        System.out.println("刪除不存在 9999=" + system.remove(9999));

        System.out.println("報表：");
        system.report();

        system.printRange(5003, 5009);
        system.printRange(5000, 5010);
        system.printRange(5010, 5000);

        System.out.println("取消 5009=" + system.cancel(5009));
        System.out.println("有效金額合計=" + system.totalAmount());
    }
}
