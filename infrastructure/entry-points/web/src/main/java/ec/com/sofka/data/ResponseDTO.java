package ec.com.sofka.data;

import java.math.BigDecimal;

public class ResponseDTO {
    public String customerId;
    public String accountId;
    public String accountName;
    public String accountNumber;
    public BigDecimal balance;
    public String status;

    //After delete
    public ResponseDTO(String customerId, String accountId, String accountName, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountName = accountName;
        this.status = status;
    }


    //After update/create
    public ResponseDTO(String customerId, String accountId, String accountName, String accountNumber, BigDecimal balance, String status) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

}
