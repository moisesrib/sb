package com.sb.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateRequestDTO(
    @NotBlank(message = "Nome é obrigatório")
    String name,
    
    @NotBlank(message = "Descrição é obrigatória") 
    String description,
    
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    BigDecimal price,
    
    @PositiveOrZero(message = "Promoção deve ser maior ou igual a zero")
    BigDecimal promotion,
    
    @PositiveOrZero(message = "Custo deve ser maior ou igual a zero") 
    BigDecimal cost,
    
    @NotNull(message = "Estoque é obrigatório")
    @PositiveOrZero(message = "Estoque deve ser maior ou igual a zero")
    Integer stock,

    @NotNull(message = "Ativo é obrigatório")
    Boolean active
) {}
