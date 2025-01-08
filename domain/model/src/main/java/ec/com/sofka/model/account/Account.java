package ec.com.sofka.model.account;

import ec.com.sofka.model.account.values.AccountId;
import ec.com.sofka.model.account.values.objects.Balance;
import ec.com.sofka.model.account.values.objects.Name;
import ec.com.sofka.model.account.values.objects.NumberAcc;
import ec.com.sofka.model.account.values.objects.Status;
import ec.com.sofka.generics.utils.Entity;

//4. Creation of an Entity class - They have logic and behavior, otherwise is a ValueObject.
public class Account extends Entity<AccountId> {
    private final Balance balance;
    private final NumberAcc numberAcc;
    private final Name name;
    private final Status status;

    public Account(AccountId id,  NumberAcc numberAcc, Name name, Balance balance, Status status) {
        super(id);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.name = name;
        this.status = status;
    }



    public Balance getBalance() {
        return balance;
    }

    public NumberAcc getNumber() {
        return numberAcc;
    }
    public Name getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }


}
