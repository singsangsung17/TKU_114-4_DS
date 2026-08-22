interface MessageSender {
    void send(String receiver, String message);
}

class EmailSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("EMAIL to " + receiver + "：" + message);
    }
}

class SmsSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("SMS to " + receiver + "：" + message);
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("CONSOLE " + receiver + "：" + message);
    }
}

class LineSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("LINE to " + receiver + "：" + message);
    }
}

public class MessageSenderSystem {
    static boolean notify(MessageSender sender, String receiver, String message) {
        if (sender == null) {
            System.out.println("未指定發送方式");
            return false;
        }
        if (receiver == null || receiver.isBlank()) {
            System.out.println("收件者不可為空");
            return false;
        }
        if (message == null || message.isBlank()) {
            System.out.println("訊息不可為空");
            return false;
        }
        sender.send(receiver.trim(), message.trim());
        return true;
    }

    public static void main(String[] args) {
        MessageSender[] senders = {
            new EmailSender(),
            new SmsSender(),
            new ConsoleSender(),
            new LineSender()
        };

        for (MessageSender sender : senders) {
            System.out.println("送出結果："
                    + notify(sender, "amy@example.com", "Assignment uploaded"));
        }

        System.out.println("送出結果："
                + notify(new EmailSender(), "   ", "Class starts at 10:10"));
        System.out.println("送出結果："
                + notify(new SmsSender(), "0912345678", "   "));
        System.out.println("送出結果："
                + notify(null, "0912345678", "Class starts at 10:10"));
    }
}
