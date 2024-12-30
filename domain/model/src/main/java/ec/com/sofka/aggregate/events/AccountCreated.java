package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountCreated extends DomainEvent {
    private final String accountNumber;
    private final BigDecimal accountBalance;
    private final String name;

    public AccountCreated(String accountNumber, BigDecimal accountBalance, String name) {
        //super("sofka.account.accountcreated");
        super(EventsEnum.ACCOUNT_CREATED.name());
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public String getName() {
        return name;
    }


}
