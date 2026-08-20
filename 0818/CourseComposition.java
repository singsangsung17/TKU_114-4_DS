class Instructor {
    private String id;
    private String name;

    Instructor(String id, String name) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String label() {
        return id + " " + name;
    }

    @Override
    public String toString() {
        return label();
    }
}

class Course {
    private String courseCode;
    private String title;
    private Instructor instructor;

    Course(String courseCode, String title, Instructor instructor) {
        this.courseCode = courseCode == null || courseCode.isBlank()
                ? "UNKNOWN" : courseCode.trim();
        this.title = title == null || title.isBlank()
                ? "Untitled" : title.trim();
        this.instructor = instructor;
    }

    Instructor getInstructor() {
        return instructor;
    }

    String summary() {
        String teacher = instructor == null ? "未指派授課者" : instructor.label();
        return courseCode + " | " + title + " | 授課者：" + teacher;
    }
}

public class CourseComposition {
    public static void main(String[] args) {
        Instructor lin = new Instructor("T001", "Lin");
        Instructor wang = new Instructor("T002", "Wang");

        Course dataStructure = new Course("CS201", "Data Structure", lin);
        Course javaLab = new Course("CS202", "Java Lab", lin);
        Course database = new Course("IM301", "Database", wang);
        Course unassigned = new Course("IM999", "Special Topics", null);

        System.out.println(dataStructure.summary());
        System.out.println(javaLab.summary());
        System.out.println(database.summary());
        System.out.println(unassigned.summary());

        System.out.println("兩門課共用同一位授課者："
                + (dataStructure.getInstructor() == javaLab.getInstructor()));
        System.out.println("授課者姓名由 composition 取得："
                + dataStructure.getInstructor().getName());
    }
}
