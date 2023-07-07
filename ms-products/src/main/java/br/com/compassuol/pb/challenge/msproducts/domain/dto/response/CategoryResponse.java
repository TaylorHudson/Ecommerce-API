package br.com.compassuol.pb.challenge.msproducts.domain.dto.response;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {
    private long id;
    private String name;
}
