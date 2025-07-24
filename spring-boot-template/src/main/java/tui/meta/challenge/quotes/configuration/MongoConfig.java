package tui.meta.challenge.quotes.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "tui.meta.challenge.quotes.repository")
public class MongoConfig {
}
