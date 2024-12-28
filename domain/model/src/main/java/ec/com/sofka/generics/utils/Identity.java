package ec.com.sofka.generics.utils;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.util.Objects;
import java.util.UUID;

//2. Generics creation to apply DDD: Identity
/*You implement IValueObject bc this abstract class when it is instantiated through
inheritance in a AR or Entity you will have the need to get the value*/
public abstract class Identity implements IValueObject<String> {
    private final String value;

    //Why protected? Because you want to use just it in the AR or Entity
    protected Identity() {
        this.value = UUID.randomUUID().toString();
    }

    //Why another one? Because maybe you want to establish your own logic to create the value
    protected Identity(final String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    //Other methods that can be useful
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identity identity = (Identity) o;
        return Objects.equals(value, identity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
