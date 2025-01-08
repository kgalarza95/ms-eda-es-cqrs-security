package ec.com.sofka.usecase.customer;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.ICustomerRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.gateway.dto.CustomerDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.request.account.UpdateAccountRequest;
import ec.com.sofka.request.customer.CreateCustomerRequest;
import ec.com.sofka.request.customer.UpdateCustomerRequest;
import ec.com.sofka.responses.account.UpdateAccountResponse;
import ec.com.sofka.responses.customer.CreateCustomerResponse;
import ec.com.sofka.responses.customer.UpdateCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdateCustomerUseCase implements IUseCaseExecute<UpdateCustomerRequest, UpdateCustomerResponse> {

    private final IEventStoreGateway iEventStoreGateway;
    private final ICustomerRepositoryGateway iCustomerRepositoryGateway;

    public UpdateCustomerUseCase(IEventStoreGateway iEventStoreGateway, ICustomerRepositoryGateway iCustomerRepositoryGateway) {
        this.iEventStoreGateway = iEventStoreGateway;
        this.iCustomerRepositoryGateway = iCustomerRepositoryGateway;
    }

    @Override
    public Mono<UpdateCustomerResponse> execute(UpdateCustomerRequest request) {
        return iEventStoreGateway.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
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

                    CustomerDTO customerDTO = new CustomerDTO(
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

                    return iCustomerRepositoryGateway.update(customerDTO)
                            .flatMap(result -> {
                                if (result != null) {
                                    return Flux.fromIterable(customer.getUncommittedEvents())
                                            .flatMap(iEventStoreGateway::save)
                                            .then(Mono.fromCallable(() -> {
                                                customer.markEventsAsCommitted();
                                                return new UpdateCustomerResponse(
                                                        result.getId(),
                                                        result.getIdentification(),
                                                        result.getFirstName(),
                                                        result.getLastName(),
                                                        result.getEmail(),
                                                        result.getPhone(),
                                                        result.getAddress(),
                                                        result.getBirthDate(),
                                                        result.getStatus()
                                                );
                                            }));
                                } else {
                                    return Mono.error(new IllegalStateException("Customer update failed"));
                                }
                            });
                });
    }

}
