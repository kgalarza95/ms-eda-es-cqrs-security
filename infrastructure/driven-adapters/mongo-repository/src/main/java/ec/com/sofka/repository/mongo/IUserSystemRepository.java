package ec.com.sofka.repository.mongo;

import ec.com.sofka.document.UserSystemEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IUserSystemRepository extends ReactiveMongoRepository<UserSystemEntity, String> {
    Mono<UserSystemEntity> findByEmail(String email);
}
