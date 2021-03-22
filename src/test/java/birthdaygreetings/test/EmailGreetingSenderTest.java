package birthdaygreetings.test;

import birthdaygreetings.domain.CannotSendGreetingException;
import birthdaygreetings.domain.Employee;
import birthdaygreetings.domain.Greeting;
import birthdaygreetings.infrastructure.EmailGreetingSender;
import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static birthdaygreetings.test.EmployeesFactory.employee;
import static birthdaygreetings.test.EmployeesFactory.employeesList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EmailGreetingSenderTest {

    public static final int SMTP_PORT = 25;
    public static final String LOCAL_HOST = "localhost";
    private EmailGreetingSender emailGreetingSender;
    private List<Greeting> greetings;

    @Before
    public void setUp() throws ParseException {
        List<Employee> employeesWhoseBirthdayIsToday = employeesList(
                employee("Mary", "Ann", "1975/03/11", "mary.ann@foobar.com"));
        greetings = Greeting.composeGreetings(employeesWhoseBirthdayIsToday);
    }

    @Test
    public void send_greetings() throws IOException, MessagingException {
        List<Message> messagesSent = new ArrayList<>();
        emailGreetingSender = greetingSenderThatStoresMessagesSent(messagesSent);

        emailGreetingSender.send(greetings);

        assertThat(messagesSent.size(), is(1));
        Message message = messagesSent.get(0);
        assertThat(message.getContent(), is("Happy Birthday, dear Mary!"));
        assertThat(message.getSubject(), is("Happy Birthday!"));
        assertThat(message.getAllRecipients().length, is(1));
        assertThat(message.getAllRecipients()[0].toString(), is("mary.ann@foobar.com"));
    }

    @Test(expected = CannotSendGreetingException.class)
    public void throwsAnExceptionWhenCannotSendAGreeting() {
        emailGreetingSender = greetingSenderThatFails();

        emailGreetingSender.send(greetings);
    }

    private EmailGreetingSender greetingSenderThatStoresMessagesSent(List<Message> messagesSent) {
        return new EmailGreetingSender(LOCAL_HOST, SMTP_PORT) {
            @Override
            public void sendMessage(Message msg) {
                messagesSent.add(msg);
            }
        };
    }

    private EmailGreetingSender greetingSenderThatFails() {
        return new EmailGreetingSender(LOCAL_HOST, SMTP_PORT) {
            @Override
            public void sendMessage(Message msg) throws MessagingException {
                throw new MessagingException();
            }

        };
    }
}
