package ec.com.sofka.commands.usecase.account;


import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.busmessage.IAccountBusMessageGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.model.util.StatusEnum;
import ec.com.sofka.commands.account.UpdateAccountCommand;
import ec.com.sofka.commands.responses.account.UpdateAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeleteAccountUseCase implements IUseCaseExecute<UpdateAccountCommand, UpdateAccountResponse> {

    private final IEventStoreGateway iEventStoreGateway;
    private final IAccountBusMessageGateway iAccountBusMessageGateway;

    public DeleteAccountUseCase(IEventStoreGateway iEventStoreGateway, IAccountBusMessageGateway iAccountBusMessageGateway) {
        this.iEventStoreGateway = iEventStoreGateway;
        this.iAccountBusMessageGateway = iAccountBusMessageGateway;
    }


    @Override
    public Mono<UpdateAccountResponse> execute(UpdateAccountCommand request) {
        return iEventStoreGateway.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {

                    if (events.isEmpty()) {
                        return Mono.error(new ResourceNotFoundException("No events found for aggregate ID: " + request.getAggregateId()));
                    }

                    //verificar si ultimo estado es inactivo para lanzar error.
                    CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(), events);

                    customer.updateAccount(
                            customer.getAccount().getId().getValue(),
                            customer.getAccount().getBalance().getValue(),
                            customer.getAccount().getNumber().getValue(),
                            customer.getAccount().getName().getValue(),
                            StatusEnum.INACTIVE.name()
                    );
                    DomainEvent accountUpdatedEvent = new AccountUpdated(
                            customer.getAccount().getId().getValue(),
                            request.getBalance(),
                            request.getNumber(),
                            request.getCustomerName(),
                            request.getStatus()
                    );

                    return Flux.fromIterable(customer.getUncommittedEvents())
                            .flatMap(iEventStoreGateway::save)
                            .then(Mono.fromCallable(() -> {
                                iAccountBusMessageGateway.sendMsg(accountUpdatedEvent);

                                customer.markEventsAsCommitted();

                                return new UpdateAccountResponse(
                                        request.getAggregateId(),
                                        customer.getAccount().getId().getValue(),
                                        customer.getAccount().getNumber().getValue(),
                                        customer.getAccount().getName().getValue(),
                                        customer.getAccount().getStatus().getValue(),
                                        customer.getAccount().getBalance().getValue()
                                );

                            }));
                });
    }


}
