package ec.com.sofka.responses;

//Response class associated to the CreateAccountUseCase
public class CreateAccountResponse{
    private final String customerId;
    private final String accountNumber;
    private final String name;

    public CreateAccountResponse(String customerId, String accountNumber, String name) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }
}
