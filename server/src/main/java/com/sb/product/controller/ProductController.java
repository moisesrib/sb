package com.sb.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.infra.security.role.Authentication;
import com.sb.product.dto.CreateRequestDTO;
import com.sb.product.dto.ProductReponseDTO;
import com.sb.product.model.Product;
import com.sb.product.service.ProductService;
import com.sb.user.enums.RoleEnum;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    @Authentication(role = RoleEnum.MANAGER)
    public ResponseEntity<ProductReponseDTO> create(@RequestBody @Valid CreateRequestDTO data) {
        ProductReponseDTO product = productService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<ProductReponseDTO>> findAll() {
        List<ProductReponseDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("barcode/{barcode}")
    @Transactional
    @Authentication(role = RoleEnum.SELLER)
    public ResponseEntity<ProductReponseDTO> findById(@PathVariable("barcode") String barcode) {
        ProductReponseDTO product = productService.findById(barcode);
        return ResponseEntity.ok(product);
    }
}
