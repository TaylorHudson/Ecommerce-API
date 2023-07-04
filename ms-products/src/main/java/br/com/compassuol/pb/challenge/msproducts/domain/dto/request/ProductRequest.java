package br.com.compassuol.pb.challenge.msproducts.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class ProductRequest {

    private String name;

    private String description;

    private String imgUrl;

    private double price;

    private Set<Long> categories;

}
