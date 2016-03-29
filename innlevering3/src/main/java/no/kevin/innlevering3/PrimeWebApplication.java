package no.kevin.innlevering3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@ImportResource("classpath:applicationContext.xml")
public class PrimeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrimeWebApplication.class, args);
    }
}
