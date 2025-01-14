package ec.com.sofka.repository;

import ec.com.sofka.document.TransactionEntity;
import ec.com.sofka.repository.mongo.ITransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.math.BigDecimal;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ITransactionRepositoryTest {

    private ITransactionRepository transactionRepository;

    @Autowired
    public ITransactionRepositoryTest(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Test
    public void testSaveAndFindById() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionType("DEBITO");
        transaction.setAmount(new BigDecimal(100.0));
        transaction.setAccountId("ACC123");

        Mono<TransactionEntity> saveMono = transactionRepository.save(transaction);

        StepVerifier.create(saveMono)
                .assertNext(savedTransaction -> {
                    assertThat(savedTransaction.getId()).isNotNull();
                    assertThat(savedTransaction.getAmount()).isEqualByComparingTo(new BigDecimal("100.00"));
                })
                .verifyComplete();

        Mono<TransactionEntity> findMono = transactionRepository.findById(transaction.getId());

        StepVerifier.create(findMono)
                .assertNext(foundTransaction -> {
                    assertThat(foundTransaction.getId()).isEqualTo(transaction.getId());
                    assertThat(foundTransaction.getAmount()).isEqualByComparingTo(new BigDecimal("100.00"));
                    assertThat(foundTransaction.getAccountId()).isEqualTo("ACC123");
                })
                .verifyComplete();
    }

    @Test
    public void testDelete() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setId("TXN001");
        transaction.setAmount(new BigDecimal(100.0));
        transaction.setAccountId("ACC123");

        Mono<Void> deleteMono = transactionRepository.save(transaction)
                .flatMap(savedTransaction -> transactionRepository.deleteById(savedTransaction.getId()));

        StepVerifier.create(deleteMono)
                .verifyComplete();

        StepVerifier.create(transactionRepository.findById(transaction.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}
