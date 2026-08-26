
public class Q03_EmployeePayroll {
    public static abstract class Employee {
        private final String id;
        private final String name;

        protected Employee(String id, String name) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("id is invalid");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("name is invalid");
            }
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public abstract int monthlyPay();

        public String summary() {
            return id + "|" + name + "|" + monthlyPay();
        }
    }

    public static class SalariedEmployee extends Employee {
        private final int salary;

        public SalariedEmployee(String id, String name, int salary) {
            super(id, name);
            this.salary = salary < 0 ? 0 : salary;
        }

        @Override
        public int monthlyPay() {
            return salary;
        }
    }

    public static class HourlyEmployee extends Employee {
        private final int hours;
        private final int hourlyRate;

        public HourlyEmployee(String id, String name, int hours, int hourlyRate) {
            super(id, name);
            this.hours = hours < 0 ? 0 : hours;
            this.hourlyRate = hourlyRate < 0 ? 0 : hourlyRate;
        }

        @Override
        public int monthlyPay() {
            if (hours <= 160) {
                return hours * hourlyRate;
            }
            double base = 160.0 * hourlyRate;
            double overtime = (hours - 160) * hourlyRate * 1.5;
            return (int) (base + overtime);
        }
    }

    public static int totalPayroll(java.util.List<Employee> employees) {
        if (employees == null) {
            return 0;
        }
        int total = 0;
        for (Employee e : employees) {
            if (e != null) {
                total += e.monthlyPay();
            }
        }
        return total;
    }
}