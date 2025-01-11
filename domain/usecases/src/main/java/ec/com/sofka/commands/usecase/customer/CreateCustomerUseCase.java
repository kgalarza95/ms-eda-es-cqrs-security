package ec.com.sofka.commands.usecase.customer;


import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.gateway.ICustomerRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.busmessage.ICustomerBusMessageGateway;
import ec.com.sofka.gateway.dto.CustomerDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.commands.customer.CreateCustomerCommand;
import ec.com.sofka.commands.responses.customer.CreateCustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateCustomerUseCase implements IUseCaseExecute<CreateCustomerCommand, CreateCustomerResponse> {

    private final IEventStoreGateway iEventStoreGateway;
    private final ICustomerBusMessageGateway iCustomerRepositoryGateway;

    public CreateCustomerUseCase(IEventStoreGateway iEventStoreGateway, ICustomerBusMessageGateway iCustomerRepositoryGateway) {
        this.iEventStoreGateway = iEventStoreGateway;
        this.iCustomerRepositoryGateway = iCustomerRepositoryGateway;
    }


    @Override
    public Mono<CreateCustomerResponse> execute(CreateCustomerCommand request) {
        CustomerAggregate customer = new CustomerAggregate();

        customer.createCustomer(
                request.getIdentification(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress(),
                request.getBirthDate(),
                request.getStatus()
        );

        return Flux.fromIterable(customer.getUncommittedEvents())
                .flatMap(event ->
                        iEventStoreGateway.save(event)
                                .doOnSuccess(savedEvent -> iCustomerRepositoryGateway.sendMsg(savedEvent))
                )
                .then(Mono.fromCallable(() -> {
                    customer.markEventsAsCommitted();
                    return new CreateCustomerResponse(
                            customer.getId().getValue(),
                            customer.getCustomer().getIdentification().getValue(),
                            customer.getCustomer().getFirstName().getValue(),
                            customer.getCustomer().getLastName().getValue(),
                            customer.getCustomer().getEmail().getValue(),
                            customer.getCustomer().getPhone().getValue(),
                            customer.getCustomer().getAddress().getValue(),
                            customer.getCustomer().getBirthDate().getValue()
                    );
                }));
    }



}
