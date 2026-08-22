abstract class EmployeeBase {
    private final String id;
    private final String name;

    EmployeeBase(String id, String name) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
        System.out.println("EmployeeBase constructor: " + this.id);
    }

    String label() {
        return id + " " + name;
    }

    abstract int calculatePay();
}

class FullTimeEmployee extends EmployeeBase {
    private final int monthlySalary;

    FullTimeEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
        System.out.println("FullTimeEmployee constructor: " + this.monthlySalary);
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends EmployeeBase {
    private final int hourlyRate;
    private final int hours;

    PartTimeEmployee(String id, String name, int hourlyRate, int hours) {
        super(id, name);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hours = Math.max(0, hours);
        System.out.println("PartTimeEmployee constructor: "
                + this.hourlyRate + " x " + this.hours);
    }

    @Override
    int calculatePay() {
        return hourlyRate * hours;
    }
}

public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("建立 FullTimeEmployee：");
        EmployeeBase fullTime = new FullTimeEmployee("E01", "Amy", 50000);

        System.out.println("建立 PartTimeEmployee：");
        EmployeeBase partTime = new PartTimeEmployee("E02", "Ben", 220, 80);

        System.out.println("建立薪資為負數的員工：");
        EmployeeBase invalid = new PartTimeEmployee("E03", "Cara", -300, -10);

        System.out.println(fullTime.label() + " pay=" + fullTime.calculatePay());
        System.out.println(partTime.label() + " pay=" + partTime.calculatePay());
        System.out.println(invalid.label() + " pay=" + invalid.calculatePay());

        System.out.println("實際執行順序：EmployeeBase constructor -> subclass constructor");
    }
}
