package br.com.compassuol.pb.challenge.msauth.application.service;

import br.com.compassuol.pb.challenge.msauth.domain.dto.response.EmailResponse;
import br.com.compassuol.pb.challenge.msauth.domain.model.UserModel;
import br.com.compassuol.pb.challenge.msauth.domain.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PublisherEmailService {

    private final RabbitTemplate rabbitTemplate;

    public PublisherEmailService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendChangedRegistrationMessage(UserModel user) {
        var email = EmailResponse.builder()
                .replyTo(user.getEmail())
                .to(user.getEmail())
                .build();
        String json = null;
        try {
            json = Utils.mapToString(email);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String directType = "Direct-Exchange";
        String routingKey = "email";
        rabbitTemplate.convertAndSend(directType, routingKey, json);
    }

}
