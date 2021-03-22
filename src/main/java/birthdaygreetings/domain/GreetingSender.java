package birthdaygreetings.domain;

import java.util.List;

public interface GreetingSender {
    void send(List<Greeting> greetings);
}
