package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


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

    public EventEntity(String id, String aggregateId, String eventType, String eventData, String timestamp, Long version) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.eventData = eventData;
        this.timestamp = timestamp;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}

