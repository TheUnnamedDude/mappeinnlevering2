package no.kevin.innlevering3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration
@ImportResource("classpath:applicationContext.xml")
@PropertySource("classpath:locale.properties")
public class PrimeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrimeWebApplication.class, args);
    }
}
