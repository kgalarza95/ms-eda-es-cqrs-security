package ec.com.sofka.model.transaction.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class Description implements IValueObject<String> {

    private final String value;

    private Description(final String value) {
        this.value = validate(value);
    }

    public static Description of(final String value) {
        return new Description(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private String validate(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The description can't be null or empty");
        }

        if (value.length() > 500) {
            throw new IllegalArgumentException("The description can't exceed 500 characters");
        }

        return value.trim();
    }
}
