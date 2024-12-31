package ec.com.sofka.request;

import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

//Usage of the Request class
public class CreateAccountRequest extends Request
{
    private final BigDecimal balance;
    private final String numberAcc;
    private final String customerName;

    public CreateAccountRequest(final BigDecimal balance, final String numberAcc, final String customerName) {
        super(null);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.customerName = customerName;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public String getNumber() {
        return numberAcc;
    }

    public String getCustomerName() {
        return customerName;
    }

}
