class InventoryProduct {
    int id;
    String name;
    int stock;

    InventoryProduct(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return id + " " + name + " stock=" + stock;
    }
}

class InventoryNode {
    InventoryProduct product;
    InventoryNode left;
    InventoryNode right;

    InventoryNode(InventoryProduct product) {
        this.product = product;
    }
}

class InventoryBst {
    private InventoryNode root;

    boolean add(InventoryProduct product) {
        if (product == null) {
            return false;
        }
        if (root == null) {
            root = new InventoryNode(product);
            return true;
        }
        InventoryNode current = root;
        while (true) {
            if (product.id == current.product.id) {
                return false;
            }
            if (product.id < current.product.id) {
                if (current.left == null) {
                    current.left = new InventoryNode(product);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new InventoryNode(product);
                    return true;
                }
                current = current.right;
            }
        }
    }

    InventoryProduct find(int id) {
        InventoryNode current = root;
        while (current != null) {
            if (id == current.product.id) {
                return current.product;
            }
            current = id < current.product.id ? current.left : current.right;
        }
        return null;
    }

    boolean restock(int id, int amount) {
        InventoryProduct product = find(id);
        if (product == null || amount <= 0) {
            return false;
        }
        product.stock += amount;
        return true;
    }

    boolean reduce(int id, int amount) {
        InventoryProduct product = find(id);
        if (product == null || amount <= 0 || amount > product.stock) {
            return false;
        }
        product.stock -= amount;
        return true;
    }

    boolean remove(int id) {
        if (find(id) == null) {
            return false;
        }
        root = remove(root, id);
        return true;
    }

    private InventoryNode remove(InventoryNode node, int id) {
        if (node == null) {
            return null;
        }
        if (id < node.product.id) {
            node.left = remove(node.left, id);
        } else if (id > node.product.id) {
            node.right = remove(node.right, id);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            InventoryNode successor = minimumNode(node.right);
            node.product = successor.product;
            node.right = remove(node.right, successor.product.id);
        }
        return node;
    }

    private InventoryNode minimumNode(InventoryNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    void report() {
        report(root);
        System.out.println();
    }

    private void report(InventoryNode node) {
        if (node == null) {
            return;
        }
        report(node.left);
        System.out.print(node.product + " | ");
        report(node.right);
    }
}

public class ProductInventoryBst {
    public static void main(String[] args) {
        InventoryBst inventory = new InventoryBst();

        System.out.println("新增 300=" + inventory.add(new InventoryProduct(300, "Keyboard", 5)));
        System.out.println("新增 100=" + inventory.add(new InventoryProduct(100, "Mouse", 8)));
        System.out.println("新增 500=" + inventory.add(new InventoryProduct(500, "Monitor", 2)));
        System.out.println("新增 200=" + inventory.add(new InventoryProduct(200, "Hub", 4)));
        System.out.println("重複 300=" + inventory.add(new InventoryProduct(300, "Keyboard2", 9)));

        System.out.print("report=");
        inventory.report();

        System.out.println("查詢 200=" + inventory.find(200));
        System.out.println("查詢 999=" + inventory.find(999));

        System.out.println("補貨 500 +10=" + inventory.restock(500, 10));
        System.out.println("補貨 500 -5=" + inventory.restock(500, -5));
        System.out.println("補貨 999 +10=" + inventory.restock(999, 10));

        System.out.println("扣庫存 100 -3=" + inventory.reduce(100, 3));
        System.out.println("扣庫存 100 -99=" + inventory.reduce(100, 99));
        System.out.println("扣庫存 999 -1=" + inventory.reduce(999, 1));

        System.out.print("report=");
        inventory.report();

        System.out.println("刪除 200=" + inventory.remove(200));
        System.out.println("刪除 300=" + inventory.remove(300));
        System.out.println("刪除 999=" + inventory.remove(999));

        System.out.print("report=");
        inventory.report();
    }
}
