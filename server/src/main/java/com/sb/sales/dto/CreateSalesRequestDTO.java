package com.sb.sales.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSalesRequestDTO {

    @NotNull(message = "Cliente é obrigatório")
    private UUID customerId;

    @NotNull(message = "Desconto é obrigatório")
    private BigDecimal discount;

    @NotEmpty(message = "Método de pagamento é obrigatório")
    private String paymentMethod;

    @NotEmpty(message = "Observação é obrigatória")
    private String observation;

    @NotEmpty(message = "Status é obrigatório")
    private String status;

    @NotEmpty(message = "Lista de itens não pode estar vazia")
    private List<SalesItemRequestDTO> items;
}
