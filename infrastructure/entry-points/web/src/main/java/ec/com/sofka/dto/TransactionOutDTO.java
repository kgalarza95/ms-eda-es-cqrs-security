package ec.com.sofka.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionOutDTO {

    private String id;
    private String description;
    private BigDecimal amount;
    private String transactionType;
    private LocalDate date;
    private String accountId;

    public TransactionOutDTO() {}

    public TransactionOutDTO(String id, String description, BigDecimal amount, String transactionType, LocalDate date, String accountId) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
