package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "bank_account")
public class AccountEntity {
    @Id
    private String id;

    @Field("account_id")
    private String accountId;

    @Field("account_number")
    private String accountNumber;

    @Field("account_holder")
    private String name;

    @Field("global_balance")
    private BigDecimal balance;

    @Field("status_account")
    private String status;

    public AccountEntity(String accountId, String name, String accountNumber, BigDecimal balance,  String status) {
        this.accountId = accountId;
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }

    public AccountEntity(String id, String accountId, String name, String accountNumber, BigDecimal balance,  String status) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }

    public AccountEntity(){
        
    }

    public String getAccountId() {
        return accountId;
    }

    public String getId() {
        return id;
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

