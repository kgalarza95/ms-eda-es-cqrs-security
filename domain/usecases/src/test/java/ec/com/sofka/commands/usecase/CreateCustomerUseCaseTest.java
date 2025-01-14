package ec.com.sofka.commands.usecase;

import static org.mockito.Mockito.*;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.CustomerCreated;
import ec.com.sofka.commands.customer.CreateCustomerCommand;
import ec.com.sofka.commands.usecase.customer.CreateCustomerUseCase;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.busmessage.ICustomerBusMessageGateway;
import ec.com.sofka.generics.domain.DomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDate;
import java.util.List;

public class CreateCustomerUseCaseTest {

    private IEventStoreGateway eventStoreGateway;
    private ICustomerBusMessageGateway customerBusMessageGateway;
    private CreateCustomerUseCase createCustomerUseCase;

    @BeforeEach
    void setUp() {
        eventStoreGateway = mock(IEventStoreGateway.class);
        customerBusMessageGateway = mock(ICustomerBusMessageGateway.class);
        createCustomerUseCase = new CreateCustomerUseCase(eventStoreGateway, customerBusMessageGateway);
    }

    @Test
    void shouldCreateCustomerSuccessfully() {
        // Arrange
        String identification = "0930988804";
        String firstName = "Juan";
        String lastName = "Pérez";
        String email = "juan.perez@example.com";
        String phone = "0961251803";
        String address = "Calle Ficticia 123, Ciudad Ejemplo, País";
        LocalDate birthDate = LocalDate.of(1990, 5, 15);

        CreateCustomerCommand command = new CreateCustomerCommand(
                identification, firstName, lastName, email, phone, address, birthDate);

        // Simulamos el comportamiento de los agregados y el sistema de eventos
        CustomerAggregate customerAggregate = mock(CustomerAggregate.class);
        when(customerAggregate.getUncommittedEvents()).thenReturn(List.of(new CustomerCreated()));
        when(eventStoreGateway.save(any())).thenReturn(Mono.empty());
        doNothing().when(customerBusMessageGateway).sendMsg(any());

        // Act & Assert
        StepVerifier.create(createCustomerUseCase.execute(command))
                .expectNextMatches(response -> response.getIdentification().equals(identification)
                        && response.getFirstName().equals(firstName)
                        && response.getLastName().equals(lastName)
                        && response.getEmail().equals(email)
                        && response.getPhone().equals(phone)
                        && response.getAddress().equals(address)
                        && response.getBirthDate().equals(birthDate))
                .verifyComplete();

        // Verificar interacciones con los mocks
        verify(eventStoreGateway).save(any(DomainEvent.class));  // Verificamos que se haya guardado el evento
       // verify(customerBusMessageGateway).sendMsg(any(DomainEvent.class));  // Verificamos que el evento se envió
    }

    @Test
    void shouldCreateCustomerError() {
        String customerIdentification = "12345";
        String customerFirstName = "John";
        String customerLastName = "Doe";
        String customerEmail = "john.doe@example.com";
        String customerPhone = "+593961251803";
        String customerAddress = "123 Main St, Cityville, Country";
        LocalDate customerBirthDate = LocalDate.of(1990, 1, 1);

        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand(
                customerIdentification, customerFirstName, customerLastName, customerEmail, customerPhone, customerAddress, customerBirthDate);

        CustomerAggregate customerAggregateMock = mock(CustomerAggregate.class);
        when(customerAggregateMock.getUncommittedEvents()).thenReturn(List.of(new CustomerCreated()));
        when(eventStoreGateway.save(any())).thenReturn(Mono.empty());
        doNothing().when(customerBusMessageGateway).sendMsg(any());

        StepVerifier.create(createCustomerUseCase.execute(createCustomerCommand))
                .expectNextMatches(response -> response.getIdentification().equals(customerIdentification)
                        && response.getFirstName().equals(customerFirstName)
                        && response.getLastName().equals(customerLastName)
                        && response.getEmail().equals(customerEmail)
                        && response.getPhone().equals(customerPhone)
                        && response.getAddress().equals(customerAddress)
                        && response.getBirthDate().equals(customerBirthDate))
                .verifyComplete();

        verify(eventStoreGateway).save(any(DomainEvent.class));
        //verify(customerBusMessageGateway).sendMsg(any(DomainEvent.class));
    }

    @Test
    void shouldReturnErrorIfEventSaveFails() {
        String customerIdentification = "12345";
        String customerFirstName = "John";
        String customerLastName = "Doe";
        String customerEmail = "john.doe@example.com";
        String customerPhone = "+593961251803";
        String customerAddress = "123 Main St, Cityville, Country";
        LocalDate customerBirthDate = LocalDate.of(1990, 1, 1);

        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand(
                customerIdentification, customerFirstName, customerLastName, customerEmail, customerPhone, customerAddress, customerBirthDate);

        when(eventStoreGateway.save(any())).thenReturn(Mono.error(new RuntimeException("Save failed")));

        StepVerifier.create(createCustomerUseCase.execute(createCustomerCommand))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("Save failed"))
                .verify();

        verifyNoInteractions(customerBusMessageGateway);
    }

}
