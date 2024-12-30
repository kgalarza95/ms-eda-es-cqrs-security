package ec.com.sofka.database.account;

import ec.com.sofka.data.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMongoRepository extends MongoRepository<AccountEntity, String> {

}
