package ec.com.sofka.adapter;

import ec.com.sofka.gateway.IUserRepositoryGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.gateway.dto.UserAdminDTO;
import ec.com.sofka.mapper.AccountRepoMapper;
import ec.com.sofka.mapper.UserSystemRepoMapper;
import ec.com.sofka.repository.mongo.IUserSystemRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserSystemMongoAdapterGateWay implements IUserRepositoryGateway {

    private final ReactiveMongoTemplate accountReactiveMongoTemplate;
    private final IUserSystemRepository iUserSystemRepository;

    public UserSystemMongoAdapterGateWay(ReactiveMongoTemplate accountReactiveMongoTemplate, IUserSystemRepository iUserSystemRepository) {
        this.accountReactiveMongoTemplate = accountReactiveMongoTemplate;
        this.iUserSystemRepository = iUserSystemRepository;
    }

    @Override
    public Mono<UserAdminDTO> findByEmail(String email) {
        return iUserSystemRepository.findByEmail(email)
                .map(UserSystemRepoMapper::toDTO);
    }

    @Override
    public Mono<UserAdminDTO> save(UserAdminDTO dto) {
        return Mono.just(dto)
                .map(UserSystemRepoMapper::toEntity)
                .flatMap(iUserSystemRepository::save)
                .map(UserSystemRepoMapper::toDTO);
    }
}
