package ec.com.sofka.repository;


import ec.com.sofka.document.UserSystemEntity;
import ec.com.sofka.repository.mongo.IUserSystemRepository;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.assertThat;




@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IUserSystemRepositoryTest {

    private IUserSystemRepository userSystemRepository;

    @Autowired
    public IUserSystemRepositoryTest(IUserSystemRepository userSystemRepository) {
        this.userSystemRepository = userSystemRepository;
    }

    @Test
    public void testSaveAndFindById() {
        UserSystemEntity user = new UserSystemEntity();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");

        Mono<UserSystemEntity> saveMono = userSystemRepository.save(user);

        StepVerifier.create(saveMono)
                .assertNext(savedUser -> {
                    assertThat(savedUser.getId()).isNotNull();
                    assertThat(savedUser.getUsername()).isEqualTo("test@example.com");
                    assertThat(savedUser.getFirstName()).isEqualTo("John");
                    assertThat(savedUser.getLastName()).isEqualTo("Doe");
                })
                .verifyComplete();

        Mono<UserSystemEntity> findMono = userSystemRepository.findById(user.getId());

        StepVerifier.create(findMono)
                .assertNext(foundUser -> {
                    assertThat(foundUser.getId()).isEqualTo(user.getId());
                    assertThat(foundUser.getUsername()).isEqualTo("test@example.com");
                    assertThat(foundUser.getFirstName()).isEqualTo("John");
                })
                .verifyComplete();
    }

    @Test
    public void testFindByEmail() {
        UserSystemEntity user = new UserSystemEntity();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setPassword("password123");

        Mono<UserSystemEntity> saveMono = userSystemRepository.save(user);

        StepVerifier.create(saveMono)
                .assertNext(savedUser -> {
                    assertThat(savedUser.getId()).isNotNull();
                    assertThat(savedUser.getUsername()).isEqualTo("test@example.com");
                })
                .verifyComplete();

        Mono<UserSystemEntity> findMono = userSystemRepository.findByEmail("test@example.com");

        StepVerifier.create(findMono)
                .assertNext(foundUser -> {
                    assertThat(foundUser.getUsername()).isEqualTo("test@example.com");
                    assertThat(foundUser.getFirstName()).isEqualTo("John");
                })
                .verifyComplete();
    }

    @Test
    public void testDelete() {
        UserSystemEntity user = new UserSystemEntity();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setPassword("password123");

        Mono<Void> deleteMono = userSystemRepository.save(user)
                .flatMap(savedUser -> userSystemRepository.deleteById(savedUser.getId()));

        StepVerifier.create(deleteMono)
                .verifyComplete();

        StepVerifier.create(userSystemRepository.findById(user.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}
