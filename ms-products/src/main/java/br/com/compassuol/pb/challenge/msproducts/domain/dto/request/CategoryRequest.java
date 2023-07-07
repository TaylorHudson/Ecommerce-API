package br.com.compassuol.pb.challenge.msproducts.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {

    @NotNull
    @NotEmpty
    @Min(5)
    private String name;
}
