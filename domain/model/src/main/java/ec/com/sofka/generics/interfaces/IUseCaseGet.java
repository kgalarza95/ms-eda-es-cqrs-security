package ec.com.sofka.generics.interfaces;

import ec.com.sofka.generics.utils.Request;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IUseCaseGet <R> {
    Flux<R> get();
}
