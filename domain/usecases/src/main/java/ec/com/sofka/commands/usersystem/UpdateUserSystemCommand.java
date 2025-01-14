package ec.com.sofka.commands.usersystem;

import ec.com.sofka.generics.utils.Command;
import ec.com.sofka.model.util.RoleSystemEnum;

import java.math.BigDecimal;

public class UpdateUserSystemCommand extends Command {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RoleSystemEnum role;

    public UpdateUserSystemCommand(String aggregateId, String firstname, String lastname, String email, String password, RoleSystemEnum role) {
        super(aggregateId);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
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
