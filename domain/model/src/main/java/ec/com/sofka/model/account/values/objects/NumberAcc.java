package ec.com.sofka.model.account.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

//5. Creation of a value object: In Value objects is where validations must go.
//Objects values garantees the integrity of the data
public class NumberAcc implements IValueObject<String> {
    private final String value;

    private NumberAcc(final String value) {
        this.value = validate(value);
    }

    public static NumberAcc of(final String value) {
        return new NumberAcc(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    private String validate(final String value){
        return value;
    }

}
