package ec.com.sofka.aggregate.values;

import ec.com.sofka.generics.utils.Identity;

//1. Creation of the ID value object: All Identity classes must follow the same structure
public class CustomerId extends Identity {
    //To initialize with the UUID
    public CustomerId() {
        super();
    }

    //For reconstruction - but wtf why private??
    private CustomerId(final String id) {
        super(id);
    }

    //who tf are you?? I am the method to access/make instances the id with the private modifier :D
    public static CustomerId of(final String id) {
        return new CustomerId(id);
    }
}
