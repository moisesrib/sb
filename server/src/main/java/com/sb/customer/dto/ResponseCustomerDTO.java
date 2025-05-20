package com.sb.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sb.customer.model.Customer;

public record ResponseCustomerDTO(
    UUID id,
    String name,
    String email,
    String phone,
    String address,
    String document,
    String observation,
    Boolean active,
    LocalDateTime createdAt
) {
    public ResponseCustomerDTO(Customer customer) {
        this(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress(), customer.getDocument(), customer.getObservation(), customer.getActive(), customer.getCreatedAt());
    }
}
