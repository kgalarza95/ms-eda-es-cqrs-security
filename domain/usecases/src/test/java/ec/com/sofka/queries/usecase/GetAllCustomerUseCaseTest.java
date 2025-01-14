package ec.com.sofka.queries.usecase;


import ec.com.sofka.aggregate.events.CustomerCreated;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.queries.usecase.customer.GetAllCustomerUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class GetAllCustomerUseCaseTest {

    @Mock
    private IEventStoreGateway iEventStoreGateway;

    @InjectMocks
    private GetAllCustomerUseCase useCase;

    @Test
    void shouldReturnEmptyWhenNoAggregatesFound() {
        when(iEventStoreGateway.findAllAggregates()).thenReturn(Flux.empty());

        StepVerifier.create(useCase.get())
                .expectNextCount(0)
                .verifyComplete();

        verify(iEventStoreGateway).findAllAggregates();
    }
    @Test
    void shouldReturnEmptyWhenOnlyNonCustomerCreatedEventsAreFound() {
        DomainEvent nonCustomerEvent = mock(DomainEvent.class);

        when(iEventStoreGateway.findAllAggregates()).thenReturn(Flux.just(nonCustomerEvent));

        StepVerifier.create(useCase.get())
                .expectNextCount(0)
                .verifyComplete();

        verify(iEventStoreGateway).findAllAggregates();
    }


}
