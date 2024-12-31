package ec.com.sofka.generics.interfaces;

import ec.com.sofka.generics.utils.Request;
//9. Generics creation to apply DDD: IUseCase - Interface to execute use cases
public interface IUseCase<T extends Request, R> {
    R execute(T request);
}
