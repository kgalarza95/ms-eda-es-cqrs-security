package ec.com.sofka.repository.mongo;

import ec.com.sofka.document.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends ReactiveMongoRepository<TransactionEntity, String> {
}
