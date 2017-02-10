package pgoggin;

import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

  public Application() throws SchedulerException {
  }

  public static void main(String[] args) throws SchedulerException {
    SpringApplication.run(Application.class, args);
  }

}
