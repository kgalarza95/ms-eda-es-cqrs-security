package ec.com.sofka.repository;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "ec.com.sofka.repository")
public class TestConfiguration {
        @Bean
        public MongoClient reactiveMongoClient() {
                return MongoClients.create("mongodb://localhost:27017/test");
        }

        @Bean
        public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient, MappingMongoConverter converter) {
                return new ReactiveMongoTemplate(mongoClient, "testdb");
        }
}
