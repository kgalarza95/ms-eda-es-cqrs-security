package ec.com.sofka.generics.interfaces;

import reactor.core.publisher.Flux;

public interface IUseCaseGet <R> {
    Flux<R> get();
}
