package br.com.compassuol.pb.challenge.msnotification.domain.consumer;

import br.com.compassuol.pb.challenge.msnotification.application.service.EmailService;
import br.com.compassuol.pb.challenge.msnotification.domain.dto.request.EmailRequest;
import br.com.compassuol.pb.challenge.msnotification.domain.model.EmailModel;
import br.com.compassuol.pb.challenge.msnotification.domain.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailConsumerTest {

    @Mock
    private EmailService emailService;

    @Spy
    private Utils utils;

    @InjectMocks
    private EmailConsumer emailConsumer;

    @Value("${queue.name}")
    private String queue;

    @Test
    void listen() throws JsonProcessingException {
        String payload = "{\"toEmail\": \"example@example.com\"}";

        var emailRequest = new EmailRequest();
        emailRequest.setToEmail("example@example.com");

        emailConsumer.listen(payload);

        verify(emailService).sendChangedRegistrationMessage(any(EmailModel.class));
    }

}