package ec.com.sofka.commands.account;

import ec.com.sofka.generics.utils.Command;
import ec.com.sofka.model.util.StatusEnum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CreateAccountCommand extends Command {

    private final BigDecimal balance;
    private final String numberAcc;
    private final String customerName;
    private final String status;

    private static final Set<String> generatedNumbers = new HashSet<>();
    private static final int ACCOUNT_NUMBER_LENGTH = 10;
    private static final Random random = new Random();


    public CreateAccountCommand(final String customerName, final BigDecimal balance) {
        super(null);
        this.balance = balance;
        this.numberAcc = generateUniqueAccountNumber();
        this.customerName = customerName;
        this.status = StatusEnum.ACTIVE.name();
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

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = generateRandomAccountNumber();
        } while (generatedNumbers.contains(accountNumber));

        generatedNumbers.add(accountNumber);
        return accountNumber;
    }

    private String generateRandomAccountNumber() {
        StringBuilder number = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }
}
