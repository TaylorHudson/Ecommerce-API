package br.com.compassuol.pb.challenge.msproducts.domain.dto.response;

import br.com.compassuol.pb.challenge.msproducts.domain.model.ProductModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class ProductResponse {

    private long id;

    private String name;

    private String description;

    private String imgUrl;

    private double price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    private Set<Long> categories;

    public static class ProductResponseBuilder {

        public ProductResponseBuilder product(ProductModel product) {
            this.id = product.getId();
            this.name = product.getName();
            this.description = product.getDescription();
            this.price = product.getPrice();
            this.imgUrl = product.getImgUrl();
            this.date = product.getDate();

            return this;
        }

    }

}
