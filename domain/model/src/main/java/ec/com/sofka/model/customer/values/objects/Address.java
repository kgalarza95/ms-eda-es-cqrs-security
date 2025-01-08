package ec.com.sofka.model.customer.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class Address implements IValueObject<String> {

    private final String value;

    private Address(final String value) {
        this.value = validate(value);
    }

    public static Address of(final String value) {
        return new Address(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private String validate(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The address can't be null or empty");
        }

        if (value.length() > 255) {
            throw new IllegalArgumentException("The address can't exceed 255 characters");
        }

        return value.trim();
    }
}
