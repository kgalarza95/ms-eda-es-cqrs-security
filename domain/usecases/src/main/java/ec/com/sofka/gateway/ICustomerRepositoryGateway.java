package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerRepositoryGateway {
    Flux<CustomerDTO> findAll();
    Mono<CustomerDTO> findByCustomerId(String id);
    Mono<CustomerDTO> save(CustomerDTO dto);
    Mono<CustomerDTO> update(CustomerDTO dto);
    Mono<Void> delete(CustomerDTO dto);
}
