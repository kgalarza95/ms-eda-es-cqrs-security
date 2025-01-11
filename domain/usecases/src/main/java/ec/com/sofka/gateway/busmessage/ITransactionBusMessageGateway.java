package ec.com.sofka.gateway.busmessage;

import ec.com.sofka.gateway.dto.LogDTO;
import ec.com.sofka.generics.domain.DomainEvent;

public interface ITransactionBusMessageGateway {

    void sendMsg(DomainEvent event);

}
