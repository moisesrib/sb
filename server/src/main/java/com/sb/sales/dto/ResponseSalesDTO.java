package com.sb.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sb.customer.model.Customer;
import com.sb.sales.model.Sales;

public record ResponseSalesDTO(
        UUID id,
        BigDecimal total,
        LocalDateTime date,
        BigDecimal discount,
        String paymentMethod,
        String observation,
        String status,
        Customer customer,
        List<ResponseSalesItemDTO> items
) {
    public ResponseSalesDTO(Sales sale) {
        this(
            sale.getId(),
            sale.getTotal(),
            sale.getDate(),
            sale.getDiscount(),
            sale.getPaymentMethod(),
            sale.getObservation(),
            sale.getStatus().toString(),
            sale.getCustomer(),
            sale.getItems().stream()
                .map(ResponseSalesItemDTO::new)
                .collect(Collectors.toList())
        );
    }
}
