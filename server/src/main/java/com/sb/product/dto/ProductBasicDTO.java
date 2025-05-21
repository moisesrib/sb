package com.sb.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.sb.product.model.Product;

public record ProductBasicDTO(
    UUID id,
    String name,
    String barcode,
    BigDecimal price
) {
    public ProductBasicDTO(Product product) {
        this(
            product.getId(),
            product.getName(),
            product.getBarcode(),
            product.getPrice()
        );
    }
} 