package ec.com.sofka.gateway;

import ec.com.sofka.gateway.dto.AccountDTO;

import java.util.List;

public interface AccountRepository {
    List<AccountDTO> findAll();
    AccountDTO findByAcccountId(String id);
    AccountDTO findByNumber(String number);
    AccountDTO save(AccountDTO account);
    AccountDTO update(AccountDTO account);
    AccountDTO delete(AccountDTO account);
}
