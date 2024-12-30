package ec.com.sofka.mapper;

import ec.com.sofka.account.Account;
import ec.com.sofka.account.values.AccountId;
import ec.com.sofka.account.values.objects.Balance;
import ec.com.sofka.account.values.objects.Name;
import ec.com.sofka.account.values.objects.NumberAcc;
import ec.com.sofka.data.AccountEntity;

public class AccountMapper {
    public static AccountEntity toEntity(Account account) {
        return new AccountEntity(account.getBalance().getValue(),
                account.getName().getValue(),
                account.getNumber().getValue()
                );
    }

    public static Account toDomain(AccountEntity accountEntity) {
        return new Account(
                AccountId.of(accountEntity.getId()),
                Balance.of(accountEntity.getBalance()),
                NumberAcc.of(accountEntity.getAccountNumber()),
                Name.of(accountEntity.getOwner())
        );
    }
}
