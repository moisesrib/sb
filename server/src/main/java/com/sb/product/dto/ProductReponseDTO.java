package com.sb.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.sb.product.model.Product;

public record ProductReponseDTO(
    UUID id,
    String name,
    String description,
    String barcode,
    BigDecimal price,
    BigDecimal promotion,
    BigDecimal cost,
    Integer stock,
    Boolean active,
    LocalDateTime createdAt
) {
    public ProductReponseDTO(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getBarcode(), product.getPrice(), product.getPromotion(), product.getCost(), product.getStock(), product.getActive(), product.getCreatedAt());
    }
}
