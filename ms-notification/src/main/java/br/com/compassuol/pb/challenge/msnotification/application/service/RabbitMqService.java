package br.com.compassuol.pb.challenge.msnotification.application.service;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqService {

    @Value("${exchange.name}")
    private String exchange;

    @Value("${routing.key}")
    private String routingKey;

    @Value("${queue.name}")
    private String queueName;

    private final AmqpAdmin amqpAdmin;

    public RabbitMqService(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange(String exchangeName) {
        return new DirectExchange(exchangeName);
    }

    private Binding binding(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), routingKey, null);
    }

    @PostConstruct
    private void init() {
        var emailQueue = queue(queueName);

        var directExchange = directExchange(exchange);

        var emailQueueBinding = binding(emailQueue, directExchange);

        amqpAdmin.declareQueue(emailQueue);
        amqpAdmin.declareExchange(directExchange);
        amqpAdmin.declareBinding(emailQueueBinding);
    }

}
