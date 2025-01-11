package ec.com.sofka.commands.account;

import ec.com.sofka.generics.utils.Command;

import java.math.BigDecimal;

//Usage of the Request class
public class UpdateAccountCommand extends Command {
    private  BigDecimal balance;
    private  String numberAcc;
    private  String customerName;
    private  String status;

    public UpdateAccountCommand(final String aggregateId, final BigDecimal balance, final String numberAcc, final String customerName, final String status) {
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
