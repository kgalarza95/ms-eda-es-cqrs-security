package ec.com.sofka.repository;

import ec.com.sofka.document.ClientEntity;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import ec.com.sofka.repository.mongo.ICustomerMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.time.LocalDate;


@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ICustomerMongoRepositoryTest {

    private ICustomerMongoRepository customerMongoRepository;

    @Autowired
    public ICustomerMongoRepositoryTest(ICustomerMongoRepository customerMongoRepository) {
        this.customerMongoRepository = customerMongoRepository;
    }

    @Test
    public void testSaveAndFindById() {
        ClientEntity client = new ClientEntity();
        client.setId("123456");
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");
        client.setPhone("123456789");
        client.setAddress("123 Main St");
        client.setBirthDate(LocalDate.of(1980, 1, 1));

        Mono<ClientEntity> saveMono = customerMongoRepository.save(client);

        StepVerifier.create(saveMono)
                .assertNext(savedClient -> {
                    assertThat(savedClient.getId()).isNotNull();
                    assertThat(savedClient.getFirstName()).isEqualTo("John");
                    assertThat(savedClient.getLastName()).isEqualTo("Doe");
                    assertThat(savedClient.getEmail()).isEqualTo("john.doe@example.com");
                })
                .verifyComplete();

        Mono<ClientEntity> findMono = customerMongoRepository.findById(client.getId());

        StepVerifier.create(findMono)
                .assertNext(foundClient -> {
                    assertThat(foundClient.getId()).isEqualTo(client.getId());
                    assertThat(foundClient.getFirstName()).isEqualTo("John");
                    assertThat(foundClient.getLastName()).isEqualTo("Doe");
                    assertThat(foundClient.getEmail()).isEqualTo("john.doe@example.com");
                })
                .verifyComplete();
    }

    @Test
    public void testDelete() {
        ClientEntity client = new ClientEntity();
        client.setId("123456");
        client.setFirstName("Jane");
        client.setLastName("Doe");
        client.setEmail("jane.doe@example.com");
        client.setPhone("987654321");
        client.setAddress("456 Secondary St");
        client.setBirthDate(LocalDate.of(1990, 5, 15));

        Mono<Void> deleteMono = customerMongoRepository.save(client)
                .flatMap(savedClient -> customerMongoRepository.deleteById(savedClient.getId()));

        StepVerifier.create(deleteMono)
                .verifyComplete();

        StepVerifier.create(customerMongoRepository.findById(client.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void testFindAll() {
        ClientEntity client1 = new ClientEntity();
        client1.setId("123456");
        client1.setFirstName("Alice");
        client1.setLastName("Smith");
        client1.setEmail("alice.smith@example.com");
        client1.setPhone("555123456");
        client1.setAddress("789 Oak St");
        client1.setBirthDate(LocalDate.of(1975, 3, 10));

        ClientEntity client2 = new ClientEntity();
        client2.setId("789101");
        client2.setFirstName("Bob");
        client2.setLastName("Brown");
        client2.setEmail("bob.brown@example.com");
        client2.setPhone("555654321");
        client2.setAddress("101 Pine St");
        client2.setBirthDate(LocalDate.of(1995, 7, 20));

        customerMongoRepository.save(client1).block();
        customerMongoRepository.save(client2).block();

        Flux<ClientEntity> allClients = customerMongoRepository.findAll();

        StepVerifier.create(allClients)
                .expectNextMatches(client -> client.getId().equals("123456"))
                .expectNextMatches(client -> client.getId().equals("789101"))
                .verifyComplete();
    }


}
