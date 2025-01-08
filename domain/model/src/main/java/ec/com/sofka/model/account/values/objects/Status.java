package ec.com.sofka.model.account.values.objects;

import ec.com.sofka.generics.interfaces.IValueObject;

public class Status implements IValueObject<String> {
    private final String value;

    private Status(final String value) {
        this.value = validate(value);
    }

    public static Status of(final String value) {
        return new Status(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    private String validate(final String value){
        if(value == null){
            throw new IllegalArgumentException("The status can't be null");
        }

        if(value.isBlank()){
            throw new IllegalArgumentException("The status can't be empty");
        }

        if(value.length() < 3){
            throw new IllegalArgumentException("The status must have at least 3 characters");
        }

        return value;
    }
}
