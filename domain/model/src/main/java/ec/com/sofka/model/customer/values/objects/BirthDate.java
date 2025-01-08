package ec.com.sofka.model.customer.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.time.LocalDate;

public class BirthDate implements IValueObject<LocalDate> {
    private final LocalDate value;

    private BirthDate(final LocalDate value) {
        this.value = validate(value);
    }

    public static BirthDate of(final LocalDate value) {
        return new BirthDate(value);
    }

    @Override
    public LocalDate getValue() {
        return this.value;
    }

    private LocalDate validate(final LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("The birth date can't be null");
        }

        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The birth date can't be in the future");
        }

        return value;
    }
}
