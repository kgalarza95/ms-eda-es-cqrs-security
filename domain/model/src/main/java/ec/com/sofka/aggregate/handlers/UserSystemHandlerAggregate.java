package ec.com.sofka.aggregate.handlers;

import ec.com.sofka.aggregate.CustomerAggregate;
import ec.com.sofka.aggregate.UserSystemAggregate;
import ec.com.sofka.aggregate.events.TransactionCreated;
import ec.com.sofka.aggregate.events.UserSystemCreated;
import ec.com.sofka.aggregate.events.UserSystemUpdated;
import ec.com.sofka.generics.domain.DomainActionsContainer;
import ec.com.sofka.model.customer.values.objects.Email;
import ec.com.sofka.model.transaction.Transaction;
import ec.com.sofka.model.transaction.values.TransactionId;
import ec.com.sofka.model.transaction.values.objects.*;
import ec.com.sofka.model.usersystem.UserSystem;
import ec.com.sofka.model.usersystem.value.UserSystemId;
import ec.com.sofka.model.usersystem.value.objects.Firstname;
import ec.com.sofka.model.usersystem.value.objects.Lastname;
import ec.com.sofka.model.usersystem.value.objects.Password;

public class UserSystemHandlerAggregate extends DomainActionsContainer {

    public UserSystemHandlerAggregate(UserSystemAggregate userSystemAggregate) {

        addDomainActions((UserSystemCreated event) -> {
            UserSystem userSystem = new UserSystem(
                    UserSystemId.of(event.getId()),
                    Firstname.of(event.getFirstname()),
                    Lastname.of(event.getLastname()),
                    Email.of(event.getEmail()),
                    Password.of(event.getPassword()),
                    event.getRole()
            );
            userSystemAggregate.setUserSystem(userSystem);
        });

        addDomainActions((UserSystemUpdated event) -> {
            UserSystem userSystem = new UserSystem(
                    UserSystemId.of(event.getId()),
                    Firstname.of(event.getFirstname()),
                    Lastname.of(event.getLastname()),
                    Email.of(event.getEmail()),
                    Password.of(event.getPassword()),
                    event.getRole()
            );
            userSystemAggregate.setUserSystem(userSystem);
        });


    }

}
