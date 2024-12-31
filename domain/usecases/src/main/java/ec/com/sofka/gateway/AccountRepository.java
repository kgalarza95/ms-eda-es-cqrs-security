package ec.com.sofka.gateway;

import ec.com.sofka.account.Account;
import ec.com.sofka.gateway.dto.AccountDTO;

public interface AccountRepository {
    AccountDTO findByAcccountId(String id);
    AccountDTO save(AccountDTO account);
}
