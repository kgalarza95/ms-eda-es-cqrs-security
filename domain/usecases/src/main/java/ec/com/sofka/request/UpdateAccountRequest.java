package ec.com.sofka.request;

import ec.com.sofka.account.values.AccountEnum;
import ec.com.sofka.generics.utils.Request;

import java.math.BigDecimal;

//Usage of the Request class
public class UpdateAccountRequest extends Request {
    private final BigDecimal balance;
    private final String numberAcc;
    private final String customerName;
    private final String status;

    public UpdateAccountRequest(final String aggregateId, final BigDecimal balance, final String numberAcc, final String customerName, final String status) {
        super(aggregateId);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.customerName = customerName;
        this.status = status;
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

    public String getStatus() {
        return status;
    }
}
