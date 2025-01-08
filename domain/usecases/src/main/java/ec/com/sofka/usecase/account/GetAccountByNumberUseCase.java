package ec.com.sofka.usecase.account;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.request.account.GetAccountRequest;
import ec.com.sofka.responses.account.GetAccountResponse;
import reactor.core.publisher.Mono;

public class GetAccountByNumberUseCase implements IUseCaseExecute<GetAccountRequest, GetAccountResponse> {
    private final IAccountRepositoryGateway IAccountRepositoryGateway;
    private final IEventStoreGateway eventRepository;

    public GetAccountByNumberUseCase(IAccountRepositoryGateway IAccountRepositoryGateway, IEventStoreGateway eventRepository) {
        this.IAccountRepositoryGateway = IAccountRepositoryGateway;
        this.eventRepository = eventRepository;
    }

    @Override
    public Mono<GetAccountResponse> execute(GetAccountRequest request) {
        return eventRepository.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {

                    CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(), events);

                    return IAccountRepositoryGateway.findByNumber(customer.getAccount().getNumber().getValue())
                            .map(result -> {
                                return new GetAccountResponse(
                                        request.getAggregateId(),
                                        result.getId(),
                                        result.getAccountNumber(),
                                        result.getName(),
                                        result.getBalance(),
                                        result.getStatus()
                                );
                            });
                });
    }

}
