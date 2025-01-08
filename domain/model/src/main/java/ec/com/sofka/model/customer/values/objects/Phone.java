package ec.com.sofka.model.customer.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class Phone implements IValueObject<String> {
    private final String value;

    private Phone(final String value) {
        this.value = validate(value);
    }

    public static Phone of(final String value) {
        return new Phone(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private String validate(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The phone number can't be null or empty");
        }

        if (!value.matches("\\+?\\d{10,15}")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        return value.trim();
    }
}
