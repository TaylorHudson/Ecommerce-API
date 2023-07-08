package br.com.compassuol.pb.challenge.msnotification.domain.consumer;

import br.com.compassuol.pb.challenge.msnotification.application.service.EmailService;
import br.com.compassuol.pb.challenge.msnotification.domain.dto.request.EmailRequest;
import br.com.compassuol.pb.challenge.msnotification.domain.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static br.com.compassuol.pb.challenge.msnotification.domain.utils.EmailUtil.createEmailModel;

@Component
@AllArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = {"${queue.name}"})
    public void listen(@Payload String string) throws JsonProcessingException {
        var emailRequest = Utils.mapToClass(string, EmailRequest.class);
        var emailModel = createEmailModel(emailRequest);
        emailService.sendChangedRegistrationMessage(emailModel);
    }

}