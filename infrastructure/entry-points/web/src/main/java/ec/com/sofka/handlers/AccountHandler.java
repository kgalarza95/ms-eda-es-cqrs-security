package ec.com.sofka.handlers;

import ec.com.sofka.CreateAccountUseCase;
import ec.com.sofka.request.CreateAccountRequest;
import ec.com.sofka.data.RequestDTO;
import ec.com.sofka.data.ResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountHandler {
    private final CreateAccountUseCase createAccountUseCase;

    public AccountHandler(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    public ResponseDTO createAccount(RequestDTO request){

        var response = createAccountUseCase.execute(
                new CreateAccountRequest(
                        request.getBalance(),
                        request.getAccount(),
                        request.getCustomer()

                ));
        return new ResponseDTO(response.getCustomerId(), response.getName());
    }
}
