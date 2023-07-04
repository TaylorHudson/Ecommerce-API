package br.com.compassuol.pb.challenge.msauth.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class AuthResponse {

    private long id;
    private String email;
    private String token;

}
