package br.com.compassuol.pb.challenge.msauth.framework.adapters.out;

import br.com.compassuol.pb.challenge.msauth.domain.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

    Boolean existsByEmail(String email);

}
