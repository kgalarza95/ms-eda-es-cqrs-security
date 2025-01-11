package ec.com.sofka.commands.usecase.account;


import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.commands.account.CreateAccountCommand;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.busmessage.IAccountBusMessageGateway;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.commands.responses.account.CreateAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreateAccountUseCase implements IUseCaseExecute<CreateAccountCommand, CreateAccountResponse> {

    private final IEventStoreGateway iEventStoreGateway;
    private final IAccountBusMessageGateway iAccountBusMessageGateway;

    public CreateAccountUseCase(IEventStoreGateway iEventStoreGateway, IAccountBusMessageGateway iAccountBusMessageGateway) {
        this.iEventStoreGateway = iEventStoreGateway;
        this.iAccountBusMessageGateway = iAccountBusMessageGateway;
    }

    @Override
    public Mono<CreateAccountResponse> execute(CreateAccountCommand request) {
        return Mono.fromCallable(() -> {

                    CustomerAggregate customer = new CustomerAggregate();
                    customer.createAccount(
                            request.getNumber(),
                            request.getBalance(),
                            request.getCustomerName(),
                            request.getStatus()
                    );
                    return customer;
                })
                .flatMap(customer -> Flux.fromIterable(customer.getUncommittedEvents())
                        .flatMap(iEventStoreGateway::save)
                        .doOnNext(iAccountBusMessageGateway::sendMsg)
                        .then(Mono.just(customer))
                )
                .doOnNext(CustomerAggregate::markEventsAsCommitted)
                .map(customer -> new CreateAccountResponse(
                        customer.getId().getValue(),
                        customer.getAccount().getId().getValue(),
                        customer.getAccount().getNumber().getValue(),
                        customer.getAccount().getName().getValue(),
                        customer.getAccount().getBalance().getValue(),
                        customer.getAccount().getStatus().getValue()
                ));
    }




}
