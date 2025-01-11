package ec.com.sofka.commands.usecase.customer;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.CustomerUpdated;
import ec.com.sofka.commands.responses.account.UpdateAccountResponse;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.busmessage.ICustomerBusMessageGateway;
import ec.com.sofka.gateway.dto.CustomerDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.commands.customer.UpdateCustomerCommand;
import ec.com.sofka.commands.responses.customer.UpdateCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdateCustomerUseCase implements IUseCaseExecute<UpdateCustomerCommand, UpdateCustomerResponse> {

    private final IEventStoreGateway iEventStoreGateway;
    private final ICustomerBusMessageGateway iCustomerRepositoryGateway;

    public UpdateCustomerUseCase(IEventStoreGateway iEventStoreGateway, ICustomerBusMessageGateway iCustomerRepositoryGateway) {
        this.iEventStoreGateway = iEventStoreGateway;
        this.iCustomerRepositoryGateway = iCustomerRepositoryGateway;
    }


    @Override
    public Mono<UpdateCustomerResponse> execute(UpdateCustomerCommand request) {
        return iEventStoreGateway.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {

                    if (events.isEmpty()) {
                        return Mono.error(new ResourceNotFoundException("No events found for aggregate ID: " + request.getAggregateId()));
                    }

                    CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(), events);

                    customer.updateCustomer(
                            request.getIdentification(),
                            request.getFirstName(),
                            request.getLastName(),
                            request.getEmail(),
                            request.getPhone(),
                            request.getAddress(),
                            request.getBirthDate(),
                            request.getStatus()
                    );

                    DomainEvent customerUpdatedEvent = new CustomerUpdated(
                            customer.getCustomer().getId().getValue(),
                            customer.getCustomer().getIdentification().getValue(),
                            customer.getCustomer().getFirstName().getValue(),
                            customer.getCustomer().getLastName().getValue(),
                            customer.getCustomer().getEmail().getValue(),
                            customer.getCustomer().getPhone().getValue(),
                            customer.getCustomer().getAddress().getValue(),
                            customer.getCustomer().getBirthDate().getValue(),
                            customer.getCustomer().getStatus().getValue());


                    return Flux.fromIterable(customer.getUncommittedEvents())
                            .flatMap(iEventStoreGateway::save)
                            .then(Mono.fromCallable(() -> {
                                iCustomerRepositoryGateway.sendMsg(customerUpdatedEvent);

                                customer.markEventsAsCommitted();

                                return new UpdateCustomerResponse(
                                        customer.getCustomer().getId().getValue(),
                                        customer.getCustomer().getIdentification().getValue(),
                                        customer.getCustomer().getFirstName().getValue(),
                                        customer.getCustomer().getLastName().getValue(),
                                        customer.getCustomer().getEmail().getValue(),
                                        customer.getCustomer().getPhone().getValue(),
                                        customer.getCustomer().getAddress().getValue(),
                                        customer.getCustomer().getBirthDate().getValue(),
                                        customer.getCustomer().getStatus().getValue()
                                );

                            }));
                });
    }

}
