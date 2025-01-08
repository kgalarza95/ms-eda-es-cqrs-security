package ec.com.sofka.model.account.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

import java.math.BigDecimal;

//4. Creation of a value object: In Value objects is where validations must go.
//Objects values garantees the integrity of the data
public class Balance implements IValueObject<BigDecimal> {
    private final BigDecimal value;

    private Balance(final BigDecimal value) {
        this.value = validate(value);
    }

    public static Balance of(final BigDecimal value) {
        return new Balance(value);
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    //hello validations
    private BigDecimal validate(final BigDecimal value){
        if(value == null){
            throw new IllegalArgumentException("The balance can't be null");
        }

        if(value.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("The balance must be greater than 0");
        }


        return value;
    }
}
