package ec.com.sofka.event;


import ec.com.sofka.document.EventEntity;
import ec.com.sofka.repository.TestConfiguration;
import ec.com.sofka.repository.events.IEventMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IEventMongoRepositoryTest {

    private IEventMongoRepository eventMongoRepository;

    @Autowired
    public IEventMongoRepositoryTest(IEventMongoRepository eventMongoRepository) {
        this.eventMongoRepository = eventMongoRepository;
    }

    @Test
    public void testSaveAndFindById() {
        EventEntity event = new EventEntity();
        event.setAggregateId("AGG123");
        event.setEventType("CREATED");

        Mono<EventEntity> saveMono = eventMongoRepository.save(event);

        StepVerifier.create(saveMono)
                .assertNext(savedEvent -> {
                    assertThat(savedEvent.getId()).isNotNull();
                    assertThat(savedEvent.getAggregateId()).isEqualTo("AGG123");
                    assertThat(savedEvent.getEventType()).isEqualTo("CREATED");
                })
                .verifyComplete();

        // Buscando por el ID generado
        Mono<EventEntity> findMono = eventMongoRepository.findById(event.getId());

        StepVerifier.create(findMono)
                .assertNext(foundEvent -> {
                    assertThat(foundEvent.getId()).isEqualTo(event.getId());
                    assertThat(foundEvent.getAggregateId()).isEqualTo("AGG123");
                    assertThat(foundEvent.getEventType()).isEqualTo("CREATED");
                })
                .verifyComplete();
    }

    @Test
    public void testDelete() {
        EventEntity event = new EventEntity();
        event.setAggregateId("AGG123");
        event.setEventType("CREATED");

        Mono<Void> deleteMono = eventMongoRepository.save(event)
                .flatMap(savedEvent -> eventMongoRepository.deleteById(savedEvent.getAggregateId()));

        StepVerifier.create(deleteMono)
                .verifyComplete();

        StepVerifier.create(eventMongoRepository.findById(event.getAggregateId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}
