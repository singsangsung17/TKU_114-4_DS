import java.util.ArrayDeque;
import java.util.Deque;

class BrowserHistory {
    private final Deque<String> history = new ArrayDeque<>();

    boolean visit(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }
        history.push(url.trim());
        return true;
    }

    String back() {
        if (history.isEmpty()) {
            return "EMPTY";
        }
        history.pop();
        return current();
    }

    String current() {
        String page = history.peek();
        return page == null ? "BLANK" : page;
    }

    int size() {
        return history.size();
    }

    @Override
    public String toString() {
        return history.toString();
    }
}

public class BrowserBackStack {
    public static void main(String[] args) {
        BrowserHistory browser = new BrowserHistory();

        System.out.println("初始頁面：" + browser.current());
        System.out.println("空歷程按上一頁：" + browser.back());

        System.out.println("visit tku.edu.tw：" + browser.visit("tku.edu.tw"));
        System.out.println("visit moodle：" + browser.visit("moodle.tku.edu.tw"));
        System.out.println("visit github：" + browser.visit("github.com"));
        System.out.println("visit 空字串：" + browser.visit("   "));
        System.out.println("visit null：" + browser.visit(null));

        System.out.println("目前頁面：" + browser.current());
        System.out.println("歷程：" + browser + " 筆數=" + browser.size());

        System.out.println("上一頁：" + browser.back());
        System.out.println("上一頁：" + browser.back());
        System.out.println("上一頁：" + browser.back());
        System.out.println("再按上一頁：" + browser.back());

        System.out.println("目前頁面：" + browser.current());
        System.out.println("歷程：" + browser + " 筆數=" + browser.size());
    }
}
