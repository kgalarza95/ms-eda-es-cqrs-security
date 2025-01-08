package ec.com.sofka.aggregate.events;

import ec.com.sofka.aggregate.events.util.AccountEventsEnum;
import ec.com.sofka.generics.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountCreated extends DomainEvent {
    private String accountId;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String name;
    private String status;


    public AccountCreated(String accountId, String accountNumber, BigDecimal accountBalance, String name, String status) {
        super(AccountEventsEnum.ACCOUNT_CREATED.name());
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.name = name;
        this.status = status;
    }

    public AccountCreated(String accountNumber, BigDecimal accountBalance, String name, String status) {
        super(AccountEventsEnum.ACCOUNT_CREATED.name());
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.name = name;
        this.status = status;
    }

    public AccountCreated() {
        super(AccountEventsEnum.ACCOUNT_CREATED.name());

    }


    public String getAccountId() {
        return accountId;
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

    public String getStatus() {
        return status;
    }


}
