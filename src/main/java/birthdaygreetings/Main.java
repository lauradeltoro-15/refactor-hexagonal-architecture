package birthdaygreetings;

import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        BirthdayService service = new BirthdayService(new FileEmployeesRepository("employee_data.txt"),
                                new EmailGreetingSender("localhost", 25));
        try {
            service.sendGreetings(
                    new OurDate(new SimpleDateFormat("yyyy/MM/dd").parse("2008/10/08"))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
