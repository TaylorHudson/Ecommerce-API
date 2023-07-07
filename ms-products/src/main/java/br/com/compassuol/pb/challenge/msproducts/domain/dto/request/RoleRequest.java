package br.com.compassuol.pb.challenge.msproducts.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RoleRequest {
    @NotNull
    @NotEmpty
    @Min(8)
    private String name;
}