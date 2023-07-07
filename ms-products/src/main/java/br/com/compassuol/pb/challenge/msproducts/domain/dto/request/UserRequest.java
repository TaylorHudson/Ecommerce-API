package br.com.compassuol.pb.challenge.msproducts.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {

    private String firstName;

    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8,message = "The password must contain at least 8 characters")
    private String password;

    private Set<Long> roles;

}