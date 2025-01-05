package ec.com.sofka.handlers;

import ec.com.sofka.*;
import ec.com.sofka.request.CreateAccountRequest;
import ec.com.sofka.data.RequestDTO;
import ec.com.sofka.data.ResponseDTO;
import ec.com.sofka.request.DeleteAccountRequest;
import ec.com.sofka.request.GetAccountRequest;
import ec.com.sofka.request.UpdateAccountRequest;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public ResponseDTO createAccount(RequestDTO request){
        var response = createAccountUseCase.execute(
                new CreateAccountRequest(
                        request.getAccountNum(),
                        request.getName(),
                        request.getBalance()

                ));
        return new ResponseDTO(response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus());
    }

    public List<ResponseDTO> getAllAccounts(){
        var response = getAllAccountsUseCase.get();
        return response.stream()
                .map(accountResponse -> new ResponseDTO(
                        accountResponse.getCustomerId(),
                        accountResponse.getAccountId(),
                        accountResponse.getName(),
                        accountResponse.getAccountNumber(),
                        accountResponse.getBalance(),
                        accountResponse.getStatus()
                        )
                ).toList();
    }

    public ResponseDTO getAccountByNumber(RequestDTO request){
        var response = getAccountByNumberUseCase.execute(
                new GetAccountRequest(
                        request.getCustomerId(),
                        request.getAccountNum()
                ));
        return new ResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus());
    }

    public ResponseDTO updateAccount(RequestDTO request){
        var response = updateAccountUseCase.execute(
                new UpdateAccountRequest(
                        request.getCustomerId(),
                        request.getBalance(),
                        request.getAccountNum(),
                        request.getName(),
                        request.getStatus()
                ));

        return new ResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus()
        );
    }

    public ResponseDTO deleteAccount(RequestDTO request){
        var response = deleteAccountUseCase.execute(
                new UpdateAccountRequest(
                        request.getCustomerId(),
                        request.getBalance(),
                        request.getAccountNum(),
                        request.getName(),
                        request.getStatus()

                ));
        return new ResponseDTO(
                response.getCustomerId(),
                response.getAccountId(),
                response.getName(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getStatus());
    }
}
