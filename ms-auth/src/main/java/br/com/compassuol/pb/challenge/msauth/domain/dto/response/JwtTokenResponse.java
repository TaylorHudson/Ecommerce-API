package br.com.compassuol.pb.challenge.msauth.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtTokenResponse {

    private String token;

    private String tokenType;

}
