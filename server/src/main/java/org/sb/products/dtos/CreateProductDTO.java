package org.sb.products.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateProductDTO(
        @NotBlank(message = "O nome do produto é obrigatório.")
        String name,

        @NotBlank(message = "A descrição do produto é obrigatória.")
        String description,

        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal price,

        BigDecimal promotional,

        BigDecimal cost,
        
        @Positive(message = "O estoque deve ser maior que zero.")
        Integer stock,

        @NotNull(message = "O status do produto é obrigatório.")
        Boolean active
        
){}
