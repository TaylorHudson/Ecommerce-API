package br.com.compassuol.pb.challenge.msauth;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse implements Serializable {

    private long id;
    private String email;
    private String token;

}
