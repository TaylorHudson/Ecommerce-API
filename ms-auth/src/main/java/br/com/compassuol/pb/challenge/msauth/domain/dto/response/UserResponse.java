package br.com.compassuol.pb.challenge.msauth.domain.dto.response;

import br.com.compassuol.pb.challenge.msauth.domain.model.UserModel;
import lombok.*;

@Getter
@Setter
@Builder
public class UserResponse {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    public static class UserResponseBuilder {

        public UserResponseBuilder user(UserModel user) {
            this.id = user.getId();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.email = user.getEmail();

            return this;
        }

    }

}
