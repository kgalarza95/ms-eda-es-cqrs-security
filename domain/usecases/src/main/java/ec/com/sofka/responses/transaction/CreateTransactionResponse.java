package ec.com.sofka.responses.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTransactionResponse {

    private String id;
    private String description;
    private BigDecimal amount;
    private String transactionType;
    private LocalDate date;
    private String accountId;

    public CreateTransactionResponse(String id, String description, BigDecimal amount, String transactionType, LocalDate date, String accountId) {
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
}
