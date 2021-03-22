package birthdaygreetings.test;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

import birthdaygreetings.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AcceptanceTest {

    private static final int SMTP_PORT = 25;
    private List<Message> messagesSent;
    private BirthdayService service;

    @Before
    public void setUp() {
        messagesSent = new ArrayList<>();
        service = new BirthdayService(new FileEmployeesRepository("src/test/resources/employee_data.txt"),
                new EmailGreetingSender("localhost",SMTP_PORT) {
                    @Override
                    public void sendMessage(Message msg) {
                        messagesSent.add(msg);
                    }

                });
    }

    @Test
    public void baseScenario() throws Exception {

        service.sendGreetings(
                DateHelper.date("2008/10/08")
        );

        assertEquals("message not sent?", 1, messagesSent.size());
        Message message = messagesSent.get(0);
        assertEquals("Happy Birthday, dear John!", message.getContent());
        assertEquals("Happy Birthday!", message.getSubject());
        assertEquals(1, message.getAllRecipients().length);
        assertEquals("john.doe@foobar.com",
                message.getAllRecipients()[0].toString());
    }

    @Test
    public void willNotSendEmailsWhenNobodysBirthday() throws Exception {
        service.sendGreetings(
                DateHelper.date("2008/01/01"));

        assertEquals("what? messages?", 0, messagesSent.size());
    }
}
