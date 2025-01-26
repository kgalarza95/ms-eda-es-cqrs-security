package ec.com.sofka.adapter;

import ec.com.sofka.gateway.ITransactionRepositoryGateway;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.mapper.TransactionRepoMapper;
import ec.com.sofka.queries.response.transaction.GetTransactionResponse;
import ec.com.sofka.repository.mongo.ITransactionRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TransactionAdapterGateway implements ITransactionRepositoryGateway {

    private final ITransactionRepository transactionRepository;
    private final ReactiveMongoTemplate accountReactiveMongoTemplate;

    public TransactionAdapterGateway(ITransactionRepository transactionRepository, ReactiveMongoTemplate accountReactiveMongoTemplate) {
        this.transactionRepository = transactionRepository;
        this.accountReactiveMongoTemplate = accountReactiveMongoTemplate;
    }

    @Override
    public Mono<TransactionDTO> save(TransactionDTO transaction) {
        return Mono.just(transaction)
                .map(TransactionRepoMapper::toEntity)
                .flatMap(transactionRepository::save)
                .map(TransactionRepoMapper::toDomain);
    }

    @Override
    public Flux<GetTransactionResponse> getByAccount(String accountId) {
        return transactionRepository.findByAccountId(accountId)
                .map(TransactionRepoMapper::toDomainTax);
    }

}
