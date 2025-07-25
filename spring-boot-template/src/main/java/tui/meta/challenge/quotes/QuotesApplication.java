package tui.meta.challenge.quotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
@EnableRetry
public class QuotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotesApplication.class, args);
    }
}