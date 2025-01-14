package ec.com.sofka.aggregate;

import ec.com.sofka.aggregate.events.*;
import ec.com.sofka.aggregate.handlers.UserSystemHandlerAggregate;
import ec.com.sofka.aggregate.values.UserSystemId;
import ec.com.sofka.generics.domain.DomainEvent;
import ec.com.sofka.generics.utils.AggregateRoot;
import ec.com.sofka.model.usersystem.UserSystem;
import ec.com.sofka.model.util.RoleSystemEnum;
import java.util.List;

public class UserSystemAggregate extends AggregateRoot<UserSystemId> {

    private UserSystem userSystem;

    public UserSystemAggregate() {
        super(new UserSystemId());
        addSubscription();
    }

    private UserSystemAggregate(final String id) {
        super(UserSystemId.of(id));
        addSubscription();
    }

    private void addSubscription() {
        setSubscription(new UserSystemHandlerAggregate(this));
    }

    public UserSystem getUserSystem() {
        return userSystem;
    }

    public void setUserSystem(UserSystem userSystem) {
        this.userSystem = userSystem;
    }


    public void createUserSystem( String firstname, String lastname, String email, String password, RoleSystemEnum role) {
        addEvent(new UserSystemCreated(new UserSystemId().getValue(), firstname, lastname, email, password, role)).apply();
    }

    public void updateUserSystem(String id, String firstname, String lastname, String email, String password, RoleSystemEnum role) {
        addEvent(new UserSystemUpdated(id, firstname, lastname, email, password, role)).apply();
    }


    public static UserSystemAggregate from(final String id, List<DomainEvent> events) {
        UserSystemAggregate aggregate = new UserSystemAggregate(id);
        events.stream()
                .filter(event -> id.equals(event.getAggregateRootId()))
                .reduce((first, second) -> second)
                .ifPresent(event -> aggregate.addEvent(event).apply());
        aggregate.markEventsAsCommitted();
        return aggregate;
    }

}
