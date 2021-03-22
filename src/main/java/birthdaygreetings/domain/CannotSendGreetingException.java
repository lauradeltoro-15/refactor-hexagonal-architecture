package birthdaygreetings.domain;

public class CannotSendGreetingException extends RuntimeException {
    public CannotSendGreetingException(String message) {
        super(message);
    }
}
