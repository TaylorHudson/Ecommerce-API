package br.com.compassuol.pb.challenge.msnotification.application.service;

import br.com.compassuol.pb.challenge.msnotification.domain.dto.request.EmailRequest;
import br.com.compassuol.pb.challenge.msnotification.domain.model.EmailModel;
import br.com.compassuol.pb.challenge.msnotification.domain.producer.EmailProducer;
import br.com.compassuol.pb.challenge.msnotification.framework.adapters.out.EmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static br.com.compassuol.pb.challenge.msnotification.domain.utils.EmailUtil.createEmailModel;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private final EmailRepository emailRepository;

    private final EmailProducer emailProducer;

    @Transactional
    public String create(EmailRequest request) {
        var emailModel = createEmailModel(request);
        emailRepository.save(emailModel);
        emailProducer.sendChangedRegistrationMessageToQueue(request);
        return "Email created successfully";
    }

    public void sendChangedRegistrationMessage(EmailModel emailModel) {
        try{
            var message = new SimpleMailMessage();
            message.setFrom(emailModel.getFromEmail());
            message.setTo(emailModel.getToEmail());
            message.setReplyTo(emailModel.getReplyTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getBody());
            message.setSentDate(new Date());
            emailSender.send(message);
        } catch (MailException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
