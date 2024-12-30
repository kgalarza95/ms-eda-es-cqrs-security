package ec.com.sofka;

import ec.com.sofka.account.Account;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.Balance;
import ec.com.sofka.account.values.objects.Name;
import ec.com.sofka.account.values.objects.NumberAcc;
import ec.com.sofka.aggregate.Customer;
import ec.com.sofka.commands.CreateAccountCommand;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.IEventStore;
import ec.com.sofka.generics.domain.DomainEvent;

import java.util.List;

public class CreateAccountUseCase {
    private final IEventStore repository;
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(IEventStore repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public void execute(CreateAccountCommand cmd) {
        //Ask coach jacobo for this part*
        List<DomainEvent> events = repository.findAggregate(cmd.getAggregateId());
        //Rebuild the aggregate
        Customer customer = Customer.from(cmd.getAggregateId(), events);

        customer.createAccount(cmd.getBalance(), cmd.getNumber(), cmd.getName());

        Account account = new Account(new AccountId(),
                Balance.of(cmd.getBalance()),
                NumberAcc.of(cmd.getNumber()),
                Name.of(cmd.getName()));

        //Last step for events to be saved
        customer.getUncommittedEvents().forEach(repository::save);

        //Save the account on the account repo
        accountRepository.save(account);

        customer.markEventsAsCommitted();
    }
}
