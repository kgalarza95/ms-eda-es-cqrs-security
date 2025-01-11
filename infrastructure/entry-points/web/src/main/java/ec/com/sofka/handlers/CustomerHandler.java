package ec.com.sofka.handlers;

import ec.com.sofka.commands.usecase.customer.DeleteCustomerUseCase;
import ec.com.sofka.dto.CustomerRequestDTO;
import ec.com.sofka.dto.CustomerResponseDTO;
import ec.com.sofka.commands.customer.CreateCustomerCommand;
import ec.com.sofka.commands.customer.UpdateCustomerCommand;
import ec.com.sofka.commands.usecase.customer.CreateCustomerUseCase;
import ec.com.sofka.queries.customer.GetCustomerQuery;
import ec.com.sofka.queries.usecase.customer.GetAllCustomerUseCase;
import ec.com.sofka.commands.usecase.customer.UpdateCustomerUseCase;
import ec.com.sofka.queries.usecase.customer.GetCustomerByIdUseCase;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    private final GetAllCustomerUseCase getAllCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;


    public CustomerHandler(GetAllCustomerUseCase getAllCustomerUseCase, GetCustomerByIdUseCase getCustomerByIdUseCase, UpdateCustomerUseCase updateCustomerUseCase, CreateCustomerUseCase createCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase) {
        this.getAllCustomerUseCase = getAllCustomerUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    public Flux<CustomerResponseDTO> getAllCustomer() {
        return getAllCustomerUseCase.get()
                .map(customerResponse -> new CustomerResponseDTO(
                        customerResponse.getId(),
                        customerResponse.getIdentification(),
                        customerResponse.getFirstName(),
                        customerResponse.getLastName(),
                        customerResponse.getEmail(),
                        customerResponse.getPhone(),
                        customerResponse.getAddress(),
                        customerResponse.getBirthDate()
                ));
    }

    public Mono<CustomerResponseDTO> getCustomerById(String customerId) {
        return getCustomerByIdUseCase.get(new GetCustomerQuery(customerId))
                .map(response -> new CustomerResponseDTO(
                        response.getId(),
                        response.getIdentification(),
                        response.getFirstName(),
                        response.getLastName(),
                        response.getEmail(),
                        response.getPhone(),
                        response.getAddress(),
                        response.getBirthDate()
                ));
    }

    public Mono<CustomerResponseDTO> createCustomer(CustomerRequestDTO request) {
        return createCustomerUseCase.execute(
                new CreateCustomerCommand(
                        request.getIdentification(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPhone(),
                        request.getAddress(),
                        request.getBirthDate()
                )
        ).map(response -> new CustomerResponseDTO(
                response.getId(),
                response.getIdentification(),
                response.getFirstName(),
                response.getLastName(),
                response.getEmail(),
                response.getPhone(),
                response.getAddress(),
                response.getBirthDate()
        ));
    }

    public Mono<CustomerResponseDTO> updateCustomer(CustomerRequestDTO request) {
        return updateCustomerUseCase.execute(
                new UpdateCustomerCommand(
                        request.getAggregateId(),
                        request.getIdentification(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPhone(),
                        request.getAddress(),
                        request.getBirthDate()
                )
        ).map(response -> new CustomerResponseDTO(
                response.getId(),
                response.getIdentification(),
                response.getFirstName(),
                response.getLastName(),
                response.getEmail(),
                response.getPhone(),
                response.getAddress(),
                response.getBirthDate()
        ));
    }

    public Mono<CustomerResponseDTO> deleteCustomer(CustomerRequestDTO request) {
        return deleteCustomerUseCase.execute(
                new UpdateCustomerCommand( request.getAggregateId() )
        ).map(response -> new CustomerResponseDTO(
                response.getId(),
                response.getIdentification(),
                response.getFirstName(),
                response.getLastName(),
                response.getEmail(),
                response.getPhone(),
                response.getAddress(),
                response.getBirthDate()
        ));
    }


}
