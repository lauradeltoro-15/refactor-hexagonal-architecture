package birthdaygreetings.domain;

public class NotValidEmployeeException extends RuntimeException {
    public NotValidEmployeeException(String message) {
        super(message);
    }
}
