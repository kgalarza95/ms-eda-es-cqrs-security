package ec.com.sofka.usecase.customer;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.CustomerCreated;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import ec.com.sofka.request.customer.GetCustomerRequest;
import ec.com.sofka.responses.account.GetAccountResponse;
import ec.com.sofka.responses.customer.GetCustomerResponse;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetAllCustomerUseCase implements IUseCaseGet<GetCustomerResponse> {

    private final IEventStoreGateway iEventStoreGateway;

    public GetAllCustomerUseCase(IEventStoreGateway iEventStoreGateway) {
        this.iEventStoreGateway = iEventStoreGateway;
    }

    @Override
    public Flux<GetCustomerResponse> get() {
        return iEventStoreGateway.findAllAggregates()
                .collectList()
                .flatMapMany(events -> {
                    Map<String, DomainEvent> latestEventsMap = events.stream()
                            .filter(event -> event instanceof CustomerCreated)
                            .collect(Collectors.toMap(
                                    DomainEvent::getAggregateRootId,
                                    event -> event,
                                    (existing, replacement) -> existing.getVersion() >= replacement.getVersion() ? existing : replacement
                            ));

                    List<DomainEvent> latestEvents = latestEventsMap.values().stream().toList();

                    List<CustomerAggregate> customers = latestEvents.stream()
                            .map(event -> CustomerAggregate.from(event.getAggregateRootId(), latestEvents))
                            .toList();

                    return Flux.fromIterable(customers)
                            .map(customer -> new GetCustomerResponse(
                                    customer.getCustomer().getId().getValue(),
                                    customer.getCustomer().getIdentification().getValue(),
                                    customer.getCustomer().getFirstName().getValue(),
                                    customer.getCustomer().getLastName().getValue(),
                                    customer.getCustomer().getEmail().getValue(),
                                    customer.getCustomer().getPhone().getValue(),
                                    customer.getCustomer().getAddress().getValue(),
                                    customer.getCustomer().getBirthDate().getValue(),
                                    customer.getCustomer().getStatus().getValue()
                            ));
                });
    }

}
