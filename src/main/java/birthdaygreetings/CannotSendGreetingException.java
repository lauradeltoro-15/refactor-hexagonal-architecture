package birthdaygreetings;

public class CannotSendGreetingException extends RuntimeException {
    public CannotSendGreetingException(String message) {
        super(message);
    }
}
