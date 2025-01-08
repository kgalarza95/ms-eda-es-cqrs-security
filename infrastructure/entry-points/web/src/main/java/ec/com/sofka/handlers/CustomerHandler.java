package ec.com.sofka.handlers;

import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.dto.AccountResponseDTO;
import ec.com.sofka.dto.CustomerRequestDTO;
import ec.com.sofka.dto.CustomerResponseDTO;
import ec.com.sofka.request.account.CreateAccountRequest;
import ec.com.sofka.request.customer.CreateCustomerRequest;
import ec.com.sofka.request.customer.GetCustomerRequest;
import ec.com.sofka.request.customer.UpdateCustomerRequest;
import ec.com.sofka.usecase.account.UpdateAccountUseCase;
import ec.com.sofka.usecase.customer.CreateCustomerUseCase;
import ec.com.sofka.usecase.customer.GetAllCustomerUseCase;
import ec.com.sofka.usecase.customer.GetCustomerByIdCaseUse;
import ec.com.sofka.usecase.customer.UpdateCustomerUseCase;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetAllCustomerUseCase getAllCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
//    private final GetCustomerByIdCaseUse getCustomerByIdCaseUse;

//    public CustomerHandler(CreateCustomerUseCase createCustomerUseCase, GetAllCustomerUseCase getAllCustomerUseCase, UpdateCustomerUseCase updateCustomerUseCase, GetCustomerByIdCaseUse getCustomerByIdCaseUse) {
//        this.createCustomerUseCase = createCustomerUseCase;
//        this.getAllCustomerUseCase = getAllCustomerUseCase;
//        this.updateCustomerUseCase = updateCustomerUseCase;
//        this.getCustomerByIdCaseUse = getCustomerByIdCaseUse;
//    }

    public CustomerHandler(CreateCustomerUseCase createCustomerUseCase, GetAllCustomerUseCase getAllCustomerUseCase, UpdateCustomerUseCase updateCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getAllCustomerUseCase = getAllCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
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

    public Mono<CustomerResponseDTO> updateCustomer(CustomerRequestDTO request) {
        return updateCustomerUseCase.execute(
                new UpdateCustomerRequest(
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

    public Mono<CustomerResponseDTO> getCustomerById(String customerId) {
        return null;
//        return getCustomerByIdCaseUse.execute(new GetCustomerRequest(customerId))
//                .map(response -> new CustomerResponseDTO(
//                        response.getId(),
//                        response.getIdentification(),
//                        response.getFirstName(),
//                        response.getLastName(),
//                        response.getEmail(),
//                        response.getPhone(),
//                        response.getAddress(),
//                        response.getBirthDate()
//                ));
    }


}
