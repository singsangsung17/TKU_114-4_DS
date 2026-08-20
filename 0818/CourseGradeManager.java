class CourseGrade {
    private final String studentId;
    private final String name;
    private int homework;
    private int midterm;
    private int finalExam;
    private int attendance;

    CourseGrade(String studentId, String name, int homework, int midterm,
                int finalExam, int attendance) {
        this.studentId = studentId == null || studentId.isBlank()
                ? "UNKNOWN" : studentId.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        this.homework = clamp(homework);
        this.midterm = clamp(midterm);
        this.finalExam = clamp(finalExam);
        this.attendance = clamp(attendance);
    }

    private static int clamp(int score) {
        if (score < 0) {
            return 0;
        }
        if (score > 100) {
            return 100;
        }
        return score;
    }

    String getName() {
        return name;
    }

    double calculateFinalScore() {
        return homework * 0.5 + midterm * 0.2 + finalExam * 0.2 + attendance * 0.1;
    }

    String getLevel() {
        double score = calculateFinalScore();
        if (score >= 90) {
            return "A";
        }
        if (score >= 80) {
            return "B";
        }
        if (score >= 70) {
            return "C";
        }
        if (score >= 60) {
            return "D";
        }
        return "F";
    }

    boolean isFailed() {
        return calculateFinalScore() < 60;
    }

    @Override
    public String toString() {
        return String.format("%s %s 平時=%d 期中=%d 期末=%d 出席=%d 總分=%.1f 等第=%s",
                studentId, name, homework, midterm, finalExam, attendance,
                calculateFinalScore(), getLevel());
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] grades = {
            new CourseGrade("S101", "Amy", 92, 88, 90, 100),
            new CourseGrade("S102", "Ben", 75, 68, 72, 90),
            new CourseGrade("S103", "Cara", 58, 45, 50, 80),
            new CourseGrade("S104", "Dan", 85, 79, 83, 95),
            new CourseGrade("S105", "Eva", 40, 120, -10, 70)
        };

        System.out.println("成績明細：");
        for (CourseGrade grade : grades) {
            System.out.println(grade);
        }

        double total = 0;
        for (CourseGrade grade : grades) {
            total += grade.calculateFinalScore();
        }
        System.out.printf("全班平均：%.1f%n", total / grades.length);

        CourseGrade highest = grades[0];
        for (CourseGrade grade : grades) {
            if (grade.calculateFinalScore() > highest.calculateFinalScore()) {
                highest = grade;
            }
        }
        System.out.println("最高分：" + highest);

        System.out.println("不及格名單：");
        int failedCount = 0;
        for (CourseGrade grade : grades) {
            if (grade.isFailed()) {
                System.out.printf("  %s %.1f (%s)%n", grade.getName(),
                        grade.calculateFinalScore(), grade.getLevel());
                failedCount++;
            }
        }
        System.out.println("不及格人數：" + failedCount);
    }
}
