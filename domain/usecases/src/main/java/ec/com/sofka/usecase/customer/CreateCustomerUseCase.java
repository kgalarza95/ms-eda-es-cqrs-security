package ec.com.sofka.usecase.customer;


import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.ICustomerRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.gateway.dto.CustomerDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.request.account.CreateAccountRequest;
import ec.com.sofka.request.customer.CreateCustomerRequest;
import ec.com.sofka.responses.account.CreateAccountResponse;
import ec.com.sofka.responses.customer.CreateCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateCustomerUseCase implements IUseCaseExecute<CreateCustomerRequest, CreateCustomerResponse> {

    private final IEventStoreGateway iEventStoreGateway;
    private final ICustomerRepositoryGateway iCustomerRepositoryGateway;

    public CreateCustomerUseCase(IEventStoreGateway repository, ICustomerRepositoryGateway iCustomerRepositoryGateway) {
        this.iEventStoreGateway = repository;
        this.iCustomerRepositoryGateway = iCustomerRepositoryGateway;
    }

    @Override
    public Mono<CreateCustomerResponse> execute(CreateCustomerRequest request) {
        CustomerAggregate customer = new CustomerAggregate();

        customer.createCustomer(request.getIdentification(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getPhone(), request.getAddress(), request.getBirthDate(), request.getStatus());

        CustomerDTO customerDTO = new CustomerDTO(
                customer.getCustomer().getId().getValue(),
                customer.getCustomer().getIdentification().getValue(),
                customer.getCustomer().getFirstName().getValue(),
                customer.getCustomer().getLastName().getValue(),
                customer.getCustomer().getEmail().getValue(),
                customer.getCustomer().getPhone().getValue(),
                customer.getCustomer().getAddress().getValue(),
                customer.getCustomer().getBirthDate().getValue(),
                customer.getCustomer().getStatus().getValue()
        );

        return iCustomerRepositoryGateway.save(customerDTO)
                .flatMap(result -> {

                    return Flux.fromIterable(customer.getUncommittedEvents())
                            .flatMap(iEventStoreGateway::save)
                            .then(Mono.fromCallable(() -> {
                                customer.markEventsAsCommitted();
                                return new CreateCustomerResponse(
                                        customer.getCustomer().getId().getValue(),
                                        customer.getCustomer().getIdentification().getValue(),
                                        customer.getCustomer().getFirstName().getValue(),
                                        customer.getCustomer().getLastName().getValue(),
                                        customer.getCustomer().getEmail().getValue(),
                                        customer.getCustomer().getPhone().getValue(),
                                        customer.getCustomer().getAddress().getValue(),
                                        customer.getCustomer().getBirthDate().getValue()
                                );
                            }));
                });
    }



}
