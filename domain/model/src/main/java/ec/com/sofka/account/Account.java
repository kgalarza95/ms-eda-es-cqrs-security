package ec.com.sofka.account;

import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.Balance;
import ec.com.sofka.account.values.objects.Name;
import ec.com.sofka.account.values.objects.NumberAcc;
import ec.com.sofka.generics.utils.Entity;

//4. Creation of an Entity class - They have logic and behavior, otherwise is a ValueObject.
public class Account extends Entity<AccountId> {
    private final Balance balance;
    private final NumberAcc numberAcc;
    private final Name name;

    public Account(AccountId id, Balance balance, NumberAcc numberAcc, Name name) {
        super(id);
        this.balance = balance;
        this.numberAcc = numberAcc;
        this.name = name;
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


}
