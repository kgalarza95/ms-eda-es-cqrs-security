package ec.com.sofka.aggregate;

import ec.com.sofka.account.Account;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.aggregate.values.CustomerId;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AggregateRoot;

import java.math.BigDecimal;
import java.util.List;

//2. Creation of the aggregate class - The communication between the entities and the external world.
public class Customer extends AggregateRoot<CustomerId> {
    //5. Add the Account to the aggregate: Can't be final bc the aggregate is mutable by EventDomains
    private Account account;

    //To create the Aggregate the first time, ofc have to set the id as well.
    public Customer() {
        super(new CustomerId());
        //Add the handler to the aggregate
        setSubscription(new CustomerHandler(this));
    }

    //To rebuild the aggregate
    private Customer(final String id) {
        super(CustomerId.of(id));
        //Add the handler to the aggregate
        setSubscription(new CustomerHandler(this));
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    //Remember that User as Aggregate is the open door to interact with the entities
    public void createAccount(String accountNumber, BigDecimal accountBalance, String name, String status) {
        //Add the event to the aggregate
        addEvent(new AccountCreated(new AccountId().getValue(), accountNumber,accountBalance,name,status)).apply();

    }

    //Remember that User as Aggregate is the open door to interact with the entities
    public void updateAccount(String accountId, BigDecimal balance, String accountNumber, String name, String status ) {
        //Add the event to the aggregate
        addEvent(new AccountUpdated(accountId, balance, accountNumber, name, status)).apply();

    }

    //To rebuild the aggregate
    public static Customer from(final String id, List<DomainEvent> events) {
        Customer customer = new Customer(id);
        events.stream()
                .filter(event -> id.equals(event.getAggregateRootId()))
                .forEach((event) -> customer.addEvent(event).apply());
        return customer;
    }


}
