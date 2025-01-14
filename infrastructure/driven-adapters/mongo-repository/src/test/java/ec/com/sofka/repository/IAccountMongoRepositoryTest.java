package ec.com.sofka.repository;

import ec.com.sofka.document.AccountEntity;
import ec.com.sofka.repository.mongo.IAccountMongoRepository;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.math.BigDecimal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IAccountMongoRepositoryTest {

    private IAccountMongoRepository accountMongoRepository;

    @Autowired
    public IAccountMongoRepositoryTest(IAccountMongoRepository accountMongoRepository) {
        this.accountMongoRepository = accountMongoRepository;
    }

    @Test
    public void testSaveAndFindById() {
        AccountEntity account = new AccountEntity();
        account.setAccountNumber("123456");
        account.setBalance(new BigDecimal(500.0));
        account.setAccountId("CUST001");
        account.setStatus("ACTIVE");

        Mono<AccountEntity> saveMono = accountMongoRepository.save(account);

        StepVerifier.create(saveMono)
                .assertNext(savedAccount -> {
                    assertThat(savedAccount.getId()).isNotNull();
                    assertThat(savedAccount.getAccountNumber()).isEqualTo("123456");
                    assertThat(savedAccount.getBalance()).isEqualByComparingTo(new BigDecimal("500.00"));
                })
                .verifyComplete();

        Mono<AccountEntity> findMono = accountMongoRepository.findById(account.getId());

        StepVerifier.create(findMono)
                .assertNext(foundAccount -> {
                    assertThat(foundAccount.getId()).isEqualTo(account.getId());
                    assertThat(foundAccount.getAccountNumber()).isEqualTo("123456");
                    assertThat(foundAccount.getBalance()).isEqualByComparingTo(new BigDecimal("500.00"));
                    assertThat(foundAccount.getAccountId()).isEqualTo("CUST001");
                })
                .verifyComplete();
    }

    @Test
    public void testDelete() {
        AccountEntity account = new AccountEntity();
        account.setAccountNumber("123456");
        account.setBalance(new BigDecimal(500.0));
        account.setAccountId("CUST001");
        account.setStatus("ACTIVE");

        Mono<Void> deleteMono = accountMongoRepository.save(account)
                .flatMap(savedAccount -> accountMongoRepository.deleteById(savedAccount.getId()));

        StepVerifier.create(deleteMono)
                .verifyComplete();

        StepVerifier.create(accountMongoRepository.findById(account.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}
