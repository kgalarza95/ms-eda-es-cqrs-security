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

    //hello validations: They can be translated to their own class
    private String validate(final String value){
//        if(value == null){
//            throw new IllegalArgumentException("The number can't be null");
//        }
//
//        if(value.isBlank()){
//            throw new IllegalArgumentException("The number can't be empty");
//        }
//
//        if(value.length() != 9){
//            throw new IllegalArgumentException("The number must have 9 characters");
//        }
//
//        if (!value.matches("[0-9]+")) {
//            throw new IllegalArgumentException("The number must be numeric");
//        }

        return value;
    }

}
