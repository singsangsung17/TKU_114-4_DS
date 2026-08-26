
import java.util.ArrayList;
import java.util.List;

public class Q04_NotificationRouter {
    public interface Channel {
        String name();

        boolean supports(String destination);

        String send(String destination, String message);
    }

    public static class EmailChannel implements Channel {
        @Override
        public String name() {
            return "EMAIL";
        }

        @Override
        public boolean supports(String destination) {
            if (destination == null) {
                return false;
            }
            int at = destination.indexOf('@');
            return at > 0 && at < destination.length() - 1;
        }

        @Override
        public String send(String destination, String message) {
            return name() + "|" + destination + "|" + message;
        }
    }

    public static class SmsChannel implements Channel {
        @Override
        public String name() {
            return "SMS";
        }

        @Override
        public boolean supports(String destination) {
            if (destination == null) {
                return false;
            }
            String digits = destination.replace("-", "");
            if (digits.length() != 10) {
                return false;
            }
            for (int i = 0; i < digits.length(); i++) {
                if (digits.charAt(i) < '0' || digits.charAt(i) > '9') {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String send(String destination, String message) {
            return name() + "|" + destination + "|" + message;
        }
    }

    public static java.util.List<String> route(
            java.util.List<Channel> channels,
            String destination,
            String message
    ) {
        List<String> result = new ArrayList<String>();
        if (channels == null || destination == null || message == null) {
            return result;
        }
        for (Channel c : channels) {
            if (c != null && c.supports(destination)) {
                result.add(c.send(destination, message));
            }
        }
        return result;
    }
}
