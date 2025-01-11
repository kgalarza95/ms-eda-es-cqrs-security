package ec.com.sofka.commands.responses.customer;

import java.time.LocalDate;

public class UpdateCustomerResponse {
    private String id;
    private String identification;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private String status;

    public UpdateCustomerResponse(String id, String identification, String firstName, String lastName, String email, String phone, String address, LocalDate birthDate, String status) {
        this.id = id;
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.status = status;
    }

    public String getId() {
        return id;
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
