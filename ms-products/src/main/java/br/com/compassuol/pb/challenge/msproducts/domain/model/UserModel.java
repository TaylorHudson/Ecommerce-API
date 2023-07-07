package br.com.compassuol.pb.challenge.msproducts.domain.model;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.UserRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    public static class UserModelBuilder {

        public UserModelBuilder request(UserRequest request) {
            this.firstName = request.getFirstName();
            this.lastName = request.getLastName();
            this.email = request.getEmail();

            return this;
        }

    }

}
