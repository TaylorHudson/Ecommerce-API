package br.com.compassuol.pb.challenge.msnotification.domain.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailRequest {

    private String toEmail;

}
