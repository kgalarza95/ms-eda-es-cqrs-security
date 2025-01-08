package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.model.account.Account;
import ec.com.sofka.model.account.values.AccountId;
import ec.com.sofka.model.account.values.objects.Balance;
import ec.com.sofka.model.account.values.objects.Name;
import ec.com.sofka.model.account.values.objects.NumberAcc;
import ec.com.sofka.model.account.values.objects.Status;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.generics.domain.DomainActionsContainer;

//6. Create the handler for the Aggregate root that will be on the Agreggate root constructor
public class AccountHandlerAggregate extends DomainActionsContainer {
    //7. Add the actions to the handler
    public AccountHandlerAggregate(CustomerAggregate customer) {
        //8. Add the actions to the handler


        addDomainActions((AccountCreated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    NumberAcc.of(event.getAccountNumber()),
                    Name.of(event.getName()),
                    Balance.of(event.getAccountBalance()),
                    Status.of(event.getStatus()));
            customer.setAccount(account);
        });

        addDomainActions((AccountUpdated event) -> {
            Account account = new Account(
                    AccountId.of(event.getAccountId()),
                    NumberAcc.of(event.getAccountNumber()),
                    Name.of(event.getName()),
                    Balance.of(event.getBalance()),
                    Status.of(event.getStatus()));
            customer.setAccount(account);
        });
    }
}
