package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.framework.exception.MessageCouldNotBeSentToQueueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static br.com.compassuol.pb.challenge.msproducts.utils.UserUtil.userDefault;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherEmailServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PublisherEmailService publisherEmailService;

    @Test
    void sendChangedRegistrationMessage() {
        var user = userDefault();

        publisherEmailService.sendChangedRegistrationMessage(user);

        verify(rabbitTemplate).convertAndSend(eq("Direct-Exchange"), eq("email"), anyString());
    }

    @Test
    public void sendChangedRegistrationMessageErrorMessageCouldNotBeSentToQueueException() {
        var user = userDefault();

        doThrow(new RuntimeException()).when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());

        assertThrows(MessageCouldNotBeSentToQueueException.class, () -> publisherEmailService.sendChangedRegistrationMessage(user));
    }

}