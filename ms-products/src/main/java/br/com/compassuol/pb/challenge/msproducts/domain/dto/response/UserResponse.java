package br.com.compassuol.pb.challenge.msproducts.domain.dto.response;

import br.com.compassuol.pb.challenge.msproducts.domain.model.UserModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
