package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.AccountDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountRepositoryGateway {
    Flux<AccountDTO> findAll();
    Mono<AccountDTO> findByAccountId(String id);
    Mono<AccountDTO> findByNumber(String number);
    Mono<AccountDTO> save(AccountDTO account);
    Mono<AccountDTO> update(AccountDTO account);
    Mono<Void> delete(AccountDTO account);
}
