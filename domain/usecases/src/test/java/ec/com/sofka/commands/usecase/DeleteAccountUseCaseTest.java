package ec.com.sofka.commands.usecase;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.commands.account.UpdateAccountCommand;
import ec.com.sofka.commands.usecase.account.DeleteAccountUseCase;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.busmessage.IAccountBusMessageGateway;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.model.account.Account;
import ec.com.sofka.model.account.values.AccountId;
import ec.com.sofka.model.account.values.objects.Balance;
import ec.com.sofka.model.account.values.objects.NumberAcc;
import ec.com.sofka.model.account.values.objects.Status;
import ec.com.sofka.model.account.values.objects.Name;
import ec.com.sofka.model.util.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

public class DeleteAccountUseCaseTest {

    private DeleteAccountUseCase deleteAccountUseCase;

    @Mock
    private IEventStoreGateway eventStoreGateway;

    @Mock
    private IAccountBusMessageGateway accountBusMessageGateway;

    @BeforeEach
    void setUp() {
        eventStoreGateway = mock(IEventStoreGateway.class);
        accountBusMessageGateway = mock(IAccountBusMessageGateway.class);
        deleteAccountUseCase = new DeleteAccountUseCase(eventStoreGateway, accountBusMessageGateway);
    }

    @Test
    void shouldDeleteAccountSuccessfully() {
        String aggregateId = "customer-123";
        UpdateAccountCommand command = new UpdateAccountCommand(
                aggregateId, new BigDecimal("0.00"), "ACC-001", "John Doe", "INACTIVE"
        );

        Account account = new Account(
                AccountId.of("account-123"),
                NumberAcc.of("ACC-001"),
                Name.of("John Doe"),
                Balance.of(new BigDecimal("1000.00")),
                Status.of("ACTIVE")
        );

        CustomerAggregate customerAggregate = mock(CustomerAggregate.class);
        when(customerAggregate.getAccount()).thenReturn(account); // Asegúrate de que `getAccount()` no sea null

        DomainEvent accountUpdatedEvent = new AccountUpdated(
                "account-123", new BigDecimal("0.00"), "ACC-001", "John Doe", StatusEnum.INACTIVE.name()
        );

        when(customerAggregate.getUncommittedEvents()).thenReturn(List.of(accountUpdatedEvent));


        when(eventStoreGateway.save(any())).thenReturn(Mono.empty());

    }


    @Test
    void shouldReturnErrorIfNoEventsFoundForAggregate() {
        String aggregateId = "customer-123";
        UpdateAccountCommand command = new UpdateAccountCommand(
                aggregateId, new BigDecimal("0.00"), "ACC-001", "John Doe", "INACTIVE"
        );

        when(eventStoreGateway.findAggregate(aggregateId)).thenReturn(Flux.empty());

        StepVerifier.create(deleteAccountUseCase.execute(command))
                .expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException &&
                        throwable.getMessage().contains("No events found for aggregate ID: " + aggregateId)
                )
                .verify();

        verify(eventStoreGateway).findAggregate(aggregateId);
        verifyNoInteractions(accountBusMessageGateway);
    }

}
