package ec.com.sofka.handlers;

import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.dto.AccountResponseDTO;
import ec.com.sofka.dto.CustomerRequestDTO;
import ec.com.sofka.dto.CustomerResponseDTO;
import ec.com.sofka.request.account.CreateAccountRequest;
import ec.com.sofka.request.customer.CreateCustomerRequest;
import ec.com.sofka.usecase.customer.CreateCustomerUseCase;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {
    private final CreateCustomerUseCase createCustomerUseCase;

    public CustomerHandler(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    public Mono<CustomerResponseDTO> createCustomer(CustomerRequestDTO request) {
        return createCustomerUseCase.execute(
                new CreateCustomerRequest(
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

}
