package ec.com.sofka.model.transaction;

import ec.com.sofka.generics.utils.Entity;
import ec.com.sofka.model.transaction.values.TransactionId;
import ec.com.sofka.model.transaction.values.objects.*;

public class Transaction extends Entity<TransactionId> {

    private final Description description;
    private final Amount amount;
    private final TransactionType transactionType;
    private final TransactionDate date;
    private final AccountId accountId;

    public Transaction(TransactionId id, Description description, Amount amount, TransactionType transactionType, TransactionDate date, AccountId accountId) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.transactionType = transactionType;
        this.date = date;
        this.accountId = accountId;
    }

    public Description getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionDate getDate() {
        return date;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}
