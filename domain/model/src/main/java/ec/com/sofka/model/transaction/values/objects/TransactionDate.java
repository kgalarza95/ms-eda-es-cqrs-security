package ec.com.sofka.model.transaction.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.time.LocalDate;

public class TransactionDate implements IValueObject<LocalDate> {
    private final LocalDate value;

    private TransactionDate(final LocalDate value) {
        this.value = validate(value);
    }

    public static TransactionDate of(final LocalDate value) {
        return new TransactionDate(value);
    }

    @Override
    public LocalDate getValue() {
        return this.value;
    }

    private LocalDate validate(final LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("The date can't be null");
        }

        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The date can't be in the future");
        }

        return value;
    }
}
