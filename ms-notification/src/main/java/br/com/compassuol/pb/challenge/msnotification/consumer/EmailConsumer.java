package br.com.compassuol.pb.challenge.msnotification.consumer;

import br.com.compassuol.pb.challenge.msnotification.EmailModel;
import br.com.compassuol.pb.challenge.msnotification.EmailService;
import br.com.compassuol.pb.challenge.msnotification.dto.EmailDTO;
import br.com.compassuol.pb.challenge.msnotification.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = {"${queue.name}"})
    public void listen(@Payload String string) throws JsonProcessingException {
        var emailDto = Utils.mapToClass(string, EmailDTO.class);
        var emailModel = createEmailModel(emailDto);
        emailService.sendChangedRegistrationMessage(emailModel);
    }

    private EmailModel createEmailModel(EmailDTO emailDTO) {
        return EmailModel.builder()
                .to(emailDTO.getTo())
                .replyTo(emailDTO.getReplyTo())
                .fromEmail("challengethreecompass@gmail.com")
                .fromName("Challenge")
                .subject("Notificação de Alteração de Dados")
                .body(getUpdateMessage(emailDTO.getTo()))
                .contentType("application/json")
                .build();
    }

    private String getUpdateMessage(String email) {
        return "Gostaríamos de informar que houve uma alteração nos seus dados cadastrais em nosso sistema.\n" +
                "Agradecemos por nos manter atualizados!\n\n" +
                "Detalhes da alteração:\n" +
                "E-mail: " + email +
                "\nSe você não reconhece essa alteração ou acredita que tenha ocorrido algum erro, entre em contato conosco imediatamente para que possamos verificar e corrigir qualquer problema. " +
                "Estamos à disposição para ajudar e responder a quaisquer perguntas adicionais que você possa ter.\n\n" +
                "Atenciosamente,\n" +
                "Challenge 3 - Compass";
    }

}