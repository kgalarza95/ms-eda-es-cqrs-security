package ec.com.sofka;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import ec.com.sofka.responses.GetAccountResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetAllAccountsUseCase implements IUseCaseGet<GetAccountResponse> {

    private final IEventStore eventRepository;

    public GetAllAccountsUseCase(IEventStore eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<GetAccountResponse> get() {
        //Get all events
        List<DomainEvent> events = eventRepository.findAllAggregates();

        //Get the last events
        Map<String, DomainEvent> maplatestEvents = events.stream()
                .collect(Collectors.toMap(
                        DomainEvent::getAggregateRootId,   // Key: aggregateId
                        event -> event,                // Value: the event itself
                        (existing, replacement) -> existing.getVersion() >= replacement.getVersion() ? existing : replacement // Keep the latest version
                ));

        //Keep the events
        List<DomainEvent> latestEvents = maplatestEvents.values().stream().toList();

        //------ Then I can rebuild all the customers
        List<Customer> customers = latestEvents.stream()
                .map(event -> Customer.from(event.getAggregateRootId(),latestEvents))
                .toList();

        //------ Finally I can return the response
        return customers.stream()
                .map(customer -> new GetAccountResponse(
                        customer.getId().getValue(),
                        customer.getAccount().getId().getValue(),
                        customer.getAccount().getNumber().getValue(),
                        customer.getAccount().getName().getValue(),
                        customer.getAccount().getBalance().getValue(),
                        customer.getAccount().getStatus().getValue()
                        )
                ).collect(Collectors.toList());
    }

}
