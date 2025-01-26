package ec.com.sofka.queries.usecase.transaction;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.aggregate.events.TransactionCreated;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.ICustomerRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.ITransactionRepositoryGateway;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseGet;
import ec.com.sofka.generics.interfaces.IUseCaseGetMono;
import ec.com.sofka.queries.customer.GetCustomerQuery;
import ec.com.sofka.queries.response.account.GetAccountResponse;
import ec.com.sofka.queries.response.customer.GetCustomerResponse;
import ec.com.sofka.queries.response.transaction.GetTransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class GetTransactionByIdUseCase  {

    private final ITransactionRepositoryGateway iTransactionRepositoryGateway;

    public GetTransactionByIdUseCase(ITransactionRepositoryGateway ITransactionRepositoryGateway) {
        this.iTransactionRepositoryGateway = ITransactionRepositoryGateway;
    }

    public Flux<GetTransactionResponse> get(String accountId) {
        return iTransactionRepositoryGateway.getByAccount(accountId);
    }



}
