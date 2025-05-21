package com.sb.sales.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.sb.sales.enums.PaymentMethodEnum;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSalesRequestDTO {

    @NotNull(message = "Cliente é obrigatório")
    private UUID customerId;

    private BigDecimal discount;

    @NotNull(message = "Método de pagamento é obrigatório")
    private PaymentMethodEnum paymentMethod;

    private String observation;

    @NotEmpty(message = "Status é obrigatório")
    private String status;

    @NotEmpty(message = "Lista de itens não pode estar vazia")
    private List<SalesItemRequestDTO> items;
}
