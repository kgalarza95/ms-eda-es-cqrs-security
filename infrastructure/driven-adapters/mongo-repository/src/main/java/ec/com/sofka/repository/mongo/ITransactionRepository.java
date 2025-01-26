package ec.com.sofka.repository.mongo;

import ec.com.sofka.document.TransactionEntity;
import ec.com.sofka.queries.response.transaction.GetTransactionResponse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ITransactionRepository extends ReactiveMongoRepository<TransactionEntity, String> {
    Flux<TransactionEntity> findByAccountId(String accountId);
}
