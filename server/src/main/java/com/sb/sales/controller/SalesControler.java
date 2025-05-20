package com.sb.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.infra.security.role.Authentication;
import com.sb.sales.dto.CreateSalesRequestDTO;
import com.sb.sales.dto.ResponseSalesDTO;
import com.sb.sales.service.SalesService;
import com.sb.user.enums.RoleEnum;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SalesControler {

    @Autowired
    private SalesService salesService;
    
    @PostMapping
    @Transactional
    @Authentication(role = RoleEnum.SELLER)
    public ResponseEntity<ResponseSalesDTO> create(@RequestBody @Valid CreateSalesRequestDTO sales) {
        ResponseSalesDTO sale = salesService.create(sales);
        return ResponseEntity.ok(sale);
    }
}
