package ec.com.sofka.dto;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.TransactionCreated;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.queries.response.transaction.GetTransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public class TransactionDTO {

    private String id;
    private String description;
    private String amount;
    private String tax;
    private String transactionType;
    private LocalDate date;

    public TransactionDTO(String id, String description, String amount, String tax, String transactionType, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.tax = tax;
        this.transactionType = transactionType;
        this.date = date;
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

}
