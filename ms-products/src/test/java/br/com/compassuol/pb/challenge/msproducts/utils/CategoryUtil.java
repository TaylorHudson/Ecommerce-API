package br.com.compassuol.pb.challenge.msproducts.utils;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.CategoryRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.CategoryResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.CategoryModel;

public class CategoryUtil {

    public static CategoryRequest categoryRequestDefault() {
        return CategoryRequest.builder()
                .name("Category test")
                .build();
    }

    public static CategoryResponse categoryResponseDefault() {
        return CategoryResponse.builder()
                .id(1L)
                .name("Category test")
                .build();
    }

    public static CategoryModel categoryDefault() {
        return CategoryModel.builder()
                .id(1L)
                .name("Category test")
                .build();
    }

}
