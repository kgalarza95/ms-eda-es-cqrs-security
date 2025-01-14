package ec.com.sofka.model.usersystem.value.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class Firstname implements IValueObject<String> {
    private final String value;

    private Firstname(final String value) {
        this.value = validate(value);
    }

    public static Firstname of(final String value) {
        return new Firstname(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private String validate(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The firstname can't be null or empty");
        }

        if (value.length() > 50) {
            throw new IllegalArgumentException("The firstname can't exceed 50 characters");
        }

        return value.trim();
    }
}