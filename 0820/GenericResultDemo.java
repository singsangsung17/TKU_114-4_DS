class Result<T> {
    private final boolean success;
    private final String message;
    private final T data;

    private Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    static <T> Result<T> ok(T data) {
        return new Result<>(true, "OK", data);
    }

    static <T> Result<T> fail(String message) {
        String reason = message == null || message.isBlank()
                ? "UNKNOWN_ERROR" : message.trim();
        return new Result<>(false, reason, null);
    }

    boolean isSuccess() {
        return success;
    }

    String getMessage() {
        return message;
    }

    T getData() {
        return data;
    }

    T orElse(T fallback) {
        return success && data != null ? data : fallback;
    }

    @Override
    public String toString() {
        return "success=" + success + " message=" + message + " data=" + data;
    }
}

public class GenericResultDemo {
    static Result<String> findName(String id) {
        if ("S101".equals(id)) {
            return Result.ok("Amy");
        }
        return Result.fail("查無此學號：" + id);
    }

    static Result<Integer> findScore(String id) {
        if ("S101".equals(id)) {
            return Result.ok(88);
        }
        return Result.fail("查無成績：" + id);
    }

    public static void main(String[] args) {
        Result<String> nameResult = findName("S101");
        Result<Integer> scoreResult = findScore("S101");

        System.out.println(nameResult);
        System.out.println(scoreResult);
        System.out.println("大寫名字：" + nameResult.getData().toUpperCase());
        System.out.println("加分後：" + (scoreResult.getData() + 5));

        Result<String> missingName = findName("S999");
        Result<Integer> missingScore = findScore("S999");

        System.out.println(missingName);
        System.out.println(missingScore);
        System.out.println("失敗時 data 是否為 null："
                + (missingName.getData() == null));
        System.out.println("預設名字：" + missingName.orElse("Unknown"));
        System.out.println("預設分數：" + missingScore.orElse(0));

        Result<String> emptyReason = Result.fail("   ");
        System.out.println(emptyReason);
        System.out.println("取出資料不需要 cast，型態由 Result<T> 決定");
        System.out.println("把 Result<String> 指派給 Result<Integer> 會在編譯階段被擋下");
    }
}
