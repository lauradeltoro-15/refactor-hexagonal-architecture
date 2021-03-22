package birthdaygreetings.test;

import birthdaygreetings.*;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.List;

import static birthdaygreetings.test.DateHelper.date;
import static birthdaygreetings.test.EmployeesFactory.employee;
import static birthdaygreetings.test.EmployeesFactory.employeesList;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

public class AcceptanceTest {

    private BirthdayService service;
    private GreetingSender greetingSender;

    @Before
    public void setUp() throws ParseException {
        EmployeesRepository employeesRepository = mock(EmployeesRepository.class);
        greetingSender = mock(GreetingSender.class);
        when(employeesRepository.getEmployees()).thenReturn(employeesList(employee("John", "Doe", "1982/10/08", "john.doe@foobar.com"),
                employee("Mary", "Ann", "1975/03/11", "mary.ann@foobar.com")));

        service = new BirthdayService(employeesRepository, greetingSender);
    }

    @Test
    public void baseScenario() throws ParseException {
        service.sendGreetings(date("2008/10/08"));

        List<Employee> employeesWhoseBirthdayIsToday = employeesList(
                employee("John", "Doe", "1982/10/08", "john.doe@foobar.com"));

        verify(greetingSender).send(Greeting.composeGreetings(employeesWhoseBirthdayIsToday));
    }

    @Test
    public void willNotSendEmailsWhenNobodysBirthday() throws ParseException {
        service.sendGreetings(date("2008/12/18"));

        verify(greetingSender).send(emptyList());
    }
}
