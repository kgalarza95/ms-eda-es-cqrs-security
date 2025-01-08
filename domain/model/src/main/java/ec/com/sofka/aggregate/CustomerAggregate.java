package ec.com.sofka.aggregate;

import ec.com.sofka.aggregate.events.*;
import ec.com.sofka.aggregate.handlers.AccountHandlerAggregate;
import ec.com.sofka.aggregate.handlers.CustomerHandlerAggregate;
import ec.com.sofka.aggregate.handlers.TransactionHandlerAggregate;
import ec.com.sofka.model.account.Account;
import ec.com.sofka.model.account.values.AccountId;
import ec.com.sofka.aggregate.values.CustomerId;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AggregateRoot;
import ec.com.sofka.model.customer.Customer;
import ec.com.sofka.model.customer.values.CustomerID;
import ec.com.sofka.model.transaction.Transaction;
import ec.com.sofka.model.transaction.values.TransactionId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//2. Creation of the aggregate class - The communication between the entities and the external world.
public class CustomerAggregate extends AggregateRoot<CustomerId> {
    //5. Add the Account to the aggregate: Can't be final bc the aggregate is mutable by EventDomains
    private Account account;
    private Customer customer;
    private Transaction transaction;

    public CustomerAggregate() {
        super(new CustomerId());
        addSubscription();
    }

    private CustomerAggregate(final String id) {
        super(CustomerId.of(id));
        addSubscription();
    }

    private void addSubscription() {
        setSubscription(new AccountHandlerAggregate(this));
        setSubscription(new CustomerHandlerAggregate(this));
        setSubscription(new TransactionHandlerAggregate(this));
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void createAccount(String accountNumber, BigDecimal accountBalance, String name, String status) {
        addEvent(new AccountCreated(new AccountId().getValue(), accountNumber, accountBalance, name, status)).apply();
    }

    public void updateAccount(String accountId, BigDecimal balance, String accountNumber, String name, String status) {
        addEvent(new AccountUpdated(accountId, balance, accountNumber, name, status)).apply();
    }

    public static CustomerAggregate from(final String id, List<DomainEvent> events) {
        CustomerAggregate customer = new CustomerAggregate(id);
        events.stream()
                .filter(event -> id.equals(event.getAggregateRootId()))
                .reduce((first, second) -> second)
                .ifPresent(event -> customer.addEvent(event).apply());
        return customer;
    }

    public void createCustomer(String identification, String firstName, String lastName, String email, String phone, String address, LocalDate birthDate, String status) {
        addEvent(new CustomerCreated(new CustomerID().getValue(), identification, firstName, lastName, email, phone, address, birthDate, status)).apply();
    }

    public void updateCustomer(String identification, String firstName, String lastName, String email, String phone, String address, LocalDate birthDate, String status) {
        addEvent(new CustomerUpdated(new CustomerID().getValue(), identification, firstName, lastName, email, phone, address, birthDate, status)).apply();
    }

    public void createTransaction(String description, BigDecimal amount, String transactionType, LocalDate date, String accountId) {
        addEvent(new TransactionCreated(
                new TransactionId().getValue(),
                description,  amount, transactionType, date, accountId
        )).apply();
    }

}
