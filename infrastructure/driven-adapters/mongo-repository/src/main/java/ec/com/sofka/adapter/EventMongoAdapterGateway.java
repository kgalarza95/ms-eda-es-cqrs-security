package ec.com.sofka.adapter;

import ec.com.sofka.JSONMap;
import ec.com.sofka.document.EventEntity;
import ec.com.sofka.repository.events.IEventMongoRepository;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Repository
public class EventMongoAdapterGateway implements IEventStoreGateway {

    private final IEventMongoRepository iEventMongoRepository;
    private final JSONMap mapper;
    private final ReactiveMongoTemplate eventReactiveMongoTemplate;

    public EventMongoAdapterGateway(IEventMongoRepository iEventMongoRepository, JSONMap mapper, @Qualifier("eventReactiveMongoTemplate") ReactiveMongoTemplate eventReactiveMongoTemplate) {
        this.iEventMongoRepository = iEventMongoRepository;
        this.mapper = mapper;
        this.eventReactiveMongoTemplate = eventReactiveMongoTemplate;
    }

    @Override
    public Mono<DomainEvent> save(DomainEvent event) {
        EventEntity eventEntity = new EventEntity(
                event.getEventId(),
                event.getAggregateRootId(),
                event.getEventType(),
                EventEntity.wrapEvent(event, mapper),
                event.getWhen().toString(),
                event.getVersion()
        );

        return iEventMongoRepository.save(eventEntity)
                .map(savedEventEntity -> event);
    }

    @Override
    public Flux<DomainEvent> findAggregate(String aggregateId) {
        return iEventMongoRepository.findByAggregateId(aggregateId)
                .map(eventEntity -> eventEntity.deserializeEvent(mapper))
                .sort(Comparator.comparing(DomainEvent::getVersion));
    }


    @Override
    public Flux<DomainEvent> findAllAggregates() {
        return iEventMongoRepository.findAll()
                .map(eventEntity -> eventEntity.deserializeEvent(mapper))
                .sort(Comparator.comparing(DomainEvent::getAggregateRootId)
                        .thenComparing(DomainEvent::getVersion, Comparator.reverseOrder()));
    }

}
