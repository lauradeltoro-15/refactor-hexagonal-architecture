package birthdaygreetings.test;

import birthdaygreetings.CannotSendGreetingException;
import birthdaygreetings.EmailGreetingSender;
import birthdaygreetings.Greeting;
import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.List;

import static birthdaygreetings.Greeting.composeGreetings;
import static birthdaygreetings.test.EmployeesFactory.employee;
import static birthdaygreetings.test.EmployeesFactory.employeesList;

public class EmailGreetingSenderTest {

    private EmailGreetingSender emailGreetingSender;

    @Before
    public void setUp() {
        emailGreetingSender = new EmailGreetingSender("localhost", 3) {
            @Override
            public void sendMessage(Message msg) throws MessagingException {
                throw new MessagingException();
            }

        };
    }

    @Test(expected = CannotSendGreetingException.class)
    public void throwsAnExceptionWhenCannotSendAGreeting() throws Exception {
        List<Greeting> greetings = composeGreetings(
                employeesList(
                employee("Pepe", "González", "0000/12/12", "pepe@pepe.com")
                ));

        emailGreetingSender.send(greetings);

    }
}
