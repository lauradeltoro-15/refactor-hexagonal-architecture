package birthdaygreetings.test;

import birthdaygreetings.domain.Employee;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class EmployeesFactory {
    public static Employee employee (String firstName, String lastName, String birthDate, String email) throws ParseException {
        return new Employee(firstName, lastName, DateHelper.date(birthDate), email);
    }

    public static List<Employee> employeesList(Employee ...employees) {
        return Arrays.asList(employees);
    }
}
