interface ReportExporter {
    String format();

    String export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public String format() {
        return "csv";
    }

    @Override
    public String export(String title, int[] values) {
        StringBuilder builder = new StringBuilder();
        builder.append(title);
        if (values != null) {
            for (int value : values) {
                builder.append(",").append(value);
            }
        }
        return builder.toString();
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public String format() {
        return "json";
    }

    @Override
    public String export(String title, int[] values) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"title\":\"").append(title).append("\",\"values\":[");
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    builder.append(",");
                }
                builder.append(values[i]);
            }
        }
        builder.append("]}");
        return builder.toString();
    }
}

class TextExporter implements ReportExporter {
    @Override
    public String format() {
        return "text";
    }

    @Override
    public String export(String title, int[] values) {
        int total = 0;
        int count = 0;
        if (values != null) {
            for (int value : values) {
                total += value;
                count++;
            }
        }
        return title + " 共 " + count + " 筆，合計 " + total;
    }
}

public class ReportExporterFactory {
    static ReportExporter createExporter(String format) {
        if ("csv".equalsIgnoreCase(format)) {
            return new CsvExporter();
        }
        if ("json".equalsIgnoreCase(format)) {
            return new JsonExporter();
        }
        return new TextExporter();
    }

    static void exportReport(ReportExporter exporter, String title, int[] values) {
        if (exporter == null) {
            System.out.println("未指定輸出格式");
            return;
        }
        String safeTitle = title == null || title.isBlank()
                ? "Untitled" : title.trim();
        System.out.println("[" + exporter.format() + "] "
                + exporter.export(safeTitle, values));
    }

    public static void main(String[] args) {
        int[] values = {120, 340, 260};

        exportReport(createExporter("csv"), "月銷售", values);
        exportReport(createExporter("json"), "月銷售", values);
        exportReport(createExporter("text"), "月銷售", values);
        exportReport(createExporter("xml"), "月銷售", values);
        exportReport(createExporter(null), "月銷售", values);

        exportReport(createExporter("csv"), "空資料", null);
        exportReport(createExporter("json"), "空資料", null);
        exportReport(createExporter("text"), "空資料", null);
        exportReport(createExporter("text"), "   ", new int[0]);
        exportReport(null, "月銷售", values);
    }
}
