package ec.com.sofka.aggregate;

import ec.com.sofka.aggregate.events.CustomerCreated;
import ec.com.sofka.model.account.Account;
import ec.com.sofka.model.account.values.AccountId;
import ec.com.sofka.aggregate.events.AccountCreated;
import ec.com.sofka.aggregate.events.AccountUpdated;
import ec.com.sofka.aggregate.values.CustomerId;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AggregateRoot;
import ec.com.sofka.model.customer.Customer;
import ec.com.sofka.model.customer.values.CustomerID;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//2. Creation of the aggregate class - The communication between the entities and the external world.
public class CustomerAggregate extends AggregateRoot<CustomerId> {
    //5. Add the Account to the aggregate: Can't be final bc the aggregate is mutable by EventDomains
    private Account account;
    private Customer customer;

    //To create the Aggregate the first time, ofc have to set the id as well.
    public CustomerAggregate() {
        super(new CustomerId());
        //Add the handler to the aggregate
        addSubscription();
    }

    //To rebuild the aggregate
    private CustomerAggregate(final String id) {
        super(CustomerId.of(id));
        //Add the handler to the aggregate
        addSubscription();
    }

    private void addSubscription() {
        setSubscription(new AccountHandlerAggregate(this));
        setSubscription(new CustomerHandlerAggregate(this));
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //Remember that User as Aggregate is the open door to interact with the entities
    public void createAccount(String accountNumber, BigDecimal accountBalance, String name, String status) {
        //Add the event to the aggregate
        addEvent(new AccountCreated(new AccountId().getValue(), accountNumber, accountBalance, name, status)).apply();

    }

    //Remember that User as Aggregate is the open door to interact with the entities
    public void updateAccount(String accountId, BigDecimal balance, String accountNumber, String name, String status) {
        //Add the event to the aggregate
        addEvent(new AccountUpdated(accountId, balance, accountNumber, name, status)).apply();

    }

    //To rebuild the aggregate
    public static CustomerAggregate from(final String id, List<DomainEvent> events) {
        CustomerAggregate customer = new CustomerAggregate(id);
        events.stream()
                .filter(event -> id.equals(event.getAggregateRootId()))
                .forEach((event) -> customer.addEvent(event).apply());
        return customer;
    }


    public void createCustomer(String identification, String firstName, String lastName, String email, String phone, String address, LocalDate birthDate) {
        addEvent(new CustomerCreated(new CustomerID().getValue(), identification, firstName, lastName, email, phone, address, birthDate)).apply();
    }


}
