package birthdaygreetings.infrastructure;

import birthdaygreetings.domain.CannotSendGreetingException;
import birthdaygreetings.domain.Employee;
import birthdaygreetings.domain.Greeting;
import birthdaygreetings.domain.GreetingSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class EmailGreetingSender implements GreetingSender {

    private final String smtpHost;
    private final int smtpPort;
    private final String SENDER = "sender@here.com";

    public EmailGreetingSender(String smtpHost, int smtpPort) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    @Override
    public void send(List<Greeting> greetings) {
        for (Greeting greeting: greetings){
            send(greeting);
        }
    }

    private void send(Greeting greeting)  {
        Session session = createMailSession();
        Message msg = null;
        try {
            msg = constructMessage(greeting, session);
            sendMessage(msg);
        } catch (MessagingException e) {
            throw new CannotSendGreetingException(e.getMessage());
        }
    }

    private Session createMailSession() {
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", this.smtpHost);
        props.put("mail.smtp.port", "" + this.smtpPort);
        return Session.getDefaultInstance(props, null);
    }

    private Message constructMessage(Greeting greeting, Session session) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(SENDER));
        Employee employee = greeting.getEmployee();
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
                employee.getEmail()));
        msg.setSubject(greeting.getTitle());
        msg.setText(greeting.getText());
        return msg;
    }

    protected void sendMessage(Message msg) throws MessagingException {
        Transport.send(msg);
    }
}
