package br.com.compassuol.pb.challenge.msproducts.utils;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.ProductRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.ProductResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.CategoryModel;
import br.com.compassuol.pb.challenge.msproducts.domain.model.ProductModel;

import java.util.HashSet;

import static br.com.compassuol.pb.challenge.msproducts.utils.CategoryUtil.categoryDefault;

public class ProductUtil {

    public static ProductModel productDefault() {
        var categories = new HashSet<CategoryModel>();
        categories.add(categoryDefault());

        return ProductModel.builder()
                .name("Product test")
                .description("Description test")
                .price(12.5)
                .categories(categories)
                .build();
    }

    public static ProductRequest productRequestDefault() {
        var categories = new HashSet<Long>();
        categories.add(1L);

        return ProductRequest.builder()
                .name("Product test")
                .description("Description test")
                .categories(categories)
                .price(12.5)
                .build();
    }

    public static ProductResponse productResponseDefault() {
        var categories = new HashSet<Long>();
        categories.add(1L);

        return ProductResponse.builder()
                .name("Product test")
                .description("Description test")
                .categories(categories)
                .price(12.5)
                .build();
    }

}
