package ec.com.sofka.queries.usecase.usersystem;

import ec.com.sofka.aggregate.UserSystemAggregate;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.IUserRepositoryGateway;
import ec.com.sofka.queries.response.usersystem.CreateUserAdminResponse;
import reactor.core.publisher.Mono;

public class GetUserSystemByEmailUseCase {
     private final IEventStoreGateway iEventStoreGateway;
     private final IUserRepositoryGateway iUserRepositoryGateway;

    public GetUserSystemByEmailUseCase(IEventStoreGateway iEventStoreGateway, IUserRepositoryGateway iUserRepositoryGateway) {
        this.iEventStoreGateway = iEventStoreGateway;
        this.iUserRepositoryGateway = iUserRepositoryGateway;
    }

    public Mono<CreateUserAdminResponse> findByEmail(String aggregateId) {

        return iUserRepositoryGateway.findByEmail(aggregateId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("User System not found with number: " + aggregateId)))
                .map(result -> new CreateUserAdminResponse(
                        aggregateId,
                        result.getFirstname(),
                        result.getLastname(),
                        result.getEmail(),
                        result.getPassword(),
                        result.getRole()
                ));
    }
}
