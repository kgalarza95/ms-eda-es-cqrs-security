package ec.com.sofka.gateway;

import ec.com.sofka.generics.domain.DomainEvent;

import java.util.List;

public interface IEventStore {
    DomainEvent save(DomainEvent event);
    List<DomainEvent> findAggregate(String aggregateId);
}
