package br.com.compassuol.pb.challenge.msnotification.domain.producer;

import br.com.compassuol.pb.challenge.msnotification.domain.dto.request.EmailRequest;
import br.com.compassuol.pb.challenge.msnotification.domain.utils.Utils;
import br.com.compassuol.pb.challenge.msnotification.framework.exception.MessageCouldNotBeSentToQueueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {

    @Value("${exchange.name}")
    private String exchange;

    @Value("${routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendChangedRegistrationMessageToQueue(EmailRequest request) {
        try {
            String json = Utils.mapToString(request);
            rabbitTemplate.convertAndSend(exchange, routingKey, json);
        }catch (Exception e) {
            throw new MessageCouldNotBeSentToQueueException();
        }
    }

}
