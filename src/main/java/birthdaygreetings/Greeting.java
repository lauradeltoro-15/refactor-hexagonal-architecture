package birthdaygreetings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Greeting {

    private final Employee employee;
    private final Content content;

    private Greeting(Employee employee, Content content) {
        this.employee = employee;
        this.content = content;
    }

    public static List<Greeting> composeGreetings(List<Employee> employeesWhoseBirthdayIsToday) {
        return employeesWhoseBirthdayIsToday.stream().map(Greeting::compose).collect(Collectors.toList());
    }

    private static Greeting compose(Employee employee) {
        Content content = Content.compose(employee);
        return new Greeting(employee,content);
    }

    public Employee getEmployee() {
        return employee;
    }

    String getTitle() {
        return content.getTitle();
    }

    String getText() {
        return content.getText();
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "employee=" + employee +
                ", greetingContent=" + content +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Greeting greeting = (Greeting) o;
        return Objects.equals(employee, greeting.employee) &&
                Objects.equals(content, greeting.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, content);
    }

    public static class Content {

        public static final String HAPPY_BIRTHDAY = "Happy Birthday!";
        private String text;

        public Content(String text) {
            this.text = text;
        }

        public static Content compose(Employee employee) {
            String text = "Happy Birthday, dear %NAME%!".replace("%NAME%",
                    employee.getFirstName());
            return new Content(text);
        }

        String getTitle() {
            return HAPPY_BIRTHDAY;
        }

        String getText() {
            return this.text;
        }

        @Override
        public String toString() {
            return "GreetingContent{" +
                    "text='" + text + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Content that = (Content) o;
            return Objects.equals(text, that.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }
    }
}
