package am.greenlight.greenlight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GreenLightApplication  {

    public static void main(String[] args) {
        SpringApplication.run(GreenLightApplication.class, args);
    }

}
