package ec.com.sofka;

import ec.com.sofka.generics.domain.DomainEvent;

public interface IJSONMapper {
    String writeToJson(Object obj);
    DomainEvent readFromJson(String json, Class<?> clazz);
}
