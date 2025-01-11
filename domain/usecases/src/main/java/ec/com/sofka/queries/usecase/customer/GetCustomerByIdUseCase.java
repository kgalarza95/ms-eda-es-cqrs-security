package ec.com.sofka.queries.usecase.customer;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.ICustomerRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.generics.interfaces.IUseCaseGetMono;
import ec.com.sofka.queries.customer.GetCustomerQuery;
import ec.com.sofka.queries.response.customer.GetCustomerResponse;
import reactor.core.publisher.Mono;

public class GetCustomerByIdUseCase implements IUseCaseGetMono<GetCustomerQuery,GetCustomerResponse> {

    private final ICustomerRepositoryGateway iCustomerRepositoryGateway;
    private final IEventStoreGateway iEventStoreGateway;

    public GetCustomerByIdUseCase(ICustomerRepositoryGateway iCustomerRepositoryGateway, IEventStoreGateway iEventStoreGateway) {
        this.iCustomerRepositoryGateway = iCustomerRepositoryGateway;
        this.iEventStoreGateway = iEventStoreGateway;
    }

    @Override
    public Mono<GetCustomerResponse> get(GetCustomerQuery request) {
        return iEventStoreGateway.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
                    if (events.isEmpty()) {
                        return Mono.error(new ResourceNotFoundException("No events found for aggregate ID: " + request.getAggregateId()));
                    }

                    CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(), events);

                    return iCustomerRepositoryGateway.findByCustomerId(customer.getCustomer().getId().getValue())
                            .map(result -> new GetCustomerResponse(
                                    result.getId(),
                                    result.getIdentification(),
                                    result.getFirstName(),
                                    result.getLastName(),
                                    result.getEmail(),
                                    result.getPhone(),
                                    result.getAddress(),
                                    result.getBirthDate(),
                                    customer.getCustomer().getStatus().getValue()
                            ));
                });
    }

}
