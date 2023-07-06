package br.com.compassuol.pb.challenge.msauth.domain.dto.response;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailResponse implements Serializable {

    private String replyTo;
    private String to;

}
