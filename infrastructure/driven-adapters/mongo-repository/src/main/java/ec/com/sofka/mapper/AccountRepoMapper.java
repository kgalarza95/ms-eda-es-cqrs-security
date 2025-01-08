package ec.com.sofka.mapper;

import ec.com.sofka.document.AccountEntity;
import ec.com.sofka.gateway.dto.AccountDTO;

public class AccountRepoMapper {
    public static AccountEntity toEntity(AccountDTO accountDTO) {
        return new AccountEntity(
                accountDTO.getId(),
                accountDTO.getName(),
                accountDTO.getAccountNumber(),
                accountDTO.getBalance(),
                accountDTO.getStatus()
                );
    }

    public static AccountDTO toDTO(AccountEntity accountEntity) {
        return new AccountDTO(
                accountEntity.getAccountId(),
                accountEntity.getName(),
                accountEntity.getAccountNumber(),
                accountEntity.getBalance(),
                accountEntity.getStatus()

        );
    }
}
