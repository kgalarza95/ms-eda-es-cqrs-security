package ec.com.sofka.adapter;

import ec.com.sofka.document.AccountEntity;
import ec.com.sofka.repository.mongo.IAccountMongoRepository;
import ec.com.sofka.gateway.IAccountRepositoryGateway;
import ec.com.sofka.gateway.dto.AccountDTO;
import ec.com.sofka.mapper.AccountRepoMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountMongoAdapterGateway implements IAccountRepositoryGateway {

    private final IAccountMongoRepository repository;
    private final ReactiveMongoTemplate accountReactiveMongoTemplate;

    public AccountMongoAdapterGateway(IAccountMongoRepository repository, ReactiveMongoTemplate accountReactiveMongoTemplate) {
        this.repository = repository;
        this.accountReactiveMongoTemplate = accountReactiveMongoTemplate;
    }


    @Override
    public Flux<AccountDTO> findAll() {
        return repository.findAll()
                .map(AccountRepoMapper::toDTO);
    }

    @Override
    public Mono<AccountDTO> findByAccountId(String id) {
        return repository.findById(id)
                .map(AccountRepoMapper::toDTO);
    }

    @Override
    public Mono<AccountDTO> findByNumber(String number) {
        return repository.findByAccountNumber(number)
                .map(AccountRepoMapper::toDTO);
    }

    @Override
    public Mono<AccountDTO> save(AccountDTO account) {
        return Mono.just(account)
                .map(AccountRepoMapper::toEntity)
                .flatMap(repository::save)
                .map(AccountRepoMapper::toDTO);
    }

    @Override
    public Mono<AccountDTO> update(AccountDTO accountDTO) {
        AccountEntity entity = AccountRepoMapper.toEntity(accountDTO);

        return repository.findByAccountId(entity.getAccountId())
                .flatMap(existingAccount -> {
                    existingAccount.setName(accountDTO.getName());
                    existingAccount.setAccountNumber(accountDTO.getAccountNumber());
                    existingAccount.setBalance(accountDTO.getBalance());
                    existingAccount.setStatus(accountDTO.getStatus());

                    return repository.save(existingAccount)
                            .map(AccountRepoMapper::toDTO);
                });
    }

    @Override
    public Mono<Void> delete(AccountDTO accountDTO) {
        AccountEntity entity = AccountRepoMapper.toEntity(accountDTO);

        return repository.findByAccountId(entity.getAccountId())
                .flatMap(existingAccount -> {
                    return repository.delete(existingAccount).then();
                });
    }


}
