interface Exportable {
    String export();
}

interface Compressible {
    int compress();
}

class BackupDocument implements Exportable, Compressible {
    private final String title;
    private final int sizeKb;

    BackupDocument(String title, int sizeKb) {
        this.title = title == null || title.isBlank() ? "Untitled" : title.trim();
        this.sizeKb = Math.max(0, sizeKb);
    }

    @Override
    public String export() {
        return title + "," + sizeKb + "KB";
    }

    @Override
    public int compress() {
        return sizeKb / 2;
    }
}

public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument document = new BackupDocument("Semester Backup", 480);

        Exportable exportable = document;
        Compressible compressible = document;

        System.out.println("匯出結果：" + exportable.export());
        System.out.println("壓縮後大小：" + compressible.compress() + "KB");

        System.out.println("兩個 reference 指向同一物件："
                + (exportable == compressible));
        System.out.println("exportable 只看得到 export()，compressible 只看得到 compress()");
        System.out.println("同時使用兩種能力：" + document.export()
                + " -> " + document.compress() + "KB");
    }
}
