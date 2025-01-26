package ec.com.sofka.dto;

import java.math.BigDecimal;

public class AccountResponseDTO {
    public String customerId;
    public String accountId;
    public String name;
    public String accountNum;
    public BigDecimal balance;
    public String status;

    public AccountResponseDTO(String customerId, String accountId, String accountName, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.name = accountName;
        this.status = status;
    }


    public AccountResponseDTO(String customerId, String accountId, String accountName, String accountNumber, BigDecimal balance, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.name = accountName;
        this.accountNum = accountNumber;
        this.balance = balance;
        this.status = status;
    }

    public AccountResponseDTO() {
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

}
