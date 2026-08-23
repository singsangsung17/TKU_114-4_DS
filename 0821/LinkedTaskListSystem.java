class Task {
    private final String id;
    private final String title;

    Task(String id, String title) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.title = title == null || title.isBlank() ? "Untitled" : title.trim();
    }

    String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " " + title;
    }
}

class TaskNode {
    private final Task task;
    private TaskNode next;

    TaskNode(Task task) {
        this.task = task;
    }

    Task getTask() {
        return task;
    }

    TaskNode getNext() {
        return next;
    }

    void setNext(TaskNode next) {
        this.next = next;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private int size;

    boolean addFirst(Task task) {
        if (task == null || findById(task.getId()) != null) {
            return false;
        }
        TaskNode node = new TaskNode(task);
        node.setNext(head);
        head = node;
        size++;
        return true;
    }

    boolean addLast(Task task) {
        if (task == null || findById(task.getId()) != null) {
            return false;
        }
        TaskNode node = new TaskNode(task);
        if (head == null) {
            head = node;
            size++;
            return true;
        }
        TaskNode current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(node);
        size++;
        return true;
    }

    Task findById(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        TaskNode current = head;
        while (current != null) {
            if (current.getTask().getId().equals(id.trim())) {
                return current.getTask();
            }
            current = current.getNext();
        }
        return null;
    }

    boolean removeById(String id) {
        if (id == null || head == null) {
            return false;
        }
        String target = id.trim();
        if (head.getTask().getId().equals(target)) {
            head = head.getNext();
            size--;
            return true;
        }
        TaskNode previous = head;
        while (previous.getNext() != null) {
            if (previous.getNext().getTask().getId().equals(target)) {
                previous.setNext(previous.getNext().getNext());
                size--;
                return true;
            }
            previous = previous.getNext();
        }
        return false;
    }

    boolean insertAfter(String existingId, Task task) {
        if (task == null || existingId == null
                || findById(task.getId()) != null) {
            return false;
        }
        TaskNode current = head;
        while (current != null) {
            if (current.getTask().getId().equals(existingId.trim())) {
                TaskNode node = new TaskNode(task);
                node.setNext(current.getNext());
                current.setNext(node);
                size++;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    int size() {
        return size;
    }

    void printAll(String title) {
        System.out.println(title + "（size=" + size + "）");
        if (head == null) {
            System.out.println("  空清單");
            return;
        }
        TaskNode current = head;
        while (current != null) {
            System.out.println("  " + current.getTask());
            current = current.getNext();
        }
    }
}

public class LinkedTaskListSystem {
    public static void main(String[] args) {
        TaskLinkedList tasks = new TaskLinkedList();

        tasks.printAll("初始狀態：");
        System.out.println("空清單查詢：" + tasks.findById("T101"));
        System.out.println("空清單刪除：" + tasks.removeById("T101"));
        System.out.println("空清單插入後方：" + tasks.insertAfter("T101",
                new Task("T102", "Backup")));

        System.out.println("addLast T101：" + tasks.addLast(new Task("T101", "Backup")));
        System.out.println("addLast T102：" + tasks.addLast(new Task("T102", "Deploy")));
        System.out.println("addLast T103：" + tasks.addLast(new Task("T103", "Review")));
        System.out.println("addFirst T100：" + tasks.addFirst(new Task("T100", "Standup")));
        System.out.println("重複 id T102：" + tasks.addLast(new Task("T102", "Deploy2")));
        System.out.println("加入 null：" + tasks.addFirst(null));
        tasks.printAll("建立後：");

        System.out.println("在 T101 後插入 T105："
                + tasks.insertAfter("T101", new Task("T105", "Test")));
        System.out.println("在不存在的 T999 後插入："
                + tasks.insertAfter("T999", new Task("T106", "Docs")));
        tasks.printAll("插入後：");

        System.out.println("查詢 T103：" + tasks.findById("T103"));
        System.out.println("查詢 T999：" + tasks.findById("T999"));

        System.out.println("刪除 head T100：" + tasks.removeById("T100"));
        tasks.printAll("刪除 head 後：");

        System.out.println("刪除中間 T105：" + tasks.removeById("T105"));
        tasks.printAll("刪除中間後：");

        System.out.println("刪除 tail T103：" + tasks.removeById("T103"));
        tasks.printAll("刪除 tail 後：");

        System.out.println("刪除不存在的 T999：" + tasks.removeById("T999"));
        System.out.println("刪除 T101：" + tasks.removeById("T101"));
        System.out.println("刪除 T102：" + tasks.removeById("T102"));
        tasks.printAll("全部刪除後：");
        System.out.println("再次刪除：" + tasks.removeById("T101"));
    }
}
