package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.TransactionDTO;
import reactor.core.publisher.Mono;

public interface ITransactionRepositoryGateway {
    Mono<TransactionDTO> save(TransactionDTO transaction);
}
