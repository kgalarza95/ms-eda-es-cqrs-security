package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.TransactionCreated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.model.transaction.Transaction;
import ec.com.sofka.model.transaction.values.TransactionId;
import ec.com.sofka.model.transaction.values.objects.*;

public class TransactionHandlerAggregate extends DomainActionsContainer {

    public TransactionHandlerAggregate(CustomerAggregate customerAggregate) {

        addDomainActions((TransactionCreated event) -> {
            Transaction transaction = new Transaction(
                    TransactionId.of(event.getId()),
                    Description.of(event.getDescription()),
                    Amount.of(event.getAmount()),
                    TransactionType.of(event.getTransactionType()),
                    TransactionDate.of(event.getDate()),
                    AccountId.of(event.getAccountId())
            );
            customerAggregate.setTransaction(transaction);
        });


    }

}
