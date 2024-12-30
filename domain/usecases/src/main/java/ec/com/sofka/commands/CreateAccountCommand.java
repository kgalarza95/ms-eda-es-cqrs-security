package ec.com.sofka.commands;

import java.math.BigDecimal;

public class CreateAccountCommand {
    private final String aggregateId;
    private final BigDecimal balance;
    private final String number;
    private final String name;

    public CreateAccountCommand(String aggregateId, BigDecimal balance, String number, String name) {
        this.aggregateId = aggregateId;
        this.balance = balance;
        this.number = number;
        this.name = name;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}
