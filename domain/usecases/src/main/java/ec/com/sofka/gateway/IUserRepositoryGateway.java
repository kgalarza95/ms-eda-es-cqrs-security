package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.UserAdminDTO;
import reactor.core.publisher.Mono;

public interface IUserRepositoryGateway {
    Mono<UserAdminDTO> findByEmail(String email);
    Mono<UserAdminDTO> save(UserAdminDTO dto);
}
