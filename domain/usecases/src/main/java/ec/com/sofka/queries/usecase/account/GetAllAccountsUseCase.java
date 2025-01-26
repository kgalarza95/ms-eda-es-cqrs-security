package ec.com.sofka.queries.usecase.account;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import ec.com.sofka.queries.response.account.GetAccountResponse;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetAllAccountsUseCase implements IUseCaseGet<GetAccountResponse> {

    private final IEventStoreGateway eventRepository;

    public GetAllAccountsUseCase(IEventStoreGateway eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Flux<GetAccountResponse> get() {
        return eventRepository.findAllAggregates()
                .collectList()
                .flatMapMany(events -> {

                    Map<String, DomainEvent> latestEventsMap = events.stream()
                            .filter(event -> event instanceof AccountCreated || event instanceof AccountUpdated)
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
                            .map(customer -> new GetAccountResponse(
                                    customer.getId().getValue(),
                                    customer.getAccount().getId().getValue(),
                                    customer.getAccount().getNumber().getValue(),
                                    customer.getAccount().getName().getValue(),
                                    customer.getAccount().getBalance().getValue(),
                                    customer.getAccount().getStatus().getValue()
                            ));
                });
    }


}
