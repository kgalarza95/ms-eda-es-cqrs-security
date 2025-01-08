package ec.com.sofka.model.transaction.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class AccountId implements IValueObject<String> {

    private final String value;

    private AccountId(final String value) {
        this.value = validate(value);
    }

    public static AccountId of(final String value) {
        return new AccountId(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private String validate(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The account ID can't be null or empty");
        }

        if (!value.matches("^[A-Za-z0-9_-]+$")) {
            throw new IllegalArgumentException("The account ID must contain only alphanumeric characters, dashes, or underscores");
        }

        return value.trim();
    }
}
