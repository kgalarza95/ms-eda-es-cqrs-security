package ec.com.sofka.database.events;

import ec.com.sofka.data.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IEventMongoRepository extends MongoRepository<EventEntity, String> {
    List<EventEntity> findByAggregateId(String aggregateId);
}
