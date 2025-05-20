package com.sb.sales.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.sb.product.model.Product;
import com.sb.sales.model.SalesItem;

public record ResponseSalesItemDTO(
        UUID id,
        Product product,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal,
        String status
) {
    public ResponseSalesItemDTO(SalesItem item) {
        this(
            item.getId(),
            item.getProduct(),
            item.getQuantity(),
            item.getUnitPrice(),
            item.getSubtotal(),
            item.getStatus().toString()
        );
    }
}
