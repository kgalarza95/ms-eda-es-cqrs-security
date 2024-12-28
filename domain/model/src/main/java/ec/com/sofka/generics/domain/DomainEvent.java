package ec.com.sofka.generics.domain;

import java.time.Instant;
import java.util.UUID;

//4. Generics creation to apply DDD: DomainEvent - An event that occurs in the domain
public abstract class DomainEvent {
    private final Instant when;
    private final String eventId;
    private final String eventType;

    //This pair is setting on the AR or Entity that is related to the event - Later
    private String aggregateRootId;
    private String aggregateRootName;

    private Long version;

    public DomainEvent(String eventType) {
        this.when = Instant.now();
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.version = 1L;
    }

    public Instant getWhen() {
        return when;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public void setAggregateRootId(String aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }

    public String getAggregateRootName() {
        return aggregateRootName;
    }

    public void setAggregateRootName(String aggregateRootName) {
        this.aggregateRootName = aggregateRootName;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
