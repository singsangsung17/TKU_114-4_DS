class Book {
    String isbn;
    String title;
    String author;
    boolean available;

    Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    @Override
    public String toString() {
        return isbn + " " + title + " / " + author
                + (available ? " [在架]" : " [借出]");
    }
}

class BookNode {
    Book book;
    BookNode left;
    BookNode right;

    BookNode(Book book) {
        this.book = book;
    }
}

class BookBst {
    private BookNode root;

    boolean add(Book book) {
        if (book == null) {
            return false;
        }
        if (root == null) {
            root = new BookNode(book);
            return true;
        }
        BookNode current = root;
        while (true) {
            int compare = book.isbn.compareTo(current.book.isbn);
            if (compare == 0) {
                return false;
            }
            if (compare < 0) {
                if (current.left == null) {
                    current.left = new BookNode(book);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new BookNode(book);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Book find(String isbn) {
        BookNode current = root;
        while (current != null) {
            int compare = isbn.compareTo(current.book.isbn);
            if (compare == 0) {
                return current.book;
            }
            current = compare < 0 ? current.left : current.right;
        }
        return null;
    }

    boolean borrow(String isbn) {
        Book book = find(isbn);
        if (book == null || !book.available) {
            return false;
        }
        book.available = false;
        return true;
    }

    boolean returnBook(String isbn) {
        Book book = find(isbn);
        if (book == null || book.available) {
            return false;
        }
        book.available = true;
        return true;
    }

    boolean remove(String isbn) {
        Book book = find(isbn);
        if (book == null || !book.available) {
            return false;
        }
        root = remove(root, isbn);
        return true;
    }

    private BookNode remove(BookNode node, String isbn) {
        if (node == null) {
            return null;
        }
        int compare = isbn.compareTo(node.book.isbn);
        if (compare < 0) {
            node.left = remove(node.left, isbn);
        } else if (compare > 0) {
            node.right = remove(node.right, isbn);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            BookNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.book = successor.book;
            node.right = remove(node.right, successor.book.isbn);
        }
        return node;
    }

    void printRange(String low, String high) {
        System.out.print("range[" + low + "," + high + "]=");
        if (low.compareTo(high) > 0) {
            System.out.println("無效範圍");
            return;
        }
        printRange(root, low, high);
        System.out.println();
    }

    private void printRange(BookNode node, String low, String high) {
        if (node == null) {
            return;
        }
        if (node.book.isbn.compareTo(low) > 0) {
            printRange(node.left, low, high);
        }
        if (node.book.isbn.compareTo(low) >= 0
                && node.book.isbn.compareTo(high) <= 0) {
            System.out.print(node.book.isbn + " ");
        }
        if (node.book.isbn.compareTo(high) < 0) {
            printRange(node.right, low, high);
        }
    }

    void report() {
        report(root);
    }

    private void report(BookNode node) {
        if (node == null) {
            return;
        }
        report(node.left);
        System.out.println("  " + node.book);
        report(node.right);
    }
}

public class LibraryBookBst {
    public static void main(String[] args) {
        BookBst library = new BookBst();

        System.out.println("加入 5003=" + library.add(new Book("5003", "Data Structure", "Lin")));
        System.out.println("加入 5001=" + library.add(new Book("5001", "Java Basics", "Wang")));
        System.out.println("加入 5007=" + library.add(new Book("5007", "Algorithms", "Chen")));
        System.out.println("加入 5005=" + library.add(new Book("5005", "Database", "Liu")));
        System.out.println("重複 5003=" + library.add(new Book("5003", "DS2", "Lin2")));
        System.out.println("加入 null=" + library.add(null));

        System.out.println("報表：");
        library.report();

        System.out.println("查詢 5005=" + library.find("5005"));
        System.out.println("查詢 9999=" + library.find("9999"));

        System.out.println("借出 5003=" + library.borrow("5003"));
        System.out.println("重複借出 5003=" + library.borrow("5003"));
        System.out.println("借出 9999=" + library.borrow("9999"));

        System.out.println("借出中不得刪除 5003=" + library.remove("5003"));
        System.out.println("歸還 5003=" + library.returnBook("5003"));
        System.out.println("重複歸還 5003=" + library.returnBook("5003"));
        System.out.println("歸還後刪除 5003=" + library.remove("5003"));

        System.out.println("刪除 5001=" + library.remove("5001"));
        System.out.println("刪除 9999=" + library.remove("9999"));

        library.printRange("5000", "5006");
        library.printRange("5006", "5000");

        System.out.println("報表：");
        library.report();
    }
}
