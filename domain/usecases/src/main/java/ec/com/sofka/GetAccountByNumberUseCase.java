package ec.com.sofka;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.request.GetAccountRequest;
import ec.com.sofka.responses.GetAccountResponse;

import java.util.List;

public class GetAccountByNumberUseCase implements IUseCaseExecute<GetAccountRequest, GetAccountResponse> {
    private final AccountRepository accountRepository;
    private final IEventStore eventRepository;

    public GetAccountByNumberUseCase(AccountRepository accountRepository, IEventStore eventRepository) {
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public GetAccountResponse execute(GetAccountRequest request) {
        //Get events related to the aggregateId on the request
        List<DomainEvent> events = eventRepository.findAggregate(request.getAggregateId());

        //Rebuild the aggregate
        Customer customer = Customer.from(request.getAggregateId(),events);

        //Get the account from the repository
        AccountDTO result = accountRepository.findByNumber(customer.getAccount().getNumber().getValue());

        //Return the response
        return new GetAccountResponse(
                request.getAggregateId(),
                result.getId(),
                result.getAccountNumber(),
                result.getName(),
                result.getBalance(),
                result.getStatus()
        );
    }
}
