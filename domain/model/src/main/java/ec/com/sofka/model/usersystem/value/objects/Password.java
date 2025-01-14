package ec.com.sofka.model.usersystem.value.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class Password implements IValueObject<String> {

    private final String value;

    private Password(final String value) {
        this.value = validate(value);
    }

    public static Password of(final String value) {
        return new Password(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getRawValue() {
        return this.value;
    }

    private String validate(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The password can't be null or empty");
        }

        if (value.length() < 8) {
            throw new IllegalArgumentException("The password must be at least 8 characters long");
        }

        return value;
    }

}
