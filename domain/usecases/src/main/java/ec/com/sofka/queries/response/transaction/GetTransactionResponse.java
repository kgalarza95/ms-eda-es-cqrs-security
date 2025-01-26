package ec.com.sofka.queries.response.transaction;

import java.time.LocalDate;

public class GetTransactionResponse {
    private String id;
    private String description;
    private String amount;
    private String tax;
    private String transactionType;
    private LocalDate date;

    public GetTransactionResponse(String id, String description, String amount, String tax, String transactionType, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.tax = tax;
        this.transactionType = transactionType;
        this.date = date;
    }

    public GetTransactionResponse() {
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getTax() {
        return tax;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
