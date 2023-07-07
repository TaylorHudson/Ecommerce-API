package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.EmailResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.UserModel;
import br.com.compassuol.pb.challenge.msproducts.domain.utils.Utils;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.MessageCouldNotBeSentToQueueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PublisherEmailService {

    @Value("${exchange.name}")
    private String exchange;

    @Value("${routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public PublisherEmailService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendChangedRegistrationMessage(UserModel user) {

        var email = EmailResponse.builder()
                .replyTo(user.getEmail())
                .to(user.getEmail())
                .build();
        try {
            String json = Utils.mapToString(email);
            rabbitTemplate.convertAndSend(exchange, routingKey, json);
        }catch (Exception e) {
            throw new MessageCouldNotBeSentToQueueException();
        }
    }

}
