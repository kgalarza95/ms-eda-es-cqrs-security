package ec.com.sofka.commands.usecase.transaction;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.TransactionCreated;
import ec.com.sofka.commands.responses.transaction.CreateTransactionResponse;
import ec.com.sofka.commands.transaction.CreateTransactionCommand;
import ec.com.sofka.exception.InsufficientBalanceException;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.ITransactionRepositoryGateway;
import ec.com.sofka.gateway.busmessage.IAccountBusMessageGateway;
import ec.com.sofka.gateway.busmessage.ITransactionBusMessageGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;

public class SaveTransactionUseCase implements IUseCaseExecute<CreateTransactionCommand, CreateTransactionResponse> {

    private final ITransactionRepositoryGateway iTransactionRepositoryGateway;
    private final IAccountRepositoryGateway iAccountRepositoryGateway;
    private final IEventStoreGateway iEventStoreGateway;

    private final IAccountBusMessageGateway iAccountBusMessageGateway;
    private final ITransactionBusMessageGateway iTransactionBusMessageGateway;

    public SaveTransactionUseCase(ITransactionRepositoryGateway iTransactionRepositoryGateway, IAccountRepositoryGateway iAccountRepositoryGateway, IEventStoreGateway iEventStoreGateway, IAccountBusMessageGateway iAccountBusMessageGateway, ITransactionBusMessageGateway iTransactionBusMessageGateway) {
        this.iTransactionRepositoryGateway = iTransactionRepositoryGateway;
        this.iAccountRepositoryGateway = iAccountRepositoryGateway;
        this.iEventStoreGateway = iEventStoreGateway;
        this.iAccountBusMessageGateway = iAccountBusMessageGateway;
        this.iTransactionBusMessageGateway = iTransactionBusMessageGateway;
    }


    @Override
    public Mono<CreateTransactionResponse> execute(CreateTransactionCommand request) {


        CustomerAggregate transactionAggregate = new CustomerAggregate();
        BigDecimal transactionCostBD = new BigDecimal(request.getTransactionCost());

        Function<AccountDTO, BigDecimal> calculateAffectedBalance = account -> {
            BigDecimal amount = request.getAmount();
            return request.isDeposit()
                    ? amount.subtract(transactionCostBD)
                    : amount.negate().subtract(transactionCostBD);
        };

        Predicate<BigDecimal> validBalance = balance -> balance.compareTo(BigDecimal.ZERO) >= 0;

        return iEventStoreGateway.findAggregate(request.getAggregateId())
                .collectList()
                .flatMap(events -> {


                    if (events.isEmpty()) {
                        return Mono.error(new ResourceNotFoundException("No events found for aggregate ID: " + request.getAggregateId()));
                    }

                    transactionAggregate.createTransaction(
                            request.getDescription(),
                            request.getAmount(),
                            request.getTransactionType(),
                            request.getDate(),
                            request.getAccountId(),
                            transactionCostBD
                    );


                    CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(), events);

                    return iAccountRepositoryGateway.findByNumber(customer.getAccount().getNumber().getValue())
                            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with ID: " + request.getAccountId())))
                            .flatMap(account -> {
                                System.out.println("3");

                                BigDecimal affectedBalance = calculateAffectedBalance.apply(account);

                                if (!validBalance.test(account.getBalance().add(affectedBalance))) {
                                    return Mono.error(new InsufficientBalanceException("Insufficient balance for the transaction."));
                                }

                                account.setBalance(account.getBalance().add(affectedBalance));

                                return iAccountRepositoryGateway.update(account)
                                        .then(Flux.fromIterable(transactionAggregate.getUncommittedEvents())
                                                .flatMap(event ->
                                                        iEventStoreGateway.save(event)
                                                                .then(Mono.fromRunnable(() -> iTransactionBusMessageGateway.sendMsg(event)))
                                                )
                                                .then())
                                        .then(Mono.fromCallable(() -> {
                                            transactionAggregate.markEventsAsCommitted();
                                            return new CreateTransactionResponse(
                                                    transactionAggregate.getTransaction().getId().getValue(),
                                                    transactionAggregate.getTransaction().getDescription().getValue(),
                                                    account.getBalance(),
                                                    transactionAggregate.getTransaction().getTransactionType().getValue(),
                                                    transactionAggregate.getTransaction().getDate().getValue(),
                                                    transactionAggregate.getTransaction().getAccountId().getValue()
                                            );
                                        }));


                            });
                });
    }





}
