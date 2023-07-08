package br.com.compassuol.pb.challenge.msnotification.domain.producer;

import br.com.compassuol.pb.challenge.msnotification.domain.dto.request.EmailRequest;
import br.com.compassuol.pb.challenge.msnotification.framework.exception.MessageCouldNotBeSentToQueueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EmailProducer emailProducer;

    @Value("${exchange.name}")
    private String exchange;

    @Value("${routing.key}")
    private String routingKey;

    @Test
    void sendChangedRegistrationMessageToQueue() {
        var request = EmailRequest.builder().toEmail("john.doe@gmail.com").build();

        emailProducer.sendChangedRegistrationMessageToQueue(request);

        verify(rabbitTemplate).convertAndSend(eq(exchange), eq(routingKey), anyString());

    }

    @Test
    public void sendChangedRegistrationMessageToQueueErrorMessageCouldNotBeSentToQueueException() {
        var request = EmailRequest.builder().toEmail("john.doe@gmail.com").build();

        doThrow(new RuntimeException()).when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());

        assertThrows(MessageCouldNotBeSentToQueueException.class, () -> emailProducer.sendChangedRegistrationMessageToQueue(request));
    }

}