package ec.com.sofka.generics.interfaces;

import ec.com.sofka.generics.utils.Request;

import java.util.List;

public interface IUseCaseGet <R> {
    List<R> get();
}
