package ec.com.sofka.generics.interfaces;

import ec.com.sofka.generics.utils.Request;
import reactor.core.publisher.Mono;

//9. Generics creation to apply DDD: IUseCase - Interface to execute use cases
public interface IUseCaseExecute<T extends Request, R> {
    Mono<R> execute(T request);
}
