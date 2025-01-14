package ec.com.sofka.aggregate.values;

import ec.com.sofka.generics.utils.Identity;

public class UserSystemId extends Identity {

    public UserSystemId() {
        super();
    }

    private UserSystemId(final String id) {
        super(id);
    }

    public static UserSystemId of(final String id) {
        return new UserSystemId(id);
    }
}
