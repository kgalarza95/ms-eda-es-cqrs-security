package ec.com.sofka.model.transaction.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.math.BigDecimal;

public class Amount implements IValueObject<BigDecimal> {
    private final BigDecimal value;

    private Amount(final BigDecimal value) {
        this.value = validate(value);
    }

    public static Amount of(final BigDecimal value) {
        return new Amount(value);
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    private BigDecimal validate(final BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("The amount can't be null");
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The amount must be greater than or equal to 0");
        }

        return value;
    }
}
