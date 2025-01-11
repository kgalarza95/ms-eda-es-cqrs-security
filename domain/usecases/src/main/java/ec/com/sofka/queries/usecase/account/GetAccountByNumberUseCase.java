package ec.com.sofka.queries.usecase.account;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import ec.com.sofka.generics.interfaces.IUseCaseGetMono;
import ec.com.sofka.queries.account.GetAccountQuery;
import ec.com.sofka.queries.response.account.GetAccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetAccountByNumberUseCase implements IUseCaseGetMono<GetAccountQuery, GetAccountResponse> {

    private final IAccountRepositoryGateway iAccountRepositoryGateway;
    private final IEventStoreGateway iEventStoreGateway;

    public GetAccountByNumberUseCase(IAccountRepositoryGateway IAccountRepositoryGateway, IEventStoreGateway eventRepository) {
        this.iAccountRepositoryGateway = IAccountRepositoryGateway;
        this.iEventStoreGateway = eventRepository;
    }

    @Override
    public Mono<GetAccountResponse> get(GetAccountQuery request) {
        return  findByNumber(request.getAggregateId());
    }

    public Mono<GetAccountResponse> findByNumber(String aggregateId) {

        return iEventStoreGateway.findAggregate(aggregateId)
                .collectList()
                .flatMap(events -> {
                    if (events.isEmpty()) {
                        return Mono.error(new ResourceNotFoundException("No events found for aggregate ID: " + aggregateId));
                    }

                    CustomerAggregate customer = CustomerAggregate.from(aggregateId, events);

                    return iAccountRepositoryGateway.findByNumber(customer.getAccount().getNumber().getValue())
                            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with number: " + customer.getAccount().getNumber().getValue())))
                            .map(result -> new GetAccountResponse(
                                    aggregateId,
                                    result.getId(),
                                    result.getAccountNumber(),
                                    result.getName(),
                                    result.getBalance(),
                                    result.getStatus()
                            ));
                });
    }


}
