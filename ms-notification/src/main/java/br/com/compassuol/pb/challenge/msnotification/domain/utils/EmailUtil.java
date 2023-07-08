package br.com.compassuol.pb.challenge.msnotification.domain.utils;

import br.com.compassuol.pb.challenge.msnotification.domain.dto.request.EmailRequest;
import br.com.compassuol.pb.challenge.msnotification.domain.model.EmailModel;

public class EmailUtil {

    public static EmailModel createEmailModel(EmailRequest emailRequest) {
        return EmailModel.builder()
                .toEmail(emailRequest.getToEmail())
                .replyTo(emailRequest.getToEmail())
                .fromEmail("challengethreecompass@gmail.com")
                .fromName("Challenge")
                .subject("Notificação de Alteração de Dados")
                .body(getUpdateMessage(emailRequest.getToEmail()))
                .contentType("text/plain")
                .build();
    }

    private static String getUpdateMessage(String email) {
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
