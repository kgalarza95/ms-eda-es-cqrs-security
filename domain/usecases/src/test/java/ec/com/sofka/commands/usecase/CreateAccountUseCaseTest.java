package ec.com.sofka.commands.usecase;

import ec.com.sofka.commands.account.CreateAccountCommand;
import ec.com.sofka.commands.usecase.account.CreateAccountUseCase;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.busmessage.IAccountBusMessageGateway;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;

public class CreateAccountUseCaseTest {

    @Mock
    private IEventStoreGateway eventStoreGateway;

    @Mock
    private IAccountBusMessageGateway accountBusMessageGateway;

    private CreateAccountUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CreateAccountUseCase(eventStoreGateway, accountBusMessageGateway);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAccountAlreadyExists() {
        // Arrange
        String accountId = "account-123";
        String aggregateId = "customer-123";
        CreateAccountCommand command = new CreateAccountCommand(
                "John Doe",
                new BigDecimal("1000.00")
        );

        when(eventStoreGateway.save(any()))
                .thenReturn(Mono.error(new IllegalArgumentException("Account already exists")));

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectError(IllegalArgumentException.class)
                .verify();

        verify(eventStoreGateway).save(any());
        verifyNoInteractions(accountBusMessageGateway);
    }

    @Test
    void shouldHandleEventStoreError() {
        // Arrange
        String accountId = "account-123";
        String aggregateId = "customer-123";
        CreateAccountCommand command = new CreateAccountCommand(
                "John Doe",
                new BigDecimal("1000.00")
        );

        when(eventStoreGateway.save(any()))
                .thenReturn(Mono.error(new RuntimeException("Database connection error")));

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectError(RuntimeException.class)
                .verify();

        verify(eventStoreGateway).save(any());
        verifyNoInteractions(accountBusMessageGateway);
    }


}
