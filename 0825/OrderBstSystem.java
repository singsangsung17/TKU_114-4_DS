class Order {
    int orderId;
    String customer;
    int amount;
    boolean cancelled;

    Order(int orderId, String customer, int amount) {
        this.orderId = orderId;
        this.customer = customer;
        this.amount = amount;
        this.cancelled = false;
    }

    @Override
    public String toString() {
        return orderId + " " + customer + " amount=" + amount
                + (cancelled ? " [CANCELLED]" : "");
    }
}

class OrderNode {
    Order order;
    OrderNode left;
    OrderNode right;

    OrderNode(Order order) {
        this.order = order;
    }
}

class OrderBst {
    private OrderNode root;

    boolean add(Order order) {
        if (order == null) {
            return false;
        }
        if (root == null) {
            root = new OrderNode(order);
            return true;
        }
        OrderNode current = root;
        while (true) {
            if (order.orderId == current.order.orderId) {
                return false;
            }
            if (order.orderId < current.order.orderId) {
                if (current.left == null) {
                    current.left = new OrderNode(order);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new OrderNode(order);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Order find(int orderId) {
        OrderNode current = root;
        while (current != null) {
            if (orderId == current.order.orderId) {
                return current.order;
            }
            current = orderId < current.order.orderId
                    ? current.left : current.right;
        }
        return null;
    }

    boolean cancel(int orderId) {
        Order order = find(orderId);
        if (order == null || order.cancelled) {
            return false;
        }
        order.cancelled = true;
        return true;
    }

    boolean updateAmount(int orderId, int amount) {
        Order order = find(orderId);
        if (order == null || order.cancelled || amount < 0) {
            return false;
        }
        order.amount = amount;
        return true;
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

    private void printRange(OrderNode node, int low, int high) {
        if (node == null) {
            return;
        }
        if (node.order.orderId > low) {
            printRange(node.left, low, high);
        }
        if (node.order.orderId >= low && node.order.orderId <= high) {
            System.out.print(node.order + " | ");
        }
        if (node.order.orderId < high) {
            printRange(node.right, low, high);
        }
    }

    void summary() {
        int[] stats = new int[3];
        summary(root, stats);
        System.out.println("訂單總數=" + stats[0]
                + " 有效訂單=" + stats[1]
                + " 有效金額合計=" + stats[2]);
    }

    private void summary(OrderNode node, int[] stats) {
        if (node == null) {
            return;
        }
        summary(node.left, stats);
        stats[0]++;
        if (!node.order.cancelled) {
            stats[1]++;
            stats[2] += node.order.amount;
        }
        summary(node.right, stats);
    }

    void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(OrderNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.order + " | ");
        inorder(node.right);
    }
}

public class OrderBstSystem {
    public static void main(String[] args) {
        OrderBst system = new OrderBst();

        System.out.println("新增 3005=" + system.add(new Order(3005, "Amy", 1200)));
        System.out.println("新增 3001=" + system.add(new Order(3001, "Ben", 800)));
        System.out.println("新增 3009=" + system.add(new Order(3009, "Cara", 2500)));
        System.out.println("新增 3003=" + system.add(new Order(3003, "Dan", 450)));
        System.out.println("新增 3007=" + system.add(new Order(3007, "Eva", 1800)));
        System.out.println("重複 3005=" + system.add(new Order(3005, "Amy2", 999)));
        System.out.println("新增 null=" + system.add(null));

        System.out.print("inorder=");
        system.inorder();

        System.out.println("查詢 3007=" + system.find(3007));
        System.out.println("查詢 9999=" + system.find(9999));

        System.out.println("更新 3003 金額=" + system.updateAmount(3003, 600));
        System.out.println("更新 3003 負數=" + system.updateAmount(3003, -100));
        System.out.println("更新 9999=" + system.updateAmount(9999, 500));

        System.out.println("取消 3009=" + system.cancel(3009));
        System.out.println("重複取消 3009=" + system.cancel(3009));
        System.out.println("取消 9999=" + system.cancel(9999));
        System.out.println("取消後更新 3009=" + system.updateAmount(3009, 100));

        System.out.print("inorder=");
        system.inorder();

        system.printRange(3003, 3007);
        system.printRange(3000, 3010);
        system.printRange(3010, 3000);

        system.summary();
    }
}
