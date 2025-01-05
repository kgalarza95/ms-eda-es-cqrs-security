package ec.com.sofka;

import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.aggregate.events.EventsEnum;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.request.UpdateAccountRequest;
import ec.com.sofka.responses.UpdateAccountResponse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateAccountUseCase implements IUseCaseExecute<UpdateAccountRequest, UpdateAccountResponse> {
    private final AccountRepository accountRepository;
    private final IEventStore eventRepository;

    public UpdateAccountUseCase(AccountRepository accountRepository, IEventStore eventRepository) {
        this.accountRepository = accountRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public UpdateAccountResponse execute(UpdateAccountRequest request) {
        //Get events related to the aggregateId on the request
        List<DomainEvent> events = eventRepository.findAggregate(request.getAggregateId());


        //Rebuild the aggregate
        Customer customer = Customer.from(request.getAggregateId(),events);


        customer.updateAccount(
                customer.getAccount().getId().getValue(),
                request.getBalance(),
                request.getNumber(),
                request.getCustomerName(),
                request.getStatus());

        //Update the account
        AccountDTO result = accountRepository.update(
                new AccountDTO(customer.getAccount().getId().getValue(),
                        request.getCustomerName(),
                        request.getNumber(),
                        customer.getAccount().getBalance().getValue(),
                        customer.getAccount().getStatus().getValue()
                ));

        if (result != null) {
            //Last step for events to be saved
            customer.getUncommittedEvents().forEach(eventRepository::save);

            customer.markEventsAsCommitted();

            return new UpdateAccountResponse(
                    request.getAggregateId(),
                    result.getId(),
                    result.getAccountNumber(),
                    result.getName(),
                    result.getStatus());
        }

        return new UpdateAccountResponse();

    }
}
