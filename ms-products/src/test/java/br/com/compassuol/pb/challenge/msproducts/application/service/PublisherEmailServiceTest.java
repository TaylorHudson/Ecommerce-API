package br.com.compassuol.pb.challenge.msproducts.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

import static br.com.compassuol.pb.challenge.msproducts.utils.UserUtil.userDefault;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherEmailServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PublisherEmailService publisherEmailService;

    @Value("${exchange.name}")
    private String exchange;

    @Value("${routing.key}")
    private String routingKey;

    @Test
    void sendChangedRegistrationMessage() {
        var user = userDefault();

        publisherEmailService.sendChangedRegistrationMessage(user);

        verify(rabbitTemplate).convertAndSend(eq(exchange), eq(routingKey), anyString());
    }

    @Test
    public void sendChangedRegistrationMessageErrorMessageCouldNotBeSentToQueueException() {
        var user = userDefault();

        doThrow(new RuntimeException()).when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());

        assertThrows(MessageCouldNotBeSentToQueueException.class, () -> publisherEmailService.sendChangedRegistrationMessage(user));
    }

}