package br.com.compassuol.pb.challenge.msnotification.application.service;

import br.com.compassuol.pb.challenge.msnotification.domain.dto.request.EmailRequest;
import br.com.compassuol.pb.challenge.msnotification.domain.model.EmailModel;
import br.com.compassuol.pb.challenge.msnotification.domain.producer.EmailProducer;
import br.com.compassuol.pb.challenge.msnotification.domain.utils.Utils;
import br.com.compassuol.pb.challenge.msnotification.framework.adapters.out.EmailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailProducer emailProducer;

    @Spy
    private Utils utils;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendChangedRegistrationMessage() {
        var emailModel = emailModelDefault();

        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        emailService.sendChangedRegistrationMessage(emailModel);

        verify(emailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendChangedRegistrationMessageError() {
        var emailModel = emailModelDefault();

        doThrow(MailSendException.class).when(emailSender).send(any(SimpleMailMessage.class));

        assertThrows(RuntimeException.class, () -> emailService.sendChangedRegistrationMessage(emailModel));
    }

    private EmailModel emailModelDefault() {
        return EmailModel.builder()
                .fromEmail("john.doe@gmail.com")
                .fromName("John Doe")
                .toEmail("jose.santos@gmail.com")
                .build();
    }

}