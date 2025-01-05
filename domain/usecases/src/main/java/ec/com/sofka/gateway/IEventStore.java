package ec.com.sofka.gateway;

import ec.com.sofka.generics.domain.DomainEvent;

import java.util.List;

//Here you can create more functions according to the needs of the project related to events
public interface IEventStore {
    DomainEvent save(DomainEvent event);
    List<DomainEvent> findAggregate(String aggregateId);
    List<DomainEvent> findAllAggregates();
}
