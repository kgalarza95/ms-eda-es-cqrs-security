package ec.com.sofka.commands.usecase;

import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.commands.account.UpdateAccountCommand;
import ec.com.sofka.commands.usecase.account.UpdateAccountUseCase;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.busmessage.IAccountBusMessageGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateAccountUseCaseTest {
    @Mock
    private IEventStoreGateway eventStoreGateway;

    @Mock
    private IAccountBusMessageGateway accountBusMessageGateway;

    private UpdateAccountUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new UpdateAccountUseCase(eventStoreGateway, accountBusMessageGateway);
    }


    @Test
    void shouldThrowResourceNotFoundExceptionWhenNoEventsFound() {
        // Arrange
        String aggregateId = "non-existent-id";
        UpdateAccountCommand command = new UpdateAccountCommand(
                aggregateId,
                BigDecimal.ZERO,
                "ACC-001",
                "John Doe",
                "ACTIVE"
        );

        when(eventStoreGateway.findAggregate(aggregateId))
                .thenReturn(Flux.empty());

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(eventStoreGateway).findAggregate(aggregateId);
        verifyNoInteractions(accountBusMessageGateway);
    }

    @Test
    void shouldHandleEventStoreError() {
        // Arrange
        String aggregateId = "customer-123";
        UpdateAccountCommand command = new UpdateAccountCommand(
                aggregateId,
                BigDecimal.ZERO,
                "ACC-001",
                "John Doe",
                "ACTIVE"
        );

        when(eventStoreGateway.findAggregate(aggregateId))
                .thenReturn(Flux.error(new RuntimeException("Database connection error")));

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectError(RuntimeException.class)
                .verify();

        verify(eventStoreGateway).findAggregate(aggregateId);
        verifyNoInteractions(accountBusMessageGateway);
    }

    @Test
    void shouldHandleMessageBusError() {
        // Arrange
        String aggregateId = "customer-123";
        String accountId = "account-123";

        AccountCreated accountCreatedEvent = new AccountCreated(
                accountId,
                "ACC-001",
                BigDecimal.ZERO,
                "John Doe",
                "ACTIVE"
        );

        UpdateAccountCommand command = new UpdateAccountCommand(
                aggregateId,
                new BigDecimal("1000.00"),
                "NEW-ACC-001",
                "New John Doe",
                "INACTIVE"
        );

        when(eventStoreGateway.findAggregate(aggregateId))
                .thenReturn(Flux.just(accountCreatedEvent));

        when(eventStoreGateway.save(any()))
                .thenReturn(Mono.empty());

        doThrow(new RuntimeException("Message bus error"))
                .when(accountBusMessageGateway).sendMsg(any());

        StepVerifier.create(useCase.execute(command))
                .expectError(RuntimeException.class)
                .verify();

        verify(eventStoreGateway).findAggregate(aggregateId);

        verifyNoMoreInteractions(eventStoreGateway);
        verifyNoInteractions(accountBusMessageGateway);
    }

}
