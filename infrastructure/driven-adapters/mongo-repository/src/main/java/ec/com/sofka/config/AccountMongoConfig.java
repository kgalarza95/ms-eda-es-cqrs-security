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

@Configuration
@EnableMongoRepositories(basePackages = "ec.com.sofka.database.account",
       mongoTemplateRef = "accountMongoTemplate")
public class AccountMongoConfig {
    @Primary
    @Bean(name = "accountsDatabaseFactory")
    public MongoDatabaseFactory accountsDatabaseFactory(
            @Value("${spring.data.mongodb.accounts-uri}") String uri) {
        return new SimpleMongoClientDatabaseFactory(uri);
    }


    @Primary
    @Bean(name = "accountMongoTemplate")
    public MongoTemplate accountsMongoTemplate(@Qualifier("accountsDatabaseFactory") MongoDatabaseFactory accountsDatabaseFactory) {
        return new MongoTemplate(accountsDatabaseFactory);
    }
}
