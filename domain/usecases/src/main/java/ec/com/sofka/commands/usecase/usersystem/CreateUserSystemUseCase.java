package ec.com.sofka.commands.usecase.usersystem;

import ec.com.sofka.aggregate.UserSystemAggregate;
import ec.com.sofka.commands.responses.usersystem.CreateUserAdminResponse;
import ec.com.sofka.commands.usersystem.CreateUserSystemCommand;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.IUserRepositoryGateway;
import ec.com.sofka.gateway.dto.UserAdminDTO;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateUserSystemUseCase {

    private final IEventStoreGateway eventRepository;
    private final IUserRepositoryGateway iUserRepositoryGateway;

    public CreateUserSystemUseCase(IEventStoreGateway eventRepository, IUserRepositoryGateway iUserRepositoryGateway) {
        this.eventRepository = eventRepository;
        this.iUserRepositoryGateway = iUserRepositoryGateway;
    }


    public Mono<CreateUserAdminResponse> save(CreateUserSystemCommand createUserSystemCommand) {
        return iUserRepositoryGateway.findByEmail(createUserSystemCommand.getEmail())
                .flatMap(existingUser -> Mono.error(new IllegalArgumentException("El correo ya está registrado.")))
                .switchIfEmpty(Mono.defer(() -> {
                    UserSystemAggregate userSystemAggregate = new UserSystemAggregate();

                    userSystemAggregate.createUserSystem(
                            createUserSystemCommand.getFirstname(),
                            createUserSystemCommand.getLastname(),
                            createUserSystemCommand.getEmail(),
                            createUserSystemCommand.getPassword(),
                            createUserSystemCommand.getRole());

                    UserAdminDTO userAdminDTO = new UserAdminDTO(
                            userSystemAggregate.getUserSystem().getId().getValue(),
                            userSystemAggregate.getUserSystem().getFirstname().getValue(),
                            userSystemAggregate.getUserSystem().getLastname().getValue(),
                            userSystemAggregate.getUserSystem().getEmail().getValue(),
                            userSystemAggregate.getUserSystem().getPassword().getValue(),
                            userSystemAggregate.getUserSystem().getRole().name()
                    );

                    return iUserRepositoryGateway.save(userAdminDTO)
                            .flatMap(result -> Flux.fromIterable(userSystemAggregate.getUncommittedEvents())
                                    .flatMap(eventRepository::save)
                                    .then(Mono.fromCallable(() -> {
                                        userSystemAggregate.markEventsAsCommitted();
                                        return new CreateUserAdminResponse(
                                                userSystemAggregate.getUserSystem().getId().getValue(),
                                                userSystemAggregate.getUserSystem().getFirstname().getValue(),
                                                userSystemAggregate.getUserSystem().getLastname().getValue(),
                                                userSystemAggregate.getUserSystem().getEmail().getValue(),
                                                userSystemAggregate.getUserSystem().getPassword().getValue(),
                                                userSystemAggregate.getUserSystem().getRole().name()
                                        );
                                    })));
                }))
                .cast(CreateUserAdminResponse.class); // Asegurar el tipo final del flujo
    }



//
//    public Mono<CreateUserAdminResponse> save(CreateUserSystemCommand createUserSystemCommand){
//        UserSystemAggregate userSystemAggregate = new UserSystemAggregate();
//
//        userSystemAggregate.createUserSystem(
//                createUserSystemCommand.getFirstname(),
//                createUserSystemCommand.getLastname(),
//                createUserSystemCommand.getEmail(),
//                createUserSystemCommand.getPassword(),
//                createUserSystemCommand.getRole());
//
//        UserAdminDTO userAdminDTO = new UserAdminDTO(
//                userSystemAggregate.getUserSystem().getId().getValue(),
//                userSystemAggregate.getUserSystem().getFirstname().getValue(),
//                userSystemAggregate.getUserSystem().getLastname().getValue(),
//                userSystemAggregate.getUserSystem().getEmail().getValue(),
//                userSystemAggregate.getUserSystem().getPassword().getValue(),
//                userSystemAggregate.getUserSystem().getRole().name()
//        );
//
//        return iUserRepositoryGateway.save(userAdminDTO)
//                .flatMap(result -> {
//
//                    return Flux.fromIterable(userSystemAggregate.getUncommittedEvents())
//                            .flatMap(eventRepository::save)
//                            .then(Mono.fromCallable(() -> {
//                                userSystemAggregate.markEventsAsCommitted();
//                                return new CreateUserAdminResponse(
//                                        userSystemAggregate.getUserSystem().getId().getValue(),
//                                        userSystemAggregate.getUserSystem().getFirstname().getValue(),
//                                        userSystemAggregate.getUserSystem().getLastname().getValue(),
//                                        userSystemAggregate.getUserSystem().getEmail().getValue(),
//                                        userSystemAggregate.getUserSystem().getPassword().getValue(),
//                                        userSystemAggregate.getUserSystem().getRole().name()
//                                );
//                            }));
//                });
//
//    }

}
