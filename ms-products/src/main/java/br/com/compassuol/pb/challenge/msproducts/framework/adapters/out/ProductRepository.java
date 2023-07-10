package br.com.compassuol.pb.challenge.msproducts.framework.adapters.out;

import br.com.compassuol.pb.challenge.msproducts.domain.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Boolean existsByName(String name);
}
