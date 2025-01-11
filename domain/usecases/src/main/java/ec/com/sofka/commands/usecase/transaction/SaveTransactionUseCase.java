package ec.com.sofka.commands.usecase.transaction;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.IEventStoreGateway;
import ec.com.sofka.gateway.ITransactionRepositoryGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.generics.interfaces.IUseCaseExecute;
import ec.com.sofka.commands.transaction.CreateTransactionCommand;
import ec.com.sofka.exception.InsufficientBalanceException;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.commands.responses.transaction.CreateTransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;

public class SaveTransactionUseCase implements IUseCaseExecute<CreateTransactionCommand, CreateTransactionResponse> {

    private final ITransactionRepositoryGateway iTransactionRepositoryGateway;
    private final IEventStoreGateway iEventStoreGateway;
    private final IAccountRepositoryGateway iAccountRepositoryGateway;

    public SaveTransactionUseCase(ITransactionRepositoryGateway iTransactionRepositoryGateway, IEventStoreGateway iEventStoreGateway, IAccountRepositoryGateway iAccountRepositoryGateway) {
        this.iTransactionRepositoryGateway = iTransactionRepositoryGateway;
        this.iEventStoreGateway = iEventStoreGateway;
        this.iAccountRepositoryGateway = iAccountRepositoryGateway;
    }

    @Override
    public Mono<CreateTransactionResponse> execute(CreateTransactionCommand request) {

        CustomerAggregate transactionAggregate = new CustomerAggregate();

        transactionAggregate.createTransaction(
                request.getDescription(),
                request.getAmount(),
                request.getTransactionType(),
                request.getDate(),
                request.getAccountId()
        );

        TransactionDTO transactionDTO = new TransactionDTO(
                transactionAggregate.getTransaction().getId().getValue(),
                transactionAggregate.getTransaction().getDescription().getValue(),
                transactionAggregate.getTransaction().getAmount().getValue(),
                transactionAggregate.getTransaction().getTransactionType().getValue(),
                transactionAggregate.getTransaction().getDate().getValue(),
                transactionAggregate.getTransaction().getAccountId().getValue()
        );

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

                    CustomerAggregate customer = CustomerAggregate.from(request.getAggregateId(), events);

                    return iAccountRepositoryGateway.findByNumber(customer.getAccount().getNumber().getValue())
                            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with ID: " + request.getAccountId())))
                            .flatMap(account -> {
                                BigDecimal affectedBalance = calculateAffectedBalance.apply(account);

                                if (!validBalance.test(account.getBalance().add(affectedBalance))) {
                                    return Mono.error(new InsufficientBalanceException("Insufficient balance for the transaction."));
                                }

                                account.setBalance(account.getBalance().add(affectedBalance));

                                return iAccountRepositoryGateway.update(account)
                                        .then(iTransactionRepositoryGateway.save(transactionDTO))
                                        .then(Flux.fromIterable(transactionAggregate.getUncommittedEvents())
                                                .flatMap(iEventStoreGateway::save)
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
