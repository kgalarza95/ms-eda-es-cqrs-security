package ec.com.sofka.model.customer;

import ec.com.sofka.generics.utils.Entity;
import ec.com.sofka.model.account.values.objects.Status;
import ec.com.sofka.model.customer.values.CustomerID;
import ec.com.sofka.model.customer.values.objects.*;

public class Customer extends Entity<CustomerID> {

    private final Identification identification;
    private final Name firstName;
    private final Name lastName;
    private final Email email;
    private final Phone phone;
    private final Address address;
    private final BirthDate birthDate;
    private final Status status;

    public Customer(CustomerID id, Identification identification, Name firstName, Name lastName, Email email, Phone phone, Address address, BirthDate birthDate, Status status) {
        super(id);
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.status = status;
    }

    public Identification getIdentification() {
        return identification;
    }

    public Name getFirstName() {
        return firstName;
    }

    public Name getLastName() {
        return lastName;
    }

    public Email getEmail() {
        return email;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public BirthDate getBirthDate() {
        return birthDate;
    }

    public Status getStatus() {
        return status;
    }
}
