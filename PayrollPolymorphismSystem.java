abstract class Employee {
    private final String id;
    private final String name;

    Employee(String id, String name) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id.trim();
        this.name = name == null || name.isBlank() ? "Unknown" : name.trim();
    }

    String label() {
        return id + " " + name;
    }

    abstract int calculatePay();

    @Override
    public String toString() {
        return label() + " pay=" + calculatePay();
    }
}

class MonthlyEmployee extends Employee {
    private final int monthlySalary;

    MonthlyEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    private final int hourlyRate;
    private final int hours;

    HourlyEmployee(String id, String name, int hourlyRate, int hours) {
        super(id, name);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hours = Math.max(0, hours);
    }

    @Override
    int calculatePay() {
        return hourlyRate * hours;
    }
}

class SalesEmployee extends Employee {
    private final int baseSalary;
    private final int salesAmount;
    private final int commissionPercent;

    SalesEmployee(String id, String name, int baseSalary, int salesAmount,
                  int commissionPercent) {
        super(id, name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionPercent = Math.min(100, Math.max(0, commissionPercent));
    }

    @Override
    int calculatePay() {
        return baseSalary + salesAmount * commissionPercent / 100;
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = {
            new MonthlyEmployee("E01", "Amy", 50000),
            new HourlyEmployee("E02", "Ben", 220, 80),
            new SalesEmployee("E03", "Cara", 30000, 400000, 5),
            new MonthlyEmployee("E04", "Dan", -1000),
            new HourlyEmployee("E05", "Eva", 250, -10)
        };

        System.out.println("薪資明細：");
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        int total = 0;
        for (Employee employee : employees) {
            total += employee.calculatePay();
        }
        System.out.println("薪資總額：" + total);

        Employee highest = employees[0];
        for (Employee employee : employees) {
            if (employee.calculatePay() > highest.calculatePay()) {
                highest = employee;
            }
        }
        System.out.println("最高薪資：" + highest);
    }
}
