package br.com.compassuol.pb.challenge.msproducts.domain.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {

    @Size(min = 2, message = "The product name must be at least 2 characters long")
    private String name;

    @Size(min = 5, message = "The product description must be at least 5 characters long")
    private String description;

    private String imgUrl;

    private double price;

    private Set<Long> categories;

}
