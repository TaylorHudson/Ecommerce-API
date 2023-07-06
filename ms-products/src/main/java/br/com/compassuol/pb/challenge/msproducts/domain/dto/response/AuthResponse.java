package br.com.compassuol.pb.challenge.msproducts.domain.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private long id;
    private String email;
    private String token;

}
