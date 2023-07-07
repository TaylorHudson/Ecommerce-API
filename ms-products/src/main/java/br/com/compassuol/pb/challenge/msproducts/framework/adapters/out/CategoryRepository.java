package br.com.compassuol.pb.challenge.msproducts.framework.adapters.out;

import br.com.compassuol.pb.challenge.msproducts.domain.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    Optional<CategoryModel> findByName(String name);

}
