package ec.com.sofka.busmessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ec.com.sofka.gateway.busmessage.IAccountBusMessageGateway;
import ec.com.sofka.gateway.dto.LogDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IAccountBusMessageAdapter implements IAccountBusMessageGateway {

    @Value("${amqp.exchange.account}")
    private String exchange;

    @Value("${amqp.routing.key.account}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public IAccountBusMessageAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMsg(DomainEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
