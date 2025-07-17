package tn.star.rhconge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RhCongeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RhCongeApplication.class, args);
    }

}
