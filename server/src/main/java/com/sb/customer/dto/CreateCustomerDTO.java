package com.sb.customer.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCustomerDTO(
    @NotBlank(message = "Nome é obrigatório")
    String name,

    @Email(message = "Email inválido")
    String email,

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\+?[1-9][0-9]{10,14}$", message = "Telefone inválido")
    String phone,

    @NotBlank(message = "Endereço é obrigatório")
    String address,

    @NotBlank(message = "Documento é obrigatório")
    @CPF(message = "CPF inválido")
    String document,

    String observation,
    
    Boolean active
) {

}
