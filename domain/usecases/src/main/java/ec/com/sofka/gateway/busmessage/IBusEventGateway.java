package ec.com.sofka.gateway.busmessage;

import ec.com.sofka.generics.domain.DomainEvent;

public interface IBusEventGateway {
    void sendEvent(DomainEvent event);
}
