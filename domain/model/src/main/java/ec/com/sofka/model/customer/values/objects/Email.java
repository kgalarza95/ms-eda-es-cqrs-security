package ec.com.sofka.model.customer.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class Email implements IValueObject<String> {
    private final String value;

    private Email(final String value) {
        this.value = validate(value);
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private String validate(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The email can't be null or empty");
        }

        if (!value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return value.trim();
    }
}
