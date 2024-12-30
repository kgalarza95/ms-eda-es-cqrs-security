package ec.com.sofka.handlers;

import ec.com.sofka.CreateAccountUseCase;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.aggregate.values.CustomerId;
import ec.com.sofka.commands.CreateAccountCommand;
import ec.com.sofka.data.RequestDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountHandler {
    private final CreateAccountUseCase createAccountUseCase;

    public AccountHandler(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    public void createAccount(RequestDTO request){
        createAccountUseCase.execute(
                new CreateAccountCommand(
                        CustomerId.of(UUID.randomUUID().toString()).getValue(),
                        request.getBalance(),
                        request.getAccount(),
                        request.getCustomer()

                ));

    }
}
