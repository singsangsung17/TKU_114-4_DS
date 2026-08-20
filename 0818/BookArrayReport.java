class Book {
    private String id;
    private String title;
    private int price;
    private int stock;

    Book(String id, String title, int price, int stock) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.title = title == null || title.isBlank() ? "Untitled" : title.trim();
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }

    int getPrice() {
        return price;
    }

    int getStock() {
        return stock;
    }

    int inventoryValue() {
        return price * stock;
    }

    boolean isLowStock() {
        return stock <= 3;
    }

    @Override
    public String toString() {
        return id + " " + title + " price=" + price + " stock=" + stock;
    }
}

public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
            new Book("B101", "Java Basics", 520, 6),
            new Book("B102", "Data Structure", 680, 3),
            new Book("B103", "Algorithm Design", 750, 1),
            new Book("B104", "Database System", 600, 8)
        };

        System.out.println("所有書籍：");
        for (Book book : books) {
            System.out.println(book);
        }

        int totalValue = 0;
        for (Book book : books) {
            totalValue += book.inventoryValue();
        }
        System.out.println("庫存總價值：" + totalValue);

        Book highest = books[0];
        for (Book book : books) {
            if (book.getPrice() > highest.getPrice()) {
                highest = book;
            }
        }
        System.out.println("價格最高：" + highest);

        System.out.println("庫存小於或等於 3：");
        for (Book book : books) {
            if (book.isLowStock()) {
                System.out.println(book);
            }
        }
    }
}
