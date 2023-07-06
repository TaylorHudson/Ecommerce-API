package br.com.compassuol.pb.challenge.msnotification;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendChangedRegistrationMessage(EmailModel emailModel) {
        try{
            var message = new SimpleMailMessage();
            message.setFrom(emailModel.getFromEmail());
            message.setTo(emailModel.getTo());
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
