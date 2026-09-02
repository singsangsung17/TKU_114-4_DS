import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginActivityReport {
    record LoginRecord(String account, String ip) {}

    static Map<String, Integer> countByAccount(List<LoginRecord> records) {
        Map<String, Integer> counts = new HashMap<>();
        for (LoginRecord record : records) {
            counts.merge(record.account(), 1, Integer::sum);
        }
        return counts;
    }

    static Map<String, Set<String>> ipsByAccount(List<LoginRecord> records) {
        Map<String, Set<String>> ips = new HashMap<>();
        for (LoginRecord record : records) {
            ips.computeIfAbsent(record.account(), key -> new HashSet<>()).add(record.ip());
        }
        return ips;
    }

    static List<String> abnormalReport(List<LoginRecord> records, int threshold) {
        Map<String, Integer> counts = countByAccount(records);
        Map<String, Set<String>> ips = ipsByAccount(records);
        List<String> accounts = new ArrayList<>(counts.keySet());
        accounts.sort(null);
        List<String> report = new ArrayList<>();
        for (String account : accounts) {
            int count = counts.get(account);
            int ipCount = ips.get(account).size();
            if (count > threshold || ipCount > 1) {
                report.add(account + " count=" + count + " ipCount=" + ipCount);
            }
        }
        return report;
    }

    public static void main(String[] args) {
        List<LoginRecord> records = List.of(
                new LoginRecord("amy", "192.168.0.1"),
                new LoginRecord("amy", "192.168.0.1"),
                new LoginRecord("amy", "10.0.0.5"),
                new LoginRecord("ben", "192.168.0.2"),
                new LoginRecord("cathy", "192.168.0.3"),
                new LoginRecord("cathy", "192.168.0.3"),
                new LoginRecord("cathy", "192.168.0.3"));

        System.out.println("counts=" + countByAccount(records));
        Map<String, Set<String>> ips = ipsByAccount(records);
        for (String account : ips.keySet()) {
            System.out.println(account + " distinctIp=" + ips.get(account).size());
        }
        for (String line : abnormalReport(records, 2)) System.out.println(line);
    }
}
