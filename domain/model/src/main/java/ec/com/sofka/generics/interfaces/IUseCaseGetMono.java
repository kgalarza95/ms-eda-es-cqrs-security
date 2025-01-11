package ec.com.sofka.generics.interfaces;

import ec.com.sofka.generics.utils.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUseCaseGetMono<Q extends Query, R>  {
    Mono<R> get(Q request);
}
