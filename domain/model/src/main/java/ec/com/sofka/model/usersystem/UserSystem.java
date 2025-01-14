package ec.com.sofka.model.usersystem;

import ec.com.sofka.model.customer.values.objects.Email;
import ec.com.sofka.model.usersystem.value.UserSystemId;
import ec.com.sofka.generics.utils.Entity;
import ec.com.sofka.model.usersystem.value.objects.Firstname;
import ec.com.sofka.model.usersystem.value.objects.Lastname;
import ec.com.sofka.model.usersystem.value.objects.Password;
import ec.com.sofka.model.util.RoleSystemEnum;

public class UserSystem extends Entity<UserSystemId> {

    private Firstname firstname;
    private Lastname lastname;
    private Email email;
    private Password password;
    private RoleSystemEnum role;

    public UserSystem(UserSystemId id, Firstname firstname, Lastname lastname, Email email, Password password, RoleSystemEnum role) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Firstname getFirstname() {
        return firstname;
    }

    public Lastname getLastname() {
        return lastname;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public RoleSystemEnum getRole() {
        return role;
    }
}
