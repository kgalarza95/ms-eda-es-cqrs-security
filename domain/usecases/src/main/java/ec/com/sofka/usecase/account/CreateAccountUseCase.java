package ec.com.sofka.usecase.account;


import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.request.account.CreateAccountRequest;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.responses.account.CreateAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Usage of the IUseCase interface
public class CreateAccountUseCase implements IUseCaseExecute<CreateAccountRequest, CreateAccountResponse> {
    private final IEventStoreGateway eventRepository;
    private final IAccountRepositoryGateway iAccountRepositoryGateway;

    public CreateAccountUseCase(IEventStoreGateway repository, IAccountRepositoryGateway IAccountRepositoryGateway) {
        this.eventRepository = repository;
        this.iAccountRepositoryGateway = IAccountRepositoryGateway;
    }

    @Override
    public Mono<CreateAccountResponse> execute(CreateAccountRequest request) {
        CustomerAggregate customer = new CustomerAggregate();

        customer.createAccount(request.getNumber(), request.getBalance(), request.getCustomerName(), request.getStatus());

        AccountDTO accountDTO = new AccountDTO(
                customer.getAccount().getId().getValue(),
                customer.getAccount().getName().getValue(),
                customer.getAccount().getNumber().getValue(),
                customer.getAccount().getBalance().getValue(),
                customer.getAccount().getStatus().getValue()
        );

        return iAccountRepositoryGateway.save(accountDTO)
                .flatMap(result -> {

                    return Flux.fromIterable(customer.getUncommittedEvents())
                            .flatMap(eventRepository::save)
                            .then(Mono.fromCallable(() -> {
                                customer.markEventsAsCommitted();
                                return new CreateAccountResponse(
                                        customer.getId().getValue(),
                                        customer.getAccount().getId().getValue(),
                                        customer.getAccount().getNumber().getValue(),
                                        customer.getAccount().getName().getValue(),
                                        customer.getAccount().getBalance().getValue(),
                                        customer.getAccount().getStatus().getValue()
                                );
                            }));
                });
    }



}
