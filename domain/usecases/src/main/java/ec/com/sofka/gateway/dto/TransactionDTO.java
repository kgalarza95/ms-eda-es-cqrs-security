package ec.com.sofka.gateway.dto;

import ec.com.sofka.model.transaction.values.objects.*;

import java.time.LocalDate;

public class TransactionDTO {
    private String id;
    private String description;
    private String amount;
    private String transactionType;
    private LocalDate date;
    private String accountId;

    public TransactionDTO(String id, String description, String amount, String transactionType, LocalDate date, String accountId) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.transactionType = transactionType;
        this.date = date;
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
