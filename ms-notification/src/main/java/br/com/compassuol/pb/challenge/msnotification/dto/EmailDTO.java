package br.com.compassuol.pb.challenge.msnotification.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailDTO implements Serializable {

    private String replyTo;
    private String to;

}
