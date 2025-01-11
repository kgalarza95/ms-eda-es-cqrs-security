package ec.com.sofka.dto;

import java.math.BigDecimal;

public class AccountRequestDTO {
    public String customerId;

    public String name;

    public String accountNum;

    public BigDecimal balance;

    public AccountRequestDTO(String customerId, String name, String accountNum, BigDecimal balance) {
        this.customerId = customerId;
        this.name = name;
        this.accountNum = accountNum;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCustomerId() {
        return customerId;
    }

}
