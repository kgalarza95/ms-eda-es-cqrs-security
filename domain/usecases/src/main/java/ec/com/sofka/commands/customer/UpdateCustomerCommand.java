package ec.com.sofka.commands.customer;

import ec.com.sofka.generics.utils.Command;
import ec.com.sofka.model.util.StatusEnum;

import java.time.LocalDate;

//Usage of the Request class
public class UpdateCustomerCommand extends Command {

    private String identification;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private String status;


    public UpdateCustomerCommand(final String aggregateId, String identification, String firstName, String lastName, String email, String phone, String address, LocalDate birthDate) {
        super(aggregateId);
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.status = StatusEnum.ACTIVE.name();
    }

    public UpdateCustomerCommand(final String aggregateId) {
        super(aggregateId);
    }

    public String getIdentification() {
        return identification;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getStatus() {
        return status;
    }
}
