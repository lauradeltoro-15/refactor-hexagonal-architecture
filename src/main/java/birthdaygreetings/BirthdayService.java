package birthdaygreetings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BirthdayService {

    private GreetingSender greetingsSender;
    private EmployeesRepository employeesRepository;

    public BirthdayService(EmployeesRepository employeesRepository, GreetingSender greetingSender) {
        this.employeesRepository = employeesRepository;
        this.greetingsSender = greetingSender;
    }

    public void sendGreetings(OurDate ourDate) {
        List<Employee> employees = employeesRepository.getEmployees();
        List<Employee> employeesWhoseBirthdayIsToday = getEmployeesWhoseBirthdayIsToday(ourDate, employees);

        List<Greeting> greetingList = Greeting.composeGreetings(employeesWhoseBirthdayIsToday);

        greetingsSender.send(greetingList);
    }

    private List<Employee> getEmployeesWhoseBirthdayIsToday(OurDate ourDate, List<Employee> employees) {
        return employees.stream().filter(employee -> {
            return employee.isBirthday(ourDate);
        }).collect(Collectors.toList());
    }
}
