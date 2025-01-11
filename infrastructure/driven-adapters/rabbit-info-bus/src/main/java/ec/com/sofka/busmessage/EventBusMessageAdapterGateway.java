package ec.com.sofka.busmessage;

import ec.com.sofka.gateway.busmessage.IBusEventGateway;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


//11. BusMessage implementation, this is a service so, don't forget the annotation
@Service
public class EventBusMessageAdapterGateway implements IBusEventGateway {

    //13. Use of RabbitTemplate to define the sendMsg method
    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.exchange.event}")
    private String exchange;

    @Value("${amqp.routing.key.event}")
    private String routingKey;

    public EventBusMessageAdapterGateway(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendEvent(DomainEvent event) {
        rabbitTemplate.convertAndSend(exchange,
                routingKey,
                event);
    }


}
