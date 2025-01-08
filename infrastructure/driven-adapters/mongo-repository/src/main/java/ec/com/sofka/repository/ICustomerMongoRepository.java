package ec.com.sofka.repository;

import ec.com.sofka.document.ClientEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerMongoRepository extends ReactiveMongoRepository<ClientEntity, String> {

}
