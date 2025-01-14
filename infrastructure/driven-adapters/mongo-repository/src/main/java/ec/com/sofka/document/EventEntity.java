package ec.com.sofka.document;

import ec.com.sofka.JSONMap;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.stream.Collectors;


@Document(collection = "events")
public class EventEntity {
    @Id
    private String id;

    @Field("aggregate_id")
    private String aggregateId;

    @Field("event_type")
    private String eventType;

    @Field("event_data")
    private String eventData;

    @Field("timestamp")
    private String timestamp;

    @Field("version")
    private Long version;

    public EventEntity() {
    }

    public EventEntity(String id, String aggregateId, String eventType, String eventData, String timestamp, Long version) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.eventData = eventData;
        this.timestamp = timestamp;
        this.version = version;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getEventData() {
        return eventData;
    }

    public String getEventType() {
        return eventType;
    }

    public static String wrapEvent(DomainEvent domainEvent, JSONMap eventSerializer){
        return eventSerializer.writeToJson(domainEvent);
    }

    public DomainEvent deserializeEvent(JSONMap eventSerializer) {
        try {

            String className = Arrays.stream(this.getEventType().toLowerCase().split("_"))
                    .map(part -> Character.toUpperCase(part.charAt(0)) + part.substring(1))
                    .collect(Collectors.joining());

            return (DomainEvent) eventSerializer
                    .readFromJson(this.getEventData(), Class.forName("ec.com.sofka.aggregate.events."+className));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }
}

