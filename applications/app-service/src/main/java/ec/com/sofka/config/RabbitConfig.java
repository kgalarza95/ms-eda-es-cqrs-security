package ec.com.sofka.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${amqp.exchange.default}")
    public String EXCHANGE_NAME;
    @Value("${amqp.queue.default}")
    public String QUEUE_NAME;
    @Value("${amqp.routing.key.default}")
    public String ROUTING_KEY;

    @Value("${amqp.exchange.account}")
    private String EXCHANGE_ACCOUNT;
    @Value("${amqp.queue.account}")
    private String QUEUE_ACCOUNT;
    @Value("${amqp.routing.key.account}")
    private String ROUTINGKEY_ACCOUNT;

    @Value("${amqp.exchange.customer}")
    private String EXCHANGE_CUSTOMER;
    @Value("${amqp.queue.customer}")
    private String QUEUE_CUSTOMER;
    @Value("${amqp.routing.key.customer}")
    private String ROUTINGKEY_CUSTOMER;

    @Value("${amqp.exchange.transaction}")
    private String EXCHANGE_TRANSACTION;
    @Value("${amqp.queue.transaction}")
    private String QUEUE_TRANSACTION;
    @Value("${amqp.routing.key.transaction}")
    private String ROUTINGKEY_TRANSACTION;

    @Bean(name = "defaultExchange")
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean(name = "defaultQueue")
    public Queue defaultQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding defaultBinding(@Qualifier("defaultQueue") Queue queue, @Qualifier("defaultExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean(name = "accountExchange")
    public DirectExchange accountExchange() {
        return new DirectExchange(EXCHANGE_ACCOUNT);
    }

    @Bean(name = "accountQueue")
    public Queue accountQueue() {
        return new Queue(QUEUE_ACCOUNT, true);
    }

    @Bean
    public Binding accountBinding(@Qualifier("accountQueue") Queue queue, @Qualifier("accountExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_ACCOUNT);
    }

    @Bean(name = "customerExchange")
    public DirectExchange customerExchange() {
        return new DirectExchange(EXCHANGE_CUSTOMER);
    }

    @Bean(name = "customerQueue")
    public Queue customerQueue() {
        return new Queue(QUEUE_CUSTOMER, true);
    }

    @Bean
    public Binding customerBinding(@Qualifier("customerQueue") Queue queue, @Qualifier("customerExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_CUSTOMER);
    }

    @Bean(name = "transactionExchange")
    public DirectExchange transactionExchange() {
        return new DirectExchange(EXCHANGE_TRANSACTION);
    }

    @Bean(name = "transactionQueue")
    public Queue transactionQueue() {
        return new Queue(QUEUE_TRANSACTION, true);
    }

    @Bean
    public Binding transactionBinding(@Qualifier("transactionQueue") Queue queue, @Qualifier("transactionExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_TRANSACTION);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // AMQP Template: For consuming messages
    @Bean
    public AmqpTemplate rabbitTemplateBean(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
