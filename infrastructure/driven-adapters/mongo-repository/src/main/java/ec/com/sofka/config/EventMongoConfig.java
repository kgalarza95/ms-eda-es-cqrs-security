package ec.com.sofka.config;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(
        basePackages = "ec.com.sofka.repository.events",
        reactiveMongoTemplateRef = "eventReactiveMongoTemplate"
)
public class EventMongoConfig {


    @Bean(name = "eventsDatabaseFactory")
    public ReactiveMongoDatabaseFactory eventsDatabaseFactory(
            @Value("${spring.data.mongodb.events-uri}") String uri) {
        return new SimpleReactiveMongoDatabaseFactory(new ConnectionString(uri));
    }

    @Bean(name = "eventReactiveMongoTemplate")
    public ReactiveMongoTemplate eventsMongoTemplate(@Qualifier("eventsDatabaseFactory") ReactiveMongoDatabaseFactory eventsDatabaseFactory) {
        return new ReactiveMongoTemplate(eventsDatabaseFactory);
    }
}
