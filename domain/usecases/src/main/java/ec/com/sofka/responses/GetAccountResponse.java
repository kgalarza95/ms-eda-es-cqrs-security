package ec.com.sofka.responses;

import java.math.BigDecimal;

//Response class associated to the CreateAccountUseCase
public class GetAccountResponse {
    private final String customerId;
    private final String accountId;
    private final String accountNumber;
    private final String name;
    private final BigDecimal balance;
    private final String status;


   public GetAccountResponse(String customerId, String accountId, String accountNumber, String name, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.status = status;
        this.balance = null;
    }

    public GetAccountResponse(String customerId, String accountId, String accountNumber, String name) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.status = null;
        this.balance = null;
    }

    public GetAccountResponse(String customerId, String accountId, String accountNumber, String name, BigDecimal balance, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }
}
