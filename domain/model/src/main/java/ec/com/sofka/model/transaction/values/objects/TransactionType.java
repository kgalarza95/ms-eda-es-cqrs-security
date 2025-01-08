package ec.com.sofka.model.transaction.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class TransactionType implements IValueObject<String> {
    private final String value;

    private TransactionType(final String value) {
        this.value = validate(value);
    }

    public static TransactionType of(final String value) {
        return new TransactionType(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private String validate(final String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The transaction type can't be null or empty");
        }

        return value.trim().toUpperCase();
    }
}
