package ec.com.sofka.data;

import java.math.BigDecimal;

public class RequestDTO {
    public String customerId;
    //Name
    public String customer;
    //NumAcc
    public String account;
    public BigDecimal balance;

    public RequestDTO(String customerId, String customer, String account, BigDecimal balance) {
        this.customerId = customerId;
        this.customer = customer;
        this.account = account;
        this.balance = balance;
    }


    public String getCustomer() {
        return customer;
    }

    public String getAccount() {
        return account;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public String getCustomerId() {
        return customerId;
    }


}
