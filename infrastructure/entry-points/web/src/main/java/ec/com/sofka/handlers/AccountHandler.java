package ec.com.sofka.handlers;

import ec.com.sofka.commands.account.CreateAccountCommand;
import ec.com.sofka.commands.usecase.account.*;
import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.dto.AccountResponseDTO;
import ec.com.sofka.model.util.StatusEnum;
import ec.com.sofka.queries.account.GetAccountQuery;
import ec.com.sofka.commands.account.UpdateAccountCommand;
import ec.com.sofka.queries.usecase.account.GetAccountByNumberUseCase;
import ec.com.sofka.queries.usecase.account.GetAllAccountsUseCase;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final GetAccountByNumberUseCase getAccountByNumberUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    public AccountHandler(CreateAccountUseCase createAccountUseCase, GetAllAccountsUseCase getAllAccountsUseCase, GetAccountByNumberUseCase getAccountByNumberUseCase, UpdateAccountUseCase updateAccountUseCase, DeleteAccountUseCase deleteAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAllAccountsUseCase = getAllAccountsUseCase;
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
        this.deleteAccountUseCase = deleteAccountUseCase;
    }

    public Flux<AccountResponseDTO> getAllAccounts() {
        return getAllAccountsUseCase.get()
                .map(accountResponse -> new AccountResponseDTO(
                        accountResponse.getCustomerId(),
                        accountResponse.getAccountId(),
                        accountResponse.getName(),
                        accountResponse.getAccountNumber(),
                        accountResponse.getBalance(),
                        accountResponse.getStatus()
                ));
    }

    public Mono<AccountResponseDTO> getAccountByNumber(AccountRequestDTO request) {
        return getAccountByNumberUseCase.get(
                new GetAccountQuery(
                        request.getCustomerId(),
                        request.getAccountNum()
                )
        ).map(response -> new AccountResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus()
        ));
    }

    public Mono<AccountResponseDTO> createAccount(AccountRequestDTO request) {
        return createAccountUseCase.execute(
                new CreateAccountCommand(
                        request.getName(),
                        request.getBalance()
                )
        ).map(response -> new AccountResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus()
        ));
    }

    public Mono<AccountResponseDTO> updateAccount(AccountRequestDTO request) {
        return updateAccountUseCase.execute(
                new UpdateAccountCommand(
                        request.getCustomerId(),
                        request.getBalance(),
                        request.getAccountNum(),
                        request.getName(),
                        StatusEnum.ACTIVE.name()
                )
        ).map(response -> new AccountResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus()
        ));
    }

    public Mono<AccountResponseDTO> deleteAccount(AccountRequestDTO request) {
        return deleteAccountUseCase.execute(
                new UpdateAccountCommand(
                        request.getCustomerId(),
                        request.getBalance(),
                        request.getAccountNum(),
                        request.getName(),
                        StatusEnum.INACTIVE.name()
                )
        ).map(response -> new AccountResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus()
        ));
    }

}
