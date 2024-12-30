package ec.com.sofka.data;

import java.math.BigDecimal;

public class RequestDTO {
    public String customer;
    public String account;
    public BigDecimal balance;

    public RequestDTO(String customer, String account, BigDecimal balance) {
        this.customer = customer;
        this.account = account;
        this.balance = balance;
    }


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
