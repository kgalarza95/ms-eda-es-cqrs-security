package ec.com.sofka.usecase.account;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.request.account.UpdateAccountRequest;
import ec.com.sofka.responses.account.UpdateAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdateAccountUseCase implements IUseCaseExecute<UpdateAccountRequest, UpdateAccountResponse> {
    private final IAccountRepositoryGateway IAccountRepositoryGateway;
    private final IEventStoreGateway eventRepository;

    public UpdateAccountUseCase(IAccountRepositoryGateway IAccountRepositoryGateway, IEventStoreGateway eventRepository) {
        this.IAccountRepositoryGateway = IAccountRepositoryGateway;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<UpdateAccountResponse> execute(UpdateAccountRequest request) {
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
                    CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(), events);

                    customer.updateAccount(
                            customer.getAccount().getId().getValue(),
                            request.getBalance(),
                            request.getNumber(),
                            request.getCustomerName(),
                            request.getStatus()
                    );

                    AccountDTO accountDTO = new AccountDTO(
                            customer.getAccount().getId().getValue(),
                            request.getCustomerName(),
                            request.getNumber(),
                            customer.getAccount().getBalance().getValue(),
                            customer.getAccount().getStatus().getValue()
                    );

                    return IAccountRepositoryGateway.update(accountDTO)
                            .flatMap(result -> {
                                if (result != null) {
                                    return Flux.fromIterable(customer.getUncommittedEvents())
                                            .flatMap(eventRepository::save)
                                            .then(Mono.fromCallable(() -> {
                                                customer.markEventsAsCommitted();
                                                return new UpdateAccountResponse(
                                                        request.getAggregateId(),
                                                        result.getId(),
                                                        result.getAccountNumber(),
                                                        result.getName(),
                                                        result.getStatus()
                                                );
                                            }));
                                } else {
                                    return Mono.just(new UpdateAccountResponse());
                                }
                            });
                });
    }

}
