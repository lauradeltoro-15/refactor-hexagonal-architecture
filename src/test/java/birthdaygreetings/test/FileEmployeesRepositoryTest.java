package birthdaygreetings.test;

import birthdaygreetings.domain.EmployeesNotFoundException;
import birthdaygreetings.domain.EmployeesRepository;
import birthdaygreetings.domain.NotValidEmployeeException;
import birthdaygreetings.infrastructure.FileEmployeesRepository;
import org.junit.Test;

import static birthdaygreetings.test.EmployeesFactory.employee;
import static birthdaygreetings.test.EmployeesFactory.employeesList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileEmployeesRepositoryTest {
    private EmployeesRepository employeeRepository;

    @Test
    public void obtains_employees_list() throws Exception {
        employeeRepository = new FileEmployeesRepository("src/test/resources/employee_data.txt");

        assertThat(
                employeeRepository.getEmployees(),
                is(employeesList(employee("John", "Doe", "1982/10/08", "john.doe@foobar.com"),
                        employee("Mary", "Ann", "1975/03/11", "mary.ann@foobar.com"))));
    }

    @Test(expected = EmployeesNotFoundException.class)
    public void throws_an_exception_if_there_is_no_file() throws Exception {
        employeeRepository = new FileEmployeesRepository("src/test/resources/non_existent_data.txt");

        employeeRepository.getEmployees();
    }

    @Test(expected = NotValidEmployeeException.class)
    public void throws_an_exception_if_there_is_an_employee_with_invalid_data() throws Exception {
        employeeRepository = new FileEmployeesRepository("src/test/resources/employee_data_with_invalid_date.txt");

        employeeRepository.getEmployees();
    }
}