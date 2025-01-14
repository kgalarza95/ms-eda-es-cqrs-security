package ec.com.sofka.queries.usecase;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.QueryResponse;
import ec.com.sofka.model.account.Account;
import ec.com.sofka.queries.account.GetAccountQuery;
import ec.com.sofka.queries.usecase.account.GetAccountByNumberUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class GetAccountByNumberUseCaseTest {

    @Mock
    private IAccountRepositoryGateway iAccountRepositoryGateway;

    @Mock
    private IEventStoreGateway iEventStoreGateway;

    @InjectMocks
    private GetAccountByNumberUseCase useCase;

    @Test
    void shouldThrowResourceNotFoundExceptionWhenNoEventsInEventStore() {
        String aggregateId = "customer-123";
        GetAccountQuery query = new GetAccountQuery(aggregateId, "account-456");

        when(iEventStoreGateway.findAggregate(aggregateId)).thenReturn(Flux.empty());

        StepVerifier.create(useCase.get(query))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(iEventStoreGateway).findAggregate(aggregateId);
        verifyNoInteractions(iAccountRepositoryGateway);
    }


}
