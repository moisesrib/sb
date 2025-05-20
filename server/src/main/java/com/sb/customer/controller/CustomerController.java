package com.sb.customer.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.customer.dto.CreateCustomerDTO;
import com.sb.customer.dto.ResponseCustomerDTO;
import com.sb.customer.service.CustomerService;
import com.sb.infra.security.role.Authentication;
import com.sb.user.enums.RoleEnum;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @Transactional
    @Authentication(role = RoleEnum.SELLER)
    public ResponseEntity<ResponseCustomerDTO> create(@RequestBody @Valid CreateCustomerDTO data) {
        ResponseCustomerDTO customer = customerService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping
    @Transactional
    @Authentication(role = RoleEnum.SELLER)
    public ResponseEntity<List<ResponseCustomerDTO>> findAll() {
        List<ResponseCustomerDTO> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }   

    @GetMapping("/{id}")
    @Transactional
    @Authentication(role = RoleEnum.SELLER)
    public ResponseEntity<ResponseCustomerDTO> findById(@PathVariable UUID id) {
        ResponseCustomerDTO customer = customerService.findById(id);
        return ResponseEntity.ok(customer);
    }
}
