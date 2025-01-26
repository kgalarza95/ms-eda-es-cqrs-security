package ec.com.sofka.mapper;

import ec.com.sofka.document.TransactionEntity;
import ec.com.sofka.gateway.dto.TransactionDTO;
import ec.com.sofka.queries.response.transaction.GetTransactionResponse;

public class TransactionRepoMapper {

    public static GetTransactionResponse toDomainTax(TransactionEntity entity) {
        if (entity == null) {
            return null;
        }

        GetTransactionResponse transaction = new GetTransactionResponse();
        transaction.setId(entity.getId());
        transaction.setDescription(entity.getDescription());
        transaction.setAmount(entity.getAmount().toString());
        transaction.setTax(entity.getTax().toString());
        transaction.setTransactionType(entity.getTransactionType());
        transaction.setDate(entity.getDate());
        return transaction;
    }

    public static TransactionDTO toDomain(TransactionEntity entity) {
        if (entity == null) {
            return null;
        }

        TransactionDTO transaction = new TransactionDTO();
        transaction.setId(entity.getId());
        transaction.setDescription(entity.getDescription());
        transaction.setAmount(entity.getAmount());
        transaction.setTransactionType(entity.getTransactionType());
        transaction.setDate(entity.getDate());
        transaction.setAccountId(entity.getAccountId());
        return transaction;
    }

    public static TransactionEntity toEntity(TransactionDTO transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setDescription(transaction.getDescription());
        entity.setAmount(transaction.getAmount());
        entity.setTransactionType(transaction.getTransactionType());
        entity.setDate(transaction.getDate());
        entity.setAccountId(transaction.getAccountId());
        return entity;
    }
}
