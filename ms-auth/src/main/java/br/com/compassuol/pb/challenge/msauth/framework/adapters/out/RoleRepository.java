package br.com.compassuol.pb.challenge.msauth.framework.adapters.out;

import br.com.compassuol.pb.challenge.msauth.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
