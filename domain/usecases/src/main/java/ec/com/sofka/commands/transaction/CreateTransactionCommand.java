package ec.com.sofka.commands.transaction;

import ec.com.sofka.generics.utils.Command;
import ec.com.sofka.model.util.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTransactionCommand extends Command {

    private String description;
    private BigDecimal amount;
    private String transactionType;
    private LocalDate date;
    private String accountId;
    private final String status;

    private double transactionCost;
    private boolean isDeposit;

    public CreateTransactionCommand(String description, BigDecimal amount, String transactionType, LocalDate date, String accountId, String status) {
        super(null);
        this.description = description;
        this.amount = amount;
        this.transactionType = transactionType;
        this.date = date;
        this.accountId = accountId;
        this.status = StatusEnum.ACTIVE.name();
    }

    public CreateTransactionCommand(String aggregateId, String description, BigDecimal amount, String transactionType, LocalDate date, String accountId, String status, double transactionCost, boolean isDeposit) {
        super(aggregateId);
        this.description = description;
        this.amount = amount;
        this.transactionType = transactionType;
        this.date = date;
        this.accountId = accountId;
        this.status = status;
        this.transactionCost = transactionCost;
        this.isDeposit = isDeposit;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getStatus() {
        return status;
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public boolean isDeposit() {
        return isDeposit;
    }
}
