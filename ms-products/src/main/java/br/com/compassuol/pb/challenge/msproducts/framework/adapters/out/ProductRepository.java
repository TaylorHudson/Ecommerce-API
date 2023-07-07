package br.com.compassuol.pb.challenge.msproducts.framework.adapters.out;

import br.com.compassuol.pb.challenge.msproducts.domain.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

}
