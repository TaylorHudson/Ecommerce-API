package br.com.compassuol.pb.challenge.msproducts.domain.model;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.ProductRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tb_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private String imgUrl;

    private double price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<CategoryModel> categories;

    public static class ProductModelBuilder {

        public ProductModelBuilder request(ProductRequest request) {
            this.name = request.getName();
            this.description = request.getDescription();
            this.price = request.getPrice();
            this.imgUrl = request.getImgUrl();

            return this;
        }

    }
}
