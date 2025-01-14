package ec.com.sofka.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AccountRequestDTO {

    public String customerId;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    public String name;

    public String accountNum;

    @NotNull(message = "Balance cannot be null")
    @Min(value = 0, message = "Balance must be greater than or equal to 0")
    @Max(value = 1000000, message = "Balance must be less than or equal to 1,000,000")
    public BigDecimal balance;

    public AccountRequestDTO() {
    }

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
