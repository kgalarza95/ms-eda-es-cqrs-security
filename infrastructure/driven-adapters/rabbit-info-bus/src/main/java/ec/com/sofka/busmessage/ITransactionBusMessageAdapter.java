package ec.com.sofka.busmessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ec.com.sofka.gateway.busmessage.ITransactionBusMessageGateway;
import ec.com.sofka.gateway.dto.LogDTO;
import ec.com.sofka.generics.domain.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ITransactionBusMessageAdapter implements ITransactionBusMessageGateway {

    @Value("${amqp.exchange.transaction}")
    private String exchange;

    @Value("${amqp.routing.key.transaction}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public ITransactionBusMessageAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMsg(DomainEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
