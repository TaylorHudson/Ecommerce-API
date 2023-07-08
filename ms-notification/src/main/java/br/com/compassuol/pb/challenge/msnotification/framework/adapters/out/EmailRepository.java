package br.com.compassuol.pb.challenge.msnotification.framework.adapters.out;

import br.com.compassuol.pb.challenge.msnotification.domain.model.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel, Long> {

}
