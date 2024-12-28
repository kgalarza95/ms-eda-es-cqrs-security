package ec.com.sofka.generics.interfaces;

//1. Generics creation to apply DDD: ValueObject
public interface IValueObject<T> {
    T getValue();
}
