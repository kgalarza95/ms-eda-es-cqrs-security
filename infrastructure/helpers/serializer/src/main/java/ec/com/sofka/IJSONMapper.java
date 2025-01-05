package ec.com.sofka;

import ec.com.sofka.generics.domain.DomainEvent;

public interface IJSONMapper {
    String writeToJson(Object obj);
    Object readFromJson(String json, Class<?> clazz);
}
