package ec.com.sofka.handlers;


import ec.com.sofka.dto.TransactionInDTO;
import ec.com.sofka.dto.TransactionOutDTO;
import ec.com.sofka.commands.transaction.CreateTransactionCommand;
import ec.com.sofka.commands.usecase.transaction.SaveTransactionUseCase;
import ec.com.sofka.commands.usecase.util.TransactionCost;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@Validated
public class TransactionHandler {

    private final SaveTransactionUseCase saveTransactionUseCase;

    public TransactionHandler(SaveTransactionUseCase saveTransactionUseCase) {
        this.saveTransactionUseCase = saveTransactionUseCase;
    }

    public Mono<TransactionOutDTO> makeBranchDeposit(@Valid TransactionInDTO transactionInDTO) {
        transactionInDTO.setTransactionType("C");
        return processTransaction(transactionInDTO, TransactionCost.DEPOSITO_SUCURSAL.getCosto(), true);
    }

    public Mono<TransactionOutDTO> makeATMDeposit(@Valid TransactionInDTO transactionInDTO) {
        transactionInDTO.setTransactionType("C");
        return processTransaction(transactionInDTO, TransactionCost.DEPOSITO_CAJERO.getCosto(), true);
    }

    public Mono<TransactionOutDTO> makeDepositToAnotherAccount(@Valid TransactionInDTO transactionInDTO) {
        transactionInDTO.setTransactionType("C");
        return processTransaction(transactionInDTO, TransactionCost.DEPOSITO_OTRA_CUENTA.getCosto(), true);
    }

    public Mono<TransactionOutDTO> makePhysicalPurchase(@Valid TransactionInDTO transactionInDTO) {
        transactionInDTO.setTransactionType("D");
        return processTransaction(transactionInDTO, TransactionCost.COMPRA_FISICA.getCosto(), false);
    }

    public Mono<TransactionOutDTO> makeOnlinePurchase(@Valid TransactionInDTO transactionInDTO) {
        transactionInDTO.setTransactionType("D");
        return processTransaction(transactionInDTO, TransactionCost.COMPRA_WEB.getCosto(), false);
    }

    public Mono<TransactionOutDTO> makeATMWithdrawal(@Valid TransactionInDTO transactionInDTO) {
        transactionInDTO.setTransactionType("D");
        return processTransaction(transactionInDTO, TransactionCost.RETIRO_CAJERO.getCosto(), false);
    }

    private Mono<TransactionOutDTO> processTransaction(TransactionInDTO request, double transactionCost, boolean isDeposit) {
        request.setDate(LocalDate.now());
        return saveTransactionUseCase.execute(
                new CreateTransactionCommand(
                        request.getAccountId(),
                        request.getDescription(),
                        request.getAmount(),
                        request.getTransactionType(),
                        request.getDate(),
                        request.getAccountId(),
                        null,
                        transactionCost,
                        isDeposit
                )
        ).map(response -> new TransactionOutDTO(
                response.getId(),
                response.getDescription(),
                response.getAmount(),
                response.getTransactionType(),
                response.getDate(),
                response.getAccountId()
        ));
    }


}
