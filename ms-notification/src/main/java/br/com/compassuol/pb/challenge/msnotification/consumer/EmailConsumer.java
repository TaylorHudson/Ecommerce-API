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
    public void listen(@Payload String string) {
        try {
            var emailDto = Utils.mapToClass(string, EmailDTO.class);
            System.out.println(string);
            var emailModel = createEmailModel(emailDto);
            System.out.println(emailModel.getFromEmail());
            emailService.sendChangedRegistrationMessage(emailModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro de conversao");
        }
    }

    private EmailModel createEmailModel(EmailDTO emailDTO) {
        return EmailModel.builder()
                .to(emailDTO.getTo())
                .replyTo(emailDTO.getReplyTo())
                .fromEmail("taylor.hudson@academico.ifpb.edu.br")
                .fromName("Challenge 3")
                .subject("Notificação de Alteração de Dados")
                .body(getUpdateMessage(emailDTO.getTo()))
                .contentType("application/json")
                .build();
    }

    private String getUpdateMessage(String email) {
        var builder = new StringBuilder();
        builder.append("Gostaríamos de informar que houve uma alteração nos seus dados cadastrais em nosso sistema. Agradecemos por nos manter atualizados!");
        builder.append("Detalhes da alteração:");
        builder.append("E-mail: ").append(email);
        builder.append("Se você não reconhece essa alteração ou acredita que tenha ocorrido algum erro, entre em contato conosco imediatamente para que possamos verificar e corrigir qualquer problema.");
        builder.append("Estamos à disposição para ajudar e responder a quaisquer perguntas adicionais que você possa ter.");
        builder.append("Atenciosamente,");
        builder.append("Challenge 3 - Compass");
        return builder.toString();
    }

}