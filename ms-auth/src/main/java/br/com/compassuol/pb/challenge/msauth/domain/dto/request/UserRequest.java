package br.com.compassuol.pb.challenge.msauth.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserRequest {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @Email
    @NotNull
    @NotEmpty
    private String email;

    @Min(8)
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private Set<Long> roles;

}