package org.sb.products.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductDTO(
        @NotBlank(message = "O nome do produto é obrigatório.")
        String name,

        @NotBlank(message = "A descrição do produto é obrigatória.")
        String description,

        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal price
){}
