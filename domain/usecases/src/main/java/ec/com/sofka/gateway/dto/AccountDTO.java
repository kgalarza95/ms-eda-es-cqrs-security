package ec.com.sofka.gateway.dto;

import java.math.BigDecimal;

//This class is used to transfer data between the application and the database -
// Notice how this affect the AccountRepository interface that lives in usecases
//Notice also how this impacts on the driven adapter that implements the AccountRepository interface that lives in usecases.
public class AccountDTO {
    private String id;
    private String accountNumber;
    private String owner;
    private BigDecimal balance;


    public AccountDTO(BigDecimal balance, String owner, String accountNumber) {
        this.balance = balance;
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return id;
    }



    public String getAccountNumber() {
        return accountNumber;
    }


    public String getOwner() {
        return owner;
    }


    public BigDecimal getBalance() {
        return balance;
    }
}
