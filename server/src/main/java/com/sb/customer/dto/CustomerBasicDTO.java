package com.sb.customer.dto;

import java.util.UUID;

import com.sb.customer.model.Customer;

public record CustomerBasicDTO(
    UUID id,
    String name,
    String email,
    String phone,
    String address,
    String document,
    String observation,
    Boolean active
) {
    public CustomerBasicDTO(Customer customer) {
        this(
            customer.getId(),
            customer.getName(),
            customer.getEmail(),
            customer.getPhone(),
            customer.getAddress(),
            customer.getDocument(),
            customer.getObservation(),
            customer.getActive()
        );
    }
} 