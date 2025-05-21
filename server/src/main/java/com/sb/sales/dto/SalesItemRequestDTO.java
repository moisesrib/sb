package com.sb.sales.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.sb.product.enums.ProductStatusEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesItemRequestDTO {

    @NotNull(message = "Produto é obrigatório")
    private UUID productId;

    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantity;

    @NotNull(message = "Valor unitário é obrigatório")
    private BigDecimal unitPrice;

    @NotNull(message = "Status é obrigatório")
    private ProductStatusEnum status;
}
