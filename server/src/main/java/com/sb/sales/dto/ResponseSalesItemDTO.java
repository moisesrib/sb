package com.sb.sales.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.sb.product.dto.ProductBasicDTO;
import com.sb.sales.model.SalesItem;

public record ResponseSalesItemDTO(
        UUID id,
        ProductBasicDTO product,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal,
        String status
) {
    public ResponseSalesItemDTO(SalesItem item) {
        this(
            item.getId(),
            new ProductBasicDTO(item.getProduct()),
            item.getQuantity(),
            item.getUnitPrice(),
            item.getSubtotal(),
            item.getStatus().toString()
        );
    }
}
