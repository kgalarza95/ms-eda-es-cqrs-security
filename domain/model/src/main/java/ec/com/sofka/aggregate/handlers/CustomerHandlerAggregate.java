package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.events.CustomerCreated;
import ec.com.sofka.aggregate.events.CustomerUpdated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.model.account.values.objects.Status;
import ec.com.sofka.model.customer.values.objects.Name;
import ec.com.sofka.model.customer.Customer;
import ec.com.sofka.model.customer.values.CustomerID;
import ec.com.sofka.model.customer.values.objects.*;

public class CustomerHandlerAggregate extends DomainActionsContainer {

    public CustomerHandlerAggregate(CustomerAggregate customerAggregate) {

        addDomainActions((CustomerCreated event) -> {
            Customer customer = new Customer(
                    CustomerID.of(event.getId()),
                    Identification.of(event.getIdentification()),
                    Name.of(event.getFirstName()),
                    Name.of(event.getLastName()),
                    Email.of(event.getEmail()),
                    Phone.of(event.getPhone()),
                    Address.of(event.getAddress()),
                    BirthDate.of(event.getBirthDate()),
                    Status.of(event.getStatus())
            );
            customerAggregate.setCustomer(customer);
        });

        addDomainActions((CustomerUpdated event) -> {
            Customer customer = new Customer(
                    CustomerID.of(event.getId()),
                    Identification.of(event.getIdentification()),
                    Name.of(event.getFirstName()),
                    Name.of(event.getLastName()),
                    Email.of(event.getEmail()),
                    Phone.of(event.getPhone()),
                    Address.of(event.getAddress()),
                    BirthDate.of(event.getBirthDate()),
                    Status.of(event.getStatus())
            );
            customerAggregate.setCustomer(customer);
        });

    }

}
