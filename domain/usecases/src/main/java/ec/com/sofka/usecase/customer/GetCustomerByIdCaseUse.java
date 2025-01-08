package ec.com.sofka.usecase.customer;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.gateway.ICustomerRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.request.customer.GetCustomerRequest;
import ec.com.sofka.responses.customer.GetCustomerResponse;
import reactor.core.publisher.Mono;

public class GetCustomerByIdCaseUse implements IUseCaseExecute<GetCustomerRequest,GetCustomerResponse> {

    private final ICustomerRepositoryGateway iCustomerRepositoryGateway;
    private final IEventStoreGateway iEventStoreGateway;

    public GetCustomerByIdCaseUse(ICustomerRepositoryGateway iCustomerRepositoryGateway, IEventStoreGateway iEventStoreGateway) {
        this.iCustomerRepositoryGateway = iCustomerRepositoryGateway;
        this.iEventStoreGateway = iEventStoreGateway;
    }

    @Override
    public Mono<GetCustomerResponse> execute(GetCustomerRequest request) {
        return iEventStoreGateway.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {
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
