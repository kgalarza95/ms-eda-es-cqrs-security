package ec.com.sofka.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CustomerRequestDTO {

    @Size(min = 1, max = 50, message = "Aggregate ID must be between 1 and 50 characters")
    private String aggregateId;

    @NotNull(message = "Identification cannot be null")
    @NotBlank(message = "Identification cannot be empty")
    @Size(min = 1, max = 20, message = "Identification must be between 1 and 20 characters")
    private String identification;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be empty")
    @Size(min = 1, max = 200, message = "Address must be between 1 and 200 characters")
    private String address;

    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;
    public CustomerRequestDTO(String aggregateId, String identification, String firstName, String lastName, String email, String phone, String address, LocalDate birthDate) {
        this.aggregateId = aggregateId;
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
    }

    public CustomerRequestDTO() {
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAggregateId() {
        return aggregateId;
    }
}
