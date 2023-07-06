package br.com.compassuol.pb.challenge.msproducts.framework.adapters.out;


import br.com.compassuol.pb.challenge.msproducts.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
