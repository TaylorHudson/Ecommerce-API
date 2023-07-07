package br.com.compassuol.pb.challenge.msproducts.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {
    @Size(min = 5, message = "The category name must contain at least 5 characters")
    private String name;
}
