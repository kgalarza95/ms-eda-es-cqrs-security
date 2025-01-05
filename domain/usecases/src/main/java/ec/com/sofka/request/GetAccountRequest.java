package ec.com.sofka.request;

import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

//Usage of the Request class
public class GetAccountRequest extends Request {
    private final BigDecimal balance;
    private final String numberAcc;
    private final String customerName;


    public GetAccountRequest(final String aggregateId, final String numberAcc) {
        super(aggregateId);
        this.balance = null;
        this.numberAcc = numberAcc;
        this.customerName = null;
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
