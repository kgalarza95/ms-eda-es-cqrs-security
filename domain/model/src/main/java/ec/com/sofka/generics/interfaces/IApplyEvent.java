package ec.com.sofka.generics.interfaces;

import ec.com.sofka.generics.domain.DomainEvent;

//7. Generics creation to apply DDD: IApplyEvent - Interface to apply events
@FunctionalInterface
public interface IApplyEvent {
    void apply();
}
