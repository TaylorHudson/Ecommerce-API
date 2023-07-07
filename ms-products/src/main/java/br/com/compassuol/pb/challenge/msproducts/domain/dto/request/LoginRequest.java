package br.com.compassuol.pb.challenge.msproducts.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8,message = "The password must contain at least 8 characters")
    private String password;

}
