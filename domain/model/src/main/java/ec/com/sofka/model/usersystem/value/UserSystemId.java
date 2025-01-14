package ec.com.sofka.model.usersystem.value;

import ec.com.sofka.generics.utils.Entity;
import ec.com.sofka.generics.utils.Identity;

public class UserSystemId extends Identity {

    public UserSystemId() {
        super();
    }

    public UserSystemId(final String id) {
        super(id);
    }

    public static UserSystemId of(final String id) {
        return new UserSystemId(id);
    }

}
