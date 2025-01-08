package ec.com.sofka.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Configuration
//@EnableMongoRepositories(basePackages = "ec.com.sofka.database.events",
//        mongoTemplateRef = "eventMongoTemplate")
public class EventMongoConfig {


//    @Bean(name = "eventsDatabaseFactory")
//    public MongoDatabaseFactory eventsDatabaseFactory(
//            @Value("${spring.data.mongodb.events-uri}") String uri) {
//        return new SimpleMongoClientDatabaseFactory(uri);
//    }
//
//    @Bean(name = "eventMongoTemplate")
//    public MongoTemplate eventsMongoTemplate(@Qualifier("eventsDatabaseFactory") MongoDatabaseFactory eventsDatabaseFactory) {
//        return new MongoTemplate(eventsDatabaseFactory);
//    }
}
