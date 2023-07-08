package br.com.compassuol.pb.challenge.msnotification.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_email")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fromEmail;

    private String fromName;

    private String replyTo;

    private String toEmail;

    private String subject;

    private String body;

    private String contentType;

}
