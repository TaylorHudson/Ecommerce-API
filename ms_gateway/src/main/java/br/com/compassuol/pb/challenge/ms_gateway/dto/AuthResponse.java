package br.com.compassuol.pb.challenge.ms_gateway.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private long id;
    private String login;
    private String token;
}
