package ec.com.sofka.aggregate.events;

import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.model.util.RoleSystemEnum;
import ec.com.sofka.aggregate.events.util.UserSystemEventsEnum;

public class UserSystemCreated extends DomainEvent {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RoleSystemEnum role;

    public UserSystemCreated(String id, String firstname, String lastname, String email, String password, RoleSystemEnum role) {
        super(UserSystemEventsEnum.USER_SYSTEM_CREATED.name());
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserSystemCreated( ) {
        super(UserSystemEventsEnum.USER_SYSTEM_CREATED.name());
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RoleSystemEnum getRole() {
        return role;
    }
}
