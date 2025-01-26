package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.queries.response.transaction.GetTransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionRepositoryGateway {
    Mono<TransactionDTO> save(TransactionDTO transaction);
    Flux<GetTransactionResponse> getByAccount(String accountId);
}
